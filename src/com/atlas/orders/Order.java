package com.atlas.orders;

import java.util.Collection;
import java.util.Date;

/**
 * 
 * Instances of this class always contain the entire history and all information pertaining to the order as nested objects.
 * 
 *	{
 *	"limit":12,"tif":"GTC","status":"DONE","type":"LIMIT","currency":"USD","executed":0,"clid":"WEB","side":"BUY","oid":"40-082714-023311-063","item":"BTC","account":40,"quantity":1,"left":0,"average":0
 *	"ack":{"oref":"44YWW4S7PY3GA0","time":"2014-08-27 02:30:43"},
 *	"urout":{"time":"2014-08-27 02:41:08"},}
 */
public class Order {

	Order () {
	}
	
	private int account;
	private double quantity;
	private double left;
	private double limit;
	private double executed;
	private String orderID;
	private String orderRef;
	private String item;
	private OrderSide side;
	private TIF tif;
	private OrderStatus status;
	private OrderType type;
	private Date received;
	private Date canceled;
	private Collection<Execution> executions;
}
