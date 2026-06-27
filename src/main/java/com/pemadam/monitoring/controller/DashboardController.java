/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

import com.pemadam.monitoring.dao.DashboardDAO;
import com.pemadam.monitoring.model.DashboardModel;

/**
 *
 * @author Yuriko
 */
public class DashboardController {

    private final DashboardDAO dao = new DashboardDAO();

    public DashboardModel getDashboard() {
        return dao.getDashboard();
    }

}
