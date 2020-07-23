package com.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MsgEventWrapper {

	/**
	 * ALL EVENT - FIELDS
	 */
	private String content;
	private Long chatId;
	private Long convId;
	private String msgType; // Msg type or notification type
	private String type; // Msg type or notification type
	private String chatType;
	private Long ownerId;

	/**
	 * ADDITIONAL FIELDS - EVENT MESSAGE
	 */
	private Long id;
	private String createdAt;
	private Long createdAtMs;
	private String username;

	/**
	 * ADDITIONAL FIELDS FOR ATTACCHMENT - EVENT MESSAGE
	 */
	private String thumbnail;
	private String mimeType;
	private String description;

	private List<String> socketIds;
	/*
	 * ONLY USERLEAVE DISCONNECTION
	 */
	private String role;
	private String prospectName;
	
	public MsgEventWrapper(Long ownerId, String role, boolean agent) {
		this.ownerId = ownerId;
	}
}
