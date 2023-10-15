package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.ProductRepository;
import com.vw.drink.dispenser.domain.ProductType;
import org.springframework.stereotype.Service;

@Service
public class Stock {

    private final ProductRepository productRepository;

    public Stock(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public long byProductType(ProductType productType) {
        return productRepository.stock(productType);
    }
}
