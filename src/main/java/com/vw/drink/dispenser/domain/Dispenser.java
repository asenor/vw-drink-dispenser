package com.vw.drink.dispenser.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Dispenser {

    private final ArrayList<Product> products;
    private Status status = Status.AVAILABLE;

    public Dispenser(Collection<Product> initialProducts) {
        this.products = new ArrayList<>(initialProducts);
    }

    public void addProduct(Product product) throws InvalidProduct {
        if (product == null)
            throw new InvalidProduct();

        products.add(product);
    }

    public long productStock(ProductType productType) {
        return products.stream().filter(product -> product.type() == productType).count();
    }

    public Status status() {
        return status;
    }

    public enum Status {
        AVAILABLE,
        OUT_OF_ORDER;
    }
}
