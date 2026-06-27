/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.LogAktivitasModel;
import com.pemadam.monitoring.model.MaintenanceModel;
import com.pemadam.monitoring.model.PenggunaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class LaporanDAO {

    public List<AlatModel> tampilkanDataAlat() {
        List<AlatModel> listAlat = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT "
                + "  a.id_alat, "
                + "  a.kode_alat, "
                + "  a.nama_alat, "
                + "  a.jenis_alat, "
                + "  l.nama_lokasi, "
                + "  i.kondisi AS kondisi_terakhir, "
                + "  i.status AS status_terakhir "
                + "FROM alat a "
                + "LEFT JOIN lokasi_alat l ON a.id_lokasi = l.id_lokasi "
                + "LEFT JOIN ( "
                + "  SELECT id_alat, kondisi, status, "
                + "         ROW_NUMBER() OVER (PARTITION BY id_alat ORDER BY tanggal_inspeksi DESC) as rn "
                + "  FROM inspeksi_alat "
                + ") i ON a.id_alat = i.id_alat AND i.rn = 1 "
                + "ORDER BY a.kode_alat ASC";

        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AlatModel alat = new AlatModel();
                alat.setIdAlat(rs.getInt("id_alat"));
                alat.setKodeAlat(rs.getString("kode_alat"));
                alat.setNamaAlat(rs.getString("nama_alat"));
                alat.setJenisAlat(rs.getString("jenis_alat"));
                alat.setNamaLokasi(rs.getString("nama_lokasi") == null ? "-" : rs.getString("nama_lokasi"));

                String kondisiRaw = rs.getString("kondisi_terakhir");
                if (kondisiRaw != null) {
                    String kondisiFormat = kondisiRaw.replace("_", " ");
                    kondisiFormat = kondisiFormat.substring(0, 1).toUpperCase() + kondisiFormat.substring(1);
                    alat.setKondisiTerakhir(kondisiFormat);
                } else {
                    alat.setKondisiTerakhir("Belum Dicek");
                }

                String statusRaw = rs.getString("status_terakhir");
                if (statusRaw != null) {
                    String statusFormat = statusRaw.replace("_", " ");
                    statusFormat = statusFormat.substring(0, 1).toUpperCase() + statusFormat.substring(1);
                    alat.setStatusTerakhir(statusFormat);
                } else {
                    alat.setStatusTerakhir("-");
                }

                listAlat.add(alat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }

        return listAlat;
    }

    public List<InspeksiModel> tampilkanDataInspeksi() {
        List<InspeksiModel> listInspeksi = new java.util.ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT "
                + "  i.id_inspeksi, "
                + "  a.kode_alat, "
                + "  a.nama_alat, "
                + "  i.tanggal_inspeksi, "
                + "  i.kondisi, "
                + "  i.status AS status_alat, "
                + "  i.status_inspeksi, "
                + "  p.nama_pengguna "
                + "FROM inspeksi_alat i "
                + "JOIN alat a ON i.id_alat = a.id_alat "
                + "LEFT JOIN pengguna p ON i.id_pengguna = p.id_pengguna "
                + "ORDER BY i.tanggal_inspeksi DESC";

        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                InspeksiModel inspeksi = new InspeksiModel();
                inspeksi.setIdInspeksi(rs.getInt("id_inspeksi"));
                inspeksi.setKodeAlat(rs.getString("kode_alat"));
                inspeksi.setNamaAlat(rs.getString("nama_alat"));
                inspeksi.setTanggalInspeksi(rs.getTimestamp("tanggal_inspeksi"));
                inspeksi.setNamaPengguna(rs.getString("nama_pengguna") == null ? "-" : rs.getString("nama_pengguna"));

                // Format Enum Kondisi
                String kondisiRaw = rs.getString("kondisi");
                if (kondisiRaw != null) {
                    String kFormat = kondisiRaw.replace("_", " ");
                    inspeksi.setKondisi(kFormat.substring(0, 1).toUpperCase() + kFormat.substring(1));
                } else {
                    inspeksi.setKondisi("-");
                }

                String statusAlatRaw = rs.getString("status_alat");
                if (statusAlatRaw != null) {
                    String saFormat = statusAlatRaw.replace("_", " ");
                    inspeksi.setStatus(saFormat.substring(0, 1).toUpperCase() + saFormat.substring(1));
                } else {
                    inspeksi.setStatus("-");
                }

                String statusInspeksiRaw = rs.getString("status_inspeksi");
                if (statusInspeksiRaw != null) {
                    String siFormat = statusInspeksiRaw.replace("_", " ");
                    inspeksi.setStatusInspeksi(siFormat.substring(0, 1).toUpperCase() + siFormat.substring(1));
                } else {
                    inspeksi.setStatusInspeksi("-");
                }

                listInspeksi.add(inspeksi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return listInspeksi;
    }

    public java.util.List<MaintenanceModel> tampilkanDataMaintenance() {
        java.util.List<MaintenanceModel> listMaintenance = new java.util.ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT "
                + "  m.id_maintenance, "
                + "  a.kode_alat, "
                + "  a.nama_alat, "
                + "  m.tanggal_mulai, "
                + "  m.tanggal_selesai, "
                + "  m.status AS status_maintenance, "
                + "  m.keterangan, "
                + "  p.nama_pengguna "
                + "FROM maintenance_alat m "
                + "JOIN alat a ON m.id_alat = a.id_alat "
                + "LEFT JOIN pengguna p ON m.id_pengguna = p.id_pengguna "
                + "ORDER BY m.tanggal_mulai DESC";

        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                MaintenanceModel maintenance = new MaintenanceModel();
                maintenance.setIdMaintenance(rs.getInt("id_maintenance"));
                maintenance.setKodeAlat(rs.getString("kode_alat"));
                maintenance.setNamaAlat(rs.getString("nama_alat"));
                maintenance.setTanggalMulai(rs.getTimestamp("tanggal_mulai"));
                maintenance.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                maintenance.setKeterangan(rs.getString("keterangan") == null ? "-" : rs.getString("keterangan"));
                maintenance.setNamaPengguna(rs.getString("nama_pengguna") == null ? "-" : rs.getString("nama_pengguna"));

                String statusRaw = rs.getString("status_maintenance");
                if (statusRaw != null) {
                    maintenance.setStatus(statusRaw.substring(0, 1).toUpperCase() + statusRaw.substring(1));
                } else {
                    maintenance.setStatus("-");
                }

                listMaintenance.add(maintenance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return listMaintenance;
    }

    public List<PenggunaModel> tampilkanDataPengguna() {

        java.util.List<PenggunaModel> listPengguna = new java.util.ArrayList<>();
        java.sql.Connection conn = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;

        String sql = "SELECT id_pengguna, nama_pengguna, username, no_telp, role FROM pengguna ORDER BY nama_pengguna ASC";

        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                PenggunaModel pengguna = new PenggunaModel();
                pengguna.setIdPengguna(rs.getInt("id_pengguna"));
                pengguna.setNamaPengguna(rs.getString("nama_pengguna"));
                pengguna.setUsername(rs.getString("username"));
                pengguna.setNoTelp(rs.getString("no_telp") == null ? "-" : rs.getString("no_telp"));

                String roleRaw = rs.getString("role");
                if (roleRaw != null) {
                    pengguna.setRole(roleRaw.substring(0, 1).toUpperCase() + roleRaw.substring(1));
                } else {
                    pengguna.setRole("-");
                }

                listPengguna.add(pengguna);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return listPengguna;

    }

    public List<LogAktivitasModel> tampilkanDataAktivitas() {
        java.util.List<LogAktivitasModel> listLog = new java.util.ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT "
                + "  l.id_log, "
                + "  l.waktu, "
                + "  l.aksi, "
                + "  l.tabel_terkait, "
                + "  l.record_id, "
                + "  l.deskripsi, "
                + "  p.id_pengguna, "
                + "  p.nama_pengguna, "
                + "  p.role "
                + "FROM log_aktivitas l "
                + "LEFT JOIN pengguna p ON l.id_pengguna = p.id_pengguna "
                + "ORDER BY l.waktu DESC";

        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LogAktivitasModel log = new LogAktivitasModel();
                log.setIdLog(rs.getInt("id_log"));
                log.setWaktu(rs.getTimestamp("waktu"));
                log.setIdPengguna(rs.getInt("id_pengguna"));
                log.setNamaPengguna(rs.getString("nama_pengguna") == null ? "System" : rs.getString("nama_pengguna"));
                log.setAksi(rs.getString("aksi"));
                log.setTabelTerkait(rs.getString("tabel_terkait"));
                log.setRecordId(rs.getInt("record_id"));
                log.setDeskripsi(rs.getString("deskripsi") == null ? "-" : rs.getString("deskripsi"));

                String roleRaw = rs.getString("role");
                if (roleRaw != null) {
                    log.setRole(roleRaw.substring(0, 1).toUpperCase() + roleRaw.substring(1));
                } else {
                    log.setRole("-");
                }

                listLog.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return listLog;

    }

}
