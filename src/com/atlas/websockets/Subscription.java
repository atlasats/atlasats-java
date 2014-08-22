package com.atlas.websockets;


public abstract class Subscription {
	
	public abstract String getChannel ();
	
	public static final Subscription LEVEL1 = new Subscription () {
		public String getChannel () { return ""; }
	};
	
	public static final Subscription TRADES = new Subscription () {
		public String getChannel () { return ""; }
	};
	
	public static final Subscription BOOK = new Subscription () {
		public String getChannel () { return ""; }
	};
	
}
