package com.app.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.config.entity.CoverProfileEntity;
import com.app.config.entity.UserEntity;
import com.app.config.firebase.PushNotificationService;
import com.app.config.firebase.Model.FCMPushObject;
import com.app.model.request.DeviceTokenRequest;
import com.app.model.response.UserInfoResponse;
import com.app.repository.UserCoverRepository;
import com.app.repository.UserRepository;
import com.app.service.IUserService;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserCoverRepository userCoverRepository;
	@Autowired
	PushNotificationService pushNotificationService;

	@RequestMapping(method = RequestMethod.GET, path = "/userinfo")
	public ResponseEntity<UserInfoResponse> getUser(HttpServletRequest r, @RequestParam String email) throws SQLException {
		UserEntity userOpt = userService.findByUsername(email);
		
		if (userOpt != null) {
			UserInfoResponse obj = UserInfoResponse.builder()
					.familyName(userOpt.getLastName())
					.givenName(userOpt.getFirstName())
					.id(userOpt.getId())
					.userName(userOpt.getUserName())
					.userAvatar(userOpt.getPhotoBase64())
					.build();
			if (userOpt.getCover() != null) {
				obj.setCover(userOpt.getCover().getPhotoBase64());
			}
			return ResponseEntity.ok(obj);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/getall")
	public ResponseEntity<List<UserInfoResponse>> getAllUser(HttpServletRequest r, @RequestParam String username) {
		List<UserEntity> users = userService.getAll();
		List<UserEntity> newList = users.stream().filter(user -> !user.getUserName().equalsIgnoreCase(username)).collect(Collectors.toList());
		List<UserInfoResponse> response = new ArrayList<>();
		newList.forEach(user -> {
			UserInfoResponse obj;
			try {
				obj = UserInfoResponse.builder()
						.familyName(user.getLastName())
						.givenName(user.getFirstName())
						.id(user.getId())
						.userName(user.getUserName())
						.userAvatar(user.getPhotoBase64())
						.build();
				if (user.getCover() != null) {
					obj.setCover(user.getCover().getPhotoBase64());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				obj = null;
			}
			response.add(obj);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/image/profile")
	public ResponseEntity<?> uploadImage(HttpServletRequest r, @RequestParam("image") MultipartFile file) throws IOException {
		UserEntity user = userService.findByUsername(file.getOriginalFilename());
		if (user != null) {
			Blob image = BlobProxy.generateProxy(file.getBytes());
			user.setImage(image);
			userRepository.save(user);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/image/cover")
	public ResponseEntity<?> uploadCover(HttpServletRequest r, @RequestParam("image") MultipartFile file) throws IOException {
		UserEntity user = userService.findByUsername(file.getOriginalFilename());
		if (user != null) {
			Blob image = BlobProxy.generateProxy(file.getBytes());
			if (user.getCover() == null) {		
				CoverProfileEntity entity = CoverProfileEntity.builder().cover(image).user(user).build();
				user.setCover(entity);
				userRepository.save(user);
			} else {
				Optional<CoverProfileEntity> coverOpt = userCoverRepository.findById(user.getCover().getId());
				if (coverOpt.isPresent()) {
					coverOpt.get().setCover(image);
					userCoverRepository.save(coverOpt.get());
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/push/register")
	public ResponseEntity<?> registerDeviceToken(HttpServletRequest r, @RequestBody DeviceTokenRequest tokenObject) throws FirebaseMessagingException {
		FCMPushObject pushObject = FCMPushObject.builder()
				.packageToSet("com.my.messenger")
				.topic(tokenObject.getTopic())
				.build();
		pushObject.addDeviceTokens(tokenObject.getDeviceToken(), "com.my.messenger");
		ResponseEntity<?> response = pushNotificationService.register(pushObject);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

}
