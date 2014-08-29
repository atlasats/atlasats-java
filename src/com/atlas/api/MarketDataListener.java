package com.atlas.api;

public interface MarketDataListener {

	void book (String json);

	void level1 (String json);

	void quote (String json);

	void trade (String json);
}
