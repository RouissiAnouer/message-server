package com.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.config.entity.ChatEntity;


@Repository
public interface ChatRepository extends CrudRepository<ChatEntity, Long>{

}
