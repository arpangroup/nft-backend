package com.arpangroup.nft_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.arpangroup.nft_core",
		"com.arpangroup.nft_common",
		"com.arpangroup.user_service",
		"com.arpangroup.referral_service"
})
public class NftCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(NftCoreApplication.class, args);
	}

}
