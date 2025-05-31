package com.arpangroup.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;
    @Column(name = "referral_code", unique = true, length = 255)
    private String referralCode;
    @Column(name = "wallet_balance", precision = 19, scale = 4)
    private BigDecimal walletBalance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "referrer_id", referencedColumnName = "id")
    private User referrer;

    @Column(name = "rank_level")
    private int rank;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;




    /*private String firstname;
    private String lastname;
    private String email;
    private String mobile;

    private int referBy;

    private int posId;
    private int position;
    private int planId;
    private float totalInvest;
    private float totalBinaryCom;
    private float totalRefCom;
    private int dailyAdLimit;

    private Address addressDetails;
    private String image;

    private int status;
    private Kyc kycData;

    private String KycStatus;
    private int profileComplete;
    private String banReason;

    private String rememberToken;
    private String provider;
    private int providerId;*/


    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PostPersist
    private void setReferralAfterInsert() {
        this.referralCode = "REF" + this.id;
    }

    public User(String username) {
        this.username = username;
    }
}
