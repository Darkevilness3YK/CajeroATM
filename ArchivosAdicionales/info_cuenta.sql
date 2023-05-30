-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-04-2022 a las 17:44:01
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
-- Estructura de tabla para la tabla `info_cuenta`
--

CREATE TABLE `info_cuenta` (
  `id_cuenta` varchar(5) NOT NULL,
  `sesion_activa` tinyint(1) NOT NULL,
  `nom_propietario` varchar(80) NOT NULL,
  `ap_paterno_propietario` varchar(30) NOT NULL,
  `ap_materno_propietario` varchar(30) NOT NULL,
  `contrasena` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `info_cuenta`
--

INSERT INTO `info_cuenta` (`id_cuenta`, `sesion_activa`, `nom_propietario`, `ap_paterno_propietario`, `ap_materno_propietario`, `contrasena`) VALUES
('C0000', 0, 'El santo 5000 wieufhsdihbvhsdvjl', '0', '', 'P345h0073r'),
('C0001', 0, 'Cuenta', 'De', 'Prueba', '1234'),
('C0002', 0, 'Gabriel Cebollas García del Señor', '0', '', 'maría_te_amo'),
('C0003', 0, 'Franco Escamilla', '0', '', 'AaaSeMamooo'),
('C0004', 0, 't', '0', '', 't'),
('C0005', 0, 'Alan', '0', '', 'P345h0073r'),
('C0006', 0, 'Alan Jair', 'Ordoñez', 'Pool', '1234'),
('C0007', 0, 'Rhoshandiatellyneshiaunneveshenk Koyaanisquatsiuth', 'OTTOVORDEMGENTSCHENFELDE', 'OTTOVORDEMGENTSCHENFELDE', '1234'),
('C0008', 0, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', '1234'),
('C0009', 0, 'apapapapap apapapapap apapapapap apapapapap', '', '', '1'),
('C0010', 0, 'Valentina Elizabeth Ordoñez Jaurigaz', '', '', '1'),
('C0011', 0, 'apapapapapapapapapapapapapapapa', '', '', '2'),
('C0012', 0, 'Cuenta', 'De', 'Prueba', '111'),
('C0013', 0, '1', '1', '1', '1'),
('C0014', 0, 'CuentaDePrueba', 'Numero', 'Dos', '112233'),
('C0015', 0, 'Zenis Yulieth', 'Parra', 'Castillo', '1234'),
('C0016', 0, 'Javier Agustín', 'Pedraza', 'Sánchez', '1234'),
('C0017', 0, '1', '2', '3', '4'),
('C0018', 0, '1234', '12', '34', '12'),
('C0019', 0, '1', '1', '1', '1'),
('C0020', 0, '1', '2', '3', '4'),
('C0021', 0, '1', '2', '3', '4'),
('C0022', 0, 'a', 'a', 'a', 'aaaaaaaa'),
('C0023', 0, 'a', 'a', 'a', 'a'),
('C0024', 0, '124124', '124124', '124124', '1'),
('C0025', 0, '1324123', '14123124', '1241241', '1'),
('C0026', 0, '214124', '124124', '124124', '12345678'),
('C0027', 0, '1234', '1234', '1234', '1234');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `info_cuenta`
--
ALTER TABLE `info_cuenta`
  ADD PRIMARY KEY (`id_cuenta`) USING BTREE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
