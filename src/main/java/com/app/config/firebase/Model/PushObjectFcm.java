package com.app.config.firebase.Model;

import java.io.FileInputStream;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class PushObjectFcm {
	
	@Getter @Setter private String appName;
	@Getter @Setter private FileInputStream fileJsonPath;

}
