# 🚒 Sistem Monitoring Alat Damkar
Aplikasi desktop berbasis **Java Swing** yang digunakan untuk memonitor kondisi alat pemadam kebakaran, mulai dari pendataan alat, inspeksi, maintenance, hingga pencatatan log aktivitas pengguna.

---

## 🔑 Informasi Login

### 👨‍💼 Admin
| Username | Password |
|----------|----------|
| `admin` | `admin` |

### 👨‍🔧 Petugas
| Username | Password |
|----------|----------|
| `petugas` | `petugas` |

> **Catatan:** Password pada database disimpan menggunakan **BCrypt** sehingga lebih aman.

---

## 🛠️ Spesifikasi Aplikasi

| Komponen | Teknologi |
|----------|-----------|
| Bahasa Pemrograman | Java |
| IDE | Apache NetBeans 29 |
| Build Tool | Apache Maven |
| Framework GUI | Java Swing |
| Database | MySQL |
| Library Enkripsi Password | BCrypt |
| Library Reporting | iReport 5.6 (JasperReports) |
| Arsitektur | MVC (Model-View-Controller) |

---

## ✨ Fitur Utama

- 🔐 Login Multi Role (Admin & Petugas)
- 👥 Manajemen Pengguna
- 🚒 Manajemen Data Alat Pemadam
- 📍 Manajemen Lokasi Alat
- 📝 Inspeksi Alat
- 🔧 Maintenance Alat
- 📊 Dashboard Monitoring
- 📄 Cetak Laporan menggunakan iReport
- 📜 Log Aktivitas Pengguna
- 🖼️ Upload Gambar Bukti Maintenance

---

## 👥 Anggota Kelompok

| No | Nama | NPM |
|----|------|-----|
| 1 | Yuriko Farhan | 202343501378 |
| 2 | Muhammad Dennis Abimanyu | 202343501359 |
| 3 | Dwi Wahyuni | 202343501396 |
| 4 | Syahria Mufida Indri Maulia | 202343501404 |
| 5 | Riska Hermeinasyah Fatihah | 202343501366 |
| 6 | Muhamad Arief Budhiyanto | 202343501367 |
| 7 | Muhammad Fahrezy | 202343501395 |
| 8 | Muhammad Hafizh Mukhlish Iskandar | 202343501381 |

---

## 📊 Entity Relationship Diagram (ERD)

Berikut adalah visualisasi struktur database yang digunakan dalam sistem ini:

```mermaid
erDiagram
    alat ||--o{ inspeksi_alat : "diinspeksi"
    alat ||--o{ maintenance_alat : "dimaintenance"
    lokasi_alat ||--o{ alat : "memiliki"
    pengguna ||--o{ inspeksi_alat : "melakukan"
    pengguna ||--o{ maintenance_alat : "melakukan"
    pengguna ||--o{ log_aktivitas : "menciptakan"

    alat {
        int id_alat PK
        varchar kode_alat
        varchar nama_alat
        enum jenis_alat "APAR, APAB, HYDRANT, SPRINKLER, SUPPRESSION, PENDUKUNG, PROTEKSI"
        datetime tanggal_pembelian
        varchar deskripsi
        int id_lokasi FK
    }

    pengguna {
        int id_pengguna PK
        varchar nama_pengguna
        varchar username
        varchar password
        varchar no_telp
        enum role "admin, petugas"
    }

    lokasi_alat {
        int id_lokasi PK
        varchar nama_lokasi
        varchar lantai
        varchar gedung
    }

    inspeksi_alat {
        int id_inspeksi PK
        datetime tanggal_inspeksi
        enum kondisi "baik, rusak_ringan, rusak_berat"
        enum status "layak, perlu_perawatan, tidak_layak, maintenance, hilang"
        enum status_inspeksi "draf, proses, selesai"
        varchar keterangan
        int id_pengguna FK
        int id_alat FK
    }

    maintenance_alat {
        int id_maintenance PK
        datetime tanggal_mulai
        datetime tanggal_selesai
        varchar bukti_image
        enum status "proses, selesai"
        varchar keterangan
        int id_alat FK
        int id_inspeksi FK
        int id_pengguna FK
    }

    log_aktivitas {
        int id_log PK
        int id_pengguna FK
        datetime waktu
        varchar aksi
        varchar tabel_terkait
        int record_id
        varchar deskripsi
    }

```

