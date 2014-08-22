package com.atlas.websockets;


public interface BayeuxExtension {

	String incoming (String message);
	
	String outgoing (String message);
}
