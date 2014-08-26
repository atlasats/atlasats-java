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
	
	@Override
	public boolean isPublic () {
		return false;
	}
}

class AccountSubscription extends PrivateSubscription {

	@Override
	protected String getSubscriptionName () {
		return "/account";
	}
}

class OrderSubscription extends PrivateSubscription {

	@Override
	protected String getSubscriptionName () {
		return "/orders";
	}
}

class StatefulSubscription extends PrivateSubscription {

	@Override
	protected String getSubscriptionName () {
		return "/stateful";
	}
}