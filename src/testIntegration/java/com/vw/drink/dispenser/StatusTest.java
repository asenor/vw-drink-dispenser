package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.Status;
import com.vw.drink.dispenser.domain.Dispenser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest extends BaseIntegrationTest {

    @Autowired
    private Status status;

    @Test
    public void testDispenserStatus() {
        assertEquals(Dispenser.Status.AVAILABLE, status.status());
    }
}