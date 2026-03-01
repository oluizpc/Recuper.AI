package com.recuperai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recuperai.domain.entity.Order;

public interface OrderRepository extends JpaRepository <Long, Order>{

}
