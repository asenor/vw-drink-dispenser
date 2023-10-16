package com.vw.drink.dispenser.configuration;

import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DispenserConfiguration {

    public static Money CASH = new Money("3.00");

    @Bean
    public Dispenser dispenser(
        ProductRepository productRepository,
        ApplicationEventPublisher eventPublisher
    ) {
        return new Dispenser(productRepository, eventPublisher, CASH);
    }
}
