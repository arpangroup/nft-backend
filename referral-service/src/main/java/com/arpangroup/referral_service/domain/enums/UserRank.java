package com.arpangroup.referral_service.domain.enums;

public enum UserRank {
    SILVER(1, "Silver Tier"),
    BRONZE(2, "Bronze Tier"),
    GOLD(3, "Gold Tier"),
    PLATINUM(4, "Platinum Tier");

    private final int value;
    private final String displayName;

    UserRank(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserRank fromValue(int value) {
        for (UserRank rank : values()) {
            if (rank.getValue() == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown UserRank value: " + value);
    }

    public static UserRank fromDisplayName(String displayName) {
        for (UserRank rank : values()) {
            if (rank.getDisplayName().equalsIgnoreCase(displayName)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown UserRank name: " + displayName);
    }
}
