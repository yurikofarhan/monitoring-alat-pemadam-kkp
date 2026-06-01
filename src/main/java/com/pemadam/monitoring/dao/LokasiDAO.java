/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.LokasiModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Yuriko
 */



public class LokasiDAO {

    public List<LokasiModel> getAll() {

        List<LokasiModel> list = new ArrayList<>();
        String sql = "SELECT * FROM lokasi_alat";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new LokasiModel(
                        rs.getInt("id_lokasi"),
                        rs.getString("nama_lokasi"),
                        rs.getString("lantai"),
                        rs.getString("gedung")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public int insert(LokasiModel l) {

        String sql = "INSERT INTO lokasi_alat "
                + "(nama_lokasi, lantai, gedung) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, l.getNamaLokasi());
            ps.setString(2, l.getLantai());
            ps.setString(3, l.getGedung());
            
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
    
    public boolean update(LokasiModel l) {
        
        String sql = "UPDATE lokasi_alat SET nama_lokasi=?, lantai=?, gedung=? WHERE id_lokasi=?";
        

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, l.getNamaLokasi());
            ps.setString(2, l.getLantai());
            ps.setString(3, l.getGedung());
            ps.setInt(4, l.getIdLokasi());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    }
    
    public LokasiModel getById(int id) {
        String sql = "SELECT * FROM lokasi_alat WHERE id_lokasi = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new LokasiModel(
                        rs.getInt("id_lokasi"),
                        rs.getString("nama_lokasi"),
                        rs.getString("lantai"),
                        rs.getString("gedung")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM lokasi_alat WHERE id_lokasi = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
