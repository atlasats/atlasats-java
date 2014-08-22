package com.atlas.common;


public abstract class Message {

	public abstract MessageType getType ();
	
	@Override
	public String toString () {
		return getType () + " (IN)";
	}
	
	public boolean isIncoming () {
		return true;
	}
}
