DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  `account_number` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  `introduction` varchar(255),
  `location` varchar(255),
  `websites` varchar(255),
  `date_of_birth` varchar(255),
  `user_image` MEDIUMBLOB,
  `user_background_image` MEDIUMBLOB,
  `create_date` DATE,
  `following_number` int NOT NULL default 0,
  `follower_number` int NOT NULL default 0,
  `delete_flag` int NOT NULL default 0,
  PRIMARY KEY (`id`)
);