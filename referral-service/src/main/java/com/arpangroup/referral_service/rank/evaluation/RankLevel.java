package com.arpangroup.referral_service.rank.evaluation;

public enum RankLevel {
    LEVEL_1(1, "Level-1"),
    LEVEL_2(2, "Level-2"),
    LEVEL_3(3, "Level-3"),
    LEVEL_4(4, "Level-4");

    /*SILVER(1, "Silver Tier"),
    BRONZE(2, "Bronze Tier"),
    GOLD(3, "Gold Tier"),
    PLATINUM(4, "Platinum Tier");*/

    private final int value;
    private final String displayName;

    RankLevel(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RankLevel previous() {
        return RankLevel.values()[this.ordinal() - 1];
    }

    public static RankLevel fromValue(int value) {
        for (RankLevel rank : values()) {
            if (rank.getValue() == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown UserRank value: " + value);
    }

    public static RankLevel fromDisplayName(String displayName) {
        for (RankLevel rank : values()) {
            if (rank.getDisplayName().equalsIgnoreCase(displayName)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown UserRank name: " + displayName);
    }
}
