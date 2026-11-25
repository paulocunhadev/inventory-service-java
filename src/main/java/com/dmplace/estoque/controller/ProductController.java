package com.dmplace.estoque.controller;

import com.dmplace.estoque.dto.ProductCreateRequest;
import com.dmplace.estoque.dto.ProductResponse;
import com.dmplace.estoque.service.ProductService;
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
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Endpoints for managing products and low-stock monitoring")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Registers a new product with a unique SKU, unit price, and minimum stock threshold."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payload or duplicate SKU")
    })
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(
            summary = "List all products",
            description = "Returns all registered products along with their current stock status."
    )
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Find product by ID",
            description = "Returns details of a specific product by its identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/low-stock")
    @Operation(
            summary = "List low-stock products",
            description = "Returns products whose current stock is less than or equal to the minimum stock threshold."
    )
    @ApiResponse(responseCode = "200", description = "Low-stock products retrieved successfully")
    public ResponseEntity<List<ProductResponse>> findLowStock() {
        return ResponseEntity.ok(productService.findLowStock());
    }
}