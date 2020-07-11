package com.app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.config.entity.ChatEntityMongoDB;

@Repository
public interface ChatMongoDBRepository extends MongoRepository<ChatEntityMongoDB, String> {

	List<ChatEntityMongoDB> findByIdReceiverAndIdSender(Long id, Long idTo);

	List<ChatEntityMongoDB> findByIdSenderAndIdReceiver(Long id, Long idTo);

	List<ChatEntityMongoDB> findByIdIn(Set<String> ids);

}
