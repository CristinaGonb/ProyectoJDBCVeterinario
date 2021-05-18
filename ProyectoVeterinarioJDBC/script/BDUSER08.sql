-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 08-05-2021 a las 20:43:17
-- Versión del servidor: 8.0.21
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `BDUSER08`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_dueno`
--

CREATE TABLE `vet_dueno` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_mascota`
--

CREATE TABLE `vet_mascota` (
  `chip` int NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `raza` varchar(30) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  `dni_dueno` varchar(9) NOT NULL,
  `dni_veterinario` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_veterinario`
--

CREATE TABLE `vet_veterinario` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `especialidad` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `vet_dueno`
--
ALTER TABLE `vet_dueno`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `vet_mascota`
--
ALTER TABLE `vet_mascota`
  ADD PRIMARY KEY (`chip`),
  ADD KEY `dni_dueno` (`dni_dueno`),
  ADD KEY `dni_veterinario` (`dni_veterinario`);

--
-- Indices de la tabla `vet_veterinario`
--
ALTER TABLE `vet_veterinario`
  ADD PRIMARY KEY (`dni`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `vet_mascota`
--
ALTER TABLE `vet_mascota`
  ADD CONSTRAINT `vet_mascota_ibfk_1` FOREIGN KEY (`dni_dueno`) REFERENCES `vet_dueno` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vet_mascota_ibfk_2` FOREIGN KEY (`dni_veterinario`) REFERENCES `vet_veterinario` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
