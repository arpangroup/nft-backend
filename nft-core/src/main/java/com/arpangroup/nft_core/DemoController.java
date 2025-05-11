package com.arpangroup.nft_core;

import com.arpangroup.nft_core.dto.Person;
import com.arpangroup.user_service.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/home")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping()
    public User person() {
        return new User(1, "John Doe");
    }

//    @GetMapping("/home")
//    public User sayHello() {
//        return new User(1, "John Doe");
//    }
}
