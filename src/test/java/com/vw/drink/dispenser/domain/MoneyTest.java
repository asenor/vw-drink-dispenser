package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.money.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTest {

    @Test
    public void plus() {
        assertEquals(
            new Money("1.05"),
            (new Money("1.00")).plus(new Money("0.05"))
        );
        assertEquals(
            new Money("0.75"),
            (new Money("0.20"))
                .plus(new Money("0.05"))
                .plus(new Money("0.50"))
        );
    }

    @Test
    public void isMoreThan() {
        assertTrue((new Money("1.05")).isMoreThan(new Money("1.00")));
        assertFalse((new Money("0.80")).isMoreThan(new Money("0.90")));
    }

    @Test
    public void difference() {
        assertEquals(
            new Money("0.25"),
            (new Money("0.50")).difference(new Money("0.75"))
        );
    }
}
