package com.arpangroup.user_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;

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
    private int providerId;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
