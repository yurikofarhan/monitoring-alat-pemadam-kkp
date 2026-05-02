/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.model;


/**
 *
 * @author Yuriko
 */
public class PenggunaModel {
    private int idPengguna;
    private String namaPengguna;
    private String username;
    private String password;
    private String noTelp;
    private String role;

    public PenggunaModel(int idPengguna, String namaPengguna,
                    String username, String password, String noTelp, String role) {
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.username = username;
        this.password = password;
        this.noTelp = noTelp;
        this.role = role;
    }

    public int getIdPengguna() { return idPengguna; }
    public String getNamaPengguna() { return namaPengguna; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNoTelp() { return noTelp; }
    public String getRole() { return role; }
}
