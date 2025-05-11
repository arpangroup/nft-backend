package com.arpangroup.user_service.dto;

import com.arpangroup.user_service.entity.Address;
import com.arpangroup.user_service.entity.Kyc;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {
    private String username;
    private String firstname;
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
    private int providerId;

}
