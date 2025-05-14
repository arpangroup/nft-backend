package com.arpangroup.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
referrer_id: who made the referral
referred_user_id: user who use referred
EX: Consider a scenario where userA refers userB
    referrer_id      : userA
    referred_user_id : userB
*/

@Entity
@Table(name = "referrals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   /* @OneToOne
    @JoinColumn(name = "referrer_id", referencedColumnName = "id")
    private User referrer;*/
    private Long referrerId;

   /* @OneToOne
    @JoinColumn(name = "referred_user_id", referencedColumnName = "id")
    private User referredUser;*/
    private Long referredUserId;

    private double bonus;

    public Referral(Long referrerId, Long referredUserId, double bonus) {
        this.referrerId = referrerId;
        this.referredUserId = referredUserId;
        this.bonus = bonus;
    }
}
