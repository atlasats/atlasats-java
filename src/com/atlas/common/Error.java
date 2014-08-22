package com.atlas.common;

public class Error extends Message {

	public Error (String message) {
		this.message = message;
	}

	@Override
	public MessageType getType () {
		return MessageType.ERROR;
	}

	@Override
	public String toString () {
		return super.toString () + " " + message;
	}

	private String message;

}
