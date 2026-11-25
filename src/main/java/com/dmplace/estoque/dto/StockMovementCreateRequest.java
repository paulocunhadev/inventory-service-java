package com.dmplace.estoque.dto;

import com.dmplace.estoque.enumerators.MovementTypes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record StockMovementCreateRequest(
        @NotNull Long productId,
        @NotNull MovementTypes type,
        @NotNull @Positive Integer quantity,
        @Size(max = 255) String reason
) {
}