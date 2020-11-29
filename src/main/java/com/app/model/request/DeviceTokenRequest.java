package com.app.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public class DeviceTokenRequest {

	@Getter @Setter private String deviceToken;
	@Getter @Setter private String username;
}
