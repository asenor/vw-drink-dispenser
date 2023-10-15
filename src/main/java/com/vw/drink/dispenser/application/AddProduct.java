package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.InvalidProduct;
import com.vw.drink.dispenser.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class AddProduct {

    private final Dispenser dispenser;

    public AddProduct(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public void addProduct(Product product) throws InvalidProduct {
        dispenser.addProduct(product);
    }
}
