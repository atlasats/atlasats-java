package com.atlas.marketdata;

public class L1Update {

	L1Update () {
	}

	@Override
	public String toString () {
		return "L1 update: " + symbol + " " + bid + " x " + ask;
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

	public double getBid () {
		return bid;
	}

	void setBid (double bid) {
		this.bid = bid;
	}

	public double getBidSize () {
		return bidSize;
	}

	void setBidSize (double bidSize) {
		this.bidSize = bidSize;
	}

	public double getAsk () {
		return ask;
	}

	void setAsk (double ask) {
		this.ask = ask;
	}

	public double getAskSize () {
		return askSize;
	}

	public void setAskSize (double askSize) {
		this.askSize = askSize;
	}

	public double getLast () {
		return last;
	}

	void setLast (double last) {
		this.last = last;
	}

	public double getLastSize () {
		return lastSize;
	}

	void setLastSize (double lastSize) {
		this.lastSize = lastSize;
	}

	public double getVolume () {
		return volume;
	}

	void setVolume (double volume) {
		this.volume = volume;
	}

	public double getChange () {
		return change;
	}

	void setChange (double change) {
		this.change = change;
	}

	private String symbol;
	private String currency;
	private double bid;
	private double bidSize;
	private double ask;
	private double askSize;
	private double last;
	private double lastSize;
	private double volume;
	private double change;
}
