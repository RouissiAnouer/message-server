package com.app.config.socketIo;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.app.config.yaml.YamlConfigurationProperties;
import com.corundumstudio.socketio.AckMode;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.StoreFactory;

@Component
public class GCWebSocketConfig {
	static final Logger logger = LoggerFactory.getLogger(GCWebSocketConfig.class);
	
	@Autowired
    private YamlConfigurationProperties env;

	@Autowired
	StoreFactory storeFactory;

	@Bean
	public SocketIOServer socketIoServer() {
		logger.info("StoreFactory info: " + storeFactory.getClass().getSimpleName());
		Configuration config = new Configuration();
		config.setHostname(env.getApplication().getSocketIo().getSocketIoServer());
		config.setPort(env.getApplication().getSocketIo().getSocketIoPort());
		config.setAllowCustomRequests(true);
		config.getSocketConfig().setReuseAddress(true);
		config.getSocketConfig().setTcpKeepAlive(true);
		config.setOrigin("*");
		config.setPingTimeout(60000);
		config.setUpgradeTimeout(70000);
		config.setPingTimeout(60000);
		config.setPingInterval(30000);
		config.setAuthorizationListener(new GCWebSocketAuthenticator());
		config.setStoreFactory(storeFactory);
		config.setAckMode(AckMode.AUTO);
		SocketIOServer server = new SocketIOServer(config);

		server.start();
		return server;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer ssrv) {
		return new SpringAnnotationScanner(ssrv);
	}

	@PreDestroy
	public void destroy() {
		System.out.println("Inside destroy method");
	}

}

