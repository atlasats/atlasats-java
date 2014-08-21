package com.atlas.orders.stateful;


public interface StatefulOrderListener {

	void handle (Ack ack);
	
	void handle (Reject reject);
	
	void handle (Fill fill);
	
	void handle (UROut urout);
}
