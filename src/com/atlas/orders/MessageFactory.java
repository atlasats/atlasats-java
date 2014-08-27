package com.atlas.orders;


public class MessageFactory {

	/**
	 * Parse: {"limit":12,"tif":"GTC","status":"DONE","ack":{"oref":"44YWW4S7PY3GA0","time":"2014-08-27 02:30:43"},"urout":{"time":"2014-08-27 02:41:08"},"type":"LIMIT","currency":"USD","executed":0,"clid":"WEB","side":"BUY","oid":"40-082714-023311-063","item":"BTC","account":40,"quantity":1,"left":0,"average":0}
	 */
	public static Order order (String json) {
		Order ord = new Order ();
		return null;
	}
}
