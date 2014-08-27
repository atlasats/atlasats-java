package com.atlas.orders;

public enum OrderStatus {

	NEW, 		// received by Atlas
	OPEN, 		// acknowledged by the exchange, can be partially filled
	REJECTED, 	// 
	DONE, 		// fully filled or cancel-acked
}
