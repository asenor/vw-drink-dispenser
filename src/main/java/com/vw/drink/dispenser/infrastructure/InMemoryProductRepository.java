package com.vw.drink.dispenser.infrastructure;

import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductRepository;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.exception.InvalidProduct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final ArrayList<Product> products;

    public InMemoryProductRepository(Collection<Product> initialProducts) {
        this.products = new ArrayList<>(initialProducts);
    }

    @Override
    public void add(Product product) throws InvalidProduct {
        if (product == null)
            throw new InvalidProduct();

        products.add(product);
    }

    @Override
    public long stock(ProductType productType) {
        return products.stream().filter(product -> product.type() == productType).count();
    }
}
