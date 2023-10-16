package com.vw.drink.dispenser.infrastructure;

import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductRepository;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.Time;
import com.vw.drink.dispenser.domain.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final ArrayList<Product> products;
    private final Time time;

    public InMemoryProductRepository(Collection<Product> initialProducts, Time time) {
        this.products = new ArrayList<>(initialProducts);
        this.time = time;
    }

    @Override
    public void add(Product product) throws InvalidProductException {
        if (product == null)
            throw new InvalidProductException();

        products.add(product);
    }

    @Override
    public long stock(ProductType type) {
        return products.stream().filter(sameType(type)).count();
    }

    @Override
    public boolean hasStock(ProductType type) {
        return stock(type) > 0;
    }

    @Override
    public boolean hasAnyUnexpired(ProductType type) {
        return products.stream().anyMatch(unexpired(type));
    }

    @Override
    public Product pickUnexpiredProduct(ProductType type) throws NoUnexpiredProductException {
        var productOpt = products.stream().filter(unexpired(type)).findFirst();
        if (productOpt.isEmpty()) {
            throw new NoUnexpiredProductException(type);
        }
        var product = productOpt.get();
        products.remove(product);
        return product;
    }

    private Predicate<Product> sameType(ProductType type) {
        return product -> product.type() == type;
    }

    private Predicate<Product> unexpired(ProductType type) {
        return sameType(type).and(product -> !product.isExpired(time.now()));
    }
}
