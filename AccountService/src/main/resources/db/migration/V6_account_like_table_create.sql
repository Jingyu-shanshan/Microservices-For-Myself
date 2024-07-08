DROP TABLE IF EXISTS `account_like`;
CREATE TABLE `account_like` (
  `id` int NOT NULL AUTO_INCREMENT,
  `liked_account_id` varchar(32),
  `liked_post_id` varchar(32),
  `status` tinyint(1) default '1',
  `create_date` DATE,
  `update_date` DATE,
  PRIMARY KEY (`id`),
  INDEX `liked_account_id`(`liked_account_id`),
  INDEX `liked_post_id`(`liked_post_id`)
);