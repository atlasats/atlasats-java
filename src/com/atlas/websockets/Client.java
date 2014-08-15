package com.atlas.websockets;


public class Client {

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

	public void subscribe (Subscription sub) {
		switch (sub) {
			
		}
	}
	
	
	private String uri;
	private String key;
	private String secret;
}
