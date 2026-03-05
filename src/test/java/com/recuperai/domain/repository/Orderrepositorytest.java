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
import com.recuperai.domain.entity.Order;
import com.recuperai.domain.entity.Shop;
import com.recuperai.domain.enums.CheckoutStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

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
        s.setShopDomain("order-test-shop.myshopify.com");
        s.setAccessToken("token-order");
        s.setActive(true);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        shop = shopRepository.save(s);

        Customer c = new Customer();
        c.setShop(shop);
        c.setShopify_customer_id(555L);
        c.setName("Pedro Order");
        c.setPhone("+5511955555555");
        c.setEmail("pedro@email.com");
        c.setUpdatedAt(LocalDateTime.now());
        c.setActive(true);
        Customer customer = customerRepository.save(c);

        Checkout ch = new Checkout();
        ch.setShop(shop);
        ch.setCustomer(customer);
        ch.setShopifyCheckoutId(666L);
        ch.setStatus(CheckoutStatus.CONVERTED);
        ch.setTotalValue(new BigDecimal("350.00"));
        ch.setCurrency("BRL");
        ch.setCreatedAt(LocalDateTime.now());
        ch.setUpdatedAt(LocalDateTime.now());
        ch.setActive(true);
        checkout = checkoutRepository.save(ch);
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setShop(shop);
        order.setCheckout(checkout);
        order.setIdShopifyOrder(12345L);
        order.setTotalValue(new BigDecimal("350.00"));
        order.setCurrency("BRL");
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    @Test
    @DisplayName("Deve salvar e recuperar um order pelo id")
    void deveSalvarERecuperarOrder() {
        Order order = orderRepository.save(buildOrder());

        assertThat(order.getId()).isNotNull();

        Order encontrado = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(encontrado.getIdShopifyOrder()).isEqualTo(12345L);
        assertThat(encontrado.getTotalValue()).isEqualByComparingTo(new BigDecimal("350.00"));
        assertThat(encontrado.getCurrency()).isEqualTo("BRL");
    }

    @Test
    @DisplayName("Deve atualizar um order existente")
    void deveAtualizarOrder() {
        Order order = orderRepository.save(buildOrder());

        order.setTotalValue(new BigDecimal("400.00"));
        orderRepository.save(order);

        Order atualizado = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(atualizado.getTotalValue()).isEqualByComparingTo(new BigDecimal("400.00"));
    }

    @Test
    @DisplayName("Deve deletar um order")
    void deveDeletarOrder() {
        Order order = orderRepository.save(buildOrder());
        Integer id = order.getId();

        orderRepository.deleteById(id);

        assertThat(orderRepository.findById(id)).isEmpty();
    }
}