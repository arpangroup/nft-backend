package com.arpangroup.config_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_config")
@Data
@NoArgsConstructor
public class AppConfig {
    @Id
    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value")
    private String value;

    @Column(name = "value_type") // optional
    private String valueType;

    @Column(name = "enum_values") // comma-separated
    private String enumValues;

    public AppConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public AppConfig(String key, Object value) {
        this.key = key;
        this.value = String.valueOf(value);
        this.valueType = detectValueType(value).name();
    }

    public AppConfig(String key, Object value, String enumValues) {
        this.key = key;
        this.value = String.valueOf(value);
        this.valueType = detectValueType(value).name();
        this.enumValues = enumValues;
    }

    public enum ValueType {
        INT,
        FLOAT,
        DOUBLE,
        BIG_DECIMAL,
        BOOLEAN,
        STRING
    }

    private ValueType detectValueType(Object value) {
        if (value instanceof Integer) return ValueType.INT;
        if (value instanceof Float) return ValueType.FLOAT;
        if (value instanceof Double) return ValueType.DOUBLE;
        if (value instanceof java.math.BigDecimal) return ValueType.BIG_DECIMAL;
        if (value instanceof Boolean) return ValueType.BOOLEAN;
        return ValueType.STRING;
    }
}