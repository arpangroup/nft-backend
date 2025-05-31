package com.arpangroup.referral_service.income.entity;

import com.arpangroup.referral_service.rank.model.Rank;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "income_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // Recipient
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private IncomeType  type; // "DAILY" or "TEAM"

    private String note; // e.g. "From user 42, RANK_2"

    private Long sourceUserId; // Who triggered this income

    @Enumerated(EnumType.STRING)
    private Rank sourceUserRank;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum IncomeType {
        DAILY, TEAM
    }
}
