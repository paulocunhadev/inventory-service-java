package com.dmplace.estoque.dto;

import com.dmplace.estoque.enumerators.MovementTypes;
import com.dmplace.estoque.domain.StockMovement;

import java.time.Instant;

public record StockMovementResponse(
        Long id,
        Long productId,
        String productSku,
        MovementTypes type,
        Integer quantity,
        Integer resultingStock,
        String reason,
        Instant occurredAt
) {
    public static StockMovementResponse fromEntity(StockMovement movement, Integer resultingStock) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getProduct().getSku(),
                movement.getType(),
                movement.getQuantity(),
                resultingStock,
                movement.getReason(),
                movement.getOccurredAt()
        );
    }
}