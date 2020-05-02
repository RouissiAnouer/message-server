package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.UserEntity;
import com.app.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/userinfo")
	public ResponseEntity<UserEntity> getUser(HttpServletRequest r, @RequestParam String email) {
		UserEntity userOpt = userService.findByUsername(email);
		if (userOpt != null) {
			return ResponseEntity.ok(userOpt);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
