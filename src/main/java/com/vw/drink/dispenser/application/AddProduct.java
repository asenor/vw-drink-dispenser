package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductRepository;
import com.vw.drink.dispenser.domain.exception.InvalidProductException;
import org.springframework.stereotype.Service;

@Service
public class AddProduct {

    private final ProductRepository productRepository;

    public AddProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handle(Product product) throws InvalidProductException {
        productRepository.add(product);
    }
}
