package com.app.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

public interface WebSocketService {

	void connectEvent(SocketIOServer server, SocketIOClient client);

	void disconnectEvent(SocketIOServer server, SocketIOClient client);

	void userLeave(SocketIOServer server, SocketIOClient client);
	

}
