package com.atlas.websockets;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BayeuxMessageFactory {

	public static Collection<BayeuxMessage> create (String jsonString) {
		Collection<BayeuxMessage> messages = new LinkedList<BayeuxMessage> ();
		if (jsonString != null && jsonString.length () > 0) {
			// array of messages versus single JSON message
			if (jsonString.charAt (0) == '[') {
				JSONArray array = new JSONArray (jsonString);
				for (int i = 0; i < array.length (); i++) {
					BayeuxMessage message = parse ((JSONObject) array.get (i));
					message.setRaw (jsonString);
					messages.add (message);
				}
			} else {
				BayeuxMessage message = parse (new JSONObject (jsonString));
				message.setRaw (jsonString);
				messages.add (message);
			}
		}
		return messages;
	}

	
	private static BayeuxMessage parse (JSONObject json) {
		BayeuxMessage message = new BayeuxMessage ();
		if (json.has (KEY_ERROR)) {
			message.setType (BayeuxMessageType.ERROR);
			message.setError (json.getString (KEY_ERROR));
		}
		String channel = json.getString (KEY_CHANNEL);
		if (channel.equals (CHANNEL_HANDSHAKE)) {
			message.setType (BayeuxMessageType.HANDSHAKE);
			message.setClientId (json.getString (KEY_CLIENTID));
		} else if (channel.equals (CHANNEL_SUBSCRIBE)) {
			message.setType (BayeuxMessageType.SUBSCRIPTION);
			message.setSubscription (json.getString (KEY_SUBSCRIPTION));
		} else {
			message.setType (BayeuxMessageType.DATA);
			message.setData (json.getString (KEY_DATA));
			message.setChannel (channel);
		}
		return message;
	}
	
	// WebSocket channels
	public static final String CHANNEL_HANDSHAKE = "/meta/handshake";
	public static final String CHANNEL_SUBSCRIBE = "/meta/subscribe";
	public static final String CHANNEL_MARKET = "/market";
	public static final String CHANNEL_LEVEL1 = "/level1";
	public static final String CHANNEL_TRADES = "/trades";
	
	// JSON keys
	public static final String KEY_CHANNEL = "channel";
	public static final String KEY_CLIENTID = "clientId";
	public static final String KEY_ERROR = "error";
	
	private static final String KEY_SUBSCRIPTION = "subscription";
	private static final String KEY_DATA = "data";

}
