CREATE TABLE `arbitrageTransaction` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `askServiceId` int(11) DEFAULT NULL,
  `bidServiceId` int(11) DEFAULT NULL,
  `currency` varchar(2000) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `askAmount` double DEFAULT NULL,
  `bidAmount` double DEFAULT NULL,
  `difference` double DEFAULT NULL,
  `buyDate` datetime DEFAULT NULL,
  `sellDate` datetime DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `prcDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=311495 DEFAULT CHARSET=latin1;