/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.dao.AlatDAO;
import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.model.AlatModel;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class AlatController {
    private final AlatDAO dao = new AlatDAO();

    public List<AlatModel> getAllAlat() {
        return dao.getAll();
    }

    public boolean insertAlat(AlatModel alat) {

        try {
            if (alat.getKodeAlat() == null || alat.getKodeAlat().isEmpty()) {
                throw new IllegalArgumentException("Kode alat wajib diisi");
            }


            int id = dao.insert(alat);
            
            if (id > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "INSERT",
                        "alat",
                        id,
                        "Menambah Data Alat " + alat.getKodeAlat()
                );
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public boolean updateAlat(AlatModel alat) {
        boolean berhasil = dao.update(alat);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "UPDATE",
                    "alat",
                    alat.getIdAlat(),
                    "Mengubah data Alat " + alat.getKodeAlat()
            );
        }

        return berhasil;
    }

    public AlatModel getAlatById(int id) {
        return dao.getById(id);
    }

    public boolean deleteAlat(int id) {
        AlatModel alat = getAlatById(id);
        boolean berhasil = dao.delete(id);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "DELETE",
                    "alat",
                    alat.getIdAlat(),
                    "Menghapus data Alat " + alat.getKodeAlat()
            );
        }
        
        return berhasil;

    }
    
}
