-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-04-2022 a las 17:44:17
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
-- Estructura de tabla para la tabla `info_transacciones`
--

CREATE TABLE `info_transacciones` (
  `id_transaccion` varchar(10) NOT NULL,
  `tipo_transaccion` set('deposito','retiro','transferencia') NOT NULL,
  `fecha_y_hora_transaccion` datetime NOT NULL,
  `id_cuenta_origen` varchar(5) NOT NULL,
  `id_tarjeta_origen` varchar(5) NOT NULL,
  `monto_origen` float NOT NULL,
  `nom_propietario_origen` varchar(80) NOT NULL,
  `ap_paterno_propietario_origen` varchar(30) NOT NULL,
  `ap_materno_propietario_origen` varchar(30) NOT NULL,
  `id_cuenta_destino` varchar(5) DEFAULT NULL,
  `id_tarjeta_destino` varchar(5) DEFAULT NULL,
  `monto_destino` float DEFAULT NULL,
  `nom_propietario_destino` varchar(80) DEFAULT NULL,
  `ap_paterno_propietario_destino` varchar(30) DEFAULT NULL,
  `ap_materno_propietario_destino` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `info_transacciones`
--

INSERT INTO `info_transacciones` (`id_transaccion`, `tipo_transaccion`, `fecha_y_hora_transaccion`, `id_cuenta_origen`, `id_tarjeta_origen`, `monto_origen`, `nom_propietario_origen`, `ap_paterno_propietario_origen`, `ap_materno_propietario_origen`, `id_cuenta_destino`, `id_tarjeta_destino`, `monto_destino`, `nom_propietario_destino`, `ap_paterno_propietario_destino`, `ap_materno_propietario_destino`) VALUES
('TR-00000', 'deposito', '2022-04-13 02:45:14', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00001', 'retiro', '2022-04-13 02:45:32', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00002', 'deposito', '2022-04-13 02:46:43', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00003', 'retiro', '2022-04-13 02:47:10', 'C0008', 'T0013', -5000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00004', 'retiro', '2022-04-13 02:47:18', 'C0008', 'T0012', -1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00005', 'deposito', '2022-04-13 03:35:12', 'C0006', 'T0026', 150000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00006', 'retiro', '2022-04-13 03:41:03', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00007', 'retiro', '2022-04-13 04:02:46', 'C0006', 'T0026', -10000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00008', 'deposito', '2022-04-13 04:03:28', 'C0006', 'T0026', 1, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00009', 'deposito', '2022-04-13 04:03:32', 'C0006', 'T0026', 1, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00010', 'deposito', '2022-04-13 04:03:35', 'C0006', 'T0026', 1, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00011', 'deposito', '2022-04-13 04:03:39', 'C0006', 'T0026', 1, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00012', 'deposito', '2022-04-13 04:03:42', 'C0006', 'T0026', 1, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00013', 'retiro', '2022-04-13 04:03:47', 'C0006', 'T0026', -100, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00014', 'retiro', '2022-04-13 04:04:04', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00015', 'deposito', '2022-04-13 04:04:11', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00016', 'retiro', '2022-04-13 04:04:18', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00017', 'deposito', '2022-04-13 15:35:36', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00018', 'retiro', '2022-04-13 15:35:57', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00019', 'deposito', '2022-04-13 15:38:10', 'C0015', 'T0027', 30000.5, 'Zenis Yulieth', 'Parra', 'Castillo', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00020', 'retiro', '2022-04-13 15:38:32', 'C0015', 'T0027', -13000, 'Zenis Yulieth', 'Parra', 'Castillo', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00021', 'transferencia', '2022-04-13 17:26:34', '', '', 0, '', '', '', 'C0015', 'T0027', 1000, 'Zenis Yulieth', 'Parra', 'Castillo'),
('TR-00022', 'transferencia', '2022-04-13 17:43:45', 'C0015', 'T0027', -1500.12, 'Zenis Yulieth', 'Parra', 'Castillo', '', '', 0, '', '', ''),
('TR-00023', 'deposito', '2022-04-13 17:47:07', 'C0015', 'T0027', 12345, 'Zenis Yulieth', 'Parra', 'Castillo', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00024', 'retiro', '2022-04-13 17:48:10', 'C0015', 'T0027', -1500.12, 'Zenis Yulieth', 'Parra', 'Castillo', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00025', 'transferencia', '2022-04-13 17:49:09', 'C0003', 'T0016', -5000, 'Franco Escamilla', '0', '', 'C0006', 'T0021', 5000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00026', 'transferencia', '2022-04-13 17:51:59', 'C0015', 'T0027', -18235, 'Zenis Yulieth', 'Parra', 'Castillo', 'C0003', 'T0016', 18235, 'Franco Escamilla', '0', ''),
('TR-00027', 'retiro', '2022-04-13 23:27:04', 'C0006', 'T0021', -10000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00028', 'transferencia', '2022-04-13 23:41:28', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00029', 'transferencia', '2022-04-13 23:41:56', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00030', 'transferencia', '2022-04-13 23:43:13', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00031', 'transferencia', '2022-04-13 23:43:40', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00032', 'transferencia', '2022-04-13 23:50:32', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00033', 'transferencia', '2022-04-13 23:56:39', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00034', 'transferencia', '2022-04-14 00:33:27', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00035', 'transferencia', '2022-04-14 00:35:33', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00036', 'transferencia', '2022-04-14 00:36:18', '', '', 0, '', '', '', 'C0006', 'T0026', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00037', 'transferencia', '2022-04-14 00:36:58', 'C0006', 'T0026', -10000, 'Alan Jair', 'Ordoñez', 'Pool', '', '', 0, '', '', ''),
('TR-00038', 'transferencia', '2022-04-14 00:38:36', '', '', 0, '', '', '', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00039', 'transferencia', '2022-04-14 00:43:16', 'C0008', 'T0013', -4000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', 'C0005', 'T0010', 4000, 'Alan', '', ''),
('TR-00040', 'transferencia', '2022-04-14 00:57:18', 'C0006', 'T0026', -1000, 'Alan Jair', 'Ordoñez', 'Pool', '', '', 0, '', '', ''),
('TR-00041', 'deposito', '2022-04-14 01:18:10', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00042', 'retiro', '2022-04-14 01:18:18', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00043', 'transferencia', '2022-04-14 01:18:29', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', '', '', 0, '', '', ''),
('TR-00044', 'transferencia', '2022-04-14 01:18:48', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', '', '', 0, '', '', ''),
('TR-00045', 'transferencia', '2022-04-14 01:35:00', 'C0008', 'T0012', -1234, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga', '', '', 0, '', '', ''),
('TR-00046', 'transferencia', '2022-04-14 02:28:32', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00047', 'transferencia', '2022-04-14 02:29:32', '', '', 0, '', '', '', 'C0006', 'T0021', 1533, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00048', 'deposito', '2022-04-14 19:25:55', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00049', 'deposito', '2022-04-14 19:29:18', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00050', 'deposito', '2022-04-14 19:31:48', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00051', 'deposito', '2022-04-14 19:33:02', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00052', 'deposito', '2022-04-14 19:34:08', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00053', 'deposito', '2022-04-14 19:34:55', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00054', 'deposito', '2022-04-14 19:36:00', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00055', 'deposito', '2022-04-11 19:38:43', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00056', 'deposito', '2022-04-12 19:40:37', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00057', 'deposito', '2022-04-14 19:43:28', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00058', 'transferencia', '2022-04-14 19:55:20', 'C0006', 'T0026', -1000, 'Alan Jair', 'Ordoñez', 'Pool', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00059', 'deposito', '2022-04-15 00:19:00', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00060', 'retiro', '2022-04-15 00:19:14', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00061', 'deposito', '2022-04-15 00:19:55', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00062', 'transferencia', '2022-04-15 00:20:41', '', '', 0, '', '', '', 'C0008', 'T0025', 1500, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00063', 'retiro', '2022-04-15 01:21:17', 'C0006', 'T0021', -1250, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00064', 'retiro', '2022-04-15 01:21:34', 'C0006', 'T0028', -250, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00065', 'transferencia', '2022-04-15 01:21:58', 'C0006', 'T0028', -2000, 'Alan Jair', 'Ordoñez', 'Pool', 'C0008', 'T0025', 2000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00066', 'retiro', '2022-04-16 03:27:47', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00067', 'deposito', '2022-04-16 03:28:29', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00068', 'deposito', '2022-04-16 05:15:28', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00069', 'deposito', '2022-04-16 05:19:22', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00070', 'deposito', '2022-04-16 05:41:34', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00071', 'deposito', '2022-04-16 06:46:32', 'C0006', 'T0026', 10, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00072', 'deposito', '2022-04-16 06:46:56', 'C0006', 'T0021', 10, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00073', 'deposito', '2022-04-17 02:48:42', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00074', 'retiro', '2022-04-17 02:48:54', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00075', 'transferencia', '2022-04-17 02:49:42', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00076', 'transferencia', '2022-04-17 02:49:59', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00077', 'transferencia', '2022-04-17 02:50:11', '', '', 0, '', '', '', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00078', 'deposito', '2022-04-17 02:50:30', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00079', 'transferencia', '2022-04-17 20:06:40', '', '', 0, '', '', '', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00080', 'deposito', '2022-04-17 22:44:46', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00081', 'deposito', '2022-04-17 23:28:44', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00082', 'retiro', '2022-04-17 23:29:03', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00083', 'transferencia', '2022-04-17 23:29:30', '', '', 0, '', '', '', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00084', 'deposito', '2022-04-17 23:31:37', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00085', 'deposito', '2022-04-23 02:12:00', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00086', 'deposito', '2022-04-23 02:12:36', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00087', 'retiro', '2022-04-23 02:14:12', 'C0006', 'T0021', -1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00088', 'transferencia', '2022-04-23 02:14:28', 'C0006', 'T0028', -1000, 'Alan Jair', 'Ordoñez', 'Pool', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00089', 'deposito', '2022-04-27 10:30:48', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00090', 'deposito', '2022-04-27 10:32:40', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00091', 'retiro', '2022-04-29 07:09:08', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00092', 'transferencia', '2022-04-29 07:09:23', '', '', 0, '', '', '', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00093', 'retiro', '2022-04-29 09:57:21', 'C0006', 'T0021', -15000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00094', 'deposito', '2022-04-29 09:58:18', 'C0006', 'T0021', 1000, 'Alan Jair', 'Ordoñez', 'Pool', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00095', 'transferencia', '2022-04-29 09:59:28', 'C0006', 'T0021', -2000, 'Alan Jair', 'Ordoñez', 'Pool', 'C0006', 'T0026', 2000, 'Alan Jair', 'Ordoñez', 'Pool'),
('TR-00096', 'transferencia', '2022-04-29 10:00:17', 'C0006', 'T0026', -1000, 'Alan Jair', 'Ordoñez', 'Pool', 'C0008', 'T0025', 1000, 'Valentina Elizabeth', 'Ordoñez', 'Jauriga'),
('TR-00097', 'deposito', '2022-04-29 10:07:12', '', '', 0, '', '', '', NULL, NULL, NULL, NULL, NULL, NULL),
('TR-00098', 'deposito', '2022-04-29 10:12:35', 'C0027', 'T0014', 1000, '1234', '1234', '1234', NULL, NULL, NULL, NULL, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `info_transacciones`
--
ALTER TABLE `info_transacciones`
  ADD PRIMARY KEY (`id_transaccion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
