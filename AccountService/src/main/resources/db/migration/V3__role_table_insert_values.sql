INSERT INTO `role`(`name`) VALUES('ADMIN'), ('USER');
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `account_id` int,
  `role_id` int
);