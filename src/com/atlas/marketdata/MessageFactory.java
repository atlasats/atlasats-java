package com.atlas.marketdata;

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
	/**
	 * Parse this: {"last":100,"symbol":"BTC","change":-0.8058,"volume":10,"ask":520,"bidsize":1,"lastsize":1.1,"bid":460,"asksize":1,"currency":"USD"}
	 * @param json
	 * @return
	 */
	public static L1Update createL1 (String jsonString) {
		L1Update l1up = new L1Update ();
		JSONObject json = new JSONObject (jsonString);
		l1up.setLast (json.getDouble ("last"));
		l1up.setSymbol (json.getString ("symbol"));
		l1up.setChange (json.getDouble ("change"));
		l1up.setVolume (json.getDouble ("volume"));
		l1up.setAsk (json.getDouble ("ask"));
		l1up.setBidSize (json.getDouble ("bidsize"));
		l1up.setLastSize (json.getDouble ("lastsize"));
		l1up.setBid (json.getDouble ("bid"));
		l1up.setAskSize (json.getDouble ("asksize"));
		l1up.setCurrency (json.getString ("currency"));
		return l1up;
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
