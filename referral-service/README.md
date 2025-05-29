## Referral Service:





## The PendingBonus Entity
| Field                      | Description                                                                                        |
| -------------------------- | -------------------------------------------------------------------------------------------------- |
| `referrerId`               | ID of the user who should receive the bonus.                                                       |
| `refereeId`                | ID of the newly referred user (the one who triggers the bonus).                                    |
| `bonusAmount`              | How much bonus is to be given if approved.                                                         |
| `triggerType`              | Enum indicating what action should trigger this bonus (e.g., FIRST\_DEPOSIT, ACCOUNT\_ACTIVATION). |
| `evaluated`                | Indicates whether this bonus has been checked/processed.                                           |
| `approved`                 | Marks whether the bonus was successfully granted.                                                  |
| `remarks`                  | Optional explanation for rejection or approval reason.                                             |
| `createdAt`, `evaluatedAt` | Timestamps for tracking processing lifecycle.                                                      |


````java

@Entity
@Table(name = "pending_bonus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingBonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long referrerId;  //ID of the user who should receive the bonus.

    private Long refereeId;   // ID of the newly referred user (the one who triggers the bonus).

    private double bonusAmount; // How much bonus is to be given if approved.

    @Enumerated(EnumType.STRING)
    private TriggerType triggerType; // Enum indicating what action should trigger this bonus (e.g., FIRST_DEPOSIT, ACCOUNT_ACTIVATION).

    private boolean evaluated; // If the bonus has been evaluated/processed

    private boolean approved; // Marks whether the bonus was successfully granted.

    private String remarks; // Optional explanation for rejection or approval reason.

    private LocalDateTime createdAt;

    private LocalDateTime evaluatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
````

## 1. Creation When User Is Referred
When a new user registers using a referral code:
- The system does not grant the bonus immediately.
- Instead, it creates a `PendingBonus` record with the expected `bonusAmount`, `referrerId`, `refereeId`, and `triggerType`.
````java
// Example: inside Registration or ReferralService
pendingBonusRepository.save(PendingBonus.builder()
    .referrerId(referrer.getId())
    .refereeId(newUser.getId())
    .bonusAmount(50.0)
    .triggerType(TriggerType.FIRST_DEPOSIT)
    .evaluated(false)
    .approved(false)
    .build());

````

## 2. Evaluation Based on Events or Conditions
Later, when the referee performs an action (like deposit, activation, etc.), the system checks for matching `PendingBonus` records.
````java
public void evaluateAllPendingBonuses(Long userId, TriggerType triggerType) {
    List<PendingBonus> bonuses = pendingBonusRepository.findByRefereeIdAndTriggerTypeAndEvaluatedFalse(userId, triggerType);
    
    for (PendingBonus bonus : bonuses) {
        boolean isEligible = checkEligibility(userId, triggerType);
        if (isEligible) {
            awardBonus(bonus);
        } else {
            bonus.setRemarks("User did not meet requirements.");
        }

        bonus.setEvaluated(true);
        bonus.setEvaluatedAt(LocalDateTime.now());
        pendingBonusRepository.save(bonus);
    }
}
````

## 3. Bonus Awarding
If the condition is met, the bonus is marked as approved and the balance of the `referrer` is updated:
````java
private void awardBonus(PendingBonus bonus) {
    User referrer = userRepository.findById(bonus.getReferrerId()).orElseThrow();
    referrer.setBalance(referrer.getBalance() + bonus.getBonusAmount());

    bonus.setApproved(true);
    bonus.setRemarks("Bonus awarded successfully.");
    userRepository.save(referrer);
}
````


The `PendingBonus` entity is typically used to store and track referral bonuses that are not **awarded** immediately but depend on **certain trigger events** or conditions.

### 🔧 Summary
| Action                               | How `PendingBonus` is used                       |
| ------------------------------------ | ------------------------------------------------ |
| **On registration**                  | Record created with `evaluated = false`          |
| **On trigger (deposit, activation)** | System checks and evaluates it                   |
| **If eligible**                      | Marks as `approved` and updates referrer balance |
| **If not**                           | Marks as evaluated with remarks                  |
