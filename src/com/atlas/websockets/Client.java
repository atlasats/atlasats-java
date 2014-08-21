package com.atlas.websockets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	public Client () {
		log = LoggerFactory.getLogger (getClass ());
		jettyWSClient = new WebSocketClient ();
	}

	// AtlasClient
	
	public void connect () {
		try {
			jettyWSClient.start ();
			jettyWSClient.connect (new Socket (), new URI (uri));
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public void subscribeAccountData (AccountUpdateListener listener, Subscription sub) {

	}

	// AtlasClient (END)
	
	private String uri;
	private String key;
	private String secret;

	private WebSocketClient jettyWSClient;
	private Logger log;

}
