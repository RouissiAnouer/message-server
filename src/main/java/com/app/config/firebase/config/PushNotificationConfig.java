package com.app.config.firebase.config;

import java.io.IOException;
import java.util.HashMap;

import com.app.config.firebase.Model.PushObjectFcm;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class PushNotificationConfig extends HashMap<String, FirebaseApp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 126864543871332L;

	public PushNotificationConfig addApp(PushObjectFcm o) throws IOException {
		log.debug("Push service load app cert: " + o.getAppName().toString());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(o.getFileJsonPath()))
				.build();
		
		this.put(o.getAppName(), FirebaseApp.initializeApp(options, o.getAppName()));

		return this;
	}

	public static PushNotificationConfig instance() {
		return new PushNotificationConfig();
	}
}
