package com.atlas.websockets;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlas.common.Error;
import com.atlas.common.Handshake;
import com.atlas.common.Subscription;
import com.atlas.common.Message;
import com.atlas.common.MessageType;
import com.atlas.marketdata.MessageFactory;

public class JSON {

	public static Collection<Message> parse (String jsonString) {
		Collection<Message> messages = new LinkedList<Message> ();
		if (jsonString != null && jsonString.length () > 0) {
			// array of messages vs single JSON message
			if (jsonString.charAt (0) == '[') {
				JSONArray array = new JSONArray (jsonString);
				for (int i = 0; i < array.length (); i++) {
					messages.add (parse ((JSONObject) array.get (i)));
				}
			} else {
				messages.add (parse (new JSONObject (jsonString)));
			}
		}
		return messages;
	}

	private static Message parse (JSONObject json) {
		if (json.has (KEY_ERROR)) {
			return new Error (json.getString (KEY_ERROR));
		}
		String channel = json.getString ("channel");
		if (channel.equals (CHANNEL_HANDSHAKE)) {
			return new Handshake (json.getString (KEY_CLIENTID));
		} else if (channel.equals (CHANNEL_SUBSCRIBE)) {
			return new Subscription (json.getString (KEY_SUBSCRIPTION));
		} else if (channel.equals (CHANNEL_MARKET)) {
			return MessageFactory.book (json);
		}
		return new UnknownMessage (json.toString ());
	}
	
	public static final String CHANNEL_HANDSHAKE = "/meta/handshake";
	public static final String CHANNEL_SUBSCRIBE = "/meta/subscribe";
	public static final String KEY_CLIENTID = "clientId";
	public static final String KEY_ERROR = "error";
	
	private static final String KEY_SUBSCRIPTION = "subscription";
	private static final String CHANNEL_MARKET = "/market";
}



class UnknownMessage extends Message {

	public UnknownMessage (String json) {
		this.json = json;
	}
	
	@Override
	public MessageType getType () {
		return MessageType.UNKNOWN;
	}
	
	@Override
	public String toString () {
		return super.toString () + ": " + json;
	}

	private String json;
}

