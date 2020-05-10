package com.app.chat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.config.entity.ChatEntity;
import com.app.repository.ChatRepository;

@SpringBootTest
class UpdateStatus {
	
	@Autowired ChatRepository chatRep;
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(chatRep).isNotNull();
	}

	@Test
	void test() {
		Iterable<ChatEntity> chats = chatRep.findAll();
		chats.forEach(chat -> {
			chat.setStatus(0);
		});
		chatRep.saveAll(chats);
	}

}
