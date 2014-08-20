package com.atlas.marketdata;


public class Quote {

	Quote (String symbol, String mm, double price, double size) {
		this.symbol = symbol;
		this.price = price;
		this.size = size;
		this.mm = mm;
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

	private String symbol;
	private double price;
	private double size;
	private String mm;
}
