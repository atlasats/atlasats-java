package com.atlas.websockets;

abstract class BayeuxMessage {

	abstract BayeuxMessageType getType ();

	boolean isPublic () {
		return true;
	}
	
	int getAccount () {
		return 0;
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

	public String getError () {
		return error;
	}

	public void setError (String error) {
		this.error = error;
	}
	
	@Override
	public String toString () {
		return "bayeux:" + getType ();
	}

	private String raw;
	private String data;
	private String clientId;
	private String channel;
	private String error;
}

class InMessage extends BayeuxMessage {

	@Override
	BayeuxMessageType getType () {
		return type;
	}
	
	void setType (BayeuxMessageType type) {
		this.type = type;
	}

	private BayeuxMessageType type;
}

abstract class PrivateMessage extends BayeuxMessage {

	@Override
	boolean isPublic () {
		return false;
	}
	
	void setAccount (int account) {
		this.account = account;
	}
	
	@Override
	int getAccount () {
		return account;
	}
	
	private int account;
}
