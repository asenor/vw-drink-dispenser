package com.vw.drink.dispenser.application.product;

import com.vw.drink.dispenser.domain.product.ProductRepository;
import com.vw.drink.dispenser.domain.product.ProductType;
import org.springframework.stereotype.Service;

@Service
public class GetProductStock {

    private final ProductRepository productRepository;

    public GetProductStock(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public long byType(ProductType type) {
        return productRepository.stock(type);
    }
}
