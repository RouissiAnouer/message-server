package com.app.model.response;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import com.app.config.entity.ChatEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public class UserInfoResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3648703448526992920L;
	
	@Getter @Setter private String userName;
	@Getter @Setter private String givenName;
	@Getter @Setter private String familyName;
	@Getter @Setter private Long id;
	@Getter @Setter private List<ChatEntity> sent;
	@Getter @Setter private List<ChatEntity> received;
	@JsonProperty(value = "userAvatar")
	@Getter @Setter private Blob avatar;
}
