package com.arpangroup.user_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "kyc_details")
public class Kyc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycDocumentType documentType = KycDocumentType.NATIONAL_ID;

    // ########################### Status #############################
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EpaStatus emailVerifyStatus = EpaStatus.UNVERIFIED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EpaStatus phoneVerifyStatus = EpaStatus.UNVERIFIED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public KycStatus status = KycStatus.PENDING;
    // ########################### Status #############################

    private String birthdate;
    private String gender;

    private String kycRejectionReason;


    public enum EpaStatus {
        UNVERIFIED,
        VERIFIED,
    }

    public enum KycStatus {
        PENDING,        // Not yet submitted the KYC Documents
        UNVERIFIED,     // KYC Document Submitted, but not verified from Admin
        VERIFIED,       // Documents Verified by Admin
        REJECTED;       // KYC Rejected
    }

    public enum KycDocumentType {
        AADHAR,
        PAN,
        PASSPORT,
        DRIVING_LICENSE,
        NATIONAL_ID,
        VOTER_ID,
        TAX_ID,
        SSN;  // For international cases like the US
    }


}
