/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.dao.AlatDAO;
import com.pemadam.monitoring.dao.InspeksiDAO;
import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.dao.MaintenanceDAO;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.MaintenanceModel;
import com.pemadam.monitoring.view.maintenance.Maintenance;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author Yuriko
 */
public class MaintenanceController {

    private final Maintenance view;
    private final AlatDAO alatDAO;
    private final InspeksiDAO inspeksiDAO;
    private final MaintenanceDAO maintenanceDAO;

    public MaintenanceController(Maintenance view) {
        this.view = view;
        this.alatDAO = new AlatDAO();
        this.inspeksiDAO = new InspeksiDAO();
        this.maintenanceDAO = new MaintenanceDAO();
    }

    // Load data ke combo
    public void loadCombo() {
        List<AlatModel> list = alatDAO.getAll();
        System.out.println("list = " + list);
        view.setComboAlat(list);
    }

    public void pilihAlat(int idAlat) {
        List<InspeksiModel> list = inspeksiDAO.getByAlat(idAlat);
        AlatModel alat = alatDAO.getById(idAlat);

        if (alat != null) {
            view.showDetailAlat(alat);
        }
        view.setComboInspeksi(list);
    }

    public void pilihInspeksi(int idInspeksi) {

        List<MaintenanceModel> list
                = maintenanceDAO.getByInspeksi(idInspeksi);

        view.showTable(list);
    }

    public List<MaintenanceModel> getMaintenanceByInspeksi(int idInspeksi) {
        MaintenanceDAO dao = new MaintenanceDAO();
        return dao.getByInspeksi(idInspeksi);
    }

    public boolean tambah(MaintenanceModel m) {

        if (m.getTanggalMulai() == null || m.getTanggalSelesai() == null
                || m.getStatus() == null || m.getStatus().isEmpty()) {
            return false;
        }

        try {

            int id = maintenanceDAO.insert(m);

            if (id > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "INSERT",
                        "Maintenance",
                        id,
                        "Menamabah data Maintenance pada Id Alat : " + m.getIdAlat()
                );
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public MaintenanceModel getById(int id) {
        return maintenanceDAO.getById(id);
    }

    public boolean update(int idMaintenance, Date tgl_mulai, Date tgl_selesai, String buktiImage,
            String status, String ket) {

        if (tgl_mulai == null || tgl_selesai == null
                || status == null || status.trim().isEmpty()) {
            return false;
        }

        try {
            MaintenanceModel m = new MaintenanceModel();
            m.setIdMaintenance(idMaintenance);
            m.setTanggalMulai(new Timestamp(tgl_mulai.getTime()));
            m.setTanggalSelesai(new Timestamp(tgl_selesai.getTime()));
            m.setBuktiImage(buktiImage);
            m.setStatus(status);
            m.setKeterangan(ket);

            maintenanceDAO.update(m);
            if (idMaintenance > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "UPDATE",
                        "Maintenance",
                        idMaintenance,
                        "Mengubah data Maintenance pada Id Alat: " + m.getIdAlat()
                );
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
