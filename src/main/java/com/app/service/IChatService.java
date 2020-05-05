package com.app.service;

import com.app.model.response.ChatMessageResponse;

public interface IChatService {

	ChatMessageResponse getMyChatWith(Long id, Long idTo);

}
