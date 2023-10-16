package com.vw.drink.dispenser.infrastructure;

import com.vw.drink.dispenser.domain.product.ProductWithoutStockEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProductsNeedsManagerAppClient {
    @Async
    @EventListener
    public void onProductWithoutStockEvent(ProductWithoutStockEvent event) {
        // Send notification to app
    }
}
