package com.recuperai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recuperai.domain.entity.CheckoutItem;

public interface CheckoutItemRepository extends JpaRepository<CheckoutItem,Long>{

}
