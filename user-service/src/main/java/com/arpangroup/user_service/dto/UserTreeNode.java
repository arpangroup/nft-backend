package com.arpangroup.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTreeNode {
    private Long userId;
    private String username;
    private double walletBalance;
    private List<UserTreeNode> children = new ArrayList<>();

    public UserTreeNode(Long userId, String username, double walletBalance) {
        this.userId = userId;
        this.username = username;
        this.walletBalance = walletBalance;
    }


    public void addChild(UserTreeNode child) {
        this.children.add(child);
    }
}
