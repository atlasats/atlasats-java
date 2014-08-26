package com.atlas.websockets;

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
		public String getSubscriptionName () { return "/level1"; }
	};
	
	public static final Subscription TRADES = new Subscription () {
		public String getSubscriptionName () { return "/trades"; }
	};
	
	public static final Subscription BOOK = new Subscription () {
		public String getSubscriptionName () { return "/market"; }
	};

	protected abstract String getSubscriptionName ();
}

abstract class PrivateSubscription extends Subscription {
	
	public PrivateSubscription (int account) {
		subscriptionPrefix = "/account/" + account;
	}
	
	@Override
	public boolean isPublic () {
		return false;
	}
	
	@Override
	protected String getSubscriptionName() {
		return subscriptionPrefix;
	}
	
	
	private String subscriptionPrefix;
}

class AccountSubscription extends PrivateSubscription {

	public AccountSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + "/info";
	}
}

class OrderSubscription extends PrivateSubscription {

	public OrderSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + "/orders";
	}
}

class StatefulSubscription extends PrivateSubscription {

	public StatefulSubscription (int account) {
		super (account);
	}
	
	@Override
	protected String getSubscriptionName () {
		return super.getSubscriptionName () + "/stateful";
	}
}