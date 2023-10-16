package com.vw.drink.dispenser.application.dispenser;

import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.product.ProductType;
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
