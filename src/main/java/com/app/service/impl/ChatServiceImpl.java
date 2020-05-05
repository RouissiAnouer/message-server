package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.config.entity.ChatEntity;
import com.app.model.response.ChatMessageResponse;
import com.app.model.response.component.ChatMessageObj;
import com.app.repository.ChatRepository;
import com.app.service.IChatService;

@Service
public class ChatServiceImpl implements IChatService {
	
	@Autowired
	ChatRepository chatRepository;

	@Override
	public ChatMessageResponse getMyChatWith(Long id, Long idTo) {
		List<ChatEntity> messageSent = chatRepository.findByIdSenderAndIdReceiver(id, idTo);
		List<ChatEntity> messageReceived = chatRepository.findByIdReceiverAndIdSender(id, idTo);
		List<ChatMessageObj> listSent = new ArrayList<>();
		messageSent.forEach(msg -> {
			ChatMessageObj obj = ChatMessageObj.builder()
					.id(msg.getId())
					.message(msg.getMessage())
					.time(msg.getTimestamp())
					.build();
			listSent.add(obj);
		});
		List<ChatMessageObj> listReceived = new ArrayList<>();
		messageReceived.forEach(msg -> {
			ChatMessageObj obj = ChatMessageObj.builder()
					.id(msg.getId())
					.message(msg.getMessage())
					.time(msg.getTimestamp())
					.build();
			listReceived.add(obj);
		});
		ChatMessageResponse response = ChatMessageResponse.builder()
				.received(listReceived)
				.sent(listSent)
				.build();
		return response;
	}

}
