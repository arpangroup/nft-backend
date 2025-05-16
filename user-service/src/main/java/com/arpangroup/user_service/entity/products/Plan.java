package com.arpangroup.user_service.entity.products;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plans")
@Getter
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Name of the plan, e.g., "Level A", "Level B", "Level C"

    @Column(nullable = false)
    private double price;

    @Column(name = "business_value")
    private int businessValue;

    @Column(name = "ref_com")
    private double refComm;

    @Column(name = "daily_ad_limit")
    private int dailyAdLimit;

    @Column(name = "status")
    private int status; // "0: DISABLE 1: ENABLE"

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    private double percentage; // percentage associated with the plan

}
