/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.dao.PenggunaDAO;
import com.pemadam.monitoring.model.PenggunaModel;
/**
 *
 * @author Yuriko
 */


public class AuthController {

    private final PenggunaDAO dao = new PenggunaDAO();

    public PenggunaModel login(String input, String password) {

        PenggunaModel user = dao.getByUsername(input);

        if (user == null) return null;

        boolean match = org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getPassword());

        return match ? user : null;
    }
}
