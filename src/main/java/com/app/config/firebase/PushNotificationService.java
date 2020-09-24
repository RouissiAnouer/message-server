package com.app.config.firebase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.config.firebase.Model.FCMPushObject;
import com.app.config.firebase.config.PushNotificationConfig;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.TopicManagementResponse;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class PushNotificationService {

	private final PushNotificationConfig config;

	public ResponseEntity<?> sendMessage(FCMPushObject notificationObject) throws FirebaseMessagingException {
		FirebaseApp app = config.get(notificationObject.getPackageToSet());
		Message message = Message.builder().putAllData(notificationObject.getData())
				.setAndroidConfig(
						AndroidConfig.builder().setRestrictedPackageName(notificationObject.getPackageToSet()).build())
				.setApnsConfig(ApnsConfig.builder()
						.setAps(Aps.builder()
								.setAlert(ApsAlert.builder().setTitle(notificationObject.getTitle())
										.setBody(notificationObject.getBody()).build())
								.build())
						.build())
				.setToken(notificationObject.getDeviceTokensMultiCast().get(0).getDeviceToken()).build();
		String response = FirebaseMessaging.getInstance(app).send(message);
		return ResponseEntity.ok("Successfully sent message: " + response);
	}

	public ResponseEntity<?> sendMultiCastMessage(FCMPushObject notificationObject) throws Exception {

		FirebaseApp app = config.get(notificationObject.getPackageToSet());
		List<String> registrationTokens = notificationObject.getDeviceTokensString();

		MulticastMessage message = MulticastMessage.builder().addAllTokens(registrationTokens)
				.putAllData(notificationObject.getData())
				.setAndroidConfig(
						AndroidConfig.builder().setRestrictedPackageName(notificationObject.getPackageToSet()).build())
				.setApnsConfig(
						ApnsConfig.builder()
								.setAps(Aps.builder()
										.setAlert(ApsAlert.builder().setTitle(notificationObject.getTitle())
												.setBody(notificationObject.getBody()).build())
										.build())
								.build())
				.build();
		BatchResponse response = FirebaseMessaging.getInstance(app).sendMulticast(message);
		if (response.getFailureCount() > 0) {
			List<SendResponse> responses = response.getResponses();
			List<String> failedTokens = new ArrayList<>();
			for (int i = 0; i < responses.size(); i++) {
				if (!responses.get(i).isSuccessful()) {
					// The order of responses corresponds to the order of the registration tokens.
					failedTokens.add(registrationTokens.get(i));
				}
			}

			log.info("List of tokens that caused failures: " + failedTokens);
		}
		return ResponseEntity.ok("List of tokens MultiSend success: " + response.getSuccessCount());
	}

	public ResponseEntity<?> sendTopic(FCMPushObject notificationObject) throws FirebaseMessagingException {

		FirebaseApp app = config.get(notificationObject.getPackageToSet());

		Message message = Message.builder().putAllData(notificationObject.getData())
				.setAndroidConfig(
						AndroidConfig.builder().setRestrictedPackageName(notificationObject.getPackageToSet()).build())
				.setApnsConfig(ApnsConfig.builder()
						.setAps(Aps.builder()
								.setAlert(ApsAlert.builder().setTitle(notificationObject.getTitle())
										.setBody(notificationObject.getBody()).build())
								.build())
						.build())
				.setTopic(notificationObject.getTopic()).build();

		String response = FirebaseMessaging.getInstance(app).send(message);
		return ResponseEntity.ok("Successfully sent message: " + response);
	}

	public ResponseEntity<?> register(FCMPushObject registerObject) throws FirebaseMessagingException {

		FirebaseApp app = config.get(registerObject.getPackageToSet());
		TopicManagementResponse response = FirebaseMessaging.getInstance(app)
				.subscribeToTopic(registerObject.getDeviceTokensString(), registerObject.getTopic());

		return ResponseEntity.ok(response.getSuccessCount() + " tokens were subscribed successfully");
	}

	public ResponseEntity<?> sendBatchMessages(List<FCMPushObject> batchObject, String prefix_app)
			throws FirebaseMessagingException {
		FirebaseApp app = config.get(prefix_app);
		List<Message> messages = new ArrayList<Message>();
		batchObject.forEach(batch -> {
			if (batch.getTopic() != null) {
				Message message = Message.builder().putAllData(batch.getData())
						.setAndroidConfig(
								AndroidConfig.builder().setRestrictedPackageName(batch.getPackageToSet()).build())
						.setApnsConfig(
								ApnsConfig.builder()
										.setAps(Aps.builder()
												.setAlert(ApsAlert.builder().setTitle(batch.getTitle())
														.setBody(batch.getBody()).build())
												.build())
										.build())
						.setTopic(batch.getTopic()).build();
				messages.add(message);

			} else if (batch.getToken() != null) {
				Message message = Message.builder().putAllData(batch.getData())
						.setAndroidConfig(
								AndroidConfig.builder().setRestrictedPackageName(batch.getPackageToSet()).build())
						.setApnsConfig(
								ApnsConfig.builder()
										.setAps(Aps.builder()
												.setAlert(ApsAlert.builder().setTitle(batch.getTitle())
														.setBody(batch.getBody()).build())
												.build())
										.build())
						.setToken(batch.getToken()).build();
				messages.add(message);
			}
		});
		BatchResponse response = FirebaseMessaging.getInstance(app).sendAll(messages);
		return ResponseEntity.ok("Successfully sent messages: " + response);
	}
}
