package com.arpangroup.referral_service.temp.mlm.model;

public interface DownlineUserIds {
    Integer getDepth();
    String getUserIds(); // GROUP_CONCAT will return a comma-separated string
}
