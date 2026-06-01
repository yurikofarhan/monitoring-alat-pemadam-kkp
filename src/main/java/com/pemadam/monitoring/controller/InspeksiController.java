/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.dao.AlatDAO;
import com.pemadam.monitoring.dao.InspeksiDAO;
import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.view.inspeksi.Inspeksi;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class InspeksiController {

    private final Inspeksi view;
    private final AlatDAO alatDAO;
    private final InspeksiDAO inspeksiDAO;

    public InspeksiController(Inspeksi view) {
        this.view = view;
        this.alatDAO = new AlatDAO();
        this.inspeksiDAO = new InspeksiDAO();
    }

    // Load data ke combobox
    public void loadCombo() {
        List<AlatModel> list = alatDAO.getAll();
        view.setComboAlat(list);
    }

    // Saat pilih alat
    public void pilihAlat(int idAlat) {

        // ambil detail alat
        AlatModel alat = alatDAO.getById(idAlat);
        if (alat != null) {
            view.showDetailAlat(alat);
        }

        // ambil data inspeksi
        List<InspeksiModel> list = inspeksiDAO.getByAlat(idAlat);
        view.showTable(list);
    }

    
    public boolean tambah(int idAlat, Date tanggal,
                          String kondisi, String status, 
                          String statusInspeksi, String ket) {

        if (tanggal == null ||
            kondisi == null || kondisi.isEmpty() ||
            statusInspeksi == null || statusInspeksi.isEmpty()) {
            return false;
        }

        try {
            InspeksiModel i = new InspeksiModel();
            i.setIdAlat(idAlat);
            i.setKondisi(kondisi);
            i.setStatus(status);
            i.setStatusInspeksi(statusInspeksi);
            i.setKeterangan(ket);
            i.setTanggalInspeksi(new java.sql.Timestamp(tanggal.getTime()));

            
            int idInspeksi = inspeksiDAO.insert(i);
            
            if (idInspeksi > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "INSERT",
                        "inspeksi",
                        idInspeksi,
                        "Menambah data Inspeksi pada Id Alat " + i.getIdAlat()
                );
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public InspeksiModel getById(int id) {
        return inspeksiDAO.getById(id);
    }
    
    public boolean update(int idInspeksi, int idAlat, Date tanggal,
                          String kondisi, String status,
                          String statusInspeksi, String ket) {

        // validasi
        if (tanggal == null ||
            kondisi == null || kondisi.trim().isEmpty() ||
            statusInspeksi == null || statusInspeksi.trim().isEmpty()) {
            return false;
        }

        try {
            InspeksiModel i = new InspeksiModel();
            i.setIdInspeksi(idInspeksi);
            i.setIdAlat(idAlat);
            i.setKondisi(kondisi);
            i.setStatus(status);
            i.setStatusInspeksi(statusInspeksi);
            i.setKeterangan(ket);
            i.setTanggalInspeksi(new java.sql.Timestamp(tanggal.getTime()));

            inspeksiDAO.update(i);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
}