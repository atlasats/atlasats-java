package com.atlas.websockets;


public class BayeuxExtensionNoAuth implements BayeuxExtension {

	@Override
	public boolean incoming (BayeuxMessage message) {
		return true;
	}

	@Override
	public boolean outgoing (OutMessage message) {
		if (message.getType () == BayeuxMessageType.SUBSCRIPTION) {
			return ((Subscription) message).isPublic ();
		}
		return true;
	}
	
	@Override
	public boolean equals (Object obj) {
		return obj instanceof BayeuxExtensionNoAuth;
	}
	
	@Override
	public int hashCode () {
		return getClass ().hashCode ();
	}
}
