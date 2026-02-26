package com.recuperai.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTIFY)
    private long id;

    @Colunm(nullable = false)
    private String shopify_domain;

    @Column(nullable = false)
    private String access_token:


    
}
