package com.recuperai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recuperai.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
