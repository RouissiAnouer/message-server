package com.app.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.ChatEntityMongoDB;
import com.app.config.entity.UserEntity;
import com.app.config.firebase.PushNotificationService;
import com.app.config.firebase.Model.FCMPushObject;
import com.app.controller.config.ChatAppConstant;
import com.app.model.Message;
import com.app.repository.ChatMongoDBRepository;
import com.app.repository.ChatRepository;
import com.app.repository.UserRepository;
import com.app.service.IUserService;

@RestController
public class ChatService {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	ChatMongoDBRepository chatMongoRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	PushNotificationService pushNotificationService;
	FCMPushObject deviceTokensObject;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IUserService userService;

	@MessageMapping("/message/{id}")
	public void processMessageFromClient(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {
		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom())).message(message.getText())
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).type(message.getType()).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		
		UserEntity user = userService.findByUsername(message.getFrom());
		
		String username = user.getFirstName() + " " +user.getLastName();

		
		Map<String, String> data = new HashMap<>();
		data.put("title", username);
		data.put("body", message.getText());
		
		FCMPushObject push = FCMPushObject.builder()
				.token(user.getDeviceToken().getDeviceToken())
				.body(message.getText())
				.title(username)
				.data(data)
				.packageToSet("com.my.messenger")
				.build();
		
		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
		
		pushNotificationService.sendMessage(push);
	}

	@MessageMapping("/image/{id}")
	public void sendImageToUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {
				
		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom()))
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).fileMessage(message.getText())
				.type(ChatAppConstant.IMAGE).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
	}
	
	@MessageMapping("/audio/{id}")
	public void sendAudioToUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {

		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom()))
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).fileMessage(message.getText())
				.type(ChatAppConstant.AUDIO).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
	}
	
	@MessageMapping("/video/{id}")
	public void sendVideoToUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {

		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom()))
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).fileMessage(message.getText())
				.type(ChatAppConstant.VIDEO).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
	}

}
