package com.arpangroup.referral_service.rank.model;

public enum Rank {
    RANK_1(1, "Rank 1"),
    RANK_2(2, "Rank 2"),
    RANK_3(3, "Rank 3"),
    RANK_4(4, "Rank 4"),
    RANK_5(5, "Rank 5");

    private final int value;
    private final String displayName;

    Rank(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
