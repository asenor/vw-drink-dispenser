package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.application.exception.InvalidProduct;
import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.product.Product;
import org.springframework.stereotype.Service;

@Service
public class AddProduct {

    private final Dispenser dispenser;

    public AddProduct(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public boolean addProduct(Product product) throws InvalidProduct {
        if (product == null) {
            throw new InvalidProduct(product);
        }

        dispenser.addProduct(product);

        return true;
    }
}
