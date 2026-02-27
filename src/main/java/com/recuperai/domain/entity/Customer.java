package com.recuperai.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idcustomer")
    private Long id;

     // FK idshop NOT NULL
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idshop",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_customer_shop")
    )
    private Shop shop;
    
    @Column (name = "shopify_customer_id", nullable = false)
    private Long shopify_customer_id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "phone", nullable = false)
    private String phone;
    
    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column (name = "active")
    private Boolean active;

    // opcional: se quiser navegar Customer -> Checkouts
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Checkout> checkouts;
}
