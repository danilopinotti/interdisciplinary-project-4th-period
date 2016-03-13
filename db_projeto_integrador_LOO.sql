SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE DATABASE IF NOT EXISTS `pi_2015_2` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pi_2015_2`;

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `types_id` smallint(5) unsigned DEFAULT NULL,
  `products_id_bulk` int(11) DEFAULT NULL,
  `unit_cost` float DEFAULT NULL,
  `stock_amount` float NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `types`;
CREATE TABLE IF NOT EXISTS `types` (
`id` smallint(5) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `weight` float NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `types` (`id`, `name`, `weight`) VALUES
(1, 'Produto bruto', 1),
(2, 'Saco 25 KG', 25),
(3, 'Saco 50 KG', 50),
(4, 'Tambor 100KG', 100),
(5, 'Pacote 5 KG', 5);

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
`id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` char(40) NOT NULL,
  `last_update` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `last_access` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

INSERT INTO `users` (`id`, `name`, `email`, `password`, `last_update`, `created_at`, `last_access`) VALUES
(1, 'Administrador do Sistema', 'admin@admin', 'e211039ea8009073e8002b52861ed856c11c8da0', '2015-12-04 12:46:34', '2015-12-04 12:46:34', NULL);


ALTER TABLE `products`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_products_types` (`types_id`), ADD KEY `fk_id_bulk` (`products_id_bulk`);

ALTER TABLE `types`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `users`
 ADD PRIMARY KEY (`id`);


ALTER TABLE `products`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `types`
MODIFY `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;

ALTER TABLE `products`
ADD CONSTRAINT `fk_id_bulk` FOREIGN KEY (`products_id_bulk`) REFERENCES `products` (`id`),
ADD CONSTRAINT `fk_products_types` FOREIGN KEY (`types_id`) REFERENCES `types` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
