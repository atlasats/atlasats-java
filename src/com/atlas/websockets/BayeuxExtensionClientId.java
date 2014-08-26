package com.atlas.websockets;


public class BayeuxExtensionClientId implements BayeuxExtension {

	BayeuxExtensionClientId (String clientId) {
		this.clientId = clientId;
	}

	@Override
	public boolean incoming (BayeuxMessage message) {
		return true;
	}

	@Override
	public boolean outgoing (OutMessage message) {
		if (message.getType () != BayeuxMessageType.HANDSHAKE) {
			message.setClientId (clientId);
		}
		return true;
	}
	
	@Override
	public boolean equals (Object obj) {
		return obj instanceof BayeuxExtensionClientId;
	}

	@Override
	public int hashCode () {
		return getClass ().hashCode ();
	}
	
	private String clientId;
}
