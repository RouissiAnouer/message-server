package com.app.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@SuperBuilder
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	@Getter @Setter private String token;
	@Getter @Setter private String userName;
	@Getter @Setter private String givenName;
	@Getter @Setter private String familyName;
	@Getter @Setter private Long expiredIn;
	@Getter @Setter private String tokenType;
	@Getter @Setter private Long id;
	@Getter @Setter private String userAvatar;

}
