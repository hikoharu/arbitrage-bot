CREATE TABLE `profit` (
  `id` bigint(11) unsigned NOT NULL,
  `askServiceId` int(11) NOT NULL DEFAULT '0',
  `bidServiceId` int(11) NOT NULL DEFAULT '0',
  `profit` double DEFAULT NULL,
  `prcDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`askServiceId`,`bidServiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;