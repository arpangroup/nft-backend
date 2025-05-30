# Rank / Level Calculation Logic
In a Multi-Level Marketing (MLM) system, updating a user's level or rank depends on the business logic and the criteria defined for rank progression.

### When Should User Rank Be Updated in an MLM System: On Registration, First Deposit, or Scheduled Evaluation?


##  1. After Downline User Registration:
- **Pros**: 
  - **Immediate feedback:** Users see immediate changes in their rank or level when they successfully recruit new members 
  - **Encourages Recruitment:** motivates user with instant results to improve their rank.
- **Cons**: 
  - **May not reflect True Activity:** A user might recruit many members who do not actively participate or make deposits, leading to inflated rank. 

- **Example:**
  - Rank 2: Requires 5 direct referrals
  - Rank 3: Requires 20 total downline members (any depth)


## 2. After First Deposit / Purchase
- **Pros**:
    - **Ensure Active Participation:** By requiring a deposit, this approach ensures that new recruits are actively participating in the system.
    - **Aligns with Revenue:** Levels or ranks are updated based on actual financial contributions, aligning with the company's revenue goals
- **Cons**:
    - **Delayed Feedback:** Users may not see immediate changes in their rank until the new recruit makes a deposit

- **Example:**
  - Rank 2: 3 direct referrals, each must deposit $100+
  - Rank 3: Total downline deposit volume ≥ $1000

## 3. On a Daily Scheduled Job
- **Pros**:
  - **Consistent Updates:** Regular updates ensure that ranks reflect the most current state of the network
  - **Encourages Recruitment:** motivates user with instant results to improve their rank.
- **Cons**:
  - User doesn’t see instant feedback.


## Recommended Hybrid Approach: 
- **Immediate Update:** Update a user's rank immediately after a downline member makes their first deposit.
- **Scheduled Evaluation:** Perform a daily or weekly evaluation to adjust ranks based on overall activity, including new recruits, deposit and sale.

---

## ✅ Recommended Event Name:
UserRankUpdateEvent

| Name                                   | Use Case                                             |
| -------------------------------------- | ---------------------------------------------------- |
| `UserActivityTriggeredRankUpdateEvent` | When triggered by actions like deposit, registration |
| `ScheduledRankEvaluationEvent`         | For scheduled/daily batch processing                 |
| `ReferralBasedRankUpdateEvent`         | Triggered by new downline registrations              |
| `BusinessVolumeRankUpdateEvent`        | When based on sales or deposits (BV)                 |
| `RankRecalculationEvent`               | Generic, reusable for any rank recalculation trigger |



## ✅ Recommended Class Names:
| Class Name               | When to Use                                                    |
| ------------------------ | -------------------------------------------------------------- |
| `RankCalculationService` | Most common and clear — holds logic for calculating ranks      |
| `UserRankEvaluator`      | Emphasizes evaluation/decision-making aspect                   |
| `RankComputationService` | Focuses on computation-heavy or rules-based logic              |
| `UserRankProcessor`      | Suitable if it's part of a larger processing workflow          |
| `RankEngine`             | Good if it's a rule-driven, pluggable or strategy-based engine |



---

## What design pattern is best suited for determining a user's rank in an MLM system, where each rank has different and configurable eligibility criteria?

In our MLM system, each rank (e.g., Rank1, Rank2, etc.) has its own requirements. These requirements may include factors such as wallet balance thresholds and the number of downline users at different levels (e.g., Level-A, Level-B, Level-C). The system should allow:
- Configurable eligibility criteria for each rank (via config files or database).
- Extensible logic so that new rank evaluation rules can be added without impacting existing ones.
- Flexibility to support complex logic for future ranks.
  
**Example Rank Criteria:**
- Rank1:
  - Wallet balance between 50 and 100
- Rank2:
  - Wallet balance between 500 and 2000
  - At least 3 users in Level-A, 4 in Level-B, and 1 in Level-C
- Rank3:
  - Wallet balance between 2000 and 5000
  - At least 6 users in Level-A, 18 in Level-B, and 2 in Level-C
  - **For Rank3, you need to:**
    - First get Rank1 users.
    - Then from those, get Rank2 users.
    - Finally, their descendants will be Rank3 candidates.
- Rank4:
  - Wallet balance between 5000 and 10000
  - At least 15 users in Level-A, 30 in Level-B, and 5 in Level-C
  - **For Rank3, you need to:**
    - First get Rank1 users.
    - Then from those, get Rank2 users.
    - so on....

What would be the most appropriate design pattern to implement this kind of rank evaluation logic, keeping configurability and future extensibility in mind?
**Repeated DB calls must be avoided (e.g., reuse results for Rank1 while evaluating Rank2, etc.)**



## ✅ Recommended Design Pattern: Strategy Pattern
**1. RankEvaluationContext**
````java
@Getter
public class RankEvaluationContext {
    private final Long userId;
    private final BigDecimal walletBalance;

    // Cache to avoid duplicate DB queries
    private final Map<RankLevel, Set<Long>> rankUserMap = new EnumMap<>(RankLevel.class);
}
````

**2. UserHierarchyService:**
````java
@Service
@RequiredArgsConstructor
public class UserHierarchyService {
  private final UserHierarchyRepository hierarchyRepo;
    
  public Set<Long> getDirectReferrals(Long userId, int depth) {
    return hierarchyRepo.findByAncestorAndDepth(userId, depth).stream()
            .map(UserHierarchy::getDescendant)
            .collect(Collectors.toSet());
  }

  public Set<Long> calculateRankUsers(Long baseUserId, RankLevel level, RankEvaluationContext context) {
    if (level == RankLevel.LEVEL_1) {
      return getDirectReferrals(baseUserId, 1);
    }

    // Use cached data to build higher-level ranks
    RankLevel prevLevel = level.previous();
    Set<Long> previousRankUsers = context.getUsersForLevel(prevLevel);
    Set<Long> currentRankUsers = new HashSet<>();

    for (Long userId : previousRankUsers) {
      currentRankUsers.addAll(getDirectReferrals(userId, 1));
    }

    return currentRankUsers;
  }
}
````

**3. RankEvaluationStrategy:**
````java
public interface RankEvaluationStrategy {
    //boolean isEligible(User user, UserHierarchy hierarchy, BigDecimal walletBalance);
    boolean isEligible(RankEvaluationContext context, UserHierarchyService hierarchyService);
    Rank getRank();  // e.g., RANK_1, RANK_2...
}
````

**4. Example Rank2 Strategy:**
````java
@Component
public class Rank2Evaluation implements RankEvaluationStrategy {

    @Value("${rank2.min-balance}")
    private BigDecimal minBalance;

    @Value("${rank2.max-balance}")
    private BigDecimal maxBalance;

    /*@Override
    public boolean isEligible(User user, UserHierarchy hierarchy, BigDecimal walletBalance) {
        return walletBalance.compareTo(minBalance) >= 0 &&
               walletBalance.compareTo(maxBalance) <= 0 &&
               hierarchy.from user hierarchy, but () >= 3 &&
               hierarchy.getLevelBCount() >= 4 &&
               hierarchy.getLevelCCount() >= 1;
    }*/
  
    @Override
    public boolean isEligible(RankEvaluationContext context, UserHierarchyService hierarchyService) {
      if (context.getWalletBalance().compareTo(new BigDecimal("500")) < 0 ||
              context.getWalletBalance().compareTo(new BigDecimal("2000")) > 0) {
        return false;
      }

      // Get Rank1 users (level A)
      Set<Long> levelAUsers = context.hasCachedUsers(RankLevel.LEVEL_1)
              ? context.getUsersForLevel(RankLevel.LEVEL_1)
              : hierarchyService.calculateRankUsers(context.getUserId(), RankLevel.LEVEL_1, context);

      context.cacheUsers(RankLevel.LEVEL_1, levelAUsers);

      // Get Rank2 users (level B)
      Set<Long> levelBUsers = context.hasCachedUsers(RankLevel.LEVEL_2)
              ? context.getUsersForLevel(RankLevel.LEVEL_2)
              : hierarchyService.calculateRankUsers(context.getUserId(), RankLevel.LEVEL_2, context);

      context.cacheUsers(RankLevel.LEVEL_2, levelBUsers);

      return levelAUsers.size() >= 3 && levelBUsers.size() >= 4;
    }

    @Override
    public Rank getRank() {
        return Rank.RANK_2;
    }
}
````
💡 RankEvaluatorEngine
````java
@Service
@RequiredArgsConstructor
public class RankEvaluatorEngine {

    private final List<RankEvaluationStrategy> strategies;

    public Rank evaluate(User user, UserHierarchy hierarchy, BigDecimal walletBalance) {
        return strategies.stream()
            .filter(s -> s.isEligible(user, hierarchy, walletBalance))
            .map(RankEvaluationStrategy::getRank)
            .max(Comparator.comparing(Rank::getLevel))  // assumes Rank enum has getLevel()
            .orElse(Rank.NONE);
    }
}
````










