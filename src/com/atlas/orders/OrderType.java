package com.atlas.orders;


public enum OrderType {
	
	LIMIT,
	MARKET,
	STOP,
	STOP_LIMIT,
	RESERVE,
	DISCRETION;
	
	public char toChar () {
		switch (this) {
		case LIMIT:
			return 'L';
		case MARKET:
			return 'M';
		case STOP:
			return 'P';
		case STOP_LIMIT:
			return 'T';
		case RESERVE:
			return 'R';
		case DISCRETION:
			return 'D';
		default:
			return '?';
		}
	}
}
