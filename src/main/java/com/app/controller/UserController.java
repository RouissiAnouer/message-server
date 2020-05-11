package com.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.UserEntity;
import com.app.model.request.UploadImageRequest;
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
	public ResponseEntity<UserInfoResponse> getUser(HttpServletRequest r, @RequestParam String email) {
		UserEntity userOpt = userService.findByUsername(email);
		
		if (userOpt != null) {
			UserInfoResponse obj = UserInfoResponse.builder()
					.familyName(userOpt.getLastName())
					.givenName(userOpt.getFirstName())
					.id(userOpt.getId())
					.received(new ArrayList<>(userOpt.getReceived()))
					.sent(new ArrayList<>(userOpt.getChats()))
					.userName(userOpt.getUserName())
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
			UserInfoResponse obj = UserInfoResponse.builder()
					.familyName(user.getLastName())
					.givenName(user.getFirstName())
					.id(user.getId())
					.received(new ArrayList<>(user.getReceived()))
					.sent(new ArrayList<>(user.getChats()))
					.userName(user.getUserName())
					.build();
			response.add(obj);
		});
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/uploadimage")
	public ResponseEntity<?> uploadImage(HttpServletRequest r, @RequestBody UploadImageRequest request) {
		UserEntity user = userService.findByUsername(request.getUsername());
		if (user != null) {
			user.setAvatar(request.getImage());
			userRepository.save(user);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
