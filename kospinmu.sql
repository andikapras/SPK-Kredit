-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2020 at 09:17 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `penjualan`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE IF NOT EXISTS `akun` (
  `id_akun` char(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  PRIMARY KEY (`id_akun`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`id_akun`, `nama`) VALUES
('AKUN01', 'Kas'),
('AKUN02', 'Penjualan');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` char(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `harga` int(12) NOT NULL,
  `keterangan` varchar(30) NOT NULL,
  `stok` int(5) NOT NULL,
  PRIMARY KEY (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `nama`, `harga`, `keterangan`, `stok`) VALUES
('BK01', 'Buku 38 Kiky', 3000, 'PCS', 41),
('BK02', 'Buku Bigbos', 4000, 'PCS', 37),
('BK03', 'Buku 38 Sidu', 2000, 'PCS', 13),
('CP01', 'Ciput', 5000, 'PCS', 21),
('CP02', 'Seragam Olah Raga', 100000, 'Sepasang', 42),
('IP01', 'Ikat Pinggang', 12000, 'PCS', 5),
('KK01', 'Kaos Kaki Pendek', 5000, 'Sepasang', 14),
('KK02', 'Kaos Kaki Panjang', 10000, 'Sepasang', 20),
('MN01', 'Pulpen', 1000, 'PCS', 84),
('MN02', 'Pensil', 1500, 'PCS', 36),
('MN03', 'Penghapus', 3000, 'PCS', 0),
('MP01', 'Map File', 3000, 'PCS', 195),
('PM01', 'Pembalut', 1000, 'PCS', 18),
('SL01', 'Solasi Hitam', 2000, 'PCS', 16),
('SL02', 'Solasi Kertas', 3000, 'PCS', 15),
('SL03', 'Solasi Bening', 1000, 'PCS', 20),
('TP03', 'Topi Osis', 8000, 'PCS', 29),
('TP04', 'Topi Pramuka', 13000, 'PCS', 18),
('TS01', 'Tisu', 500, 'PCS', 100);

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE IF NOT EXISTS `detail_transaksi` (
  `id_transaksi` char(12) NOT NULL,
  `id_barang` char(12) NOT NULL,
  `nis` char(12) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_transaksi`, `id_barang`, `nis`, `jumlah`, `total`) VALUES
('TRX-PNJ-0001', 'CP02', '19.1.3137', 1, 100000);

-- --------------------------------------------------------

--
-- Table structure for table `jurnal`
--

CREATE TABLE IF NOT EXISTS `jurnal` (
  `id_jurnal` char(12) NOT NULL,
  `nama` char(12) NOT NULL,
  `tanggal` varchar(40) NOT NULL,
  `debit` int(11) NOT NULL,
  `credit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jurnal`
--

INSERT INTO `jurnal` (`id_jurnal`, `nama`, `tanggal`, `debit`, `credit`) VALUES
('Jurnal-0001', 'Kas', '2020-07-01', 184000, 0),
('Jurnal-0001', 'Penjualan', '2020-07-01', 0, 184000);

-- --------------------------------------------------------

--
-- Table structure for table `kwitansi`
--

CREATE TABLE IF NOT EXISTS `kwitansi` (
  `id_transaksi` char(12) NOT NULL,
  `nama_siswa` varchar(50) NOT NULL,
  `kelas` varchar(30) NOT NULL,
  `nama_barang` varchar(30) NOT NULL,
  `bayar` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kwitansi`
--

INSERT INTO `kwitansi` (`id_transaksi`, `nama_siswa`, `kelas`, `nama_barang`, `bayar`) VALUES
('TRX-PNJ-0001', 'ANJAS LESMANA', 'X TBSM 2', 'Seragam Olah Raga', '100000');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE IF NOT EXISTS `siswa` (
  `nis` char(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kelas` varchar(25) NOT NULL,
  `jns_kelamin` varchar(15) NOT NULL,
  PRIMARY KEY (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`nis`, `nama`, `kelas`, `jns_kelamin`) VALUES
('19.1.3136', 'ANGGUN ALFARIQI', 'X TBSM 2', 'Laki-Laki'),
('19.1.3137', 'ANJAS LESMANA', 'X TBSM 2', 'Laki-Laki'),
('19.1.3138', 'BAYU IRWANTO', 'X TBSM 2', 'Laki-Laki'),
('19.1.3141', 'DANI ARDIANSYAH', 'X TBSM 2', 'Laki-Laki'),
('19.1.3167', 'ADINDA AMALIA SALSABILA', 'X TKJ 1', 'Perempuan'),
('19.1.3168', 'AGUS PRASETYO', 'X TKJ 1', 'Laki-Laki'),
('19.1.3169', 'AHMAD HILAL HUSNI', 'X TKJ 1', 'Laki-Laki'),
('19.1.3170', 'AHMAD KHAFIDIN', 'X TKJ 1', 'Laki-Laki'),
('19.1.3171', 'ALESSIANA EMILIA PUTRI', 'X TKJ 1', 'Perempuan'),
('19.1.3178', 'AHMAD FURQON', 'X TKJ 1', 'Laki - laki'),
('19.1.3454', 'AFIDA SHAFINA AL FALHAH', 'X AKL 4', 'Perempuan'),
('19.1.3455', 'ALFIATUN NAJILAH', 'X AKL 4', 'Perempuan'),
('19.1.3475', 'NIZAR ABDUL MANAN', 'X AKL 4', 'Laki-Laki'),
('19.1.3476', 'NUR AMALIA', 'X AKL 4', 'Perempuan'),
('19.1.3477', 'PUTRI AWALLINDA', 'X AKL 4', 'Perempuan'),
('19.1.3535', 'MAULIDA AULIA ZAHWA', 'X AKL 3', 'Perempuan');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE IF NOT EXISTS `transaksi` (
  `id_transaksi` char(12) NOT NULL,
  `bayar` int(12) NOT NULL,
  `tanggal` date NOT NULL,
  `nis` char(12) NOT NULL,
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `bayar`, `tanggal`, `nis`) VALUES
('TRX-PNJ-0001', 100000, '2020-07-08', '19.1.3137');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(15) NOT NULL,
  `password` varchar(8) NOT NULL,
  `nama` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `nama`) VALUES
('admin', 'admin', 'Admin');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
