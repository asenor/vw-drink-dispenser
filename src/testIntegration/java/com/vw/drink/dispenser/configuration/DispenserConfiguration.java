package com.vw.drink.dispenser.configuration;

import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.Money;
import com.vw.drink.dispenser.domain.ProductRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DispenserConfiguration {

    public static Money CASH = new Money("3.00");

    @Bean
    public Dispenser dispenser(ProductRepository productRepository) {
        return new Dispenser(productRepository, CASH);
    }
}
