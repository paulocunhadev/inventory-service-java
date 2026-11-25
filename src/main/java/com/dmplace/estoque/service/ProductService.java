package com.dmplace.estoque.service;

import com.dmplace.estoque.domain.Product;
import com.dmplace.estoque.dto.ProductCreateRequest;
import com.dmplace.estoque.dto.ProductResponse;
import com.dmplace.estoque.dto.ProductUpdateRequest;
import com.dmplace.estoque.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        String normalizedSku = request.sku().trim();
        if (productRepository.existsBySkuIgnoreCase(normalizedSku)) {
            throw new IllegalArgumentException("A product with this SKU already exists.");
        }

        Product product = new Product(
                normalizedSku,
                request.name().trim(),
                request.unitPrice(),
                request.minimumStock()
        );

        Product saved = productRepository.save(product);
        return ProductResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Boolean active, String name, boolean lowStockOnly, Pageable pageable) {
        String filterName = (name != null && !name.isBlank()) ? name.trim() : null;
        return productRepository.searchProducts(active, filterName, lowStockOnly, pageable)
                .map(ProductResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = getProductOrThrow(id);
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findBySku(String sku) {
        Product product = productRepository.findBySkuIgnoreCase(sku.trim())
                .orElseThrow(() -> new NoSuchElementException("Product not found for SKU: " + sku));
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = getProductOrThrow(id);
        product.updateDetails(
                request.name(),
                request.unitPrice(),
                request.minimumStock(),
                request.active()
        );
        Product updated = productRepository.save(product);
        return ProductResponse.fromEntity(updated);
    }

    @Transactional
    public void deactivate(Long id) {
        Product product = getProductOrThrow(id);
        product.deactivate();
        productRepository.save(product);
    }

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found."));
    }
}