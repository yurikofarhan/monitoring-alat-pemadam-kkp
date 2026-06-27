/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.model;

/**
 *
 * @author Yuriko
 */
public class LokasiModel {

    private int idLokasi;
    private String namaLokasi;
    private String lantai;
    private String gedung;

    public LokasiModel(int idLokasi, String namaLokasi, String lantai, String gedung) {
        this.idLokasi = idLokasi;
        this.namaLokasi = namaLokasi;
        this.lantai = lantai;
        this.gedung = gedung;
    }

    public LokasiModel(int idLokasi) {
        this.idLokasi = idLokasi;
    }

    public int getIdLokasi() {
        return idLokasi;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public String getLantai() {
        return lantai;
    }

    public String getGedung() {
        return gedung;
    }

    @Override
    public String toString() {
        return namaLokasi;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LokasiModel that = (LokasiModel) obj;
        return this.idLokasi == that.idLokasi;
    }
}
