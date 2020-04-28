package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.config.entity.UserEntity;
import com.app.model.UserPrincipal;
import com.app.repository.UserRepository;

public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userOpt = userRepository.findByUserName(username);
		if (!userOpt.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(userOpt.get());
	}

}
