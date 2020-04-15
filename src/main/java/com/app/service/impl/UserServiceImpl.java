package com.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.config.entity.UserEntity;
import com.app.repository.UserRepository;
import com.app.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired UserRepository userRepository;

	@Override
	public UserEntity createUser(UserEntity user) {
		UserEntity userCreated = userRepository.save(user);
		return userCreated;
	}

	@Override
	public Optional<UserEntity> getUserbyId(Long idUser) {
		Optional<UserEntity> userOpt = userRepository.findById(idUser);
		return userOpt;
	}

}
