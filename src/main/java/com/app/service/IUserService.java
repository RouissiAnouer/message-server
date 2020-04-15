package com.app.service;

import java.util.Optional;

import com.app.config.entity.UserEntity;

public interface IUserService {
	
	UserEntity createUser(UserEntity user);

	Optional<UserEntity> getUserbyId(Long idUser);

}
