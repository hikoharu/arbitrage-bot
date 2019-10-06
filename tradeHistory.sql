CREATE TABLE `tradeHistory` (
  `transactionId` bigint(11) NOT NULL,
  `orderId` varchar(767) NOT NULL DEFAULT '0',
  `isSuccess` varchar(11) DEFAULT NULL,
  `errorContent` varchar(2000) DEFAULT NULL,
  `prcDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;