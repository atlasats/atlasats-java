package com.atlas.api;

import java.util.Collection;
import com.atlas.websockets.Subscription;

public interface AtlasClient {

	void connect ();

	void setUri (String uri);

	void setAPIToken (String token);

	void setAPISecret (String secret);

	void setAccountNumber (int accountNumber);

	boolean subscribe (MarketDataListener listener, Collection<Subscription> subscriptions);

	boolean subscribe (AccountListener listener);

	boolean subscribe (OrderListener listener);

	boolean subscribe (StatefulOrderListener listener);

	boolean place (OrderParameters order);
	
	boolean cancel (String orderId);
}
