package com.app.config.socketIo;

import com.app.security.JwtTokenUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GCWebSocketAuthenticator implements AuthorizationListener {
	
	@Override
	public boolean isAuthorized(HandshakeData handshake) {
		String token = handshake.getSingleUrlParam("xtoken");
		if (token.isEmpty()) {
			log.debug("xToken {}", token);
			log.error("failed authentication Websocket - xtoken null or empty");
			return false;
		}
		String jwtToken = null;
		String username = null;
		jwtToken = token;
		try {
			JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
			jwtTokenUtil.setSecret("qwertyuiopasdfghjklzxcvbnm");
			username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			log.info(username);
			return true;
		} catch (IllegalArgumentException e) {
			System.out.println("Unable to get JWT Token");
			return false;
		} catch (ExpiredJwtException e) {
			System.out.println("JWT Token has expired");
			return false;
		}
	}
}
