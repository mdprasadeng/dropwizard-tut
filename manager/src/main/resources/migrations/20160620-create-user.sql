--liquibase formatted sql

--changeset durga.p:1
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(256) DEFAULT NULL,
  `phone_number` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_unique` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- rollback drop table users;
