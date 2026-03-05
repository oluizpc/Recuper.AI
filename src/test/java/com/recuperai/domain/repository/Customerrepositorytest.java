package com.recuperai.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.recuperai.domain.entity.Customer;
import com.recuperai.domain.entity.Shop;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShopRepository shopRepository;

    private Shop shop;

    @BeforeEach
    void setUp() {
        Shop s = new Shop();
        s.setShopDomain("customer-test-shop.myshopify.com");
        s.setAccessToken("token-abc");
        s.setActive(true);
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        shop = shopRepository.save(s);
    }

    private Customer buildCustomer() {
        Customer customer = new Customer();
        customer.setShop(shop);
        customer.setShopify_customer_id(111L);
        customer.setName("João Silva");
        customer.setPhone("+5511999999999");
        customer.setEmail("joao@email.com");
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setActive(true);
        return customer;
    }

    @Test
    @DisplayName("Deve salvar e recuperar um customer pelo id")
    void deveSalvarERecuperarCustomer() {
        Customer customer = customerRepository.save(buildCustomer());

        assertThat(customer.getId()).isNotNull();

        Customer encontrado = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(encontrado.getName()).isEqualTo("João Silva");
        assertThat(encontrado.getEmail()).isEqualTo("joao@email.com");
        assertThat(encontrado.getPhone()).isEqualTo("+5511999999999");
    }

    @Test
    @DisplayName("Deve atualizar um customer existente")
    void deveAtualizarCustomer() {
        Customer customer = customerRepository.save(buildCustomer());

        customer.setName("João Atualizado");
        customer.setEmail("joao.novo@email.com");
        customerRepository.save(customer);

        Customer atualizado = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(atualizado.getName()).isEqualTo("João Atualizado");
        assertThat(atualizado.getEmail()).isEqualTo("joao.novo@email.com");
    }

    @Test
    @DisplayName("Deve deletar um customer")
    void deveDeletarCustomer() {
        Customer customer = customerRepository.save(buildCustomer());
        Integer id = customer.getId();

        customerRepository.deleteById(id);

        assertThat(customerRepository.findById(id)).isEmpty();
    }
}