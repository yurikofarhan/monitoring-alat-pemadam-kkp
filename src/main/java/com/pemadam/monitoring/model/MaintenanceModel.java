/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.model;

import java.sql.Timestamp;

/**
 *
 * @author Yuriko
 */
public class MaintenanceModel {
    private int idMaintenance;
    private Timestamp tanggalMulai; 
    private Timestamp tanggalSelesai; 
    private String buktiImage;
    private String status;            
    private String keterangan;
    private int idAlat;
    private int idInspeksi;
    private int idPengguna;   
    private String namaPengguna;
    private String namaAlat;

    public String getNamaAlat() {
        return namaAlat;
    }

    public void setNamaAlat(String namaAlat) {
        this.namaAlat = namaAlat;
    }

    public int getIdMaintenance() {
        return idMaintenance;
    }

    public void setIdMaintenance(int idMaintenance) {
        this.idMaintenance = idMaintenance;
    }

    public Timestamp getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(Timestamp tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Timestamp getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Timestamp tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getBuktiImage() {
        return buktiImage;
    }

    public void setBuktiImage(String buktiImage) {
        this.buktiImage = buktiImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
//        System.out.println("status = " + status);
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getIdAlat() {
        return idAlat;
    }

    public void setIdAlat(int idAlat) {
        this.idAlat = idAlat;
    }

    public int getIdInspeksi() {
        return idInspeksi;
    }

    public void setIdInspeksi(int idInspeksi) {
        this.idInspeksi = idInspeksi;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }
    
    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }
    

}
