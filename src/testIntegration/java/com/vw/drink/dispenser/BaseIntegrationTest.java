package com.vw.drink.dispenser;

import com.vw.drink.dispenser.configuration.DispenserConfiguration;
import com.vw.drink.dispenser.configuration.ProductRepositoryConfiguration;
import com.vw.drink.dispenser.configuration.TimeConfiguration;
import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@Import({
    DispenserConfiguration.class,
    TimeConfiguration.class,
    ProductRepositoryConfiguration.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class BaseIntegrationTest {
    @Autowired
    protected Dispenser dispenser;

    @Autowired
    protected ProductRepository productRepository;
}
