package com.atlas.websockets;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebSocket
public class Client {

	public Client () {
		log = LoggerFactory.getLogger (getClass ());
		jettyWSClient = new WebSocketClient ();
		state = ConnectionState.DISCONNECTED;
		extensions = new LinkedList<BayeuxExtension> ();
		extensions.add (new BayeuxExtensionNoAuth ());
		extensions.add (new BayeuxExtensionAccountID ());
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

	// @WebSocket

	@OnWebSocketClose
	public void onClose (int statusCode, String reason) {
		log.info ("connection closed: " + reason);
		session = null;
		state = ConnectionState.DISCONNECTED;
	}

	@OnWebSocketConnect
	public void onConnect (Session session) {
		log.info ("connected: " + session);
		this.session = session;
		handshake ();
	}
	
	@OnWebSocketMessage
	public void onMessage (String jsonString) {
		Collection<BayeuxMessage> messages = BayeuxMessageFactory.create (jsonString);
		for (BayeuxMessage message : messages) {
			if (!preprocess (message)) continue;
			log.debug ("processing in-message: " + message.getData ());
			switch (message.getType ()) {
			case ERROR:
				log.info ("error: " + message.getError ());
				break;
			case HANDSHAKE:
				state = ConnectionState.CONNECTED;
				synchronized (extensions) {
					BayeuxExtension ext = new BayeuxExtensionClientId (message.getClientId ());
					if (extensions.contains (ext)) extensions.remove (ext);
					extensions.add (ext);
				}
				break;
			case SUBSCRIPTION:
				log.info ("subscribed: " + message.getData ());
				break;
			default:
				process (message);
				break;
			}
		}
	}

	// @WebSocket (END)

	// Configuration

	public void setUri (String uri) {
		this.uri = uri;
	}

	public void setAPIToken (String token) {
		if (state != ConnectionState.DISCONNECTED) throw new AtlasAPIException ("set API token/secret before connecting");
		this.apiToken = token;
		setExtensions ();
	}

	public void setAPISecret (String secret) {
		if (state != ConnectionState.DISCONNECTED) throw new AtlasAPIException ("set API token/secret before connecting");
		this.apiSecret = secret;
		setExtensions ();
	}

	public void setAccountNumber (int accountNumber) {
		this.account = accountNumber;
	}
	
	// Configuration (END)

	// Business

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
		if (subscribe (new AccountSubscription (account))) {
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
		if (subscribe (new OrderSubscription (account))) {
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
		if (subscribe (new StatefulSubscription (account))) {
			synchronized (statefulListeners) {
				if (!statefulListeners.contains (listener)) {
					statefulListeners.add (listener);
				}
			}
			return true;
		}
		return false;
	}

	public boolean place (Order order) {
		if (!order.validate ()) {
			lastError = order.getError ();
			return false;
		}
		order.setAccount (account);
		return send (order);
	}
	
	public boolean cancel (String orderId) {
		return true;
	}
	
	public String getLastError () {
		return lastError;
	}
	
	// Business (END)

	// private 
	
	private boolean subscribe (Subscription subscription) {
		if (state == ConnectionState.CONNECTED) {
			return send (subscription);
		}
		return false;
	}

	private boolean handshake () {
		if (state == ConnectionState.DISCONNECTED && send (new Handshake ())) {
			state = ConnectionState.CONNECTING;
			return true;
		}
		log.warn ("invalid state for handshake: " + state);
		return false;
	}

	private boolean preprocess (BayeuxMessage message) {
		synchronized (extensions) {
			for (BayeuxExtension ext : extensions) {
				if (!ext.incoming (message)) {
					log.info (ext + " discarding in-message " + message);
					return false;
				}
			}
		}
		return true;
	}

	private void process (BayeuxMessage message) {
		if (message.getChannel ().equals (Channels.MARKET)) {
			for (MarketDataListener l : marketListeners) {
				l.book (message.getData ());
			}
		} else if (message.getChannel ().equals (Channels.LEVEL1)) {
			for (MarketDataListener l : marketListeners) {
				l.level1 (message.getData ());
			}
		} else if (message.getChannel ().equals (Channels.TRADES)) {
			for (MarketDataListener l : marketListeners) {
				l.trade (message.getData ());
			}
		} else if (message.getChannel ().equals (Channels.ACCOUNT)) {
			for (AccountListener l : accountListeners) {
				l.update (message.getData ());
			}
		} else if (message.getChannel ().equals (Channels.ORDERS)) {
			for (OrderListener l : orderListeners) {
				l.update (message.getData ());
			}
		} else if (message.getChannel ().equals (Channels.STATEFUL)) {
			for (StatefulOrderListener l : statefulListeners) {
				
			}
		}
	}
	
	private boolean send (OutMessage message) {
		if (session == null) {
			log.error ("no session");
		} else if (!session.isOpen ()) {
			log.error ("invalid session state");
		} else {
			// process extensions
			synchronized (extensions) {
				for (BayeuxExtension ext : extensions) {
					if (!ext.outgoing (message)) {
						log.info (ext + " discarded " + message);
						return false;
					}
				}
			}
			log.info ("sending: " + message.toJSON ());
			Future<Void> fut = session.getRemote ().sendStringByFuture (message.toJSON ());
			return true;
		}
		return false;
	}
	
	private void setExtensions () {
		// HMAC/SHA256 authentication
		if (apiToken != null && apiSecret != null) {
			BayeuxExtension ext = new BayeuxExtensionHMACAuthenticate (apiToken, apiSecret);
			synchronized (extensions) {
				BayeuxExtension defNoAuth = new BayeuxExtensionNoAuth ();
				if (extensions.contains (defNoAuth)) extensions.remove (defNoAuth);
				if (!extensions.contains (ext)) extensions.add (ext);
			}
		}
	}
	
	// config/credentials
	private int	account;
	private String uri;
	private String apiToken;
	private String apiSecret;
	
	private String lastError;
	
	private WebSocketClient jettyWSClient;

	private Session session;
	private Collection<BayeuxExtension> extensions;

	private ConnectionState state;

	// keep track of our users
	private Collection<AccountListener> accountListeners;
	private Collection<OrderListener> orderListeners;
	private Collection<StatefulOrderListener> statefulListeners;
	private Collection<MarketDataListener> marketListeners;

	private Logger log;
}
