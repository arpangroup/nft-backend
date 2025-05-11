package com.arpangroup.user_service.entity;

public class Kyc {
    private String firstname;
    private String lastname;

    private String email;
    private String phone;
    private Address address;


    private String emailVerifyStatus; // 	0: email unverified, 1: email verified
    private String phoneVerifyStatus; // 	0: phone unverified, 1: phone verified
    private String KycStatus; // 0: KYC Unverified, 2: KYC pending, 1: KYC verified

    private String birthdate;
    private String gender;

    private String kycRejectionReason;




}
