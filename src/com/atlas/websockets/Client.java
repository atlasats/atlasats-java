package com.atlas.websockets;


public class Client implements WSEventHandler {

	public void connect () {
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

	@Override
	public void sendMessage () {
		// TODO Auto-generated method stub
		
	}

	public void subscribeMarketData (MarketDataListener listener, Subscription sub) {
		switch (sub) {
			
		}
	}
	
	public void subscribeAccountData (AccountUpdateListener listener, Subscription sub) {
		
	}
	
	private String uri;
	private String key;
	private String secret;
}
