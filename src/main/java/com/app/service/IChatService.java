package com.app.service;

import com.app.model.response.AllChatsResponse;
import com.app.model.response.ChatMessageResponse;

public interface IChatService {

	ChatMessageResponse getMyChatWith(Long id, Long idTo);

	AllChatsResponse getAllChat(long parseLong);

}
