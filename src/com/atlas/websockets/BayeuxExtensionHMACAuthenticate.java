package com.atlas.websockets;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Mac;
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
		if (message.getType () == BayeuxMessageType.HANDSHAKE) return true; 
		long nonce = System.currentTimeMillis ();
		byte[] input = genSigInput (message, nonce);
		Key secretKeySpec = new SecretKeySpec (string2Bytes (secret, "UTF-8"), "HmacSHA256");
		try {
			Mac mac = Mac.getInstance ("HmacSHA256");
			mac.init (secretKeySpec);
		    String signature = DatatypeConverter.printHexBinary (mac.doFinal (input));
		    message.addExtension ("ident", genExtObj (signature, nonce));
		    return true;
		} catch (Exception e) {
			log.error ("encrypting out-message", e);
		}
	    return false;
	}
	
	@Override
	public String toString () {
		return "HMAC/SHA256";
	}
	
	private JSONObject genExtObj (String signature, long nonce) {
		JSONObject json = new JSONObject ();
		json.put ("key", token);
		json.put ("signature", signature.toLowerCase ());
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
		log.debug ("sig input: " + sb.toString ());
		return string2Bytes (sb.toString (), "ASCII");
	}
	
	private byte[] string2Bytes (String data, String encoding) {
		try {
			return data.getBytes (encoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	private String token;
	private String secret;

	private Logger log;
}
