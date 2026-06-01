/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.dao.LogAktivitasDAO;
import com.pemadam.monitoring.model.LogAktivitasModel;
import java.util.List;

/**
 *
 * @author Yuriko
 */
public class LogAktivitasController {
    private LogAktivitasDAO dao = new LogAktivitasDAO();

    public List<LogAktivitasModel> getLog() {
        return dao.getAll();
    }
}
