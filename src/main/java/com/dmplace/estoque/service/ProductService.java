package com.dmplace.estoque.service;

import com.dmplace.estoque.domain.Product;
import com.dmplace.estoque.dto.ProductCreateRequest;
import com.dmplace.estoque.dto.ProductResponse;
import com.dmplace.estoque.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("A product with this SKU already exists.");
        }

        Product product = new Product(
                request.sku().trim(),
                request.name().trim(),
                request.unitPrice(),
                request.minimumStock()
        );

        Product saved = productRepository.save(product);
        return ProductResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found."));
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findLowStock() {
        return productRepository.findLowStockProducts()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }
}