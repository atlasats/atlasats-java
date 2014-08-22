package com.atlas.websockets;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket
class Socket {

	public Socket () {
		log = LoggerFactory.getLogger (getClass());
		closeLatch = new CountDownLatch (1);
		extensions = new LinkedList<BayeuxExtension> ();
	}
	
	public void addExtension (BayeuxExtension ext) {
		extensions.add (ext);
	}
	
	public boolean awaitClose (int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await (duration, unit);
	}
	
	public boolean send (String message) {
		if (session == null) {
			log.error ("no session");
		} else if (!session.isOpen ()) {
			log.error ("invalid session state");
		} else {
			// process extensions
			for (BayeuxExtension ext : extensions) {
				message = ext.outgoing (message);
			}
			log.info ("out message: " + message);
			Future<Void> fut = session.getRemote ().sendStringByFuture (message);
			return true;
		}
		return false;
	}

	@OnWebSocketClose
	public void onClose (int statusCode, String reason) {
		log.info ("connection closed: " + reason);
		session = null;
		closeLatch.countDown ();
	}

	@OnWebSocketConnect
	public void onConnect (Session session) {
		log.info ("connected: " + session);
		this.session = session;
	}

	@OnWebSocketMessage
	public void onMessage (String message) {
		for (BayeuxExtension ext : extensions) {
			message = ext.incoming (message);
		}
		log.info ("in message: " + message);
	}

	private final CountDownLatch closeLatch;

	private Session session;
	private Collection<BayeuxExtension> extensions;

	private Logger log;

}
