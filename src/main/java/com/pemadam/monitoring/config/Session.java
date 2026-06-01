/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.config;

import com.pemadam.monitoring.model.PenggunaModel;

/**
 *
 * @author Yuriko
 */


public class Session {
    private static PenggunaModel currentUser;

    public static void setUser(PenggunaModel user) {
        currentUser = user;
    }
    public static PenggunaModel getUser() {
        return currentUser;
    }
    public static void clear() {
        currentUser = null;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null
            && "admin".equals(currentUser.getRole());
    }

    public static boolean isPetugas() {
        return currentUser != null
            && "petugas".equals(currentUser.getRole());
    }
}