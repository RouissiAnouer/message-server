package com.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.config.entity.ChatEntity;


@Repository
public interface ChatRepository extends CrudRepository<ChatEntity, Long>{
	
	List<ChatEntity> findByIdReceiverAndIdSender(Long id, Long idTo);
	
	List<ChatEntity> findByIdSenderAndIdReceiver(Long id, Long idTo);	

}
