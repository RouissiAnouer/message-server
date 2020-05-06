package com.app.model.response.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ChatUserObject {

	@Getter @Setter private String message;
	@Getter @Setter private String avatar;
	@Getter @Setter private String givenName;
	@Getter @Setter private String familyName;
	@Getter @Setter private String status;
	@Getter @Setter private Long id;
	@Getter @Setter private Long counter;
}
