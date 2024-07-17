DROP TABLE IF EXISTS `account_like`;
CREATE TABLE `account_like` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int,
  `liked_id` int,
  `status` tinyint(1) default '1',
  `create_date` DATE,
  `update_date` DATE,
  PRIMARY KEY (`id`),
  INDEX `account_id`(`account_id`),
  INDEX `liked_id`(`liked_id`)
);