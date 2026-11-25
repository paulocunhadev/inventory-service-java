package com.dmplace.estoque.repository;

import com.dmplace.estoque.domain.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductIdOrderByOccurredAtDesc(Long productId);
}