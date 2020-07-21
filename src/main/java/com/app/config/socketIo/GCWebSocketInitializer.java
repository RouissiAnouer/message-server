package com.app.config.socketIo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class GCWebSocketInitializer {
	private @Autowired SocketIOServer server;

	static final Logger logger = LoggerFactory.getLogger(GCWebSocketInitializer.class);

	@PostConstruct
	public void init() throws InterruptedException {
		logger.info("netty web socket started");
	}

	@PreDestroy
	public void cleanUp() {
		server.getConfiguration().getStoreFactory().shutdown();
		server.stop();
		logger.info("netty web socket stopped");
		logger.info("redissonCLient socket stopped");

	}

}