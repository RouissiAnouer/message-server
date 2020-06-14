package com.app.config.yaml;

import lombok.Getter;
import lombok.Setter;

public class DBProperties {
	
	@Getter @Setter private String driverClassName;
	@Getter @Setter private String urlDB;
	@Getter @Setter private String userName;
	@Getter @Setter private String password;

}
