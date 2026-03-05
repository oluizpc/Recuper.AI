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
import com.recuperai.domain.entity.CheckoutItem;
import com.recuperai.domain.entity.Customer;
import com.recuperai.domain.entity.Shop;
import com.recuperai.domain.enums.CheckoutStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CheckoutItemRepositoryTest {

    @Autowired
    private CheckoutItemRepository checkoutItemRepository;

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Checkout checkout;

    @BeforeEach
    void setUp() {
        Shop s = new Shop();
        s.setShopDomain("item-test-shop.myshopify.com");
        s.setAccessToken("token-item");
        s.setActive(true);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        Shop shop = shopRepository.save(s);

        Customer c = new Customer();
        c.setShop(shop);
        c.setShopify_customer_id(333L);
        c.setName("Carlos Item");
        c.setPhone("+5511977777777");
        c.setEmail("carlos@email.com");
        c.setUpdatedAt(LocalDateTime.now());
        c.setActive(true);
        Customer customer = customerRepository.save(c);

        Checkout ch = new Checkout();
        ch.setShop(shop);
        ch.setCustomer(customer);
        ch.setShopifyCheckoutId(777L);
        ch.setStatus(CheckoutStatus.OPEN);
        ch.setTotalValue(new BigDecimal("99.90"));
        ch.setCurrency("BRL");
        ch.setCreatedAt(LocalDateTime.now());
        ch.setUpdatedAt(LocalDateTime.now());
        ch.setActive(true);
        checkout = checkoutRepository.save(ch);
    }

    private CheckoutItem buildItem() {
        CheckoutItem item = new CheckoutItem();
        item.setCheckout(checkout);
        item.setProductName("Camiseta Preta");
        item.setVariantName("G");
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("49.95"));
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("Deve salvar e recuperar um checkout item pelo id")
    void deveSalvarERecuperarCheckoutItem() {
        CheckoutItem item = checkoutItemRepository.save(buildItem());

        assertThat(item.getId()).isNotNull();

        CheckoutItem encontrado = checkoutItemRepository.findById(item.getId()).orElseThrow();
        assertThat(encontrado.getProductName()).isEqualTo("Camiseta Preta");
        assertThat(encontrado.getVariantName()).isEqualTo("G");
        assertThat(encontrado.getQuantity()).isEqualTo(2);
        assertThat(encontrado.getUnitPrice()).isEqualByComparingTo(new BigDecimal("49.95"));
    }

    @Test
    @DisplayName("Deve atualizar um checkout item")
    void deveAtualizarCheckoutItem() {
        CheckoutItem item = checkoutItemRepository.save(buildItem());

        item.setQuantity(5);
        item.setUnitPrice(new BigDecimal("39.90"));
        checkoutItemRepository.save(item);

        CheckoutItem atualizado = checkoutItemRepository.findById(item.getId()).orElseThrow();
        assertThat(atualizado.getQuantity()).isEqualTo(5);
        assertThat(atualizado.getUnitPrice()).isEqualByComparingTo(new BigDecimal("39.90"));
    }

    @Test
    @DisplayName("Deve deletar um checkout item")
    void deveDeletarCheckoutItem() {
        CheckoutItem item = checkoutItemRepository.save(buildItem());
        Integer id = item.getId();

        checkoutItemRepository.deleteById(id);

        assertThat(checkoutItemRepository.findById(id)).isEmpty();
    }
}