package com.app.chat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.config.entity.UserEntity;
import com.app.repository.ChatRepository;
import com.app.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppApplicationChatTests {
//	UserEntity user = new UserEntity();
	@Autowired private UserRepository userRepository;
	@Test
	void contextLoads() {
//		UserEntity user = new UserEntity();
//		user.setFirstName("Anouer");
//		user.setLastName("Rouissi");
//		user.setEmail("anouerrouissi@yahoo.fr");
//		user.setPassword("123456");
//		UserEntity user1 = new UserEntity();
//		user1.setFirstName("Hana");
//		user1.setLastName("Driss");
//		user1.setEmail("hanadriss@yahoo.fr");
//		user1.setPassword("123456");
//		userRepository.save(user);
//		userRepository.save(user1);
		Optional<UserEntity> userOpt = userRepository.findById(Long.parseLong("1"));
//		ChatEntity chat = new ChatEntity();
//		chat.setIdReceiver(Long.parseLong("2"));
//		chat.setIdSender(Long.parseLong("1"));
//		chat.setMessage("hello Hana from Anouer");
//		ChatEntity resp = chatRepository.save(chat);
		if (userOpt.isPresent()) {
			UserEntity user = userOpt.get();
//			user.getChats().add(resp);
			user.getChats().forEach(item -> {
				System.out.println(item.getMessage());
			});
		}
		
//		Iterable<UserEntity> users = userRepository.findAll();
//		users.forEach(item -> {
//			System.out.println(item.getFirstName() + " : " + item.getId());
//		});
//		userRepository.deleteAll();
	}

}
