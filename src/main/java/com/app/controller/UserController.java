package com.app.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.config.entity.UserEntity;
import com.app.model.response.UserInfoResponse;
import com.app.repository.UserRepository;
import com.app.service.IUserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;

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
						.received(new ArrayList<>(user.getReceived()))
						.sent(new ArrayList<>(user.getChats()))
						.userName(user.getUserName())
						.userAvatar(user.getPhotoBase64())
						.build();
			} catch (SQLException e) {
				e.printStackTrace();
				obj = null;
			}
			response.add(obj);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/uploadimage")
	public ResponseEntity<?> uploadImage(HttpServletRequest r, @RequestParam("image") MultipartFile file) throws IOException {
		UserEntity user = userService.findByUsername(file.getOriginalFilename());
		if (user != null) {
			Blob image = BlobProxy.generateProxy(file.getBytes());
			user.setImage(image);
			userRepository.save(user);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
