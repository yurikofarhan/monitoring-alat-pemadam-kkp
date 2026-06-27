/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pemadam.monitoring.view.maintenance;

import com.pemadam.monitoring.controller.LokasiController;
import com.pemadam.monitoring.controller.MaintenanceController;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.LokasiModel;
import com.pemadam.monitoring.model.MaintenanceModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zy
 */
public class Maintenance extends javax.swing.JPanel {

    /**
     * Creates new form Maintenance
     */
    private MaintenanceController controller;
    private List<AlatModel> alatList;
    private List<InspeksiModel> inspeksiList;

    public Maintenance() {
        initComponents();
        loadJenisAlat();
        loadLokasi();

        tblMaintenance.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID",
                    "Tanggal Mulai",
                    "Tanggal Selesai",
                    "Status",
                    "Keterangan",
                    "Nama Petugas",
                    "Aksi"
                }
        ));

        // hide ID
        tblMaintenance.getColumnModel().getColumn(0).setMinWidth(0);
        tblMaintenance.getColumnModel().getColumn(0).setMaxWidth(0);
        tblMaintenance.getColumnModel().getColumn(0).setWidth(0);

        controller = new MaintenanceController(this);

        controller.loadCombo();

        cmbPilihAlat.addActionListener(e -> pilihAlat());
        cmbPilihInspeksi.addActionListener(e -> pilihInspeksi());

//        setTableAction();

    }
    
    private void loadJenisAlat() {

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        model.addElement("APAR");
        model.addElement("APAB");
        model.addElement("HYDRANT");
        model.addElement("SPRINKLER");

        cmbJenisAlat.setModel(model);
    }

    private void loadLokasi() {

        LokasiController controller = new LokasiController();

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (LokasiModel l : controller.getAll()) {
            model.addElement(l);
        }

        cmbLokasi.setModel(model);
    }

    public void setComboAlat(List<AlatModel> list) {
        alatList = list;

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        model.addElement("----- Pilih Alat -----");

        for (AlatModel a : list) {
            model.addElement(a.getKodeAlat() + " - " + a.getNamaAlat());
        }

        cmbPilihAlat.setModel(model);
        cmbPilihAlat.setSelectedIndex(0);
    }

    public void setComboInspeksi(List<InspeksiModel> list) {
        inspeksiList = list;

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        model.addElement("----- Pilih Inspeksi -----");

        for (InspeksiModel i : list) {
            String tgl = (i.getTanggalInspeksi() != null)
                    ? new java.text.SimpleDateFormat("dd-MM-yyyy")
                            .format(i.getTanggalInspeksi())
                    : "-";

            model.addElement(tgl + " | " + i.getKondisi());
        }

        cmbPilihInspeksi.setModel(model);
        cmbPilihInspeksi.setSelectedIndex(0);
    }

    private void pilihAlat() {
        int index = cmbPilihAlat.getSelectedIndex();

        if (index == 0) {
            resetForm();
            resetInspeksi();
            return;
        }

        int idAlat = alatList.get(index - 1).getIdAlat();

        controller.pilihAlat(idAlat);
    }

    private void pilihInspeksi() {
        int index = cmbPilihInspeksi.getSelectedIndex();

//        if (index <= 0 || inspeksiList == null) return;
        if (index == 0) {
            DefaultTableModel model = (DefaultTableModel) tblMaintenance.getModel();
            model.setRowCount(0);
            return;
        }

        int idInspeksi = inspeksiList.get(index - 1).getIdInspeksi();

        controller.pilihInspeksi(idInspeksi);
    }

    
    private void resetForm() {
        txtKodeAlat.setText("");
        txtNamaAlat.setText("");
        cmbJenisAlat.setSelectedIndex(0);
        cmbLokasi.setSelectedIndex(0);
        dateBeli.setDate(null);

        cmbPilihInspeksi.setSelectedIndex(0);

        DefaultTableModel model = (DefaultTableModel) tblMaintenance.getModel();
        model.setRowCount(0);
    }
    
    private void resetInspeksi() {
        inspeksiList = null;

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("----- Pilih Inspeksi -----");

        cmbPilihInspeksi.setModel(model);
    }
    
    
    public void showTable(List<MaintenanceModel> list) {
        DefaultTableModel model = (DefaultTableModel) tblMaintenance.getModel();
        model.setRowCount(0);

        for (MaintenanceModel i : list) {
            model.addRow(new Object[]{
                i.getIdMaintenance(),
                i.getTanggalMulai() != null
                    ? new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm")
                        .format(i.getTanggalMulai())
                    : "-",
                i.getTanggalSelesai() != null
                    ? new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm")
                        .format(i.getTanggalSelesai())
                    : "-",
                i.getStatus(),
                i.getKeterangan(),
                i.getNamaPengguna(), 
                "Detail"
            });
        }
    }
    
    public void showDetailAlat(AlatModel a) {
        txtKodeAlat.setText(a.getKodeAlat());
        txtNamaAlat.setText(a.getNamaAlat());

        cmbJenisAlat.setSelectedItem(a.getJenisAlat());
        cmbLokasi.setSelectedItem(new LokasiModel(a.getIdLokasi())); 
//        System.out.println("DB Lokasi: [" + a.getIdLokasi() + "]");

        // null safe
        if (a.getTglPembelian() != null) {
            dateBeli.setDate(new java.util.Date(a.getTglPembelian().getTime()));
        } else {
            dateBeli.setDate(null);
        }

        
    }
    
    
    public void showMessage(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg);
    }

//    DefaultTableModel model = (DefaultTableModel)jTable.getModel();
//    model.addrow(new object[] (jTextkd_alat.getText(), jTextmn_alat.getText(),
//                              jTextjns_alat.getText(), jTextlks.getText(), jTexttgl.getText()));
//
//    //kode untuk tanggal//
//    String tampilan = "yyyy-MM-dd";
//    SimpleDateFormat fm = new SimpleDateFormat(tampilan);
//    String tanggal = String.valueOf(fm.format(tgl.getDate))));
//
//    //kode combo cox
//    String alat;
//    alat = "";
//    if (alat.isSelected()){
//            alat += alat.getText() + ", ";
//    }
//    if (alat.isSelected()){
//            alat += alat.getText() + ", ";
//    {
//    else {
//            alat = alat.substring(0, alat.length()-2) + ".";
//    }
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
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbPilihAlat = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbPilihInspeksi = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtKodeAlat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNamaAlat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbJenisAlat = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbLokasi = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaintenance = new javax.swing.JTable();
        dateBeli = new com.toedter.calendar.JDateChooser();

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Maintenance ");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 490, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText(" Plih Alat :");

        cmbPilihAlat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silahkan Pilih Alat", " " }));
        cmbPilihAlat.addActionListener(this::cmbPilihAlatActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Pilih Data Inspeksi :");

        cmbPilihInspeksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Data Inspeksi" }));
        cmbPilihInspeksi.addActionListener(this::cmbPilihInspeksiActionPerformed);

        jLabel3.setText("Kode Alat");

        jLabel4.setText("Nama Alat");

        jLabel6.setText("Jenis Alat");

        jLabel7.setText("Lokasi");

        cmbLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--" }));

        jLabel8.setText("Tanggal Beli");

        tblMaintenance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblMaintenance);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5)
                        .addComponent(jLabel2)
                        .addComponent(cmbPilihInspeksi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbPilihAlat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6))
                                    .addGap(8, 8, 8))
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbJenisAlat, 0, 151, Short.MAX_VALUE)
                                        .addComponent(txtNamaAlat)
                                        .addComponent(txtKodeAlat)
                                        .addComponent(cmbLokasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(dateBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPilihAlat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPilihInspeksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtKodeAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNamaAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(cmbJenisAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cmbLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateBeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPilihAlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPilihAlatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPilihAlatActionPerformed

    private void cmbPilihInspeksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPilihInspeksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPilihInspeksiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbJenisAlat;
    private javax.swing.JComboBox<String> cmbLokasi;
    private javax.swing.JComboBox<String> cmbPilihAlat;
    private javax.swing.JComboBox<String> cmbPilihInspeksi;
    private com.toedter.calendar.JDateChooser dateBeli;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMaintenance;
    private javax.swing.JTextField txtKodeAlat;
    private javax.swing.JTextField txtNamaAlat;
    // End of variables declaration//GEN-END:variables
}
