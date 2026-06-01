/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;


/**
 *
 * @author Yuriko
 */
import com.pemadam.monitoring.config.Session;
import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.dao.PenggunaDAO;
import com.pemadam.monitoring.model.PenggunaModel;
import java.util.List;

public class PenggunaController {

    private final PenggunaDAO dao = new PenggunaDAO();

    public List<PenggunaModel> getAllPengguna() {
        return dao.getAll();
    }
    public boolean insertPengguna(PenggunaModel p) {
        int id = dao.insert(p);
            
            if (id > 0) {
                LogAktivitasDAO.simpan(
                        Session.getUser().getIdPengguna(),
                        "INSERT",
                        "pengguna",
                        id,
                        "Menambah data Pengguna " + id
                );
                return true;
            }
        return false;
    }
    public boolean updatePengguna(PenggunaModel p) {
        boolean berhasil = dao.update(p);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "UPDATE",
                    "pengguna",
                    p.getIdPengguna(),
                    "Mengubah data Pengguna " + p.getIdPengguna()
            );
        }

        return berhasil;
    }
    public PenggunaModel getPenggunaById(int id) {
        return dao.getById(id);
    }
    public boolean deletePengguna(int id) {
        PenggunaModel alat = getPenggunaById(id);
        boolean berhasil = dao.delete(id);
        if (berhasil) {

            LogAktivitasDAO.simpan(
                    Session.getUser().getIdPengguna(),
                    "DELETE",
                    "pengguna",
                    alat.getIdPengguna(),
                    "Menghapus data Pengguna " + alat.getIdPengguna()
            );
        }
        
        return berhasil;
    }

}
