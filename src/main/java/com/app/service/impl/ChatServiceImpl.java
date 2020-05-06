package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.config.entity.ChatEntity;
import com.app.config.entity.UserEntity;
import com.app.model.response.AllChatsResponse;
import com.app.model.response.ChatMessageResponse;
import com.app.model.response.component.ChatMessageObj;
import com.app.model.response.component.ChatUserObject;
import com.app.repository.ChatRepository;
import com.app.repository.UserRepository;
import com.app.service.IChatService;

@Service
public class ChatServiceImpl implements IChatService {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public ChatMessageResponse getMyChatWith(Long id, Long idTo) {
		List<ChatEntity> messageSent = chatRepository.findByIdSenderAndIdReceiver(id, idTo);
		List<ChatEntity> messageReceived = chatRepository.findByIdReceiverAndIdSender(id, idTo);
		List<ChatMessageObj> listSent = new ArrayList<>();
		messageSent.forEach(msg -> {
			ChatMessageObj obj = ChatMessageObj.builder().id(msg.getId()).message(msg.getMessage())
					.time(msg.getTimestamp()).build();
			listSent.add(obj);
		});
		List<ChatMessageObj> listReceived = new ArrayList<>();
		messageReceived.forEach(msg -> {
			ChatMessageObj obj = ChatMessageObj.builder().id(msg.getId()).message(msg.getMessage())
					.time(msg.getTimestamp()).build();
			listReceived.add(obj);
		});
		ChatMessageResponse response = ChatMessageResponse.builder().received(listReceived).sent(listSent).build();
		return response;
	}

	@Override
	public AllChatsResponse getAllChat(long id) {
		List<UserEntity> users = userRepository.findAll();
		List<ChatUserObject> listOfChats = new ArrayList<>();
		users.stream().filter(item -> (item.getId() != id)).forEach(item -> {
			String message = null;
			List<ChatEntity> resp = chatRepository.findByIdSenderAndIdReceiver(item.getId(), id);
			List<ChatEntity> newResp = resp.stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
					.collect(Collectors.toList());
			long count = newResp.stream().count();
			if (count > 0) {
				message = newResp.stream().skip(count - 1).findFirst().get().getMessage();
			}
			ChatUserObject obj = ChatUserObject.builder().avatar("assets/icon/img_avatar2.png").message(message)
					.givenName(item.getFirstName()).familyName(item.getLastName()).status("CONNECTED").id(item.getId())
					.counter(count)
					.build();
			listOfChats.add(obj);
		});
		AllChatsResponse response = AllChatsResponse.builder().chats(listOfChats).build();
		return response;
	}

}
