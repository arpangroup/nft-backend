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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserStatus status = UserStatus.ACTIVE;

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
        this.setStatus(UserStatus.ACTIVE);
    }

    public User(String username, int rank, BigDecimal walletBalance) {
        this(username);
        this.rank = rank;
        this.walletBalance = walletBalance;
        this.setStatus(UserStatus.ACTIVE);
    }

    public User(Long id, String username, int rank, BigDecimal walletBalance) {
        this(username, rank, walletBalance);
        this.id = id;
    }


    public enum UserStatus {
        ACTIVE,         // User is active and allowed to use the system
        DISABLED,       // User is deactivated (manually or due to violation)
        PENDING,        // User registered but hasn't completed verification
        SUSPENDED,      // Temporarily banned for a specific reason/time
        DELETED,        // Soft-deleted user (can be restored later)
        BANNED,         // Permanently banned
        LOCKED,         // Account locked due to security reasons (e.g., too many login attempts)
        INACTIVE        // User hasn’t used the service in a long time
    }

}
