package com.app.service;

import java.util.List;

import com.app.model.response.AllChatsResponse;
import com.app.model.response.ChatMessageResponse;

public interface IChatService {

	ChatMessageResponse getMyChatWith(Long id, Long idTo);

	AllChatsResponse getAllChat(long parseLong);

	void updateChatStatus(List<Long> ids);

}
