package com.vw.drink.dispenser.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DispenserTest {

    @Test
    public void testStatus() {
        var dispenser = new Dispenser();

        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());
    }
}
