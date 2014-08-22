package com.atlas.common;

import com.atlas.common.Message;
import com.atlas.common.MessageType;


public class Handshake extends Message {

	public Handshake (String clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public String toString () {
		return super.toString () + " " + clientId;
	}
	
	@Override
	public MessageType getType () {
		return MessageType.HANDSHAKE;
	}
	
	public String getClientId () {
		return clientId;
	}
	
	private String clientId;

}
