package com.app.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.ChatEntityMongoDB;
import com.app.controller.config.ChatAppConstant;
import com.app.model.Message;
import com.app.repository.ChatMongoDBRepository;
import com.app.repository.ChatRepository;

@RestController
public class ChatService {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	ChatMongoDBRepository chatMongoRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/message/{id}")
	public void processMessageFromClient(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {
		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom())).message(message.getText())
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).type(message.getType()).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
	}

	@MessageMapping("/image/{id}")
	public void sendImageToUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor,
			@DestinationVariable("id") String id) throws Exception {
		
//		byte[] dataBytes = Base64.getEncoder().encode(message.getText().getBytes());
//		Blob image = BlobProxy.generateProxy(dataBytes);
				
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
		
//		byte[] dataBytes = Base64.getEncoder().encode(message.getText().getBytes());
//		Blob image = BlobProxy.generateProxy(dataBytes);
				
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
		
//		byte[] dataBytes = Base64.getEncoder().encode(message.getText().getBytes());
//		Blob image = BlobProxy.generateProxy(dataBytes);
				
		ChatEntityMongoDB chat = ChatEntityMongoDB.builder().idSender(Long.parseLong(message.getFrom()))
				.idReceiver(Long.parseLong(id)).timestamp(message.getTime()).status(0).fileMessage(message.getText())
				.type(ChatAppConstant.VIDEO).build();
		ChatEntityMongoDB chatEntity = chatMongoRepository.save(chat);
		message.setId(chatEntity.getId());

		simpMessagingTemplate.convertAndSend("/topic/reply." + id, message);
	}

}
