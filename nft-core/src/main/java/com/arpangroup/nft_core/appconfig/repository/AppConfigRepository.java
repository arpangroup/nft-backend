package com.arpangroup.nft_core.appconfig.repository;

import com.arpangroup.nft_core.appconfig.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, String> {
}
