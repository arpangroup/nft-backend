package com.arpangroup.user_service.service.mlm.model;

public interface DownlineUserIds {
    Integer getDepth();
    String getUserIds(); // GROUP_CONCAT will return a comma-separated string
}
