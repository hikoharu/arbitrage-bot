CREATE TABLE `priceHistory` (
  `transactionId` bigint(11) unsigned NOT NULL,
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `buyPrice` double DEFAULT NULL,
  `sellPrice` double DEFAULT NULL,
  `balanceJpy` double DEFAULT NULL,
  `balanceBtc` double DEFAULT NULL,
  `reserveBalanceJpy` double DEFAULT NULL,
  `reserveBalanceBtc` double DEFAULT NULL,
  `prcDate` datetime DEFAULT NULL,
  PRIMARY KEY (`transactionId`,`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;