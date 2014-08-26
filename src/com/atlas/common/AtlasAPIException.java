package com.atlas.common;


public final class AtlasAPIException extends RuntimeException {

	public AtlasAPIException (String message) {
		this.message = message;
	}

	public String getMessage () {
		return message;
	}
	
	private String message;
	
	private static final long serialVersionUID = -2537050550937608656L;

}
