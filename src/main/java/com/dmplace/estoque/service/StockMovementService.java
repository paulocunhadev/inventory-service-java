package com.dmplace.estoque.service;

import com.dmplace.estoque.enumerators.MovementTypes;
import com.dmplace.estoque.domain.Product;
import com.dmplace.estoque.domain.StockMovement;
import com.dmplace.estoque.dto.StockMovementCreateRequest;
import com.dmplace.estoque.dto.StockMovementResponse;
import com.dmplace.estoque.repository.ProductRepository;
import com.dmplace.estoque.repository.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;

    public StockMovementService(StockMovementRepository movementRepository, ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockMovementResponse register(StockMovementCreateRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NoSuchElementException("Product not found."));

        if (request.type() == MovementTypes.IN) {
            product.addStock(request.quantity());
        } else if (request.type() == MovementTypes.OUT) {
            product.removeStock(request.quantity());
        }

        StockMovement movement = new StockMovement(
                product,
                request.type(),
                request.quantity(),
                request.reason() != null ? request.reason().trim() : null
        );

        StockMovement savedMovement = movementRepository.save(movement);
        productRepository.save(product);

        return StockMovementResponse.fromEntity(savedMovement, product.getCurrentStock());
    }

    @Transactional(readOnly = true)
    public Page<StockMovementResponse> findByProductId(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found."));

        return movementRepository.findByProductIdOrderByOccurredAtDesc(productId, pageable)
                .map(m -> StockMovementResponse.fromEntity(m, product.getCurrentStock()));
    }
}