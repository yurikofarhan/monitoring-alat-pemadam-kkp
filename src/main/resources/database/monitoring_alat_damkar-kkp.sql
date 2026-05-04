-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 04, 2026 at 01:45 PM
-- Server version: 8.4.3
-- PHP Version: 8.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `monitoring_alat_damkar-kkp`
--

-- --------------------------------------------------------

--
-- Table structure for table `alat`
--

CREATE TABLE `alat` (
  `id_alat` int NOT NULL,
  `kode_alat` varchar(50) NOT NULL,
  `nama_alat` varchar(50) NOT NULL,
  `jenis_alat` enum('APAR','APAB','HYDRANT','SPRINKLER','SUPPRESSION','PENDUKUNG','PROTEKSI') NOT NULL,
  `tanggal_pembelian` datetime NOT NULL,
  `id_lokasi` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `alat`
--

INSERT INTO `alat` (`id_alat`, `kode_alat`, `nama_alat`, `jenis_alat`, `tanggal_pembelian`, `id_lokasi`) VALUES
(1, 'A001', 'APAR 3kg', 'APAR', '2025-06-10 20:24:35', 2),
(2, 'A002', 'Hydrant Valve', 'HYDRANT', '2025-11-13 20:33:02', 2);

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_alat`
--

CREATE TABLE `lokasi_alat` (
  `id_lokasi` int NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `lantai` varchar(10) NOT NULL,
  `gedung` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lokasi_alat`
--

INSERT INTO `lokasi_alat` (`id_lokasi`, `nama_lokasi`, `lantai`, `gedung`) VALUES
(1, 'Ruang Server', '1', 'Gedung A'),
(2, 'Gudang APAR', '1', 'Gedung A'),
(3, 'Ruang Monitoring', '1', 'Gedung A'),
(4, 'Pos Keamanan', '1', 'Gedung B'),
(5, 'Workshop Perbaikan', '1', 'Gedung B');

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `id_pengguna` int NOT NULL,
  `nama_pengguna` varchar(50) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `role` enum('admin','petugas') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `nama_pengguna`, `username`, `password`, `no_telp`, `role`) VALUES
(1, 'Admin', 'admin', 'admin', '0812345678', 'admin'),
(2, 'Petugas', 'petugas', 'petugas', '089876543', 'petugas');

--
-- Triggers `pengguna`
--
DELIMITER $$
CREATE TRIGGER `prevent_delete_admin` BEFORE DELETE ON `pengguna` FOR EACH ROW BEGIN
    IF OLD.id_pengguna = 1 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Admin utama tidak boleh dihapus!';
    END IF;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alat`
--
ALTER TABLE `alat`
  ADD PRIMARY KEY (`id_alat`),
  ADD KEY `id_lokasi` (`id_lokasi`);

--
-- Indexes for table `lokasi_alat`
--
ALTER TABLE `lokasi_alat`
  ADD PRIMARY KEY (`id_lokasi`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alat`
--
ALTER TABLE `alat`
  MODIFY `id_alat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `lokasi_alat`
--
ALTER TABLE `lokasi_alat`
  MODIFY `id_lokasi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alat`
--
ALTER TABLE `alat`
  ADD CONSTRAINT `alat_ibfk_1` FOREIGN KEY (`id_lokasi`) REFERENCES `lokasi_alat` (`id_lokasi`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
