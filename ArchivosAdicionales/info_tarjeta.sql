-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-04-2022 a las 17:44:10
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cajero_atm`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `info_tarjeta`
--

CREATE TABLE `info_tarjeta` (
  `id_tarjeta` varchar(5) NOT NULL,
  `cvv_cvc` smallint(4) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `id_cuenta` varchar(5) NOT NULL,
  `nom_propietario` varchar(80) NOT NULL,
  `ap_paterno_propietario` varchar(30) NOT NULL,
  `ap_materno_propietario` varchar(30) NOT NULL,
  `saldo_actual` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `info_tarjeta`
--

INSERT INTO `info_tarjeta` (`id_tarjeta`, `cvv_cvc`, `fecha_vencimiento`, `id_cuenta`, `nom_propietario`, `ap_paterno_propietario`, `ap_materno_propietario`, `saldo_actual`) VALUES
('T0000', NULL, NULL, 'C0005', 'Alan', '', '', 20000),
('T0001', NULL, NULL, 'C0003', 'Franco Escamilla', '', '', 6102),
('T0002', NULL, NULL, 'C0005', 'Alan', '', '', 20000),
('T0003', NULL, NULL, 'C0005', 'Alan', '', '', 20000),
('T0004', NULL, NULL, 'C0005', 'Alan', '', '', 20000),
('T0005', NULL, NULL, 'C0005', 'Alan', '', '', 20000),
('T0006', NULL, NULL, 'C0003', 'Franco Escamilla', '', '', 10000),
('T0007', NULL, NULL, 'C0004', 't', '', '', 3250.12),
('T0008', 415, '2026-04-11', 'C0005', 'Alan', '', '', 20000),
('T0009', 419, '2054-10-26', 'C0005', 'Alan', '', '', 20000),
('T0010', 925, '2026-04-11', 'C0005', 'Alan', '', '', 14000),
('T0011', 543, '2026-04-11', 'C0010', 'Valentina Elizabeth Ordoñez Jaurigaz', '', '', 123),
('T0012', 450, '2026-04-11', 'C0008', 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', 4266),
('T0013', 610, '2026-04-11', 'C0008', 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', 0),
('T0014', 444, '2026-04-29', 'C0027', '1234', '1234', '1234', 6000),
('T0015', 232, '2026-04-11', 'C0012', 'Cuenta', 'De', 'Prueba', 1000),
('T0016', 184, '2026-04-12', 'C0003', 'Franco Escamilla', '0', '', 24337),
('T0017', 111, '2026-04-12', 'C0004', 't', '0', '', 5499.75),
('T0018', 497, '2026-04-12', 'C0005', 'Alan', '0', '', 7655),
('T0019', 875, '2026-04-12', 'C0014', 'CuentaDePrueba', 'Numero', 'Dos', 1111),
('T0020', 533, '2026-04-12', 'C0005', 'Alan', '0', '', 30000),
('T0021', 110, '2026-04-17', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 5699.1),
('T0022', 154, '2026-04-12', 'C0001', 'Tu mama', '0', '', 0),
('T0023', 112, '2026-04-12', 'C0001', 'Tu mama', '0', '', 3),
('T0024', 142, '2026-04-12', 'C0007', 'Rhoshandiatellyneshiaunneveshenk Koyaanisquatsiuth', 'OTTOVORDEMGENTSCHENFELDE', 'OTTOVORDEMGENTSCHENFELDE', 2923),
('T0025', 894, '2026-04-13', 'C0008', 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', 13900),
('T0026', 712, '2026-04-29', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 1000),
('T0027', 607, '2026-04-13', 'C0015', 'Zenis Yulieth', 'Parra', 'Castillo', 111110),
('T0028', 400, '2026-04-17', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 11750),
('T0029', 323, '2026-04-17', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 3000),
('T0030', 465, '2026-04-17', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 12345),
('T0031', 782, '2026-04-29', 'C0006', 'Alan Jair', 'Ordoñez', 'Pool', 30000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `info_tarjeta`
--
ALTER TABLE `info_tarjeta`
  ADD PRIMARY KEY (`id_tarjeta`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
