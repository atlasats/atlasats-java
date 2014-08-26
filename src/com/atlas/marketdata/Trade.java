package com.atlas.marketdata;

import java.util.Date;

import com.atlas.common.TickType;

public class Trade {

	Trade () {
	}
	
	@Override
	public String toString () {
		return "trade: " +  size + " " + symbol + " @" + price;
	}

	public long getSequence () {
		return sequence;
	}

	void setSequence (long sequence) {
		this.sequence = sequence;
	}

	public double getPrice () {
		return price;
	}

	void setPrice (double price) {
		this.price = price;
	}

	public double getSize () {
		return size;
	}

	void setSize (double size) {
		this.size = size;
	}

	public String getSymbol () {
		return symbol;
	}

	void setSymbol (String symbol) {
		this.symbol = symbol;
	}

	public String getCurrency () {
		return currency;
	}

	void setCurrency (String currency) {
		this.currency = currency;
	}

	public String getVenue () {
		return venue;
	}

	void setVenue (String venue) {
		this.venue = venue;
	}

	public Date getTime () {
		return time;
	}

	void setTime (Date time) {
		this.time = time;
	}

	public TickType getTick () {
		return tick;
	}

	void setTick (TickType tick) {
		this.tick = tick;
	}

	private long sequence;
	private double price;
	private double size;
	private String symbol;
	private String currency;
	private String venue;
	private Date time;
	private TickType tick;
}
