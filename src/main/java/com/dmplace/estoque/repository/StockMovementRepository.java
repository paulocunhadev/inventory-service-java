package com.dmplace.estoque.repository;

import com.dmplace.estoque.domain.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductIdOrderByOccurredAtDesc(Long productId);
    Page<StockMovement> findByProductIdOrderByOccurredAtDesc(Long productId, Pageable pageable);
}