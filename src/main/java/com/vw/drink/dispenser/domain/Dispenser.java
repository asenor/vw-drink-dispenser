package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.product.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Dispenser {

    private final ArrayList<Product> products;

    public Dispenser(Collection<Product> initialProducts) {
        this.products = new ArrayList<>(initialProducts);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public <P extends Product> long productStock(Class<P> productType) {
        return products.stream().filter(product -> product.getClass() == productType).count();
    }
}
