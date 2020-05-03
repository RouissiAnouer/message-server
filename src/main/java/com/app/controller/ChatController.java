package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class ChatController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getChatWithSpecificUser(HttpServletRequest r, @RequestParam Long idReceiver) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
