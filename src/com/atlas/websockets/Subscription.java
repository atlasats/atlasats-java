package com.atlas.websockets;

import org.json.JSONObject;


public abstract class Subscription extends OutMessage {
	
	public abstract String getSubscriptionName ();
	
	@Override
	public String toJSON () {
		JSONObject json = new JSONObject ();
		json.put ("channel", BayeuxMessageFactory.CHANNEL_SUBSCRIBE);
		json.put (BayeuxMessageFactory.KEY_CLIENTID, clientId);
		json.put ("subscription", getSubscriptionName ());
		return json.toString ();
	}

	@Override
	public String toString () {
		return "out:subscription " + getSubscriptionName ();
	}
	
	void setClientId (String clientId) {
		this.clientId = clientId;
	}

	private String clientId;
	
	public static final Subscription LEVEL1 = new Subscription () {
		public String getSubscriptionName () { return ""; }
	};
	
	public static final Subscription TRADES = new Subscription () {
		public String getSubscriptionName () { return "/trades"; }
	};
	
	public static final Subscription BOOK = new Subscription () {
		public String getSubscriptionName () { return "/market"; }
	};
}

abstract class PrivateSubscription extends Subscription {
	
	public PrivateSubscription (String key, String secret) {
		this.key = key;
		this.secret = secret;
	}
	
	String key;
	String secret;
}

class AccountSubscription extends PrivateSubscription {

	public AccountSubscription (String key, String secret) {
		super (key, secret);
	}

	@Override
	public String getSubscriptionName () {
		return "account";
	}
}

class OrderSubscription extends PrivateSubscription {

	public OrderSubscription (String key, String secret) {
		super (key, secret);
	}

	@Override
	public String getSubscriptionName () {
		return "orders";
	}
}

class StatefulSubscription extends PrivateSubscription {

	public StatefulSubscription (String key, String secret) {
		super (key, secret);
	}

	@Override
	public String getSubscriptionName () {
		return "stateful";
	}
}