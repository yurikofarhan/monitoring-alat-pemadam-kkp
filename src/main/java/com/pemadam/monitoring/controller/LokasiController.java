/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.dao.LokasiDAO;
import com.pemadam.monitoring.model.LokasiModel;
/**
 *
 * @author Yuriko
 */

import java.util.List;

public class LokasiController {

    private final LokasiDAO dao = new LokasiDAO();

    public List<LokasiModel> getAll() {
        return dao.getAll();
    }
    public boolean insertLokasi(LokasiModel l) {
        int id = dao.insert(l);
            
            if (id > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "INSERT",
                        "lokasi",
                        id,
                        "Menambah data Lokasi " + id
                );
                return true;
            }
        return false;
    }
    public boolean updateLokasi(LokasiModel l) {
        boolean berhasil = dao.update(l);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "UPDATE",
                    "lokasi",
                    l.getIdLokasi(),
                    "Mengubah data Lokasi " + l.getIdLokasi()
            );
        }

        return berhasil;
    }
    public LokasiModel getLokasiById(int id) {
        return dao.getById(id);
    }
    public boolean deleteLokasi(int id) {
        LokasiModel alat = getLokasiById(id);
        boolean berhasil = dao.delete(id);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "DELETE",
                    "lokasi",
                    alat.getIdLokasi(),
                    "Menghapus data Lokasi " + alat.getIdLokasi()
            );
        }
        
        return berhasil;
    }
}
