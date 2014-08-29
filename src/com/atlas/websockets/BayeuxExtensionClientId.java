package com.atlas.websockets;

class BayeuxExtensionClientId implements BayeuxExtension {

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
	public String toString () {
		return "client id check";
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
