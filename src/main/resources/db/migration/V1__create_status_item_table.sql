CREATE TABLE status_item(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  completed_yesterday VARCHAR(255),
  working_on_today VARCHAR(255),
  impediments VARCHAR(255),
  date_created TIMESTAMP
)