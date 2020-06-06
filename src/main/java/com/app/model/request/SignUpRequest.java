package com.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class SignUpRequest {
	
	@JsonProperty("firstname")
	@Getter @Setter private String firstname;
	@JsonProperty("lastname")
	@Getter @Setter private String lastname;
	@JsonProperty("email")
	@Getter @Setter private String email;
	@JsonProperty("password")
	@Getter @Setter private String password;
	@JsonProperty("role")
	@Getter @Setter private String role;

}
