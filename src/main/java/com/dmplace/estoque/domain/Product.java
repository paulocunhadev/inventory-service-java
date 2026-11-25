package com.dmplace.estoque.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String sku;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "minimum_stock", nullable = false)
    private Integer minimumStock = 0;

    @Column(nullable = false)
    private Boolean active = true;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        if (this.currentStock == null) {
            this.currentStock = 0;
        }
        if (this.minimumStock == null) {
            this.minimumStock = 0;
        }
        if (this.active == null) {
            this.active = true;
        }
    }

    public Product() {
    }

    public Product(String sku, String name, BigDecimal unitPrice, Integer minimumStock) {
        this.sku = sku;
        this.name = name;
        this.unitPrice = unitPrice;
        this.minimumStock = minimumStock;
        this.currentStock = 0;
        this.active = true;
    }

    public void updateDetails(String name, BigDecimal unitPrice, Integer minimumStock, Boolean active) {
        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }
        if (unitPrice != null) {
            this.unitPrice = unitPrice;
        }
        if (minimumStock != null) {
            this.minimumStock = minimumStock;
        }
        if (active != null) {
            this.active = active;
        }
    }

    public void deactivate() {
        this.active = false;
    }

    public void addStock(int quantity) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("Cannot register stock movement for an inactive product.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Stock inbound quantity must be greater than zero.");
        }
        this.currentStock += quantity;
    }

    public void removeStock(int quantity) {
        if (!Boolean.TRUE.equals(this.active)) {
            throw new IllegalStateException("Cannot register stock movement for an inactive product.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Stock outbound quantity must be greater than zero.");
        }
        if (this.currentStock < quantity) {
            throw new IllegalStateException("Insufficient stock balance for this operation.");
        }
        this.currentStock -= quantity;
    }

    public boolean isLowStock() {
        return this.currentStock <= this.minimumStock;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public Integer getMinimumStock() { return minimumStock; }
    public void setMinimumStock(Integer minimumStock) { this.minimumStock = minimumStock; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
}