/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pemadam.monitoring.view.laporan;

import com.pemadam.monitoring.controller.LaporanController;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.LogAktivitasModel;
import com.pemadam.monitoring.model.MaintenanceModel;
import com.pemadam.monitoring.model.PenggunaModel;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yuriko
 */
public class Laporan extends javax.swing.JPanel {

    /**
     * Creates new form Laporan
     */
    LaporanController controller = new LaporanController();

    public Laporan() {
        initComponents();
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "Kode Alat", "Nama Alat", "Jenis Alat", "Kondisi", "Status", "Lokasi"
                }
        ));

        jTable2.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "Kode Alat", "Nama Alat", "Tanggal Inspeksi", "Kondisi", "Status Alat", "Status Inspeksi", "Petugas"
                }
        ));

        jTable3.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "Kode Alat", "Nama Alat", "Tanggal Mulai", "Tanggal Selesai", "Status", "Keterangan", "Petugas"
                }
        ));

        jTable4.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "ID Pengguna", "Nama Lengkap", "Username", "No. Telepon", "Role"
                }
        ));
        jTable5.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No", "Waktu", "ID User", "Nama Pengguna", "Role", "Aksi", "Tabel Terkait", "Record ID", "Deskripsi Aktivitas"
                }
        ));

        loadLaporanAlat();
        loadLaporanInspeksi();
        loadLaporanMaintenance();
        loadLaporanPengguna();
        loadLaporanAktivitas();

    }

    public void loadLaporanAlat() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        List<AlatModel> list = controller.getLaporanAlat();

        int no = 1;
        for (AlatModel a : list) {
            Object[] row = {
                no++,
                a.getKodeAlat(),
                a.getNamaAlat(),
                a.getJenisAlat(),
                a.getKondisiTerakhir(),
                a.getStatusTerakhir(),
                a.getNamaLokasi()
            };
            model.addRow(row);
        }
        jTable1.setModel(model);
    }

    public void loadLaporanInspeksi() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        List<InspeksiModel> list = controller.getLaporanInspeksi();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        int no = 1;
        for (InspeksiModel a : list) {
            String tglFormat = a.getTanggalInspeksi() != null ? sdf.format(a.getTanggalInspeksi()) : "-";
            Object[] row = {
                no++,
                a.getKodeAlat(),
                a.getNamaAlat(),
                tglFormat,
                a.getKondisi(),
                a.getStatus(),
                a.getStatusInspeksi(),
                a.getNamaPengguna()
            };
            model.addRow(row);
        }
        jTable2.setModel(model);
    }

    public void loadLaporanMaintenance() {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);

        List<MaintenanceModel> list = controller.getLaporanMaintenance();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        int no = 1;
        for (MaintenanceModel m : list) {
            String tglMulaiStr = m.getTanggalMulai() != null ? sdf.format(m.getTanggalMulai()) : "-";
            String tglSelesaiStr = m.getTanggalSelesai() != null ? sdf.format(m.getTanggalSelesai()) : "-";

            Object[] row = {
                no++,
                m.getKodeAlat(),
                m.getNamaAlat(),
                tglMulaiStr,
                tglSelesaiStr,
                m.getStatus(),
                m.getKeterangan(),
                m.getNamaPengguna()
            };
            model.addRow(row);
        }
        jTable3.setModel(model);
    }

    public void loadLaporanPengguna() {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        List<PenggunaModel> list = controller.getLaporanPengguna();
        int no = 1;
        for (PenggunaModel p : list) {
            Object[] row = {
                no++,
                p.getIdPengguna(),
                p.getNamaPengguna(),
                p.getUsername(),
                p.getNoTelp(),
                p.getRole()
            };
            model.addRow(row);
        }
        jTable4.setModel(model);
    }

    public void loadLaporanAktivitas() {
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.setRowCount(0);

        List<LogAktivitasModel> list = controller.getLaporanAktivitas();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        int no = 1;
        for (LogAktivitasModel l : list) {
            String waktuStr = l.getWaktu() != null ? sdf.format(l.getWaktu()) : "-";
            String idUserStr = l.getIdPengguna() == 0 ? "-" : String.valueOf(l.getIdPengguna());
            String recordIdStr = l.getRecordId() == 0 ? "-" : String.valueOf(l.getRecordId());

            Object[] row = {
                no++,
                waktuStr,
                idUserStr,
                l.getNamaPengguna(),
                l.getRole(),
                l.getAksi(),
                l.getTabelTerkait(),
                recordIdStr,
                l.getDeskripsi()
            };
            model.addRow(row);
        }
        jTable5.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        btnPDF1 = new javax.swing.JButton();
        btnExcel1 = new javax.swing.JButton();
        btnExcel21 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnDocx = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnPDF2 = new javax.swing.JButton();
        btnExcel2 = new javax.swing.JButton();
        btnExcel22 = new javax.swing.JButton();
        btnDocx2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnPDF3 = new javax.swing.JButton();
        btnExcel3 = new javax.swing.JButton();
        btnExcel23 = new javax.swing.JButton();
        btnDocx3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnPDF4 = new javax.swing.JButton();
        btnExcel4 = new javax.swing.JButton();
        btnExcel24 = new javax.swing.JButton();
        btnDocx4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnPDF5 = new javax.swing.JButton();
        btnExcel5 = new javax.swing.JButton();
        btnExcel25 = new javax.swing.JButton();
        btnDocx5 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(232, 31, 71));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Taskk.png"))); // NOI18N
        jLabel1.setText("Laporan ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(629, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama Alat", "Jenis Alat", "Lokasi"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton7.setText("  Print");
        jButton7.addActionListener(this::jButton7ActionPerformed);

        btnPDF1.setBackground(new java.awt.Color(255, 0, 51));
        btnPDF1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPDF1.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF1.setText("PDF");
        btnPDF1.addActionListener(this::btnPDF1ActionPerformed);

        btnExcel1.setBackground(new java.awt.Color(51, 204, 0));
        btnExcel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel1.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel1.setText("Excel .xls");
        btnExcel1.addActionListener(this::btnExcel1ActionPerformed);

        btnExcel21.setBackground(new java.awt.Color(0, 204, 0));
        btnExcel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel21.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel21.setText("Excel .xlsx");
        btnExcel21.addActionListener(this::btnExcel21ActionPerformed);

        jLabel2.setText("Export File :");

        btnDocx.setBackground(new java.awt.Color(0, 51, 255));
        btnDocx.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDocx.setForeground(new java.awt.Color(255, 255, 255));
        btnDocx.setText("DOCX");
        btnDocx.addActionListener(this::btnDocxActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPDF1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocx)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnPDF1)
                                .addComponent(btnExcel1)
                                .addComponent(btnExcel21)
                                .addComponent(btnDocx))
                            .addComponent(jLabel2)))
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Data Alat", jPanel8);

        jTable2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Alat", "Kondisi", "Keterangan", "Diperbarui"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("  Print");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jLabel3.setText("Export File :");

        btnPDF2.setBackground(new java.awt.Color(255, 0, 51));
        btnPDF2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPDF2.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF2.setText("PDF");
        btnPDF2.addActionListener(this::btnPDF2ActionPerformed);

        btnExcel2.setBackground(new java.awt.Color(51, 204, 0));
        btnExcel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel2.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel2.setText("Excel .xls");
        btnExcel2.addActionListener(this::btnExcel2ActionPerformed);

        btnExcel22.setBackground(new java.awt.Color(0, 204, 0));
        btnExcel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel22.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel22.setText("Excel .xlsx");
        btnExcel22.addActionListener(this::btnExcel22ActionPerformed);

        btnDocx2.setBackground(new java.awt.Color(0, 51, 255));
        btnDocx2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDocx2.setForeground(new java.awt.Color(255, 255, 255));
        btnDocx2.setText("DOCX");
        btnDocx2.addActionListener(this::btnDocx2ActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPDF2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocx2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPDF2)
                        .addComponent(btnExcel2)
                        .addComponent(btnExcel22)
                        .addComponent(btnDocx2))
                    .addComponent(jButton5))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Inspeksi Alat", jPanel9);

        jTable3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Alat", "Jenis Maintenance", "Tgl Maintenance", "Teknisi", "Status"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("  Print");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jLabel4.setText("Export File :");

        btnPDF3.setBackground(new java.awt.Color(255, 0, 51));
        btnPDF3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPDF3.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF3.setText("PDF");
        btnPDF3.addActionListener(this::btnPDF3ActionPerformed);

        btnExcel3.setBackground(new java.awt.Color(51, 204, 0));
        btnExcel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel3.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel3.setText("Excel .xls");
        btnExcel3.addActionListener(this::btnExcel3ActionPerformed);

        btnExcel23.setBackground(new java.awt.Color(0, 204, 0));
        btnExcel23.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel23.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel23.setText("Excel .xlsx");
        btnExcel23.addActionListener(this::btnExcel23ActionPerformed);

        btnDocx3.setBackground(new java.awt.Color(0, 51, 255));
        btnDocx3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDocx3.setForeground(new java.awt.Color(255, 255, 255));
        btnDocx3.setText("DOCX");
        btnDocx3.addActionListener(this::btnDocx3ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPDF3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocx3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPDF3)
                        .addComponent(btnExcel3)
                        .addComponent(btnExcel23)
                        .addComponent(btnDocx3))
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Maintenance Alat", jPanel10);

        jTable4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Alat", "Jenis Inspeksi", "Tgl Inspeksi", "Inspektor", "Status"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("  Print");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jLabel5.setText("Export File :");

        btnPDF4.setBackground(new java.awt.Color(255, 0, 51));
        btnPDF4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPDF4.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF4.setText("PDF");
        btnPDF4.addActionListener(this::btnPDF4ActionPerformed);

        btnExcel4.setBackground(new java.awt.Color(51, 204, 0));
        btnExcel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel4.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel4.setText("Excel .xls");
        btnExcel4.addActionListener(this::btnExcel4ActionPerformed);

        btnExcel24.setBackground(new java.awt.Color(0, 204, 0));
        btnExcel24.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel24.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel24.setText("Excel .xlsx");
        btnExcel24.addActionListener(this::btnExcel24ActionPerformed);

        btnDocx4.setBackground(new java.awt.Color(0, 51, 255));
        btnDocx4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDocx4.setForeground(new java.awt.Color(255, 255, 255));
        btnDocx4.setText("DOCX");
        btnDocx4.addActionListener(this::btnDocx4ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPDF4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocx4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPDF4)
                        .addComponent(btnExcel4)
                        .addComponent(btnExcel24)
                        .addComponent(btnDocx4))
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Data Pengguna", jPanel11);

        jTable5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Alat", "Jenis Inspeksi", "Tgl Inspeksi", "Inspektor", "Status"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton9.setText("  Print");
        jButton9.addActionListener(this::jButton9ActionPerformed);

        jLabel6.setText("Export File :");

        btnPDF5.setBackground(new java.awt.Color(255, 0, 51));
        btnPDF5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPDF5.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF5.setText("PDF");
        btnPDF5.addActionListener(this::btnPDF5ActionPerformed);

        btnExcel5.setBackground(new java.awt.Color(51, 204, 0));
        btnExcel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel5.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel5.setText("Excel .xls");
        btnExcel5.addActionListener(this::btnExcel5ActionPerformed);

        btnExcel25.setBackground(new java.awt.Color(0, 204, 0));
        btnExcel25.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnExcel25.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel25.setText("Excel .xlsx");
        btnExcel25.addActionListener(this::btnExcel25ActionPerformed);

        btnDocx5.setBackground(new java.awt.Color(0, 51, 255));
        btnDocx5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnDocx5.setForeground(new java.awt.Color(255, 255, 255));
        btnDocx5.setText("DOCX");
        btnDocx5.addActionListener(this::btnDocx5ActionPerformed);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPDF5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocx5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPDF5)
                        .addComponent(btnExcel5)
                        .addComponent(btnExcel25)
                        .addComponent(btnDocx5))
                    .addComponent(jButton9))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Aktivitas Pengguna", jPanel7);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        controller.cetakLaporan(LaporanController.JenisLaporan.PENGGUNA);
        controller.cetakLaporan(
                LaporanController.JenisLaporan.PENGGUNA,
                LaporanController.FormatLaporan.PREVIEW);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.MAINTENANCE,
                LaporanController.FormatLaporan.PREVIEW);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.INSPEKSI,
                LaporanController.FormatLaporan.PREVIEW);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.ALAT,
                LaporanController.FormatLaporan.PREVIEW);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnPDF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF1ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.ALAT,
                LaporanController.FormatLaporan.PDF);
    }//GEN-LAST:event_btnPDF1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.AKTIVITAS,
                LaporanController.FormatLaporan.PREVIEW);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel1ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.ALAT,
                LaporanController.FormatLaporan.XLS);
    }//GEN-LAST:event_btnExcel1ActionPerformed

    private void btnExcel21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel21ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.ALAT,
                LaporanController.FormatLaporan.XLSX);
    }//GEN-LAST:event_btnExcel21ActionPerformed

    private void btnDocxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocxActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.ALAT,
                LaporanController.FormatLaporan.DOCX);
    }//GEN-LAST:event_btnDocxActionPerformed

    private void btnPDF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF2ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.INSPEKSI,
                LaporanController.FormatLaporan.PDF);
    }//GEN-LAST:event_btnPDF2ActionPerformed

    private void btnExcel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel2ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.INSPEKSI,
                LaporanController.FormatLaporan.XLS);
    }//GEN-LAST:event_btnExcel2ActionPerformed

    private void btnExcel22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel22ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.INSPEKSI,
                LaporanController.FormatLaporan.XLSX);
    }//GEN-LAST:event_btnExcel22ActionPerformed

    private void btnDocx2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocx2ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.INSPEKSI,
                LaporanController.FormatLaporan.DOCX);
    }//GEN-LAST:event_btnDocx2ActionPerformed

    private void btnPDF3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF3ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.MAINTENANCE,
                LaporanController.FormatLaporan.PDF);
    }//GEN-LAST:event_btnPDF3ActionPerformed

    private void btnExcel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel3ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.MAINTENANCE,
                LaporanController.FormatLaporan.XLS);
    }//GEN-LAST:event_btnExcel3ActionPerformed

    private void btnExcel23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel23ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.MAINTENANCE,
                LaporanController.FormatLaporan.XLSX);
    }//GEN-LAST:event_btnExcel23ActionPerformed

    private void btnDocx3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocx3ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.MAINTENANCE,
                LaporanController.FormatLaporan.DOCX);
    }//GEN-LAST:event_btnDocx3ActionPerformed

    private void btnPDF4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF4ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.PENGGUNA,
                LaporanController.FormatLaporan.PDF);
    }//GEN-LAST:event_btnPDF4ActionPerformed

    private void btnExcel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel4ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.PENGGUNA,
                LaporanController.FormatLaporan.XLS);
    }//GEN-LAST:event_btnExcel4ActionPerformed

    private void btnExcel24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel24ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.PENGGUNA,
                LaporanController.FormatLaporan.XLSX);
    }//GEN-LAST:event_btnExcel24ActionPerformed

    private void btnDocx4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocx4ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.PENGGUNA,
                LaporanController.FormatLaporan.DOCX);
    }//GEN-LAST:event_btnDocx4ActionPerformed

    private void btnPDF5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDF5ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.AKTIVITAS,
                LaporanController.FormatLaporan.PDF);
    }//GEN-LAST:event_btnPDF5ActionPerformed

    private void btnExcel5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel5ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.AKTIVITAS,
                LaporanController.FormatLaporan.XLS);
    }//GEN-LAST:event_btnExcel5ActionPerformed

    private void btnExcel25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel25ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.AKTIVITAS,
                LaporanController.FormatLaporan.XLSX);
    }//GEN-LAST:event_btnExcel25ActionPerformed

    private void btnDocx5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocx5ActionPerformed
        controller.cetakLaporan(
                LaporanController.JenisLaporan.AKTIVITAS,
                LaporanController.FormatLaporan.DOCX);
    }//GEN-LAST:event_btnDocx5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDocx;
    private javax.swing.JButton btnDocx2;
    private javax.swing.JButton btnDocx3;
    private javax.swing.JButton btnDocx4;
    private javax.swing.JButton btnDocx5;
    private javax.swing.JButton btnExcel1;
    private javax.swing.JButton btnExcel2;
    private javax.swing.JButton btnExcel21;
    private javax.swing.JButton btnExcel22;
    private javax.swing.JButton btnExcel23;
    private javax.swing.JButton btnExcel24;
    private javax.swing.JButton btnExcel25;
    private javax.swing.JButton btnExcel3;
    private javax.swing.JButton btnExcel4;
    private javax.swing.JButton btnExcel5;
    private javax.swing.JButton btnPDF1;
    private javax.swing.JButton btnPDF2;
    private javax.swing.JButton btnPDF3;
    private javax.swing.JButton btnPDF4;
    private javax.swing.JButton btnPDF5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    // End of variables declaration//GEN-END:variables
}
