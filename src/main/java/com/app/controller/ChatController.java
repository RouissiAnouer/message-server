package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.response.AllChatsResponse;
import com.app.model.response.ChatMessageResponse;
import com.app.service.IChatService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chats")
public class ChatController {
	
	@Autowired
	IChatService chatService;

	@RequestMapping(method = RequestMethod.GET, path = "/getchat")
	public ResponseEntity<?> getChatWithSpecificUser(HttpServletRequest r, @RequestParam String id, @RequestParam String idTo) {
		ChatMessageResponse response = chatService.getMyChatWith(Long.parseLong(id), Long.parseLong(idTo));
		return new ResponseEntity<ChatMessageResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/getall")
	public ResponseEntity<?> getChatList(HttpServletRequest r, @RequestParam String id) {
		AllChatsResponse response = chatService.getAllChat(Long.parseLong(id));
		return new ResponseEntity<AllChatsResponse>(response, HttpStatus.OK);
	}
	
}
