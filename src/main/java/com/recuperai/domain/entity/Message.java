package com.recuperai.domain.entity;

import java.time.LocalDateTime;

import com.recuperai.domain.Enum.Channel;
import com.recuperai.domain.Enum.SendDirection;
import com.recuperai.domain.Enum.SendStatus;
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
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmessage")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idshop",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_message_shop")
    )
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "idcheckout",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_message_checkout")
    )
    private Checkout checkout;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private SendDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private Channel channel;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "idprovider_message")
    private String idProviderMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_status")
    private SendStatus sendStatus;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

}
