//package com.arpangroup.user_service.entity.config;
//
//import com.arpangroup.user_service.entity.User;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "config_community_rebate")
//@Data
//@NoArgsConstructor
//public class CommunityRebateConfig {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "rebate_level", nullable = false)
//    private Level rebateLevel; // E.g., Lv.A, Lv.B, Lv.C
//
//    @Column(name = "level2_rebate_percent")
//    private Double level2RebatePercent;
//
//    @Column(name = "level3_rebate_percent")
//    private Double level3RebatePercent;
//
//    @Column(name = "level4_rebate_percent")
//    private Double level4RebatePercent;
//
//    @Column(name = "level5_rebate_percent")
//    private Double level5RebatePercent;
//
//    @Column(name = "max_rebate_levels")
//    private Integer maxRebateLevels;
//}
