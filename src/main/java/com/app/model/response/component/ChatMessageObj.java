package com.app.model.response.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
public class ChatMessageObj {
	
	@Getter @Setter private String message;
	@Getter @Setter private String id;
	@Getter @Setter private Long time;
	@Getter @Setter private boolean read;
	@Getter @Setter private String type;

}
