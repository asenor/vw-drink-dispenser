package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import org.springframework.context.ApplicationEvent;

public class ProductWithoutStockEvent extends ApplicationEvent {

    public ProductType type;

    public ProductWithoutStockEvent(Dispenser dispenser, ProductType type) {
        super(dispenser);
        this.type = type;
    }
}
