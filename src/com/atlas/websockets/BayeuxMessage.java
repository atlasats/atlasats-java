package com.atlas.websockets;

class BayeuxMessage {

	public BayeuxMessageType getType () {
		return type;
	}

	public void setType (BayeuxMessageType type) {
		this.type = type;
	}

	public String getRaw () {
		return raw;
	}

	public void setRaw (String raw) {
		this.raw = raw;
	}

	public String getData () {
		return data;
	}

	public void setData (String data) {
		this.data = data;
	}

	public String getChannel () {
		return channel;
	}

	public void setChannel (String channel) {
		this.channel = channel;
	}

	public String getClientId () {
		return clientId;
	}

	public void setClientId (String clientId) {
		this.clientId = clientId;
	}

	public String getSubscription () {
		return subscription;
	}

	public void setSubscription (String subscription) {
		this.subscription = subscription;
	}

	public String getError () {
		return error;
	}

	public void setError (String error) {
		this.error = error;
	}
	
	@Override
	public String toString () {
		return "bayeux:" + type;
	}

	private String raw;
	private String data;
	private String clientId;
	private String channel;
	private String subscription;
	private String error;
	private BayeuxMessageType type;
}
