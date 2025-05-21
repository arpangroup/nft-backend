const queryData = [
  {
    "title": "1. To get all Level A, B, C downlines of a user:",
    "description": "Count of all downline users under a user grouped by depth",
    "query": "SELECT depth, COUNT(*) AS user_count\nFROM user_hierarchy\nWHERE ancestor = 1 AND depth in (1,2,3)\nGROUP BY depth\nORDER BY depth;"
  },
  {
    "title": "2. To get the list of user IDs grouped by each depth:",
    "description": "To get the list of user IDs grouped by each depth under a specific ancestor from you",
    "query": "SELECT depth, GROUP_CONCAT(descendant) AS user_ids\nFROM user_hierarchy\nWHERE ancestor = 1 AND depth in (1,2,3)\nGROUP BY depth\nORDER BY depth;"
  },





  {
    "title": "2. To get all Level A, B, C downlines of a user:",
    "description": "Using inner subquery",
    "query": "SELECT descendant, depth FROM user_hierarchy\nWHERE ancestor = 1 AND depth IN (1,2,3)\nORDER BY depth;"
  },
  {
    "title": "OR Using JOIN:",
    "description": "To get all Level A, B, C downlines of a user:",
    "query": "SELECT u.id, u.username, uh.depth FROM user_hierarchy uh\nJOIN users u ON u.id = uh.descendant\nWHERE uh.ancestor = 1 AND uh.depth IN (1,2,3)\nORDER BY uh.depth;"
  },
  {
    "title": "3. SQL Query for Level-A Users:",
    "description": "2. To get direct referrals (Level A only):",
    "query": "SELECT u.id, u.username FROM users u WHERE u.id IN (\n    SELECT descendant FROM user_hierarchy\n    WHERE ancestor = 1 AND `depth` = 1\n);"
  },
  {
    "title": "4. SQL Query for Level-B Users:",
    "description": "To select all Level-B users (i.e., users at depth = 2)",
    "query": "SELECT u.id, u.username FROM users u WHERE u.id IN (\n    SELECT descendant FROM user_hierarchy uh\n  WHERE uh.ancestor = 1 AND uh.depth = 2\n);"
  },
  {
    "title": "OR Using JOIN:",
    "description": "To select all Level-B users (i.e., users at depth = 2)",
    "query": "SELECT u.id, u.username FROM users u\nJOIN user_hierarchy uh ON u.id = uh.descendant\nWHERE uh.ancestor = 1 AND uh.depth = 2"
  },
  {
    "title": "5. SQL Query for Level-C Users:",
    "description": "To select all Level-C users (i.e., users at depth = 3)",
    "query": "SELECT u.id, u.username from users u\nJOIN user_hierarchy uh ON u.id = uh.descendant\nWHERE uh.ancestor = 1 AND uh.depth = 3;"
  },
  {
    "title": "Show all users:",
    "query": "select id, username, referral_code, reserve_balance, `level` from users;"
  },
  {
    "title": "Show user_hierarchy table:",
    "query": "select id, ancestor, descendant,`depth` from user_hierarchy uh ;"
  },
  {
    "title": "Transactions table:",
    "query": "select id, user_id, sender_id, txn_type, amount, balance, remarks from transactions t ;"
  },
  {
    "title": "Config individual income table:",
    "query": "SELECT id, 'level', min_reserv_amt, max_reserv_amt, profit,\ncalculation_type, profit_frequency, txn_per_day, annualized_returns,\nlv_a_require, lv_b_require, lv_c_require\nFROM config_individual_income ic;"
  },
  {
    "title": "User’s direct referrals (example for user 2):",
    "query": "select u.id, u.username from users u where u.id in(\n\tselect `descendant` from user_hierarchy uh where uh.ancestor = 2 and depth = 1\n);"
  }
]
