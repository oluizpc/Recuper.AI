package com.recuperai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recuperai.domain.entity.Checkout;

public interface CheckoutRepository extends JpaRepository <Long, Checkout>{

}
