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
public class InspeksiModel {

    private int idInspeksi;
    private Timestamp tanggalInspeksi;
    private String kondisi;
    private String status;            // status alat
    private String statusInspeksi;
    private String keterangan;
    private int idPengguna;
    private int idAlat;
    private String kodeAlat;
    private String namaAlat;
    private String jenisAlat;
    private Timestamp tglPembelian;
    private String namaLokasi;

    private String namaPengguna;

    public String getNamaAlat() {
        return namaAlat;
    }

    public void setNamaAlat(String namaAlat) {
        this.namaAlat = namaAlat;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public int getIdInspeksi() {
        return idInspeksi;
    }

    public void setIdInspeksi(int idInspeksi) {
        this.idInspeksi = idInspeksi;
    }

    public Timestamp getTanggalInspeksi() {
        return tanggalInspeksi;
    }

    public void setTanggalInspeksi(Timestamp tanggalInspeksi) {
        this.tanggalInspeksi = tanggalInspeksi;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusInspeksi() {
        return statusInspeksi;
    }

    public void setStatusInspeksi(String statusInspeksi) {
        this.statusInspeksi = statusInspeksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public int getIdAlat() {
        return idAlat;
    }

    public void setIdAlat(int idAlat) {
        this.idAlat = idAlat;
    }

    @Override
    public String toString() {
        String tgl = (tanggalInspeksi != null)
                ? new java.text.SimpleDateFormat("dd-MM-yyyy").format(tanggalInspeksi)
                : "-";

        return tgl + " | " + kondisi;
    }

    public void setKodeAlat(String kodeAlat) {
        this.kodeAlat = kodeAlat;
    }

    public String getKodeAlat() {
        return kodeAlat;
    }

    public String getJenisAlat() {
        return jenisAlat;
    }

    public String getKondisiTerakhir() {
        return kondisi;
    }

    public String getStatusTerakhir() {
        return status;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }
}
