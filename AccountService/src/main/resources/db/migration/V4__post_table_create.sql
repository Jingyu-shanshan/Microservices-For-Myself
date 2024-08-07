DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255),
  `description` varchar(255),
  `content` TEXT,
  `type` varchar(255),
  `created_at` DATE,
  `account_number` varchar(255),
  `like_count` int,
  PRIMARY KEY (`id`)
);