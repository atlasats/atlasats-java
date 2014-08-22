package com.atlas.websockets;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlas.account.AccountListener;
import com.atlas.marketdata.MarketDataListener;
import com.atlas.orders.OrderListener;
import com.atlas.orders.stateful.StatefulOrderListener;

public class Client {

	public Client () {
		log = LoggerFactory.getLogger (getClass ());
		jettyWSClient = new WebSocketClient ();
		socket = new Socket ();
		state = FayeState.DISCONNECTED;
		clientId = "";
		subscriptionsQueue = new LinkedList<Subscription> ();
		accountListeners = new LinkedList<AccountListener> ();
		orderListeners = new LinkedList<OrderListener> ();
		statefulListeners = new LinkedList<StatefulOrderListener> ();
		marketListeners = new LinkedList<MarketDataListener> ();
	}

	public void connect () {
		try {
			jettyWSClient.start ();
			jettyWSClient.connect (socket, new URI (uri));
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}

	public void setUri (String uri) {
		this.uri = uri;
	}

	public void setKey (String key) {
		this.key = key;
	}

	public void setSecret (String secret) {
		this.secret = secret;
	}

	public void subscribe (MarketDataListener listener, Collection<Subscription> subscriptions) {
		synchronized (marketListeners) {
			if (!marketListeners.contains (listener)) {
				marketListeners.add (listener);
			}
		}
	}

	public void subscribe (AccountListener listener) {
		synchronized (accountListeners) {
			if (!accountListeners.contains (listener)) {
				accountListeners.add (listener);
			}
		}
	}
	
	public void subscribe (OrderListener listener) {
		synchronized (orderListeners) {
			if (!orderListeners.contains (listener)) {
				orderListeners.add (listener);
			}
		}
	}
	
	public void subscribe (StatefulOrderListener listener) {
		synchronized (statefulListeners) {
			if (!statefulListeners.contains (listener)) {
				statefulListeners.add (listener);
			}
		}
	}
	
	private void subscribe (Subscription subscription) {
		if (state != FayeState.CONNECTED) {
			synchronized (subscriptionsQueue) {
				subscriptionsQueue.add (subscription);
			}
		} else {
//			socket.send (message)
		}
	}

	private String uri;
	private String key;
	private String secret;

	private WebSocketClient jettyWSClient;
	private Socket socket;
	private Logger log;

	private FayeState state;
	private String clientId;
	
	// keep track of our users
	private Collection<AccountListener> accountListeners;
	private Collection<OrderListener> orderListeners;
	private Collection<StatefulOrderListener> statefulListeners;
	private Collection<MarketDataListener> marketListeners;
	
	private IFayeListener listener;
	
	private Collection<BayeuxExtension> extensions;
	
	private Queue<Subscription> subscriptionsQueue;

	private enum FayeState
	{
		DISCONNECTED,
		HANDSHAKE,
		CONNECTED
	}

	public interface IFayeListener 	{
		void OnMessage (String channel, String data);
	}

}
