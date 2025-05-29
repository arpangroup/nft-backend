package com.arpangroup.referral_service.temp.mlm.context;

import lombok.Data;

import java.util.List;

@Data
public class LevelCalculationContext {
    private Long userId;
    private List<Long> levelAIds;
    private List<Long> levelBIds;
    private List<Long> levelCIds;
    private int levelACount;
    private int levelBCount;
    private int levelCCount;
}
