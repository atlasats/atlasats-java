package com.atlas.websockets;

import com.atlas.account.Account;


abstract class PrivateDataSubscription extends Subscription {

	PrivateDataSubscription (Account account) {
		this.account = account;
	}
	
	public Account getAccount () {
		return account;
	}
	
	private Account account;
}
