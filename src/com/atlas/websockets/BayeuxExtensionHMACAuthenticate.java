package com.atlas.websockets;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
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
		long nonce = System.currentTimeMillis ();
		byte[] input = genSigInput (message, nonce);
		SecretKeySpec secretKeySpec = new SecretKeySpec (input, "HmacSHA256");
		try {
		    Cipher cipher = Cipher.getInstance ("HmacSHA256");
		    cipher.init (Cipher.ENCRYPT_MODE, secretKeySpec);
		    byte[] encrypted = cipher.doFinal (secret.getBytes());
		    String signature = DatatypeConverter.printHexBinary (encrypted);
		    message.addExtension ("ident", genExtObj (signature, nonce));
		    return true;
		} catch (Exception e) {
			log.error ("encrypting out-message", e);
		}
	    return false;
	}
	
	private JSONObject genExtObj (String signature, long nonce) {
		JSONObject json = new JSONObject ();
		json.put ("key", token);
		json.put ("signature", signature);
		json.put ("nounce", nonce);
		return json;
	}
	/**
	 * <token>:<nonce>:<channel>:<data>
	 * @return
	 */
	private byte[] genSigInput (OutMessage message, long nonce) {
		StringBuilder sb = new StringBuilder ();
		sb.append (token);
		sb.append (':');
		sb.append (Long.toString (nonce));
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
