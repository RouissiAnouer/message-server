package com.app.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public UserEntity createUser(UserEntity user, String role) throws Exception {
		boolean exist = userRepository.findByUserName(user.getUserName()).isPresent();
		if (exist) 
			throw new Exception(user.getUserName() + " already exist");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (!roleRepository.findByName(role).isPresent()) {
			throw new Exception("missing role");
		}
		if (role.equalsIgnoreCase("ADMIN")) {
			user.setRoles(new HashSet<>(roleRepository.findAll()));
		} else {
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByName(role).get());
			user.setRoles(roles);
		}
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

	@Override
	public List<UserEntity> getAll() {
		List<UserEntity> users = userRepository.findAll();
		return users;
	}

}
