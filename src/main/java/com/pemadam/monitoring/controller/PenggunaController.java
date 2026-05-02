/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;


/**
 *
 * @author Yuriko
 */
import com.pemadam.monitoring.dao.PenggunaDAO;
import com.pemadam.monitoring.model.PenggunaModel;
import java.util.List;

public class PenggunaController {

    private final PenggunaDAO dao = new PenggunaDAO();

    public List<PenggunaModel> getAllPengguna() {
        return dao.getAll();
    }
    public boolean insertPengguna(PenggunaModel p) {
        return dao.insert(p);
    }
    public boolean updatePengguna(PenggunaModel p) {
        return dao.update(p);
    }
    public PenggunaModel getPenggunaById(int id) {
        return dao.getById(id);
    }
    public boolean deletePengguna(int id) {
        return dao.delete(id);
    }

}
