package com.recuperai.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idorder")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idshop",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_orders_shop")
    )
    private Shop shop;
    
    @Column (name = "idshopify_order", nullable = false)
    private Long idShopifyOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idcheckout",
        nullable = true,
        foreignKey = @ForeignKey(name = "fk_order_checkout")
    )
    private Checkout checkout;

    @Column(name = "total_value", precision = 12, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "currency")
    private String currency;

    @Column (name = "created_at")
    private LocalDateTime createdAt;
}
