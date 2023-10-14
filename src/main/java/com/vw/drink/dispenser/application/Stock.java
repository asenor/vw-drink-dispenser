package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.product.Product;
import org.springframework.stereotype.Service;

@Service
public class Stock {

    private final Dispenser dispenser;

    public Stock(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public <P extends Product> long byProduct(Class<P> productType) {
        return dispenser.productStock(productType);
    }
}
