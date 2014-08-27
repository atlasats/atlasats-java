package com.atlas.websockets;


public class Cancel extends OutMessage {

	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.CANCEL;
	}
	
}
