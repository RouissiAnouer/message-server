package com.app.socket;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.ChatEntity;
import com.app.model.Message;
import com.app.repository.ChatRepository;

@RestController
@CrossOrigin(origins = "*")
public class ChatService {
	
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/message/{id}")
	public void processMessageFromClient(@Payload Message message, SimpMessageHeaderAccessor headerAccessor, @DestinationVariable("id") String id)
			throws Exception {
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		ChatEntity chat = ChatEntity.builder().idSender(Long.parseLong(message.getFrom()))
				.message(message.getText()).idReceiver(Long.parseLong(id)).timestamp(time).build();
		System.out.println(message.getText());
		chatRepository.save(chat);
		simpMessagingTemplate.convertAndSend("/topic/reply."+id, message);

	}

}
