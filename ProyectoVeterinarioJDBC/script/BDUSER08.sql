-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 24-05-2021 a las 20:49:10
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
-- Estructura de tabla para la tabla `vet_cita`
--

CREATE TABLE `vet_cita` (
  `id` int NOT NULL,
  `fecha` date NOT NULL,
  `motivo` varchar(150) NOT NULL,
  `chip` int NOT NULL,
  `dni_veterinario` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `vet_cita`
--

INSERT INTO `vet_cita` (`id`, `fecha`, `motivo`, `chip`, `dni_veterinario`) VALUES
(1, '2021-05-17', 'vacuna', 123451, '1234'),
(2, '2021-05-17', 'Peluqueria', 123453, '9876'),
(3, '2021-05-18', 'Alergia piel', 123452, '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_dueno`
--

CREATE TABLE `vet_dueno` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `ciudad` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `vet_dueno`
--

INSERT INTO `vet_dueno` (`dni`, `nombre`, `apellidos`, `telefono`, `ciudad`) VALUES
('111111', 'Carmen', 'Baizan Romero', '664185005', 'Carmona'),
('222222', 'Cristina', 'Gonzalez Sanchez', '954143010', 'Alcala de Guadaira'),
('333333', 'Jesus', 'Olmedo Olmedo', '999888765', 'Viso del Alcor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vet_mascota`
--

CREATE TABLE `vet_mascota` (
  `chip` int NOT NULL,
  `nombre` varchar(15) NOT NULL,
  `raza` varchar(30) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  `dni_dueno` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `vet_mascota`
--

INSERT INTO `vet_mascota` (`chip`, `nombre`, `raza`, `sexo`, `dni_dueno`) VALUES
(123451, 'Simba', 'Chihuahua', 'M', '111111'),
(123452, 'Mimi', 'Podenco', 'H', '111111'),
(123453, 'Luna', 'Yorkshire', 'H', '333333');

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
-- Volcado de datos para la tabla `vet_veterinario`
--

INSERT INTO `vet_veterinario` (`dni`, `nombre`, `apellidos`, `especialidad`) VALUES
('1234', 'Ismael', 'Cortazar Rodriguez', '0'),
('9876', 'Saray', 'Jimenez Morillo', '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `vet_cita`
--
ALTER TABLE `vet_cita`
  ADD PRIMARY KEY (`id`),
  ADD KEY `chip` (`chip`),
  ADD KEY `dni_veterinario` (`dni_veterinario`);

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
  ADD KEY `fk_mascota_dueno` (`dni_dueno`);

--
-- Indices de la tabla `vet_veterinario`
--
ALTER TABLE `vet_veterinario`
  ADD PRIMARY KEY (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `vet_cita`
--
ALTER TABLE `vet_cita`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `vet_cita`
--
ALTER TABLE `vet_cita`
  ADD CONSTRAINT `vet_cita_ibfk_1` FOREIGN KEY (`chip`) REFERENCES `vet_mascota` (`chip`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vet_cita_ibfk_2` FOREIGN KEY (`dni_veterinario`) REFERENCES `vet_veterinario` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `vet_mascota`
--
ALTER TABLE `vet_mascota`
  ADD CONSTRAINT `fk_mascota_dueno` FOREIGN KEY (`dni_dueno`) REFERENCES `vet_dueno` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
