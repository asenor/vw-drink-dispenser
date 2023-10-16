package com.vw.drink.dispenser.configuration;

import com.vw.drink.dispenser.domain.time.Time;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import com.vw.drink.dispenser.infrastructure.InMemoryProductRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class ProductRepositoryConfiguration {
    @Bean
    public ProductRepository productRepository(Time time) {
        return new InMemoryProductRepository(List.of(), time);
    }
}
