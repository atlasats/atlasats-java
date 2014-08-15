package com.atlas.websockets;

import com.atlas.common.*;

public interface AccountUpdateListener {

	void handle (Account account);
	
	void handle (Order order);
	
	void handle (Ack ack);
	
	void handle (Reject reject);
	
	void handle (Fill fill);
	
	void handle (UROut urout);
}
