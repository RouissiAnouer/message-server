package com.app.config.yaml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ChatApplicationProp {
	
	@Getter @Setter private DBProperties dbSQL;
	@Getter @Setter private NoSQLDBProperties dbNoSQL;
	@Getter @Setter private SocketIoConfig SocketIo;
}
