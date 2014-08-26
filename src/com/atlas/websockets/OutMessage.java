package com.atlas.websockets;


abstract class OutMessage extends BayeuxMessage {

	public abstract String toJSON ();
}
