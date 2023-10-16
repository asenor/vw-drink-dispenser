package com.vw.drink.dispenser.application.product;

import com.vw.drink.dispenser.domain.product.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.product.Product;
import com.vw.drink.dispenser.domain.product.ProductRepository;
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
