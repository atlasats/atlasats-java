package com.atlas.websockets;

public interface BayeuxExtension {

	boolean incoming (BayeuxMessage message);
	
	boolean outgoing (BayeuxMessage message);
}
