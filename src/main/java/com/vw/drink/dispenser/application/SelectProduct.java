package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.exception.DispenserNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class SelectProduct {

    private final Dispenser dispenser;

    public SelectProduct(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public void handle(ProductType type) throws DispenserNotAvailableException {
        dispenser.selectProduct(type);
    }
}
