    package com.recuperai.domain.entity;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    
    import org.hibernate.annotations.JdbcTypeCode;
    import org.hibernate.type.SqlTypes;

import com.recuperai.domain.enums.CheckoutStatus;

import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.EnumType;
    import jakarta.persistence.Enumerated;
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
    @Table(name = "checkouts")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Checkout {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idcheckout")
        private Integer id;
        
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(
            name = "idshop",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_shop")
        )
        private Shop shop;

        @Column(name = "idshopify_checkout", nullable = false)
        private Long shopifyCheckoutId;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(
            name = "idcustomer",
            nullable = false,
            foreignKey 
            = @ForeignKey(name = "fk_customer")
        )
        private Customer customer;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "checkout_status")
        @JdbcTypeCode(SqlTypes.NAMED_ENUM)
        private CheckoutStatus status;

        @Column (name = "total_value", precision = 12, scale = 2)
        private BigDecimal totalValue;

        @Column (name = "currency")
        private String currency;

        @Column (name = "recovery_url")
        private String recoveryUrl;

        @Column (name = "last_event_at")
        private LocalDateTime lastEventAt;

        @Column (name = "abandoned_at")
        private LocalDateTime abandonedAt;

        @Column (name = "updated_at")
        private LocalDateTime updatedAt;

        @Column (name = "created_at")
        private LocalDateTime createdAt;
        
        @Column (name = "active")
        private Boolean active;  
    }
