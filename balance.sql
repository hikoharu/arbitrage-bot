CREATE TABLE `balance` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `serviceId` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `hour` int(11) DEFAULT NULL,
  `balanceJPY` double DEFAULT NULL,
  `balanceBTC` double DEFAULT NULL,
  `reserveBalanceJpy` double DEFAULT NULL,
  `reserveBalanceBtc` double DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `prcDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6301 DEFAULT CHARSET=latin1;