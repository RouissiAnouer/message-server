package com.app.model.response;

import java.util.List;

import com.app.model.response.component.ChatMessageObj;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ChatMessageResponse {
	
	@Getter @Setter private List<ChatMessageObj> received;
	@Getter @Setter private List<ChatMessageObj> sent;

}
