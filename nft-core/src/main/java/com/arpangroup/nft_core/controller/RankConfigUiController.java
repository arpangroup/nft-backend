package com.arpangroup.nft_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config")
@RequiredArgsConstructor
public class RankConfigUiController {

    @GetMapping("/rank")
    public String rankConfig() {
        return "rank-config";
    }

}
