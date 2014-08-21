package com.atlas.websockets;

import java.net.URI;

import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlas.account.AccountListener;
import com.atlas.marketdata.MarketDataListener;

public class Client {

	public Client () {
		log = LoggerFactory.getLogger (getClass ());
		jettyWSClient = new WebSocketClient ();
	}

	public void connect () {
		try {
			jettyWSClient.start ();
			jettyWSClient.connect (new Socket (), new URI (uri));
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

	public void subscribeMarketData (MarketDataListener listener, Subscription sub) {
		switch (sub) {

		}
	}

	public void subscribeAccountData (AccountListener listener, Subscription sub) {

	}

	private String uri;
	private String key;
	private String secret;

	private WebSocketClient jettyWSClient;
	private Logger log;

}
