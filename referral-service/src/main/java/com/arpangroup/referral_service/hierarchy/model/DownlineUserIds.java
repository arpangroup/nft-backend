package com.arpangroup.referral_service.hierarchy.model;

public interface DownlineUserIds {
    Integer getDepth();
    String getUserIds(); // GROUP_CONCAT will return a comma-separated string
}
