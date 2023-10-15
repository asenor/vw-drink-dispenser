package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.InvalidProduct;
import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class AddProduct {

    private final ProductRepository productRepository;

    public AddProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) throws InvalidProduct {
        productRepository.add(product);
    }
}
