package com.dmplace.estoque.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @Size(max = 120) String name,
        @PositiveOrZero BigDecimal unitPrice,
        @PositiveOrZero Integer minimumStock,
        Boolean active
) {
}