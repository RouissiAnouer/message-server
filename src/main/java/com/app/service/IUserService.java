package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.config.entity.UserEntity;

public interface IUserService {
	
	UserEntity createUser(UserEntity user, String role) throws Exception;

	Optional<UserEntity> getUserbyId(Long idUser);
	
	UserEntity findByUsername(String username);

	void createRole(String string);

	List<UserEntity> getAll();

}
