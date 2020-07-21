package com.app.config.socketIo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.security.JwtTokenUtil;
import com.app.service.JwtUserDetailsService;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GCWebSocketAuthenticator implements AuthorizationListener {
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public boolean isAuthorized(HandshakeData handshake) {
		String token = handshake.getSingleUrlParam("xtoken");
		if (token.isBlank()) {
			log.debug("xToken {}", token);
			log.error("failed authentication Websocket - xtoken null or empty");
			return false;
		}
		String jwtToken = null;
		String username = null;
		if (token != null && token.startsWith("Bearer ")) {
			jwtToken = token.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			log.warn("JWT Token does not begin with Bearer String");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}

