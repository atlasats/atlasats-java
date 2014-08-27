package com.atlas.websockets;

/**
 * Message sent to the server through the '/meta/subscribe' channel to subscribe to a channel
 * @author ken
 *
 */
public abstract class Subscription extends OutMessage {
	
	Subscription () {
		super ();
		addRoot (JSONKeys.SUBSCRIPTION, getSubscriptionName ());
	}

	@Override
	public String getChannel () {
		return Channels.SUBSCRIBE;
	}
	
	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.SUBSCRIPTION;
	}
	
	@Override
	public String toString () {
		return "out:subscription " + getSubscriptionName ();
	}
	
	public boolean isPublic () {
		return true;
	}
	
	public static final Subscription LEVEL1 = new Subscription () {
		public String getSubscriptionName () { return Channels.LEVEL1; }
	};
	
	public static final Subscription TRADES = new Subscription () {
		public String getSubscriptionName () { return Channels.TRADES; }
	};
	
	public static final Subscription BOOK = new Subscription () {
		public String getSubscriptionName () { return Channels.MARKET; }
	};

	protected abstract String getSubscriptionName ();
}

abstract class PrivateSubscription extends Subscription {
	
	public PrivateSubscription (int account) {
		this.account = account;
		subscriptionPrefix = "/account/" + account;
	}
	
	@Override
	public boolean isPublic () {
		return false;
	}
	
	public int getAccount () {
		return account;
	}
	
	@Override
	protected String getSubscriptionName() {
		return subscriptionPrefix;
	}

	private int account;
	private String subscriptionPrefix;
}

class AccountSubscription extends PrivateSubscription {

	public AccountSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + Channels.ACCOUNT;
	}
}

class OrderSubscription extends PrivateSubscription {

	public OrderSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + Channels.ORDERS;
	}
}

class StatefulSubscription extends PrivateSubscription {

	public StatefulSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + Channels.STATEFUL;
	}
}