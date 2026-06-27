/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.model.InspeksiModel;
//import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.MaintenanceModel;
import com.pemadam.monitoring.util.EnumUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class MaintenanceDAO {

    private static final String TABLE_NAME = "maintenance_alat";

    public List<String> getEnumStatus() {
        return EnumUtil.getEnumValues(TABLE_NAME, "status");
    }

    public int insert(MaintenanceModel m) {

        String sql = "INSERT INTO " + TABLE_NAME
                + " (tanggal_mulai, tanggal_selesai, status, keterangan, id_alat, id_inspeksi, id_pengguna) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (Session.getUser() == null) {
                throw new RuntimeException("User belum login!");
            }

            ps.setTimestamp(1, m.getTanggalMulai());
            ps.setTimestamp(2, m.getTanggalSelesai());
            ps.setString(3, m.getBuktiImage());
            ps.setString(3, m.getStatus());
            ps.setString(4, m.getKeterangan());
            ps.setInt(5, m.getIdAlat());
            ps.setInt(6, m.getIdInspeksi());
            ps.setInt(7, Session.getUser().getIdPengguna());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }

    public boolean update(MaintenanceModel m) {

        String sql = "UPDATE " + TABLE_NAME + " SET "
                + "tanggal_mulai=?, tanggal_selesai=?, bukti_image=?, status=?, keterangan=? "
                + "WHERE id_maintenance=?";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, m.getTanggalMulai());
            ps.setTimestamp(2, m.getTanggalSelesai());
            ps.setString(3, m.getBuktiImage());
            ps.setString(4, m.getStatus());
            ps.setString(5, m.getKeterangan());
            ps.setInt(6, m.getIdMaintenance());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public MaintenanceModel getById(int id) {

        MaintenanceModel m = null;

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_maintenance = ?";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new MaintenanceModel();

                m.setIdMaintenance(rs.getInt("id_maintenance"));
                m.setTanggalMulai(rs.getTimestamp("tanggal_mulai"));
                m.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                m.setBuktiImage(rs.getString("bukti_image"));
                m.setStatus(rs.getString("status"));
                m.setKeterangan(rs.getString("keterangan"));
                m.setIdAlat(rs.getInt("id_alat"));
                m.setIdInspeksi(rs.getInt("id_inspeksi"));
                m.setIdPengguna(rs.getInt("id_pengguna"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }

    public List<MaintenanceModel> getByInspeksi(int idInspeksi) {

        List<MaintenanceModel> list = new ArrayList<>();

        String sql = "SELECT m.*, p.nama_pengguna "
                + "FROM " + TABLE_NAME + " m "
                + "JOIN pengguna p ON m.id_pengguna = p.id_pengguna "
                + "WHERE m.id_inspeksi = ?";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInspeksi);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MaintenanceModel m = new MaintenanceModel();

                m.setIdMaintenance(rs.getInt("id_maintenance"));
                m.setTanggalMulai(rs.getTimestamp("tanggal_mulai"));
                m.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                m.setBuktiImage(rs.getString("bukti_image"));
                m.setStatus(rs.getString("status"));
                m.setKeterangan(rs.getString("keterangan"));
                m.setIdAlat(rs.getInt("id_alat"));
                m.setIdInspeksi(rs.getInt("id_inspeksi"));
                m.setIdPengguna(rs.getInt("id_pengguna"));
                m.setNamaPengguna(rs.getString("nama_pengguna"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<MaintenanceModel> getAll() {

        List<MaintenanceModel> list = new ArrayList<>();

        String sql = """
            SELECT 
                m.*,
                a.nama_alat,
                p.nama_pengguna
            FROM maintenance_alat m
            LEFT JOIN alat a ON a.id_alat = m.id_alat
            LEFT JOIN pengguna p ON p.id_pengguna = m.id_pengguna
            ORDER BY m.tanggal_mulai DESC
        """;

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                MaintenanceModel m = new MaintenanceModel();

                m.setIdMaintenance(rs.getInt("id_maintenance"));
                m.setTanggalMulai(rs.getTimestamp("tanggal_mulai"));
                m.setTanggalSelesai(rs.getTimestamp("tanggal_selesai"));
                m.setBuktiImage(rs.getString("bukti_image"));
                m.setStatus(rs.getString("status"));
                m.setKeterangan(rs.getString("keterangan"));
                m.setIdAlat(rs.getInt("id_alat"));
                m.setIdInspeksi(rs.getInt("id_inspeksi"));
                m.setIdPengguna(rs.getInt("id_pengguna"));

                // TAMBAHAN
                m.setNamaAlat(rs.getString("nama_alat"));
                m.setNamaPengguna(rs.getString("nama_pengguna"));

                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
