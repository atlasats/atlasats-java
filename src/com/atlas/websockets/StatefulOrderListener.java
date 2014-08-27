package com.atlas.websockets;


public interface StatefulOrderListener {

	void ack (String json);
	
	void reject (String json);
	
	void fill (String json);
	
	void urout (String json);
}
