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
		if (json.has (JSONKeys.ERROR)) {
			message.setType (BayeuxMessageType.ERROR);
			message.setError (json.getString (JSONKeys.ERROR));
			return message;
		}
		String channel = json.getString (JSONKeys.CHANNEL);
		if (channel.equals (Channels.HANDSHAKE)) {
			message.setType (BayeuxMessageType.HANDSHAKE);
			message.setClientId (json.getString (JSONKeys.CLIENTID));
		} else if (channel.equals (Channels.SUBSCRIBE)) {
			message.setType (BayeuxMessageType.SUBSCRIPTION);
			message.setData (json.getString (JSONKeys.SUBSCRIPTION));
		} else {
			message.setType (BayeuxMessageType.DATA);
			message.setData (json.getString (JSONKeys.DATA));
			message.setChannel (channel);
		}
		return message;
	}
	
}
