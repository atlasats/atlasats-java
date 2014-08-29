package com.atlas.websockets;

import org.json.JSONObject;

import com.atlas.api.OrderParameters;
import com.atlas.types.OrderType;
import com.atlas.types.Side;
import com.atlas.websockets.BayeuxMessageType;
import com.atlas.websockets.Channels;
import com.atlas.websockets.JSONKeys;
import com.atlas.websockets.OutMessage;

class Order extends OutMessage {

	public Order (OrderParameters order) {
		this.params = order;
	}

	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.ORDER;
	}

	@Override
	public String getChannel () {
		return Channels.ACTIONS;
	}
	
	public String getSymbol () {
		return params.getSymbol ();
	}

	public Side getSide () {
		return params.getSide ();
	}

	public double getQuantity () {
		return params.getQuantity ();
	}

	public double getDisplay () {
		return params.getDisplay ();
	}

	public double getLimit () {
		return params.getLimit ();
	}

	public OrderType getOrderType () {
		return params.getOrderType ();
	}

	public String getClientOrderId () {
		return params.getClientOrderId ();
	}

	void setAccount (int account) {
		this.account = account;
	}

	void setCurrency (String currency) {
		this.currency = currency;
	}
	
	void prepare () {
		JSONObject data = new JSONObject ();
		data.put (JSONKeys.ACCOUNT, account);
		data.put (JSONKeys.CLID, getClientOrderId ());
		data.put (JSONKeys.ACTION, "order:create");
		data.put (JSONKeys.ITEM, getSymbol ());
		data.put (JSONKeys.CURRENCY, currency);
		data.put (JSONKeys.SIDE, getSide ().toString ());
		data.put (JSONKeys.QUANTITY, getQuantity ());
		data.put (JSONKeys.ORDER_TYPE, getOrderType ().toString ().toLowerCase ());
		data.put (JSONKeys.PRICE, getLimit ());
		data.put (JSONKeys.DISPLAY, getDisplay ());
		setData (data.toString ());
	}
	
	private OrderParameters params;
	
	// internal
	private int account;
	private String currency;
}
