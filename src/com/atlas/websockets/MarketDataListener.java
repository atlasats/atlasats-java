package com.atlas.websockets;

import com.atlas.marketdata.Book;
import com.atlas.marketdata.L1Update;
import com.atlas.marketdata.Quote;
import com.atlas.marketdata.Trade;


public interface MarketDataListener {

	void handle (Book book);
	
	void handle (L1Update update);
	
	void handle (Quote quote);
	
	void handle (Trade trade);
}
