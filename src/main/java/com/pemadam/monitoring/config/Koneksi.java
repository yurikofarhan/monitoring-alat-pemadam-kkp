/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.config;

/**
 *
 * @author Yuriko
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/monitoring_alat_damkar";
            String user = "root";
            String pass = "";

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
