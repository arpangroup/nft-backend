package com.arpangroup.user_service.processor.level.model;

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
