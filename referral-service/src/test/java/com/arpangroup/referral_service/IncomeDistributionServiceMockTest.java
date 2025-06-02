package com.arpangroup.referral_service;

import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.hierarchy.UserHierarchy;
import com.arpangroup.referral_service.hierarchy.UserHierarchyRepository;
import com.arpangroup.referral_service.income.dto.UplineIncomeLog;
import com.arpangroup.referral_service.income.entity.IncomeHistory;
import com.arpangroup.referral_service.income.entity.TeamRebateConfig;
import com.arpangroup.referral_service.income.repository.IncomeHistoryRepository;
import com.arpangroup.referral_service.income.repository.TeamRebateConfigRepository;
import com.arpangroup.referral_service.income.service.IncomeDistributionService;
import com.arpangroup.referral_service.income.service.TeamCommissionService;
import com.arpangroup.referral_service.income.strategy.TeamIncomeStrategy;
import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.repository.RankConfigRepository;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IncomeDistributionServiceMockTest {

    @InjectMocks
    private IncomeDistributionService incomeDistributionService;

    @Mock private RankConfigRepository rankConfigRepository;
    @Mock private TeamRebateConfigRepository teamRebateConfigRepository;
    @Mock private IncomeHistoryRepository incomeHistoryRepository;
    @Mock private UserHierarchyRepository userHierarchyRepository;
    @Mock private UserRepository userRepository;
    @Mock private UserClient userClient;
    @Mock private TeamCommissionService teamCommissionService;
    @Mock private TeamIncomeStrategy teamIncomeStrategy;

    @Test
    void testDistributeIncome_withSellerAtLevel3_shouldDistributeCorrectly() {
        // Setup users
        User u1 = new User(1L, "U1", Rank.RANK_3.getValue(), BigDecimal.valueOf(500));
        User u2 = new User(2L, "U2", Rank.RANK_2.getValue(), BigDecimal.valueOf(400));
        User u3 = new User(3L, "U3", Rank.RANK_2.getValue(), BigDecimal.valueOf(300));
        User u4_seller = new User(4L, "U4", Rank.RANK_2.getValue(), BigDecimal.valueOf(200));

        BigDecimal saleAmount = BigDecimal.valueOf(100);

        // Rank config mock
        when(rankConfigRepository.findById(Rank.RANK_2))
                .thenReturn(Optional.of(new RankConfig(Rank.RANK_2, 0, 0, new BigDecimal("0.10"))));

        // Hierarchy (closure table style)
        List<UserHierarchy> hierarchy = List.of(
                new UserHierarchy(1L, 4L, 3), // U1 is level 3
                new UserHierarchy(2L, 4L, 2), // U2 is level 2
                new UserHierarchy(3L, 4L, 1)  // U3 is level 1
        );

        when(userHierarchyRepository.findByDescendant(4L)).thenReturn(hierarchy);
        when(userRepository.findAllById(Set.of(1L, 2L, 3L))).thenReturn(List.of(u1, u2, u3));
        when(userRepository.findById(4L)).thenReturn(Optional.of(u4_seller));

        // Team income percentages
        when(teamCommissionService.getTeamCommissionPercentage(Rank.RANK_2, 1)).thenReturn(new BigDecimal("0.10"));
        when(teamCommissionService.getTeamCommissionPercentage(Rank.RANK_3, 2)).thenReturn(new BigDecimal("0.07"));
        when(teamCommissionService.getTeamCommissionPercentage(Rank.RANK_4, 3)).thenReturn(new BigDecimal("0.05"));

        List<UplineIncomeLog> mockLogs = List.of(
                new UplineIncomeLog(3L, Rank.RANK_2, 1, new BigDecimal("0.10"), new BigDecimal("1.0000")),
                new UplineIncomeLog(2L, Rank.RANK_2, 2, new BigDecimal("0.07"), new BigDecimal("0.7000")),
                new UplineIncomeLog(1L, Rank.RANK_3, 3, new BigDecimal("0.05"), new BigDecimal("0.5000"))
        );
        when(teamIncomeStrategy.distributeTeamIncome(anyLong(), any(), any(), any(), any())).thenReturn(mockLogs);


        // Invoke
        incomeDistributionService.distributeIncome(4L, saleAmount);

        // Capture all saved income history records
        ArgumentCaptor<IncomeHistory> captor = ArgumentCaptor.forClass(IncomeHistory.class);
        verify(incomeHistoryRepository, times(4)).save(captor.capture());

        List<IncomeHistory> savedIncomes = captor.getAllValues();

        // Verify expected incomes saved
        // Assert each expected income
        assertThat(savedIncomes).anySatisfy(income ->
                assertThat(income)
                        .extracting(IncomeHistory::getUserId, IncomeHistory::getAmount)
                        .containsExactly(4L, new BigDecimal("10.0000"))
        );

        assertThat(savedIncomes).anySatisfy(income ->
                assertThat(income)
                        .extracting(IncomeHistory::getUserId, IncomeHistory::getAmount)
                        .containsExactly(3L, new BigDecimal("1.0000"))
        );

        assertThat(savedIncomes).anySatisfy(income ->
                assertThat(income)
                        .extracting(IncomeHistory::getUserId, IncomeHistory::getAmount)
                        .containsExactly(2L, new BigDecimal("0.7000"))
        );

        assertThat(savedIncomes).anySatisfy(income ->
                assertThat(income)
                        .extracting(IncomeHistory::getUserId, IncomeHistory::getAmount)
                        .containsExactly(1L, new BigDecimal("0.5000"))
        );

    }
}
