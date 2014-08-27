package com.atlas.websockets;

import org.json.JSONObject;

public class Order extends OutMessage {

	public Order () {
		orderType = OrderType.LIMIT;
	}

	@Override
	public BayeuxMessageType getType () {
		return BayeuxMessageType.ORDER;
	}
	
	@Override
	public String toJSON () {
		JSONObject data = new JSONObject ();
		data.put (JSONKeys.ACCOUNT, account);
		data.put (JSONKeys.CLID, clientOrderId);
		addRoot (JSONKeys.DATA, data);
		return super.toJSON ();
	}
	
	public String getSymbol () {
		return symbol;
	}

	public void setSymbol (String symbol) {
		this.symbol = symbol;
	}

	public Side getSide () {
		return side;
	}

	public void setSide (Side side) {
		this.side = side;
	}

	public double getQuantity () {
		return quantity;
	}

	public void setQuantity (double quantity) {
		this.quantity = quantity;
	}

	public double getDisplay () {
		return display;
	}

	public void setDisplay (double display) {
		this.display = display;
	}

	public double getLimit () {
		return limit;
	}

	public void setLimit (double limit) {
		this.limit = limit;
	}

	public OrderType getOrderType () {
		return orderType;
	}

	public void setOrderType (OrderType orderType) {
		this.orderType = orderType;
	}

	public String getClientOrderId () {
		return clientOrderId;
	}

	public void setClientOrderId (String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}
	
	void setAccount (int account) {
		this.account = account;
	}

	boolean validate () {
		if (symbol == null) {
			setError ("Invalid symbol");
			return false;
		}
		if (orderType == null) {
			setError ("Invalid order type");
			return false;
		}
		if (side == null) {
			setError ("Invalid order side");
			return false;
		}
		if (Util.round (limit, 2) != limit) {
			setError ("Limit price can only have up to two decimals");
			return false;
		}
		if (Util.round (quantity, 4) != quantity) {
			setError ("Quantity can only have up to four decimals");
			return false;
		}
		if (quantity < Util.MIN_SIZE) {
			setError ("Minimum quantity is " + Util.MIN_SIZE);
			return false;
		}
		if ((orderType == OrderType.LIMIT || orderType == OrderType.RESERVE) && limit < Util.MIN_PRICE) {
			setError ("Minimum price is " + Util.MIN_PRICE);
			return false;
		}
		if (orderType == OrderType.RESERVE && (display >= quantity || Util.round (display, 4) != display)) {
			setError ("Invalid display: must be less than quantity and up to four decimals");
			return false;
		}
		if (orderType == OrderType.MARKET && (limit != 0d || display != 0d)) {
			setError ("Market orders should not have values for limit or display");
			return false;
		}
		return true;
	}

	private String symbol;
	private Side side;
	private double quantity;
	private double display;
	private double limit;
	private OrderType orderType;
	private String clientOrderId;
	
	// internal
	private int account;
}
