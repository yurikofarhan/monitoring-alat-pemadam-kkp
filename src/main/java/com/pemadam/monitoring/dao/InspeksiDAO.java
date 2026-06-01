/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.model.InspeksiModel;
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
public class InspeksiDAO {
    public boolean insertAwal(int idAlat) {

        String sql = """
            INSERT INTO inspeksi_alat
            (id_alat, id_pengguna, tanggal_inspeksi, kondisi, status, status_inspeksi, keterangan, created_at, updated_at)
            VALUES (?, ?, NOW(), ?, ?, ?, ?, NOW(), NOW())
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            

            int idPengguna = Session.getUser().getIdPengguna();

            // SET PARAMETER
            ps.setInt(1, idAlat);
            ps.setInt(2, idPengguna);

            ps.setString(3, "baik");        // kondisi
            ps.setString(4, "layak");       // status
            ps.setString(5, "selesai");     // status_inspeksi
            ps.setString(6, "inspeksi awal");

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<String> getEnumKondisiAlat() {
        return EnumUtil.getEnumValues("inspeksi_alat", "kondisi");
    }
    
    public List<String> getEnumStatusAlat() {
        return EnumUtil.getEnumValues("inspeksi_alat", "status");
    }

    public List<String> getEnumStatusInspeksi() {
        return EnumUtil.getEnumValues("inspeksi_alat", "status_inspeksi");
    }
    
    
    
    public List<InspeksiModel> getByAlat(int idAlat) {

        List<InspeksiModel> list = new ArrayList<>();

        String sql = "SELECT * FROM inspeksi_alat WHERE id_alat = ? ORDER BY tanggal_inspeksi DESC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAlat);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InspeksiModel i = new InspeksiModel();

                i.setIdInspeksi(rs.getInt("id_inspeksi"));
                i.setTanggalInspeksi(rs.getTimestamp("tanggal_inspeksi")); 
                i.setKondisi(rs.getString("kondisi"));
                i.setStatus(rs.getString("status"));
                i.setStatusInspeksi(rs.getString("status_inspeksi")); 
                i.setKeterangan(rs.getString("keterangan"));
                i.setIdPengguna(rs.getInt("id_pengguna"));
                i.setIdAlat(rs.getInt("id_alat"));

                list.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insert(InspeksiModel i) {

        String sql = "INSERT INTO inspeksi_alat "
                + "(tanggal_inspeksi, kondisi, status, status_inspeksi, keterangan, id_pengguna, id_alat) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            if (Session.getUser() == null) {
                throw new RuntimeException("User belum login!");
            }

            int idPengguna = Session.getUser().getIdPengguna();
            
            ps.setTimestamp(1, i.getTanggalInspeksi()); 
            ps.setString(2, i.getKondisi());
            ps.setString(3, i.getStatus());
            ps.setString(4, i.getStatusInspeksi()); 
            ps.setString(5, i.getKeterangan());
            ps.setInt(6, idPengguna); 
            ps.setInt(7, i.getIdAlat());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    
    public InspeksiModel getById(int id) {

        InspeksiModel i = null;

        String sql = "SELECT * FROM inspeksi_alat WHERE id_inspeksi = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                i = new InspeksiModel();

                i.setIdInspeksi(rs.getInt("id_inspeksi"));
                i.setTanggalInspeksi(rs.getTimestamp("tanggal_inspeksi")); 
                i.setKondisi(rs.getString("kondisi"));
                i.setStatus(rs.getString("status"));
                i.setStatusInspeksi(rs.getString("status_inspeksi")); 
                i.setKeterangan(rs.getString("keterangan"));
                i.setIdPengguna(rs.getInt("id_pengguna")); 
                i.setIdAlat(rs.getInt("id_alat"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }
    
    public boolean update(InspeksiModel i) {

        String sql = "UPDATE inspeksi_alat SET "
                + "tanggal_inspeksi=?, kondisi=?, status=?, status_inspeksi=?, keterangan=? "
                + "WHERE id_inspeksi=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, i.getTanggalInspeksi());
            ps.setString(2, i.getKondisi());
            ps.setString(3, i.getStatus());
            ps.setString(4, i.getStatusInspeksi());
            ps.setString(5, i.getKeterangan());
            ps.setInt(6, i.getIdInspeksi());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    public List<InspeksiModel> getAll() {

        List<InspeksiModel> list = new ArrayList<>();

        String sql = """
            SELECT 
                i.*,
                a.nama_alat,
                p.nama_pengguna AS nama_pengguna
            FROM inspeksi_alat i
            LEFT JOIN alat a ON a.id_alat = i.id_alat
            LEFT JOIN pengguna p ON p.id_pengguna = i.id_pengguna
            ORDER BY i.tanggal_inspeksi DESC
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                InspeksiModel i = new InspeksiModel();

                i.setIdInspeksi(rs.getInt("id_inspeksi"));
                i.setTanggalInspeksi(rs.getTimestamp("tanggal_inspeksi"));
                i.setKondisi(rs.getString("kondisi"));
                i.setStatus(rs.getString("status"));
                i.setStatusInspeksi(rs.getString("status_inspeksi"));
                i.setKeterangan(rs.getString("keterangan"));
                i.setIdPengguna(rs.getInt("id_pengguna"));
                i.setIdAlat(rs.getInt("id_alat"));

                i.setNamaAlat(rs.getString("nama_alat"));
                i.setNamaPengguna(rs.getString("nama_pengguna"));

                list.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
}
