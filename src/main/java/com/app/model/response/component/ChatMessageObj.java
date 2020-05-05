package com.app.model.response.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
public class ChatMessageObj {
	
	@Getter @Setter private String message;
	@Getter @Setter private Long id;
	@Getter @Setter private String time;

}
