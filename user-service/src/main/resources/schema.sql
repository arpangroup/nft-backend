CREATE TABLE individual_income_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    level VARCHAR(50) NOT NULL,
    reservation_min DECIMAL(10, 2) NOT NULL,
    reservation_max DECIMAL(10, 2) NOT NULL,
    profit_per_day DECIMAL(5, 2) NOT NULL,
    transactions_per_day INT NOT NULL,
    annualized_returns DECIMAL(6, 2) NOT NULL,
    community_lv_a INT,
    community_lv_b INT,
    community_total INT,
    frequency VARCHAR(20) NOT NULL DEFAULT 'PER_DAY',
    calculation_type VARCHAR(20) NOT NULL DEFAULT 'PERCENTAGE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE community_rebate_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    level VARCHAR(10) NOT NULL,
    lv2_percent DECIMAL(5, 2) NOT NULL,
    lv3_percent DECIMAL(5, 2) NOT NULL,
    lv4_percent DECIMAL(5, 2) NOT NULL,
    lv5_percent DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
