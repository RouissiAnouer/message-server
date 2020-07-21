package com.app.config.yaml;

import lombok.Getter;
import lombok.Setter;

public class SocketIoConfig {
	
	@Getter @Setter private String socketIoServer;
	@Getter @Setter private int socketIoPort;
	@Getter @Setter private String redisConfigFilePath;

}
