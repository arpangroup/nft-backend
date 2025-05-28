package com.arpangroup.user_service.batch;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DailyIncomeBatchJob {
    /*private final UserService userService;
    private final TransactionService txnService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateDailyIncome() {
        List<User> users = userService.getUsers();
        for (User user : users) {
            double dailyIncome = txnService.calculateUserIncome();

            user.setReserveBalance(user.getReserveBalance() + dailyIncome);
            userService.updateUser(user);

            propagateIncomeToUpline(user, dailyIncome);
        }
    }

    public void propagateIncomeToUpline(User user, double income) {
        User referrer = user.getgetReferrer();
        if (referrer != null) {
            double teamIncome = income * 0.10; // 10%
            referrer.setReserveBalance(referrer.getReserveBalance() + teamIncome);
            userService.updateUser(user);

            propagateIncomeToUpline(referrer, teamIncome);
        }
    }*/
}
