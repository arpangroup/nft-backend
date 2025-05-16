package com.arpangroup.user_service.entity.config;

import jakarta.persistence.*;

@Entity
@Table(name = "config_referral_bonus")
public class ReferralBonusConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
