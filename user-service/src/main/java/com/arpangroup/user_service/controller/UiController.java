package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.service.HierarchyHelper;
import com.arpangroup.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class UiController {
    @Autowired
    UserService userService;

    /*@GetMapping("/user/{id}/downline")
    public String getUserDownline(@PathVariable Long id, Model model) {
        List<UserHierarchy> downline = userService.getDownline(id);
        Map<Long, List<UserHierarchy>> hierarchy = HierarchyHelper.buildHierarchy(downline);
        User user = userService.getUserById(id);

        model.addAttribute("user", user);;
        model.addAttribute("hierarchy", hierarchy);

        return "userDownline";
    }*/

    @GetMapping("/tree")
    public String downlineTree() {
        return "index";
    }


}
