package com.atlas.websockets;


public enum BayeuxMessageType {

	HANDSHAKE,
	ERROR,
	SUBSCRIPTION,
	ORDER,
	CANCEL,
	ECHO,
	DATA
}
