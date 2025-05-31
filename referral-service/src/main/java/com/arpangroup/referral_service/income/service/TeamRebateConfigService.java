package com.arpangroup.referral_service.income.service;

import com.arpangroup.referral_service.income.dto.TeamRebateConfigDto;
import com.arpangroup.referral_service.income.entity.TeamRebateConfig;
import com.arpangroup.referral_service.income.repository.TeamRebateConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamRebateConfigService {
    private final TeamRebateConfigRepository teamRebateConfigRepository;


    public List<TeamRebateConfig> getAll() {
        List<TeamRebateConfig> list = teamRebateConfigRepository.findAll().stream()
                .collect(Collectors.toList());
        return list;
    }


    public void updateTeamConfigs(List<TeamRebateConfig> updatedConfigs) {
        for (TeamRebateConfig config : updatedConfigs) {
            Optional<TeamRebateConfig> existingOpt = teamRebateConfigRepository.findById(config.getRank());
            if (existingOpt.isPresent()) {
                TeamRebateConfig existing = existingOpt.get();
                existing.setIncomePercentages(config.getIncomePercentages());
                teamRebateConfigRepository.save(existing);
            } else {
                teamRebateConfigRepository.save(config); // in case it's new
            }
        }
    }
}
