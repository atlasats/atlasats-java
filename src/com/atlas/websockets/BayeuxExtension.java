package com.atlas.websockets;

interface BayeuxExtension {

	boolean incoming (BayeuxMessage message);
	
	boolean outgoing (OutMessage message);
}
