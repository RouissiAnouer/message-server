package com.app.config.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;

import com.app.security.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;



public class RmeSessionChannelInterceptor implements ChannelInterceptor {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		System.out.println("Channel Interceptor");
		MessageHeaders headers = message.getHeaders();
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
		String requestTokenHeader = null;
		if (multiValueMap != null) {
			if ((multiValueMap.get("Authorization") != null)) {
				requestTokenHeader = multiValueMap.getFirst("Authorization");
			}
		}
		
		String username = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
				return null;
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
				return null;
			}
		} else {
			System.out.println("Need to Be authenticated");
			return null;
		}

		return message;
	}

}
