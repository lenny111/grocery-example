CREATE DATABASE  IF NOT EXISTS `grocery` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `grocery`;

DROP TABLE IF EXISTS `fruit`;
CREATE TABLE `fruit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(9,2) not null,
  `stock` int not null,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


LOCK TABLES `fruit` WRITE;

INSERT INTO `fruit` VALUES (1,'banana',0.29,20,'2015-04-25 08:21:10'),(2,'melon',1.01,3,'2015-04-25 08:21:10');

UNLOCK TABLES;


