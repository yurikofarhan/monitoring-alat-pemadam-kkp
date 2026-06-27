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
public class AlatModel {

    private int idAlat;
    private String kodeAlat;
    private String namaAlat;
    private String jenisAlat;
    private Timestamp tglPembelian;
    private String image;
//    private String deskripsi;
    private int idLokasi;

    // hasil join
    private String statusTerakhir;
    private String kondisiTerakhir;
    private String namaLokasi;

    public AlatModel() {
    }

    public AlatModel(int idAlat, String kodeAlat, String namaAlat, String jenisAlat,
            Timestamp tglPembelian, int idLokasi) {

        this.idAlat = idAlat;
        this.kodeAlat = kodeAlat;
        this.namaAlat = namaAlat;
        this.jenisAlat = jenisAlat;
        this.tglPembelian = tglPembelian;
        this.image = image;
        this.idLokasi = idLokasi;
    }

    public int getIdAlat() {
        return idAlat;
    }

    public String getKodeAlat() {
        return kodeAlat;
    }

    public String getNamaAlat() {
        return namaAlat;
    }

    public String getJenisAlat() {
        return jenisAlat;
    }

    public Timestamp getTglPembelian() {
        return tglPembelian;
    }

    public String getImage() {
        return image;
    }

    public void setIdAlat(int idAlat) {
        this.idAlat = idAlat;
    }

    public void setKodeAlat(String kodeAlat) {
        this.kodeAlat = kodeAlat;
    }

    public void setNamaAlat(String namaAlat) {
        this.namaAlat = namaAlat;
    }

    public void setTglPembelian(Timestamp tglPembelian) {
        this.tglPembelian = tglPembelian;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String getDeskripsi() { return deskripsi; }
    public int getIdLokasi() {
        return idLokasi;
    }

//    public String getStatusTerakhir() { return statusTerakhir; }
//    public String getKondisiTerakhir() { return kondisiTerakhir; }
    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setStatusTerakhir(String statusTerakhir) {
        this.statusTerakhir = statusTerakhir;
    }

    public void setKondisiTerakhir(String kondisiTerakhir) {
        this.kondisiTerakhir = kondisiTerakhir;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public void setJenisAlat(String jenisAlat) {
        this.jenisAlat = jenisAlat;
    }

    public void setIdLokasi(int idLokasi) {
        this.idLokasi = idLokasi;
    }

    @Override
    public String toString() {
        return kodeAlat + " - " + namaAlat;
    }

    public String getKondisiTerakhir() {
        return kondisiTerakhir;

    }

    public String getStatusTerakhir() {
        return statusTerakhir;
    }
}
