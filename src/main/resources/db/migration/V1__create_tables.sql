CREATE TABLE products (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          sku VARCHAR(60) NOT NULL,
                          name VARCHAR(120) NOT NULL,
                          unit_price DECIMAL(10, 2) NOT NULL,
                          current_stock INT NOT NULL DEFAULT 0,
                          minimum_stock INT NOT NULL DEFAULT 0,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          version BIGINT NOT NULL DEFAULT 0,
                          created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                          PRIMARY KEY (id),
                          CONSTRAINT uk_products_sku UNIQUE (sku)
);

CREATE TABLE stock_movements (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 product_id BIGINT NOT NULL,
                                 type VARCHAR(20) NOT NULL,
                                 quantity INT NOT NULL,
                                 reason VARCHAR(255),
                                 occurred_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                 PRIMARY KEY (id),
                                 CONSTRAINT fk_stock_movements_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE INDEX idx_products_active ON products (active);
CREATE INDEX idx_stock_movements_product_occurred ON stock_movements (product_id, occurred_at DESC);