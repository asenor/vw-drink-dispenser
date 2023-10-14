package com.vw.drink.dispenser;

import com.vw.drink.dispenser.domain.Dispenser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class BaseIntegrationTest {

    @Autowired
    protected Dispenser dispenser;

    @TestConfiguration
    static class DispenserConfig {
        @Bean
        public Dispenser dispenser() {
            return new Dispenser(List.of());
        }
    }
}
