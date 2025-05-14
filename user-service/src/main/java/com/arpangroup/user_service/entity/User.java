package com.arpangroup.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    @Column(name = "referral_code", unique = true, length = 255)
    private String referralCode;
    @Column(name = "reserve_balance", precision = 10)
    private double reserveBalance;
    @Column(name = "level")
    private int level;

   /* @OneToOne
    @JoinColumn(name = "referrer_id", referencedColumnName = "id")
    private User referrer;*/




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

    public User(String username, double reserveBalance) {
        this.id = id;
        this.username = username;
        this.referralCode = "ReferBy_"+username;
        this.reserveBalance = reserveBalance;
    }
}
