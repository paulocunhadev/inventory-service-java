package com.dmplace.estoque.repository;

import com.dmplace.estoque.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
    Optional<Product> findBySkuIgnoreCase(String sku);

    boolean existsBySku(String sku);
    boolean existsBySkuIgnoreCase(String sku);

    @Query("SELECT p FROM Product p WHERE p.currentStock <= p.minimumStock")
    List<Product> findLowStockProducts();

    @Query("""
            SELECT p FROM Product p
            WHERE (:active IS NULL OR p.active = :active)
              AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:lowStockOnly = false OR p.currentStock <= p.minimumStock)
            """)
    Page<Product> searchProducts(@Param("active") Boolean active, @Param("name") String name, @Param("lowStockOnly") boolean lowStockOnly, Pageable pageable);
}