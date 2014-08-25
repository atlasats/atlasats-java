package com.atlas.marketdata;

import java.nio.channels.SeekableByteChannel;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlas.orders.OrderSide;


public class MessageFactory {

	public static Book book (String json) {
		Book book = new Book ();
		JSONObject data = new JSONObject (json);
		book.setSymbol (data.getString ("symbol"));
		book.setCurrency (data.getString ("currency"));
		book.setOpen (data.getDouble ("open"));
		book.setHigh (data.getDouble ("high"));
		book.setLow (data.getDouble ("low"));
		book.setLast (data.getDouble ("last"));
		book.setAverage (data.getDouble ("average"));
		book.setVolume (data.getDouble ("volume"));
		List<Quote> bids = new LinkedList<Quote> ();
		List<Quote> offers = new LinkedList<Quote> ();
		JSONArray quotes = data.getJSONArray ("quotes");
		for (int i = 0; i < quotes.length (); i++) {
			Quote q = quote ((JSONObject) quotes.get (i));
			if (q.getSide () == OrderSide.BUY) {
				bids.add (q);
			} else {
				offers.add (q);
			}
		}
		book.setBids (bids);
		book.setOffers (offers);
		return book;
	}
	
	public static L1Update createL1 (JSONObject json) {
		return null;
	}
	
	public static Quote quote (JSONObject json) {
		OrderSide side = json.getString ("side").equals ("BUY") ? OrderSide.BUY : OrderSide.SELL;
		String symbol = json.getString ("symbol");
		String mm = json.getString ("mm");
		double price = json.getDouble ("price");
		double size = json.getDouble ("size");
		return new Quote (side, symbol, mm, price, size);
	}
}
