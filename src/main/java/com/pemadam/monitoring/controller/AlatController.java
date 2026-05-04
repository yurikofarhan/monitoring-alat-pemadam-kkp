/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.dao.AlatDAO;
import com.pemadam.monitoring.model.AlatModel;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class AlatController {
    private AlatDAO dao = new AlatDAO();

    public List<AlatModel> getAllAlat() {
        return dao.getAll();
    }

    public boolean insertAlat(AlatModel alat) {

        try {
            // VALIDASI
            if (alat.getKodeAlat() == null || alat.getKodeAlat().isEmpty()) {
                throw new IllegalArgumentException("Kode alat wajib diisi");
            }

            AlatDAO alatDAO = new AlatDAO();
//            InspeksiDAO inspeksiDAO = new InspeksiDAO();

            int idAlat = alatDAO.insert(alat);
//            System.out.println("ID ALAT: " + idAlat);
            
            if (idAlat > 0) {
//                inspeksiDAO.insertAwal(idAlat);
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public boolean updateAlat(AlatModel alat) {
        return dao.update(alat);
    }

    public AlatModel getAlatById(int id) {
        return dao.getById(id);
    }

    public boolean deleteAlat(int id) {
        return dao.delete(id);
    }
    
}
