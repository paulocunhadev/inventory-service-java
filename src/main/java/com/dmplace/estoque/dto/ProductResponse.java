package com.dmplace.estoque.dto;

import com.dmplace.estoque.domain.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        BigDecimal unitPrice,
        Integer currentStock,
        Integer minimumStock,
        boolean lowStock,
        boolean active,
        Instant createdAt
) {
    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getUnitPrice(),
                product.getCurrentStock(),
                product.getMinimumStock(),
                product.isLowStock(),
                Boolean.TRUE.equals(product.getActive()),
                product.getCreatedAt()
        );
    }
}