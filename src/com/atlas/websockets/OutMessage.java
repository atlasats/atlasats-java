package com.atlas.websockets;

import org.json.JSONObject;


abstract class OutMessage extends BayeuxMessage {
	
	OutMessage () {
		json = new JSONObject ();
		json.put (JSONKeys.CHANNEL, getChannel ());
		setData ("");
	}
	
	public void addRoot (String key, Object obj) {
		json.put (key, obj);
	}
	
	public void addExtension (String key, JSONObject obj) {
		if (!json.has (JSONKeys.EXT)) {
			json.put (JSONKeys.EXT, new JSONObject ());
		}
		JSONObject ext = json.getJSONObject (JSONKeys.EXT);
		ext.put (key, obj);
	}
	
	public String toJSON () {
		json.put (JSONKeys.CLIENTID, getClientId ());
		json.put (JSONKeys.DATA, getData ());
		return json.toString ();
	}
	
	private JSONObject json;
}
