package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.InvalidProduct;
import com.vw.drink.dispenser.domain.product.Product;
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

    public <P extends Product> long productStock(Class<P> productType) {
        return products.stream().filter(product -> product.getClass() == productType).count();
    }

    public Status status() {
        return status;
    }

    public enum Status {
        AVAILABLE,
        OUT_OF_ORDER;
    }
}
