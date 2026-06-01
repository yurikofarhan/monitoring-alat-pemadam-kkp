/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.PenggunaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Yuriko
 */
public class PenggunaDAO {

    public List<PenggunaModel> getAll() {
        List<PenggunaModel> list = new ArrayList<>();

        String sql = "SELECT * FROM pengguna";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                PenggunaModel p = new PenggunaModel(
                        rs.getInt("id_pengguna"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("no_telp"),
                        rs.getString("role")   
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return list;
    }
    
    public int insert(PenggunaModel p) {

        String sql = "INSERT INTO pengguna "
                + "(nama_pengguna, username, password, no_telp, role) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNamaPengguna());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getNoTelp());
            ps.setString(5, p.getRole());
            
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
    
    public boolean update(PenggunaModel p) {
        boolean isUpdatePassword = p.getPassword() != null && !p.getPassword().isEmpty();

        String sql;

        if (isUpdatePassword) {
            // update dengan password
            sql = "UPDATE pengguna SET nama_pengguna=?, username=?, password=?, no_telp=?, role=? WHERE id_pengguna=?";
        } else {
            // TANPA password
            sql = "UPDATE pengguna SET nama_pengguna=?, username=?, no_telp=?, role=? WHERE id_pengguna=?";
        }

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaPengguna());
            ps.setString(2, p.getUsername());

            if (isUpdatePassword) {
                ps.setString(3, p.getPassword());
                ps.setString(4, p.getNoTelp());
                ps.setString(5, p.getRole());
                ps.setInt(6, p.getIdPengguna());
            } else {
                ps.setString(3, p.getNoTelp());
                ps.setString(4, p.getRole());
                ps.setInt(5, p.getIdPengguna());
            }

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    }
    
    public PenggunaModel getById(int id) {
        String sql = "SELECT * FROM pengguna WHERE id_pengguna = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PenggunaModel(
                        rs.getInt("id_pengguna"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("no_telp"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM pengguna WHERE id_pengguna = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public PenggunaModel getByUsername(String input) {

        String sql = "SELECT * FROM pengguna WHERE username=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, input);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PenggunaModel(
                        rs.getInt("id_pengguna"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("no_telp"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
