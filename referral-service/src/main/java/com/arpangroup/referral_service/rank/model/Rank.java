package com.arpangroup.referral_service.rank.model;

public enum Rank {
    RANK_1("Rank 1"),
    RANK_2("Rank 2"),
    RANK_3("Rank 3"),
    RANK_4("Rank 4"),
    RANK_5("Rank 5");

    private final String displayName;

    Rank(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
