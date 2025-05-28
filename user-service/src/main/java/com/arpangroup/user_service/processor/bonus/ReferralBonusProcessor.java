package com.arpangroup.user_service.processor.bonus;

import com.arpangroup.user_service.config.properties.ReferralBonusConfig;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;
import com.arpangroup.user_service.processor.bonus.strategy.ReferralBonusStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReferralBonusProcessor {
    private final Map<CalculationType, ReferralBonusStrategy> strategyMap;
    private final ReferralBonusConfig config;

    public ReferralBonusProcessor(List<ReferralBonusStrategy> strategies, ReferralBonusConfig config) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getSupportedType(),  // each strategy tells what type it supports
                        Function.identity()
                ));
        this.config = config;
    }

    public BigDecimal calculate(User user) {
        log.info("Calculating WelcomeBonus for userId: {}", user.getId());
        CalculationType type = config.getCalculationType(); // e.g. "percentage", "flat"
        ReferralBonusStrategy strategy = strategyMap.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported bonus type: " + type);
        }
        return strategy.calculateBonus(user);
    }

    public Optional<BigDecimal> calculateIfEnabled(User user) {
        if (!config.isEnable()) {
            return Optional.empty();
        }
        return Optional.of(calculate(user));
    }
}
