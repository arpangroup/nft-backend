package com.arpangroup.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTreeNode {
    private Long userId;
    private String username;
    private List<UserTreeNode> children = new ArrayList<>();

    public UserTreeNode(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public void addChild(UserTreeNode child) {
        this.children.add(child);
    }
}
