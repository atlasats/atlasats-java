package com.atlas.websockets;

import com.atlas.websockets.PrivateSubscription;

public class BayeuxExtensionAccountID implements BayeuxExtension {

	@Override
	public boolean incoming (BayeuxMessage message) {
		return true;
	}

	@Override
	public boolean outgoing (OutMessage message) {
		if (message instanceof PrivateSubscription) {
			return ((PrivateSubscription) message).getAccount () > 0;
		}
		return false;
	}
	
	@Override
	public String toString () {
		return "account number check";
	}

}
