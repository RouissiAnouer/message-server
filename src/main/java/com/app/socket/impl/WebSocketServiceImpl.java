package com.app.socket.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.socket.WebSocketService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

	@Autowired
	public WebSocketServiceImpl() {
	}

	@Override
	public void connectEvent(SocketIOServer server, SocketIOClient client) {
//		String token = client.getHandshakeData().getSingleUrlParam("xtoken");
//		log.info(token);
	}

	@Override
	public void disconnectEvent(SocketIOServer server, SocketIOClient client) {
//		String token = client.getHandshakeData().getSingleUrlParam("xtoken");
//		log.info(token);
	}
	
	@Override
	public void userLeave(SocketIOServer server, SocketIOClient client) {
//		String token = client.getHandshakeData().getSingleUrlParam("xtoken");
//		log.info(token);
	}

	
}

