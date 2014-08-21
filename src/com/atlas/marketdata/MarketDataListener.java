package com.atlas.marketdata;



public interface MarketDataListener {

	void handle (Book book);
	
	void handle (L1Update update);
	
	void handle (Quote quote);
	
	void handle (Trade trade);
}
