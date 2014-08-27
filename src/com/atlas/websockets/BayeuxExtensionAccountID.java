package com.atlas.websockets;

public class BayeuxExtensionAccountID implements BayeuxExtension {

	@Override
	public boolean incoming (BayeuxMessage message) {
		return true;
	}

	@Override
	public boolean outgoing (OutMessage message) {
		if (!message.isPublic ()) {
			return message.getAccount () > 0;
		}
		return true;
	}
	
	@Override
	public String toString () {
		return "account number check";
	}

}
