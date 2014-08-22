package com.atlas.websockets;

import com.atlas.common.Message;

public interface BayeuxExtension {

	boolean incoming (Message message);
	
	boolean outgoing (OutMessage message);
}
