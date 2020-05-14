package com.app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.entity.UserEntity;
import com.app.model.request.JwtRequest;
import com.app.model.request.LogOutRequest;
import com.app.model.request.SignUpRequest;
import com.app.model.response.JwtResponse;
import com.app.repository.UserRepository;
import com.app.security.JwtTokenUtil;
import com.app.service.IUserService;
import com.app.service.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	IUserService userService;
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		UserEntity userInfo = userService.findByUsername(userDetails.getUsername());
		userInfo.setConnected(1);
		userRepository.save(userInfo);
		JwtResponse response = JwtResponse.builder()
				.tokenType("Bearer")
				.token(token)
				.givenName(userInfo.getFirstName())
				.familyName(userInfo.getLastName())
				.userName(userInfo.getUserName())
				.id(userInfo.getId())
				.expiredIn(jwtTokenUtil.getExpirationDateFromToken(token).toInstant().getEpochSecond())
				.avatar(userInfo.getImage())
				.build();
		return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<?> logOut(HttpServletRequest r, @RequestBody LogOutRequest request) throws Exception {
		Optional<UserEntity> userOpt = userRepository.findByUserName(request.getUsername());
		if (userOpt.isPresent()) {
			UserEntity user = userOpt.get();
			user.setConnected(0);
			userRepository.save(user);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(HttpServletRequest r, @RequestBody SignUpRequest request) throws Exception {
		UserEntity response = userService.createUser(UserEntity.of(request), request.getRole());
		return new ResponseEntity<UserEntity>(response, HttpStatus.OK);
	}
	
	

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}