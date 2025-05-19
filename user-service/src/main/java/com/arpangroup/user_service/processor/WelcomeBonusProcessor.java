package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.config.properties.WelcomeBonusConfig;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;
import com.arpangroup.user_service.processor.strategy.WelcomeBonusStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WelcomeBonusProcessor {
    private final Map<CalculationType, WelcomeBonusStrategy> strategyMap;
    private final WelcomeBonusConfig config;

    public WelcomeBonusProcessor(List<WelcomeBonusStrategy> strategies, WelcomeBonusConfig config) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getSupportedType(),  // each strategy tells what type it supports
                        Function.identity()
                ));
        this.config = config;
    }

    public double calculate(User user) {
        log.info("Calculating WelcomeBonus for userId: {}", user.getId());
        CalculationType type = config.getCalculationType(); // e.g. "percentage", "flat"
        WelcomeBonusStrategy strategy = strategyMap.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported bonus type: " + type);
        }
        return strategy.calculateBonus(user);
    }

    public Optional<Double> calculateIfEnabled(User user) {
        if (!config.isEnable()) {
            log.warn("WelcomeBonus not enable in properties");
            return Optional.empty();
        }
        return Optional.of(calculate(user));
    }
}
