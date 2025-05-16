INSERT INTO countries (id, name) VALUES (1, 'USA');
INSERT INTO countries (id, name) VALUES (2, 'France');
INSERT INTO countries (id, name) VALUES (3, 'Brazil');
INSERT INTO countries (id, name) VALUES (4, 'Italy');
INSERT INTO countries (id, name) VALUES (5, 'Canada');


INSERT INTO nft.config_individual_income
(id, `level`, min_reserv_amt, max_reserv_amt, profit, calculation_type, profit_frequency, transactions_per_day, annualized_returns, lv_a_require, lv_b_require, lv_c_require)
VALUES(1, 'Level-1', 50.0, 1000.0, 1.8, 'PERCENTAGE', 'PER_DAY', 1, 657.0, 0, 0, 0);
INSERT INTO nft.config_individual_income
(id, `level`, min_reserv_amt, max_reserv_amt, profit, calculation_type, profit_frequency, transactions_per_day, annualized_returns, lv_a_require, lv_b_require, lv_c_require)
VALUES(2, 'Level-2', 500.0, 2000.0, 2.1, 'PERCENTAGE', 'PER_DAY', 1, 766.5, 3, 4, 1);
INSERT INTO nft.config_individual_income
(id, `level`, min_reserv_amt, max_reserv_amt, profit, calculation_type, profit_frequency, transactions_per_day, annualized_returns, lv_a_require, lv_b_require, lv_c_require)
VALUES(3, 'Level-3', 2000.0, 5000.0, 2.6, 'PERCENTAGE', 'PER_DAY', 1, 949.0, 6, 19, 1);
INSERT INTO nft.config_individual_income
(id, `level`, min_reserv_amt, max_reserv_amt, profit, calculation_type, profit_frequency, transactions_per_day, annualized_returns, lv_a_require, lv_b_require, lv_c_require)
VALUES(4, 'Level-4', 5000.0, 10000.0, 3.1, 'PERCENTAGE', 'PER_DAY', 1, 1131.5, 15, 34, 1);
INSERT INTO nft.config_individual_income
(id, `level`, min_reserv_amt, max_reserv_amt, profit, calculation_type, profit_frequency, transactions_per_day, annualized_returns, lv_a_require, lv_b_require, lv_c_require)
VALUES(5, 'Level-5', 10000.0, 30000.0, 3.7, 'PERCENTAGE', 'PER_DAY', 1, 1350.5, 25, 69, 1);

select * from nft.config_individual_income;