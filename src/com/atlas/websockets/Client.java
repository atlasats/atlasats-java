package com.atlas.websockets;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlas.account.AccountListener;
import com.atlas.common.Message;
import com.atlas.common.Handshake;
import com.atlas.marketdata.MarketDataListener;
import com.atlas.orders.OrderListener;
import com.atlas.orders.stateful.StatefulOrderListener;

@WebSocket
public class Client {

	public Client () {
		log = LoggerFactory.getLogger (getClass ());
		jettyWSClient = new WebSocketClient ();
		closeLatch = new CountDownLatch (1);
		state = ConnectionState.DISCONNECTED;
		extensions = new LinkedList<BayeuxExtension> ();
		accountListeners = new LinkedList<AccountListener> ();
		orderListeners = new LinkedList<OrderListener> ();
		statefulListeners = new LinkedList<StatefulOrderListener> ();
		marketListeners = new LinkedList<MarketDataListener> ();
	}

	public void connect () {
		try {
			jettyWSClient.start ();
			jettyWSClient.connect (this, new URI (uri));
			while (state != ConnectionState.CONNECTED) {
				Thread.sleep (10);
			}
		} catch (Exception e) {
			log.error ("error connecting to WS server", e);
		}
	}

	public void addExtension (BayeuxExtension ext) {
		extensions.add (ext);
	}

	public boolean awaitClose (int duration, TimeUnit unit) throws InterruptedException {
		return closeLatch.await (duration, unit);
	}

	// @WebSocket

	@OnWebSocketClose
	public void onClose (int statusCode, String reason) {
		log.info ("connection closed: " + reason);
		session = null;
		state = ConnectionState.DISCONNECTED;
		closeLatch.countDown ();
	}

	@OnWebSocketConnect
	public void onConnect (Session session) {
		log.info ("connected: " + session);
		this.session = session;
		handshake ();
	}

	@OnWebSocketMessage
	public void onMessage (String jsonString) {
		Collection<Message> messages = JSON.parse (jsonString);
		for (Message message : messages) {
			for (BayeuxExtension ext : extensions) {
				if (!ext.incoming (message)) {
					log.info (ext + " discarded in-message " + message);
					return;
				}
			}
			log.debug ("processing in-message: " + message);
			switch (message.getType ()) {
			case HANDSHAKE:
				clientId = ((Handshake) message).getClientId ();
				if (clientId == null) {
					log.warn ("no clientId in handshake");
				} else {
					state = ConnectionState.CONNECTED;
				}
				break;
			case SUBSCRIPTION:
				log.info (message.toString ());
				break;
			default:
				log.warn ("unknown in-message type: " + message.getType ());
				break;
			}
		}
	}

	// @WebSocket (END)

	// Configuration

	public void setUri (String uri) {
		this.uri = uri;
	}

	public void setKey (String key) {
		this.key = key;
	}

	public void setSecret (String secret) {
		this.secret = secret;
	}

	// Configuration (END)

	// Subscribe

	public boolean subscribe (MarketDataListener listener, Collection<Subscription> subscriptions) {
		boolean success = true;
		for (Subscription sub : subscriptions) {
			if (!subscribe (sub)) success = false;
		}
		if (success) {
			synchronized (marketListeners) {
				if (!marketListeners.contains (listener)) {
					marketListeners.add (listener);
				}
			}
		}
		return success;
	}

	public boolean subscribe (AccountListener listener) {
		if (subscribe (new AccountSubscription (key, secret))) {
			synchronized (accountListeners) {
				if (!accountListeners.contains (listener)) {
					accountListeners.add (listener);
				}
			}
			return true;
		}
		return false;
	}

	public boolean subscribe (OrderListener listener) {
		if (subscribe (new OrderSubscription (key, secret))) {
			synchronized (orderListeners) {
				if (!orderListeners.contains (listener)) {
					orderListeners.add (listener);
				}
			}
			return true;
		}
		return false;
	}

	public boolean subscribe (StatefulOrderListener listener) {
		if (subscribe (new StatefulSubscription (key, secret))) {
			synchronized (statefulListeners) {
				if (!statefulListeners.contains (listener)) {
					statefulListeners.add (listener);
				}
			}
			return true;
		}
		return false;
	}

	// Subscribe (END)

	private boolean subscribe (Subscription subscription) {
		if (state == ConnectionState.CONNECTED) {
			subscription.setClientId (clientId);
			return send (subscription);
		}
		return false;
	}

	private boolean handshake () {
		if (state == ConnectionState.DISCONNECTED && send (new com.atlas.websockets.Handshake ())) {
			state = ConnectionState.CONNECTING;
			return true;
		}
		log.warn ("invalid state for handshake: " + state);
		return false;
	}

	private boolean send (OutMessage message) {
		if (session == null) {
			log.error ("no session");
		} else if (!session.isOpen ()) {
			log.error ("invalid session state");
		} else {
			// process extensions
			for (BayeuxExtension ext : extensions) {
				if (!ext.outgoing (message)) {
					log.info (ext + " discarded " + message);
					return false;
				}
			}
			log.info (message.toString ());
			Future<Void> fut = session.getRemote ().sendStringByFuture (message.toJSON ());
			return true;
		}
		return false;
	}

	// config/credentials
	private String uri;
	private String key;
	private String secret;

	private WebSocketClient jettyWSClient;

	private final CountDownLatch closeLatch;
	private Session session;
	private Collection<BayeuxExtension> extensions;

	private ConnectionState state;
	private String clientId;

	// keep track of our users
	private Collection<AccountListener> accountListeners;
	private Collection<OrderListener> orderListeners;
	private Collection<StatefulOrderListener> statefulListeners;
	private Collection<MarketDataListener> marketListeners;

	private Logger log;
}
