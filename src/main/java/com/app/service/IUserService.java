package com.app.service;

import java.util.Optional;

import com.app.config.entity.UserEntity;

public interface IUserService {
	
	UserEntity createUser(UserEntity user, String role);

	Optional<UserEntity> getUserbyId(Long idUser);
	
	UserEntity findByUsername(String username);

	void createRole(String string);

}
