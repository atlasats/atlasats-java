package com.atlas.websockets;

import org.json.JSONArray;



class Handshake extends OutMessage {

	Handshake () {
		super ();
		addRoot ("version", "1.0");
		addRoot ("supportedConnectionTypes", new JSONArray ().put ("websocket"));
	}

	@Override
	public String getChannel () {
		return Channels.HANDSHAKE;
	}

	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.HANDSHAKE;
	}
	
	@Override
	public String toString () {
		return "out:handshake";
	}
}
