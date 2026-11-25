package com.dmplace.estoque.controller;

import com.dmplace.estoque.dto.StockMovementCreateRequest;
import com.dmplace.estoque.dto.StockMovementResponse;
import com.dmplace.estoque.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Stock Movements", description = "Endpoints for recording IN/OUT stock movements and viewing movement history")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    @Operation(
            summary = "Register a stock movement",
            description = "Records an IN or OUT movement for a product and updates its stock balance atomically."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stock movement recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or quantity"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "422", description = "Insufficient stock balance for an OUT movement")
    })
    public ResponseEntity<StockMovementResponse> register(
            @Valid @RequestBody StockMovementCreateRequest request
    ) {
        StockMovementResponse response = stockMovementService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/product/{productId}")
    @Operation(
            summary = "List movements by product ID",
            description = "Returns the stock movement history for a given product ordered by most recent occurrence."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movement history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<List<StockMovementResponse>> findByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.findByProductId(productId));
    }
}