package com.atlas.websockets;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class BayeuxExtensionHMACAuthenticate implements BayeuxExtension {

	public BayeuxExtensionHMACAuthenticate (String token, String secret) {
		this.token = token;
		this.secret = secret;
		log = LoggerFactory.getLogger (getClass());
	}
	
	@Override
	public boolean incoming (BayeuxMessage message) {
		// nothing to do on incoming messages
		return true;
	}

	@Override
	public boolean outgoing (OutMessage message) {
		byte[] input = genSigInput (message);
		SecretKeySpec secretKeySpec = new SecretKeySpec (input, "HmacSHA256");
		try {
		    Cipher cipher = Cipher.getInstance ("HmacSHA256");
		    cipher.init (Cipher.ENCRYPT_MODE, secretKeySpec);
		    byte[] encrypted = cipher.doFinal (secret.getBytes());
		    message.setData (DatatypeConverter.printHexBinary (encrypted));
		    return true;
		} catch (Exception e) {
			log.error ("encrypting out-message", e);
		}
	    return false;
	}
	
	/**
	 * <token>:<nonce>:<channel>:<data>
	 * @return
	 */
	private byte[] genSigInput (OutMessage message) {
		StringBuilder sb = new StringBuilder ();
		sb.append (token);
		sb.append (':');
		sb.append (Long.toString (System.currentTimeMillis ()));
		sb.append (':');
		sb.append (message.getChannel ());
		sb.append (':');
		sb.append (message.getData ());
		try {
			return sb.toString ().getBytes ("ASCII");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	private String token;
	private String secret;

	private Logger log;
}
