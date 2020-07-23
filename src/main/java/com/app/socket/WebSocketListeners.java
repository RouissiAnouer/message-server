package com.app.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.config.socketIo.WebSocketConstants;
import com.app.model.MsgEventWrapper;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketListeners {
	@Autowired
	WebSocketService socketService;

	private final SocketIOServer server;

	@Autowired
	public WebSocketListeners(SocketIOServer server) {
		this.server = server;
	}

	@OnConnect
	public void onConnectHandler(SocketIOClient client) {
		socketService.connectEvent(server, client);
		client.leaveRoom("");
	}

	@OnDisconnect
	public void onDisconnectHandler(SocketIOClient client) {
		socketService.disconnectEvent(server, client);
	}

	@OnEvent(value = WebSocketConstants.USER_LEAVE_ROOM)
	public void onUserLeaveRoom(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) {
		String room = msg.getChatId().toString();
		log.info("client leave the room : " + room);
		client.leaveRoom(room);
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.USER_LEAVE_ROOM, msg);
	}

	@OnEvent(value = WebSocketConstants.USER_JOIN)
	public void onUserJoin(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) throws Exception {
		String room = msg.getChatId().toString();
		log.info("client join the room : " + room);
		client.joinRoom(room);
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.USER_JOIN, msg);
	}

	@OnEvent(value = WebSocketConstants.USER_ENTERED)
	public void onUserEntered(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) {
		log.debug("EVENT SOCKET : {}  - SocketClient SessionID  : {} -  SocketClients Online {} ", WebSocketConstants.USER_ENTERED,
				client.getSessionId().toString(), server.getAllClients().size());
		String room = msg.getChatId().toString();
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.USER_ENTERED, msg);
	}

	@OnEvent(value = WebSocketConstants.USER_LEAVE)
	public void onUserLeave(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) {
		log.debug("EVENT SOCKET : {}  - SocketIoCLient ID  : {} -  SocketClients Online {} ", WebSocketConstants.USER_LEAVE,
				client.getSessionId().hashCode(), server.getAllClients().size());
		socketService.userLeave(server, client);
		String room = msg.getChatId().toString();
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.USER_LEAVE, msg);
	}

	@OnEvent(value = WebSocketConstants.IS_TYPING)
	public void onTyping(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) {
		log.debug("EVENT SOCKET : {}  - SocketIoCLient ID  : {} -  SocketClients Online {} ", WebSocketConstants.IS_TYPING,
				client.getSessionId().hashCode(), server.getAllClients().size());
		String room = msg.getChatId().toString();
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.IS_TYPING, msg);
	}

	@OnEvent(value = WebSocketConstants.MESSAGE)
	public void onMessage(SocketIOClient client, AckRequest ackRequest, MsgEventWrapper msg) {
		log.debug("EVENT SOCKET : {}  - SocketIoCLient ID  : {} -  SocketClients Online {} ", WebSocketConstants.MESSAGE,
				client.getSessionId().hashCode(), server.getAllClients().size());
		String room = msg.getChatId().toString();
		server.getRoomOperations(room).sendEvent(room + WebSocketConstants.MESSAGE, msg);
	}

}
