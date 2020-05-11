package com.app.chat;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.config.entity.ChatEntity;
import com.app.config.entity.UserEntity;
import com.app.repository.ChatRepository;
import com.app.repository.UserRepository;

@SpringBootTest
class UpdateStatus {
	
	@Autowired ChatRepository chatRep;
	@Autowired UserRepository userRep;
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(chatRep).isNotNull();
	}

	@Test
	void testUpdateChatStatus() {
		Iterable<ChatEntity> chats = chatRep.findAll();
		chats.forEach(chat -> {
			chat.setStatus(0);
		});
		chatRep.saveAll(chats);
	}
	
	@Test
	void testUpdateUserStatus() {
		List<UserEntity> users = userRep.findAll();
		users.forEach(user -> {
			user.setConnected(0);
		});
		userRep.saveAll(users);
	}

}
