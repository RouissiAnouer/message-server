package com.app.model.response;

import java.util.List;

import com.app.model.response.component.ChatUserObject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class AllChatsResponse {
	
	@Getter @Setter private List<ChatUserObject> chats;

}
