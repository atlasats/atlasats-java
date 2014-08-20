package com.atlas.marketdata;

import java.util.List;



public class Book {

	Book () {
	}
	
	public String getSymbol () {
		return symbol;
	}
	
	public String getCurrency () {
		return currency;
	}
	
	public double getOpen () {
		return open;
	}
	
	public double getHigh () {
		return high;
	}
	
	public double getLow () {
		return low;
	}
	
	public double getLast () {
		return last;
	}
	
	public double getAverage () {
		return average;
	}
	
	public double getVolume () {
		return volume;
	}
	
	public List<Quote> getBids () {
		return bids;
	}
	
	public List<Quote> getOffers () {
		return offers;
	}
	
	@Override
	public String toString() {
		return "[Book]" + symbol;
	}

	private String symbol;
	private String currency;
	
	private double open;
	private double high;
	private double low;
	private double last;
	private double average;
	private double volume;
	
	private List<Quote> bids;
	private List<Quote> offers;
}
