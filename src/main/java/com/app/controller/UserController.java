package com.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.ChatEntity;
import com.app.config.entity.UserEntity;
import com.app.model.Message;
import com.app.model.OutputMessage;
import com.app.repository.ChatRepository;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired ChatRepository chatRepository;
	@Autowired IUserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<UserEntity> getUser(HttpServletRequest r, @RequestParam String idUser) {
		Optional<UserEntity> userOpt = userService.getUserbyId(Long.parseLong(idUser));
		if (userOpt.isPresent()) {
			return ResponseEntity.ok(userOpt.get());
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
		
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage send(Message message) throws Exception {
		System.out.println(message.getText());
	    String time = new SimpleDateFormat("HH:mm").format(new Date());
	    ChatEntity chat = ChatEntity.builder()
	    		.idSender(Long.parseLong(message.getFrom()))
	    		.idReceiver(Long.parseLong(message.getReceiver()))
	    		.message(message.getText())
	    		.timestamp(time)
	    		.build();
	    chatRepository.save(chat);
	    return OutputMessage.builder().from(message.getFrom()).text(message.getText()).timeStamp(time).receiver(message.getReceiver()).build();
	}

	
	
}
