package com.atlas.marketdata;

import java.util.List;
import com.atlas.common.Message;
import com.atlas.common.MessageType;

public class Book extends Message {

	Book () {
	}
	
	@Override
	public MessageType getType () {
		return MessageType.BOOK;
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

	void setSymbol (String symbol) {
		this.symbol = symbol;
	}
	
	void setCurrency (String currency) {
		this.currency = currency;
	}
	
	void setOpen (double open) {
		this.open = open;
	}
	
	void setHigh (double high) {
		this.high = high;
	}
	
	void setLow (double low) {
		this.low = low;
	}
	
	void setLast (double last) {
		this.last = last;
	}
	
	void setAverage (double average) {
		this.average = average;
	}
	
	void setVolume (double volume) {
		this.volume = volume;
	}

	void setBids (List<Quote> bids) {
		this.bids = bids;
	}
	
	void setOffers (List<Quote> offers) {
		this.offers = offers;
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
