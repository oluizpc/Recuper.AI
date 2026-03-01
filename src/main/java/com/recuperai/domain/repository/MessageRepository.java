package com.recuperai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recuperai.domain.entity.Message;

public interface MessageRepository extends JpaRepository<Long, Message>{

}
