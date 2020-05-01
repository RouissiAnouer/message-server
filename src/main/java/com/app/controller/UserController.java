package com.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.ChatEntity;
import com.app.config.entity.UserEntity;
import com.app.model.Message;
import com.app.model.request.SignUpRequest;
import com.app.repository.ChatRepository;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	IUserService userService;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<UserEntity> getUser(HttpServletRequest r, @RequestParam String idUser) {
		Optional<UserEntity> userOpt = userService.getUserbyId(Long.parseLong(idUser));
		if (userOpt.isPresent()) {
			return ResponseEntity.ok(userOpt.get());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(HttpServletRequest r, @RequestBody SignUpRequest request) throws Exception {
		UserEntity response = userService.createUser(UserEntity.of(request), request.getRole());
//		userService.createRole("USER");
		return new ResponseEntity<UserEntity>(response, HttpStatus.OK);
	}

	@MessageMapping("/message/{id}")
	public void processMessageFromClient(@Payload Message message, SimpMessageHeaderAccessor headerAccessor, @DestinationVariable("id") String id)
			throws Exception {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		ChatEntity chat = ChatEntity.builder().idSender(Long.parseLong(message.getFrom()))
				.message(message.getText()).idReceiver(Long.parseLong(id)).timestamp(time).build();
		System.out.println(message.getText());
		chatRepository.save(chat);
		simpMessagingTemplate.convertAndSend("/topic/reply."+id, message);

	}

}
