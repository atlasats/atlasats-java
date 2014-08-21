package com.atlas.websockets;

import com.atlas.account.Account;
import com.atlas.account.Order;

public interface AccountUpdateListener {

	void handle (Account account);
	
	void handle (Order order);
	
	void handle (Ack ack);
	
	void handle (Reject reject);
	
	void handle (Fill fill);
	
	void handle (UROut urout);
}
