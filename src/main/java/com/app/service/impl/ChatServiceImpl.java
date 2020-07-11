package com.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.config.entity.ChatEntityMongoDB;
import com.app.config.entity.UserEntity;
import com.app.controller.config.ChatAppConstant;
import com.app.model.response.AllChatsResponse;
import com.app.model.response.ChatMessageResponse;
import com.app.model.response.component.ChatMessageObj;
import com.app.model.response.component.ChatUserObject;
import com.app.repository.ChatMongoDBRepository;
import com.app.repository.ChatRepository;
import com.app.repository.UserRepository;
import com.app.service.IChatService;

@Service
public class ChatServiceImpl implements IChatService {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	ChatMongoDBRepository chatMongoDb;
	@Autowired
	UserRepository userRepository;

	@Override
	public ChatMessageResponse getMyChatWith(Long id, Long idTo) {
		List<ChatEntityMongoDB> messageSent = chatMongoDb.findByIdSenderAndIdReceiver(id, idTo);
		List<ChatEntityMongoDB> messageReceived = chatMongoDb.findByIdReceiverAndIdSender(id, idTo);
		String avatar = null;
		try {
			avatar = userRepository.findById(idTo).get().getPhotoBase64();
		} catch (SQLException e) {
//			TODO Handle exception
		}
		List<ChatMessageObj> listSent = new ArrayList<>();
		messageSent.forEach(msg -> {
			String message = null;
			if (msg.getType().equalsIgnoreCase(ChatAppConstant.TEXT)) {
				message = msg.getMessage();
			} else {
				message = msg.getFileMessage();
			}
			ChatMessageObj obj = ChatMessageObj.builder().id(msg.getId()).message(message)
					.time(msg.getTimestamp()).read((msg.getStatus() == 1)).type(msg.getType()).build();
			listSent.add(obj);
		});
		List<ChatMessageObj> listReceived = new ArrayList<>();
		messageReceived.forEach(msg -> {
			String message = null;
			if (msg.getType().equalsIgnoreCase(ChatAppConstant.TEXT)) {
				message = msg.getMessage();
			} else {
				message = msg.getFileMessage();
			}
			ChatMessageObj obj = ChatMessageObj.builder().id(msg.getId()).message(message)
					.time(msg.getTimestamp()).read((msg.getStatus() == 1)).type(msg.getType()).build();
			listReceived.add(obj);
		});
		ChatMessageResponse response = ChatMessageResponse.builder().received(listReceived).sent(listSent).avatar(avatar).build();
		return response;
	}

	@Override
	public AllChatsResponse getAllChat(long id) {
		List<UserEntity> users = userRepository.findAll();
		List<ChatUserObject> listOfChats = new ArrayList<>();
		users.stream().filter(item -> (item.getId() != id)).forEach(item -> {
			String message = null;
			List<ChatEntityMongoDB> resp = chatMongoDb.findByIdSenderAndIdReceiver(item.getId(), id);
			List<ChatEntityMongoDB> newResp = resp.stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
					.collect(Collectors.toList());
			long count = newResp.stream().count();
			long messagesNotRead[] = {0};
			if (count > 0) {
				Optional<ChatEntityMongoDB> firstResponse = newResp.stream().skip(count - 1).findFirst();
				message = firstResponse.isPresent() ? firstResponse.get().getMessage() : ChatAppConstant.FILE;
//				if (newResp.stream().skip(count - 1).findFirst().get().getMessage() == null) {
//					message = ChatAppConstant.FILE;
//				} else {
//					message = newResp.stream().skip(count - 1).findFirst().get().getMessage();
//				}
				newResp.forEach(msg -> {
					if (msg.getStatus() == 0) {
						messagesNotRead[0]++;
					}
				});
			}
			try {
				ChatUserObject obj = ChatUserObject.builder().avatar(item.getPhotoBase64()).message(message)
						.givenName(item.getFirstName()).familyName(item.getLastName()).status("CONNECTED").id(item.getId())
						.counter(messagesNotRead[0])
						.connected(item.getConnected()==1)
						.build();
				listOfChats.add(obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		AllChatsResponse response = AllChatsResponse.builder().chats(listOfChats).build();
		return response;
	}

	@Override
	public void updateChatStatus(List<String> ids) {
		List<ChatEntityMongoDB> result = chatMongoDb.findByIdIn(new HashSet<String>(ids));
		result.forEach(item -> {
			item.setStatus(1);
		});
		chatMongoDb.saveAll(result);
	}

}
