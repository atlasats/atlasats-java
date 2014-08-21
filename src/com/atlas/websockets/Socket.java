package com.atlas.websockets;

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
	}
	
	public boolean awaitClose (int duration, TimeUnit unit) throws InterruptedException {
		return this.closeLatch.await (duration, unit);
	}
	
	public void send (String message) {
		if (session == null) {
			log.error ("no session");
		} else if (!session.isOpen ()) {
			log.error ("invalid session state");
		} else {
			Future<Void> fut = session.getRemote ().sendStringByFuture (message);
		}
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
	public void onMessage (String msg) {
		log.info ("in msg: " + msg);
	}

	private final CountDownLatch closeLatch;

	private Session session;

	private Logger log;

}
