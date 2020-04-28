package com.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.config.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	Optional<UserEntity> findByUserName(String username);
}
