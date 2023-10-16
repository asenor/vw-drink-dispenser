package com.vw.drink.dispenser.application.dispenser;

import com.vw.drink.dispenser.domain.dispenser.DispenseResult;
import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import org.springframework.stereotype.Service;

@Service
public class Dispense {

    private final Dispenser dispenser;

    public Dispense(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public DispenseResult handle() throws NoUnexpiredProductException, ProductNotSelectedException {
        return dispenser.dispense();
    }
}
