/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.dao;

import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.LogAktivitasModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class LogAktivitasDAO {

    public static void simpan(
            int idPengguna,
            String aksi,
            String tabelTerkait,
            Integer recordId,
            String deskripsi) {

        String sql = """
                     INSERT INTO log_aktivitas
                     (
                        id_pengguna,
                        aksi,
                        tabel_terkait,
                        record_id,
                        deskripsi
                     )
                     VALUES (?, ?, ?, ?, ?)
                     """;

        try (
                Connection conn = Koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, idPengguna);
            ps.setString(2, aksi);
            ps.setString(3, tabelTerkait);

            if (recordId == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, recordId);
            }

            ps.setString(5, deskripsi);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<LogAktivitasModel> getAll() {

        List<LogAktivitasModel> list = new ArrayList<>();

        String sql = """
                     SELECT *
                     FROM log_aktivitas
                     ORDER BY waktu DESC
                     """;

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                LogAktivitasModel log = new LogAktivitasModel();

                log.setIdLog(rs.getInt("id_log"));
                log.setIdPengguna(rs.getInt("id_pengguna"));
                log.setWaktu(rs.getTimestamp("waktu"));
                log.setAksi(rs.getString("aksi"));
                log.setTabelTerkait(rs.getString("tabel_terkait"));
                log.setRecordId(rs.getInt("record_id"));
                log.setDeskripsi(rs.getString("deskripsi"));

                list.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
