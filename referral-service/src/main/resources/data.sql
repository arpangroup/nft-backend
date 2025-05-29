CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(100),
    method VARCHAR(255),
    username VARCHAR(100),
    status VARCHAR(20),
    error_message TEXT,
    timestamp TIMESTAMP
);