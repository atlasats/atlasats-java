package com.atlas.websockets;

import org.json.JSONArray;
import org.json.JSONObject;


class Handshake extends OutMessage {

	@Override
	public String toJSON () {
		JSONObject json = new JSONObject ();
		json.put ("channel", BayeuxMessageFactory.CHANNEL_HANDSHAKE);
		json.put ("version", "1.0");
		json.put ("supportedConnectionTypes", new JSONArray ().put ("websocket"));
		return json.toString ();
	}

	@Override
	public String toString () {
		return "out:handshake";
	}
}
