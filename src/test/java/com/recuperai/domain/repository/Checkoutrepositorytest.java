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
import com.recuperai.domain.entity.Shop;
import com.recuperai.domain.enums.CheckoutStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckoutRepositoryTest {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Shop shop;
    private Customer customer;

    @BeforeEach
    void setUp() {
        Shop s = new Shop();
        s.setShopDomain("checkout-test-shop.myshopify.com");
        s.setAccessToken("token-checkout");
        s.setActive(true);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        shop = shopRepository.save(s);

        Customer c = new Customer();
        c.setShop(shop);
        c.setShopify_customer_id(222L);
        c.setName("Maria Souza");
        c.setPhone("+5511988888888");
        c.setEmail("maria@email.com");
        c.setUpdatedAt(LocalDateTime.now());
        c.setActive(true);
        customer = customerRepository.save(c);
    }

    private Checkout buildCheckout() {
        Checkout checkout = new Checkout();
        checkout.setShop(shop);
        checkout.setCustomer(customer);
        checkout.setShopifyCheckoutId(999L);
        checkout.setStatus(CheckoutStatus.OPEN);
        checkout.setTotalValue(new BigDecimal("199.90"));
        checkout.setCurrency("BRL");
        checkout.setRecoveryUrl("https://shop.com/recover/abc123");
        checkout.setCreatedAt(LocalDateTime.now());
        checkout.setUpdatedAt(LocalDateTime.now());
        checkout.setActive(true);
        return checkout;
    }

    @Test
    @DisplayName("Deve salvar e recuperar um checkout pelo id")
    void deveSalvarERecuperarCheckout() {
        Checkout checkout = checkoutRepository.save(buildCheckout());

        assertThat(checkout.getId()).isNotNull();

        Checkout encontrado = checkoutRepository.findById(checkout.getId()).orElseThrow();
        assertThat(encontrado.getShopifyCheckoutId()).isEqualTo(999L);
        assertThat(encontrado.getStatus()).isEqualTo(CheckoutStatus.OPEN);
        assertThat(encontrado.getTotalValue()).isEqualByComparingTo(new BigDecimal("199.90"));
    }

    @Test
    @DisplayName("Deve atualizar o status de um checkout")
    void deveAtualizarStatusDoCheckout() {
        Checkout checkout = checkoutRepository.save(buildCheckout());

        checkout.setStatus(CheckoutStatus.ABANDONED);
        checkout.setAbandonedAt(LocalDateTime.now());
        checkoutRepository.save(checkout);

        Checkout atualizado = checkoutRepository.findById(checkout.getId()).orElseThrow();
        assertThat(atualizado.getStatus()).isEqualTo(CheckoutStatus.ABANDONED);
        assertThat(atualizado.getAbandonedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um checkout")
    void deveDeletarCheckout() {
        Checkout checkout = checkoutRepository.save(buildCheckout());
        Integer id = checkout.getId();

        checkoutRepository.deleteById(id);

        assertThat(checkoutRepository.findById(id)).isEmpty();
    }
}