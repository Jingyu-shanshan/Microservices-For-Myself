DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `body` TEXT,
  `created_at` DATE,
  `account_number` varchar(255),
  `reply_to` varchar(255),
  `post_id` int,
  FOREIGN KEY (post_id) REFERENCES post(id),
  PRIMARY KEY (`id`)
);