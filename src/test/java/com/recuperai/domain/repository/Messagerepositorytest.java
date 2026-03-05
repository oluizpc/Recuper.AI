package com.recuperai.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.recuperai.domain.entity.Checkout;
import com.recuperai.domain.entity.Customer;
import com.recuperai.domain.entity.Message;
import com.recuperai.domain.entity.Shop;
import com.recuperai.domain.enums.Channel;
import com.recuperai.domain.enums.CheckoutStatus;
import com.recuperai.domain.enums.SendDirection;
import com.recuperai.domain.enums.SendStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Shop shop;
    private Checkout checkout;

    @BeforeEach
    void setUp() {
        Shop s = new Shop();
        s.setShopDomain("message-test-shop.myshopify.com");
        s.setAccessToken("token-message");
        s.setActive(true);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        shop = shopRepository.save(s);

        Customer c = new Customer();
        c.setShop(shop);
        c.setShopify_customer_id(444L);
        c.setName("Ana Msg");
        c.setPhone("+5511966666666");
        c.setEmail("ana@email.com");
        c.setUpdatedAt(LocalDateTime.now());
        c.setActive(true);
        Customer customer = customerRepository.save(c);

        Checkout ch = new Checkout();
        ch.setShop(shop);
        ch.setCustomer(customer);
        ch.setShopifyCheckoutId(888L);
        ch.setStatus(CheckoutStatus.ABANDONED);
        ch.setTotalValue(new BigDecimal("150.00"));
        ch.setCurrency("BRL");
        ch.setCreatedAt(LocalDateTime.now());
        ch.setUpdatedAt(LocalDateTime.now());
        ch.setActive(true);
        checkout = checkoutRepository.save(ch);
    }

    private Message buildMessage() {
        Message message = new Message();
        message.setShop(shop);
        message.setCheckout(checkout);
        message.setDirection(SendDirection.OUTBOUND);
        message.setChannel(Channel.WHATSAPP);
        message.setContent("Olá! Você esqueceu algo no carrinho 🛒");
        message.setIdProviderMessage("provider-msg-001");
        message.setSendStatus(SendStatus.SENT);
        message.setCreatedAt(LocalDateTime.now());
        return message;
    }

    @Test
    @DisplayName("Deve salvar e recuperar uma mensagem pelo id")
    void deveSalvarERecuperarMessage() {
        Message message = messageRepository.save(buildMessage());

        assertThat(message.getId()).isNotNull();

        Message encontrada = messageRepository.findById(message.getId()).orElseThrow();
        assertThat(encontrada.getContent()).isEqualTo("Olá! Você esqueceu algo no carrinho 🛒");
        assertThat(encontrada.getChannel()).isEqualTo(Channel.WHATSAPP);
        assertThat(encontrada.getDirection()).isEqualTo(SendDirection.OUTBOUND);
        assertThat(encontrada.getSendStatus()).isEqualTo(SendStatus.SENT);
    }

    @Test
    @DisplayName("Deve atualizar o status de envio de uma mensagem")
    void deveAtualizarStatusDaMensagem() {
        Message message = messageRepository.save(buildMessage());

        message.setSendStatus(SendStatus.DELIVERED);
        messageRepository.save(message);

        Message atualizada = messageRepository.findById(message.getId()).orElseThrow();
        assertThat(atualizada.getSendStatus()).isEqualTo(SendStatus.DELIVERED);
    }

    @Test
    @DisplayName("Deve deletar uma mensagem")
    void deveDeletarMessage() {
        Message message = messageRepository.save(buildMessage());
        Integer id = message.getId();

        messageRepository.deleteById(id);

        assertThat(messageRepository.findById(id)).isEmpty();
    }
}