package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.config.entity.CoverProfileEntity;


@Repository
public interface UserCoverRepository extends JpaRepository<CoverProfileEntity, Long>{
	
}
