package com.atlas.marketdata;

import com.atlas.orders.OrderSide;

public class Quote {

	Quote (OrderSide side, String symbol, String mm, double price, double size) {
		this.side = side;
		this.symbol = symbol;
		this.price = price;
		this.size = size;
		this.mm = mm;
	}
	
	public OrderSide getSide () {
		return side;
	}
	
	public String getSymbol () {
		return symbol;
	}
	
	public double getPrice () {
		return price;
	}
	
	public double getSize () {
		return size;
	}
	
	public String getMm () {
		return mm;
	}

	private OrderSide side;
	private String symbol;
	private double price;
	private double size;
	private String mm;
}
