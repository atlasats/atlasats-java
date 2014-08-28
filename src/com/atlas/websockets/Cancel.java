package com.atlas.websockets;

import org.json.JSONObject;

class Cancel extends OutMessage {

	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.CANCEL;
	}
	
	@Override
	public String getChannel () {
		return Channels.ACTIONS;
	}

	public String getOrderId () {
		return orderId;
	}

	public void setOrderId (String orderId) {
		this.orderId = orderId;
	}

	void prepare () {
		JSONObject data = new JSONObject ();
		data.put (JSONKeys.ACTION, "order:cancel");
		data.put (JSONKeys.ORDER_ID, orderId);
		setData (data.toString ());
	}
	
	private String orderId;
}
