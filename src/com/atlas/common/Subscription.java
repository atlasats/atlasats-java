package com.atlas.common;


public class Subscription extends Message {

	public Subscription (String name) {
		this.name = name;
	}
	
	public String getSubscriptionName () {
		return name;
	}
	
	@Override
	public MessageType getType () {
		return MessageType.SUBSCRIPTION;
	}
	
	private String name;
}
