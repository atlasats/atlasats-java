package com.atlas.api;

import com.atlas.types.OrderType;
import com.atlas.types.Side;

public class OrderParameters {

	public OrderParameters () {
		orderType = OrderType.LIMIT;
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

	public String getError () {
		return error;
	}

	public void setError (String error) {
		this.error = error;
	}

	public boolean validate () {
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
		if (round (limit, 2) != limit) {
			setError ("Limit price can only have up to two decimals");
			return false;
		}
		if (round (quantity, 4) != quantity) {
			setError ("Quantity can only have up to four decimals");
			return false;
		}
		if (quantity < MIN_SIZE) {
			setError ("Minimum quantity is " + MIN_SIZE);
			return false;
		}
		if ((orderType == OrderType.LIMIT || orderType == OrderType.RESERVE) && limit < MIN_PRICE) {
			setError ("Minimum price is " + MIN_PRICE);
			return false;
		}
		if (orderType == OrderType.RESERVE && (display >= quantity || round (display, 4) != display)) {
			setError ("Invalid display: must be less than quantity and up to four decimals");
			return false;
		}
		if (orderType == OrderType.MARKET && (limit != 0d || display != 0d)) {
			setError ("Market orders should not have values for limit or display");
			return false;
		}
		return true;
	}

	private double round (double val, int decimals) {
		double exp = powers[decimals];
		val *= exp;
		val = (double) Math.round (val);
		return val / exp;
	}

	private String symbol;
	private Side side;
	private double quantity;
	private double display;
	private double limit;
	private OrderType orderType;
	private String clientOrderId;

	private String error;


	private static final double MIN_SIZE = 0.0001;
	private static final double MIN_PRICE = 0.01;
	private static final double[] powers = { 1, 10, 100, 1000, 10000 };
}
