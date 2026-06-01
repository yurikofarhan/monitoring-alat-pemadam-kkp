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
public class LogAktivitasModel {

    private int idLog;
    private int idPengguna;
    private Timestamp waktu;
    private String aksi;
    private String tabelTerkait;
    private Integer recordId;
    private String deskripsi;

     public LogAktivitasModel() {
    }
    
    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public void setTabelTerkait(String tabelTerkait) {
        this.tabelTerkait = tabelTerkait;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getIdLog() {
        return idLog;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public String getAksi() {
        return aksi;
    }

    public String getTabelTerkait() {
        return tabelTerkait;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

   

    
}
