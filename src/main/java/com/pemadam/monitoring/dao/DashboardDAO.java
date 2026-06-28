/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.DashboardModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Yuriko
 */
public class DashboardDAO {

    public DashboardModel getDashboard() {

        DashboardModel model = new DashboardModel();

        String sql
                = "SELECT "
                + "(SELECT COUNT(*) FROM alat) AS total_alat, "
                + "(SELECT COUNT(*) "
                + " FROM inspeksi_alat i "
                + " JOIN ("
                + "     SELECT id_alat, MAX(id_inspeksi) id_inspeksi "
                + "     FROM inspeksi_alat "
                + "     GROUP BY id_alat"
                + " ) x ON i.id_inspeksi = x.id_inspeksi "
                + " WHERE i.status='layak') AS siap_pakai, "
                + "(SELECT COUNT(*) "
                + " FROM inspeksi_alat i "
                + " JOIN ("
                + "     SELECT id_alat, MAX(id_inspeksi) id_inspeksi "
                + "     FROM inspeksi_alat "
                + "     GROUP BY id_alat"
                + " ) x ON i.id_inspeksi = x.id_inspeksi "
                + " WHERE i.status='perlu_perawatan') AS perawatan, "
                + "(SELECT COUNT(*) "
                + " FROM inspeksi_alat i "
                + " JOIN ("
                + "     SELECT id_alat, MAX(id_inspeksi) id_inspeksi "
                + "     FROM inspeksi_alat "
                + "     GROUP BY id_alat"
                + " ) x ON i.id_inspeksi = x.id_inspeksi "
                + " WHERE i.status='tidak_layak') AS rusak";;

        try (Connection conn = Koneksi.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                model.setTotalAlat(rs.getInt("total_alat"));
                model.setSiapPakai(rs.getInt("siap_pakai"));
                model.setPerawatan(rs.getInt("perawatan"));
                model.setRusak(rs.getInt("rusak"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

}
