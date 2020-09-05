CREATE TABLE `servant` (
  `servant_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `servant_name_china` varchar(100) NOT NULL DEFAULT '',
  `servant_name_english` varchar(100) NOT NULL DEFAULT '',
  `sex` varchar(100) NOT NULL DEFAULT '',
  `weight` varchar(100) NOT NULL DEFAULT '',
  `height` varchar(100) NOT NULL DEFAULT '',
  `star` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`servant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;
