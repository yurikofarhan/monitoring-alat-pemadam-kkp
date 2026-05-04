/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

/**
 *
 * @author Yuriko
 */
import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.util.EnumUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class AlatDAO {

    public List<AlatModel> getAll() {
        List<AlatModel> list = new ArrayList<>();

        String sql = """
                SELECT
                    a.*,
                    l.nama_lokasi
                FROM alat a
                LEFT JOIN lokasi_alat l ON l.id_lokasi = a.id_lokasi
            """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                AlatModel a = new AlatModel(
                        rs.getInt("id_alat"),
                        rs.getString("kode_alat"),
                        rs.getString("nama_alat"),
                        rs.getString("jenis_alat"),
                        rs.getTimestamp("tanggal_pembelian"),
                        rs.getInt("id_lokasi")
                );

                
                a.setNamaLokasi(rs.getString("nama_lokasi"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insert(AlatModel a) {

        String sql = """
            INSERT INTO alat 
            (kode_alat, nama_alat, jenis_alat, tanggal_pembelian, id_lokasi)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getKodeAlat());
            ps.setString(2, a.getNamaAlat());
            ps.setString(3, a.getJenisAlat()); 
            ps.setTimestamp(4, a.getTglPembelian());
            ps.setInt(5, a.getIdLokasi());

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

    public boolean update(AlatModel a) {
        String sql = """
            UPDATE alat SET 
                kode_alat=?, 
                nama_alat=?,     
                jenis_alat=?,
                tanggal_pembelian=?, 
                id_lokasi=? 
            WHERE id_alat=?
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getKodeAlat());
            ps.setString(2, a.getNamaAlat());
            ps.setString(3, a.getJenisAlat());
            ps.setTimestamp(4, a.getTglPembelian());
            ps.setInt(5, a.getIdLokasi());
            ps.setInt(6, a.getIdAlat());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AlatModel getById(int id) {
        String sql = "SELECT * FROM alat WHERE id_alat=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new AlatModel(
                        rs.getInt("id_alat"),
                        rs.getString("kode_alat"),
                        rs.getString("nama_alat"),
                        rs.getString("jenis_alat"),
                        rs.getTimestamp("tanggal_pembelian"),
                        rs.getInt("id_lokasi")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(int id) {

        String deleteAlat = "DELETE FROM alat WHERE id_alat=?";

        try (Connection conn = Koneksi.getConnection()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement ps = conn.prepareStatement(deleteAlat)
            ) {

                ps.setInt(1, id);
                ps.executeUpdate();

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getEnumJenisAlat() {
        return EnumUtil.getEnumValues("alat", "jenis_alat");
    }
}
