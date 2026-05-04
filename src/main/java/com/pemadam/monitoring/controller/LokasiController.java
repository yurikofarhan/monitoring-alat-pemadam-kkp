/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.dao.LokasiDAO;
import com.pemadam.monitoring.model.LokasiModel;
/**
 *
 * @author Yuriko
 */

import java.util.List;

public class LokasiController {

    private LokasiDAO dao = new LokasiDAO();

    public List<LokasiModel> getAll() {
        return dao.getAll();
    }
    public boolean insertLokasi(LokasiModel l) {
        return dao.insert(l);
    }
    public boolean updateLokasi(LokasiModel l) {
        return dao.update(l);
    }
    public LokasiModel getLokasiById(int id) {
        return dao.getById(id);
    }
    public boolean deleteLokasi(int id) {
        return dao.delete(id);
    }
}
