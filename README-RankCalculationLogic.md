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



















