package com.arpangroup.user_service;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User("user1", 1000);
		User user2 = new User("user2", 500);
		User user3 = new User("user3", 2000);
		User user4 = new User("user4", 100);
		User user5 = new User("user5", 1500);
		User user6 = new User("user6", 2500);
		User user7 = new User("user7", 50);

		userService.registerUser(user1, "");
//		userService.registerUser(user2, user1.getReferralCode());
//		userService.registerUser(user3, user1.getReferralCode());
//		userService.registerUser(user4, user2.getReferralCode());
//		userService.registerUser(user5, user2.getReferralCode());
//		userService.registerUser(user6, user3.getReferralCode());
//		userService.registerUser(user7, user4.getReferralCode());
	}
}
