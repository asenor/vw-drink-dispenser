package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.DispenseResult;
import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
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
