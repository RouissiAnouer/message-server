package com.app.service.impl;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.config.entity.Role;
import com.app.config.entity.UserEntity;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserEntity createUser(UserEntity user, String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole(roleRepository.findByName(role));
		UserEntity userCreated = userRepository.save(user);
		
		return userCreated;
	}

	@Override
	public Optional<UserEntity> getUserbyId(Long idUser) {
		Optional<UserEntity> userOpt = userRepository.findById(idUser);
		return userOpt;
	}
	
	@Override
    public UserEntity findByUsername(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUserName(username);
        return userOpt.get();
    }

	@Override
	public void createRole(String string) {
		Role role = new Role(string);
		roleRepository.save(role);
	}

}
