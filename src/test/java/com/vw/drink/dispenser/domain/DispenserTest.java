package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.InvalidProduct;
import com.vw.drink.dispenser.domain.product.Coke;
import com.vw.drink.dispenser.domain.product.Water;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DispenserTest {

    @Test
    public void testAddProducts() throws InvalidProduct {
        var dispenser = new Dispenser(List.of());

        dispenser.addProduct(new Coke(new Timestamp(0)));
        dispenser.addProduct(new Coke(new Timestamp(0)));

        assertEquals(2, dispenser.productStock(Coke.class));

        assertThrows(InvalidProduct.class, () -> {
           dispenser.addProduct(null);
        });
    }

    @Test
    public void testProductStock() {
        var dispenser = new Dispenser(List.of(
            new Coke(new Timestamp(5)),
            new Coke(new Timestamp(10)),
            new Coke(new Timestamp(15)),
            new Water(new Timestamp(50)),
            new Water(new Timestamp(60))
        ));

        assertEquals(3, dispenser.productStock(Coke.class));
        assertEquals(2, dispenser.productStock(Water.class));
    }
}
