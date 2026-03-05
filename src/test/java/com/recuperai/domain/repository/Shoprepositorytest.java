package com.recuperai.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.recuperai.domain.entity.Shop;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    private Shop buildShop() {
        Shop shop = new Shop();
        shop.setShopDomain("test-shop.myshopify.com");
        shop.setAccessToken("token-123");
        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());
        shop.setActive(true);
        return shop;
    }

    @Test
    @DisplayName("Deve salvar e recuperar uma shop pelo id")
    void deveSalvarERecuperarShop() {
        Shop shop = shopRepository.save(buildShop());

        assertThat(shop.getId()).isNotNull();

        Shop encontrada = shopRepository.findById(shop.getId()).orElseThrow();
        assertThat(encontrada.getShopDomain()).isEqualTo("test-shop.myshopify.com");
        assertThat(encontrada.getAccessToken()).isEqualTo("token-123");
        assertThat(encontrada.getActive()).isTrue();
    }

    @Test
    @DisplayName("Deve atualizar uma shop existente")
    void deveAtualizarShop() {
        Shop shop = shopRepository.save(buildShop());

        shop.setShopDomain("updated-shop.myshopify.com");
        shop.setActive(false);
        shopRepository.save(shop);

        Shop atualizada = shopRepository.findById(shop.getId()).orElseThrow();
        assertThat(atualizada.getShopDomain()).isEqualTo("updated-shop.myshopify.com");
        assertThat(atualizada.getActive()).isFalse();
    }

    @Test
    @DisplayName("Deve deletar uma shop")
    void deveDeletarShop() {
        Shop shop = shopRepository.save(buildShop());
        Integer id = shop.getId();

        shopRepository.deleteById(id);

        assertThat(shopRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todas as shops")
    void deveListarShops() {
        shopRepository.save(buildShop());

        Shop outra = new Shop();
        outra.setShopDomain("outra-shop.myshopify.com");
        outra.setAccessToken("token-456");
        outra.setActive(true);
        shopRepository.save(outra);

        assertThat(shopRepository.findAll()).hasSizeGreaterThanOrEqualTo(2);
    }
}