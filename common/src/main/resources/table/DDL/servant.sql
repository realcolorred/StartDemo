CREATE TABLE `servant` (
  `servant_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `servant_name_china` varchar(100) DEFAULT NULL,
  `servant_name_english` varchar(100) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `weight` varchar(100) DEFAULT NULL,
  `height` varchar(100) DEFAULT NULL,
  `star` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`servant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4
