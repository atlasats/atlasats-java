package com.atlas.marketdata;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlas.common.TickType;
import com.atlas.orders.OrderSide;


public class MessageFactory {

	public static Book book (String json) {
		Book b = new Book ();
		JSONObject data = new JSONObject (json);
		b.setSymbol (data.getString ("symbol"));
		b.setCurrency (data.getString ("currency"));
		b.setOpen (data.getDouble ("open"));
		b.setHigh (data.getDouble ("high"));
		b.setLow (data.getDouble ("low"));
		b.setLast (data.getDouble ("last"));
		b.setAverage (data.getDouble ("average"));
		b.setVolume (data.getDouble ("volume"));
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
		b.setBids (bids);
		b.setOffers (offers);
		return b;
	}
	/**
	 * Parse this: {"last":100,"symbol":"BTC","change":-0.8058,"volume":10,"ask":520,"bidsize":1,"lastsize":1.1,"bid":460,"asksize":1,"currency":"USD"}
	 * @param json
	 * @return
	 */
	public static L1Update level1 (String jsonString) {
		L1Update l1 = new L1Update ();
		JSONObject json = new JSONObject (jsonString);
		l1.setLast (json.getDouble ("last"));
		l1.setSymbol (json.getString ("symbol"));
		l1.setChange (json.getDouble ("change"));
		l1.setVolume (json.getDouble ("volume"));
		l1.setAsk (json.getDouble ("ask"));
		l1.setBidSize (json.getDouble ("bidsize"));
		l1.setLastSize (json.getDouble ("lastsize"));
		l1.setBid (json.getDouble ("bid"));
		l1.setAskSize (json.getDouble ("asksize"));
		l1.setCurrency (json.getString ("currency"));
		return l1;
	}
	
	/**
	 * Parse this: {"tick":"UP","time":"2014-08-25 23:51:26","price":520,"symbol":"BTC","seq":1408992758,"venue":"CROX","size":0.001,"currency":"USD"}
	 * @param jsonString
	 * @return
	 */
	public static Trade trade (String jsonString) {
		Trade t = new Trade ();
		JSONObject json = new JSONObject (jsonString);
		t.setTick (TickType.valueOf (json.getString ("tick")));
		t.setTime (parse (json.getString ("time")));
		t.setPrice (json.getDouble ("price"));
		t.setSymbol (json.getString ("symbol"));
		t.setSequence (json.getLong ("seq"));
		t.setVenue (json.getString ("venue"));
		t.setSize (json.getDouble ("size"));
		t.setCurrency (json.getString ("currency"));
		return t;
	}
	
	public static Quote quote (JSONObject json) {
		OrderSide side = json.getString ("side").equals ("BUY") ? OrderSide.BUY : OrderSide.SELL;
		String symbol = json.getString ("symbol");
		String mm = json.getString ("mm");
		double price = json.getDouble ("price");
		double size = json.getDouble ("size");
		return new Quote (side, symbol, mm, price, size);
	}
	
	private static Date parse (String dateStr) {
		try {
			return dateFormat.parse (dateStr);
		} catch (ParseException e) {
		}
		return null;
	}
	
	private static final DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

}
