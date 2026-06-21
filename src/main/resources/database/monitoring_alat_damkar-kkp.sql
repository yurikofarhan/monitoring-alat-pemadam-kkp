-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 01, 2026 at 01:32 PM
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `alat`
--

INSERT INTO `alat` (`id_alat`, `kode_alat`, `nama_alat`, `jenis_alat`, `tanggal_pembelian`, `id_lokasi`) VALUES
(1, 'A001', 'APAR 3kg', 'APAR', '2025-06-10 20:24:35', 2),
(2, 'A002', 'Hydrant Valve', 'HYDRANT', '2025-11-13 20:33:02', 2);

-- --------------------------------------------------------

--
-- Table structure for table `inspeksi_alat`
--

CREATE TABLE `inspeksi_alat` (
  `id_inspeksi` int NOT NULL,
  `tanggal_inspeksi` datetime NOT NULL,
  `kondisi` enum('baik','rusak_ringan','rusak_berat') NOT NULL,
  `status` enum('layak','perlu_perawatan','tidak_layak','maintenance','hilang') NOT NULL,
  `status_inspeksi` enum('draf','proses','selesai') NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `id_pengguna` int NOT NULL,
  `id_alat` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `inspeksi_alat`
--

INSERT INTO `inspeksi_alat` (`id_inspeksi`, `tanggal_inspeksi`, `kondisi`, `status`, `status_inspeksi`, `keterangan`, `id_pengguna`, `id_alat`) VALUES
(1, '2026-05-02 00:00:00', 'baik', 'layak', 'selesai', 'ok', 1, 1),
(2, '2026-06-03 00:00:00', 'baik', 'perlu_perawatan', 'proses', 'mt', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `log_aktivitas`
--

CREATE TABLE `log_aktivitas` (
  `id_log` int NOT NULL,
  `id_pengguna` int NOT NULL,
  `waktu` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `aksi` varchar(255) NOT NULL,
  `tabel_terkait` varchar(255) NOT NULL,
  `record_id` int DEFAULT NULL,
  `deskripsi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `log_aktivitas`
--

INSERT INTO `log_aktivitas` (`id_log`, `id_pengguna`, `waktu`, `aksi`, `tabel_terkait`, `record_id`, `deskripsi`) VALUES
(1, 1, '2026-06-01 18:38:20', 'UPDATE', 'alat', 5, 'Mengubah data Alat A003'),
(2, 1, '2026-06-01 18:56:33', 'UPDATE', 'alat', 5, 'Mengubah data Alat A003'),
(3, 1, '2026-06-01 19:02:38', 'DELETE', 'alat', 5, 'Mengubah data Alat A003'),
(4, 1, '2026-06-01 19:15:50', 'INSERT', 'alat', 6, 'Menambah data Alat test'),
(5, 1, '2026-06-01 19:21:10', 'INSERT', 'alat', 7, 'Menambah data Alat 123'),
(6, 1, '2026-06-01 19:24:11', 'DELETE', 'alat', 7, 'Menghapus data Alat 123'),
(7, 1, '2026-06-01 19:52:46', 'DELETE', 'alat', 6, 'Menghapus data Alat test'),
(8, 1, '2026-06-01 20:04:36', 'INSERT', 'lokasi', 9, 'Menambah data Lokasi 9'),
(9, 1, '2026-06-01 20:19:42', 'UPDATE', 'lokasi', 9, 'Mengubah data Lokasi 9'),
(10, 1, '2026-06-01 20:19:55', 'DELETE', 'lokasi', 9, 'Menghapus data Lokasi 9'),
(11, 1, '2026-06-01 20:20:01', 'INSERT', 'lokasi', 10, 'Menambah data Lokasi 10'),
(12, 1, '2026-06-01 20:20:19', 'DELETE', 'lokasi', 10, 'Menghapus data Lokasi 10'),
(13, 1, '2026-06-01 20:20:49', 'INSERT', 'pengguna', 5, 'Menambah data Pengguna 5'),
(14, 1, '2026-06-01 20:21:03', 'UPDATE', 'pengguna', 5, 'Mengubah data Pengguna 5'),
(15, 1, '2026-06-01 20:21:35', 'DELETE', 'pengguna', 5, 'Menghapus data Pengguna 5'),
(16, 1, '2026-06-01 20:30:23', 'INSERT', 'inspeksi', 2, 'Menambah data Inspeksi pada Id Alat 1');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_alat`
--

CREATE TABLE `lokasi_alat` (
  `id_lokasi` int NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `lantai` varchar(10) NOT NULL,
  `gedung` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
-- Table structure for table `maintenance_alat`
--

CREATE TABLE `maintenance_alat` (
  `id_maintenance` int NOT NULL,
  `tanggal_mulai` datetime NOT NULL,
  `tanggal_selesai` datetime NOT NULL,
  `bukti_image` varchar(255) NOT NULL,
  `status` enum('pending','proses','selesai') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `id_alat` int NOT NULL,
  `id_inspeksi` int NOT NULL,
  `id_pengguna` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `nama_pengguna`, `username`, `password`, `no_telp`, `role`) VALUES
(1, 'Admin', 'admin', '$2a$10$ZjrLbvPBdTAX8WFQdyxiK.225auVM0dF/I4L1RKx5CrBVLGW6vZqi', '0812345678', 'admin'),
(2, 'Petugas', 'petugas', '$2a$10$S7uTXr9T83f7W.ZDB6ngxOFcCHltUVA2jfg3D7rvKqSiJYrsyTuD.', '089876543', 'petugas');

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
-- Indexes for table `inspeksi_alat`
--
ALTER TABLE `inspeksi_alat`
  ADD PRIMARY KEY (`id_inspeksi`),
  ADD KEY `id_pengguna` (`id_pengguna`),
  ADD KEY `id_alat` (`id_alat`);

--
-- Indexes for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD PRIMARY KEY (`id_log`),
  ADD KEY `id_pengguna` (`id_pengguna`);

--
-- Indexes for table `lokasi_alat`
--
ALTER TABLE `lokasi_alat`
  ADD PRIMARY KEY (`id_lokasi`);

--
-- Indexes for table `maintenance_alat`
--
ALTER TABLE `maintenance_alat`
  ADD PRIMARY KEY (`id_maintenance`),
  ADD KEY `id_alat` (`id_alat`),
  ADD KEY `id_inspeksi` (`id_inspeksi`),
  ADD KEY `id_pengguna` (`id_pengguna`);

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
  MODIFY `id_alat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `inspeksi_alat`
--
ALTER TABLE `inspeksi_alat`
  MODIFY `id_inspeksi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  MODIFY `id_log` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `lokasi_alat`
--
ALTER TABLE `lokasi_alat`
  MODIFY `id_lokasi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `maintenance_alat`
--
ALTER TABLE `maintenance_alat`
  MODIFY `id_maintenance` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alat`
--
ALTER TABLE `alat`
  ADD CONSTRAINT `alat_ibfk_1` FOREIGN KEY (`id_lokasi`) REFERENCES `lokasi_alat` (`id_lokasi`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `inspeksi_alat`
--
ALTER TABLE `inspeksi_alat`
  ADD CONSTRAINT `inspeksi_alat_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `inspeksi_alat_ibfk_2` FOREIGN KEY (`id_alat`) REFERENCES `alat` (`id_alat`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD CONSTRAINT `log_aktivitas_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `maintenance_alat`
--
ALTER TABLE `maintenance_alat`
  ADD CONSTRAINT `maintenance_alat_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `maintenance_alat_ibfk_2` FOREIGN KEY (`id_alat`) REFERENCES `alat` (`id_alat`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `maintenance_alat_ibfk_3` FOREIGN KEY (`id_inspeksi`) REFERENCES `inspeksi_alat` (`id_inspeksi`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
