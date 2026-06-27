/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.pemadam.monitoring.view.inspeksi;

import com.pemadam.monitoring.controller.InspeksiController;
import com.pemadam.monitoring.controller.LokasiController;
import com.pemadam.monitoring.model.AlatModel;
import com.pemadam.monitoring.model.InspeksiModel;
import com.pemadam.monitoring.model.LokasiModel;
import com.pemadam.monitoring.util.EnumUtil;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author M Arief Budhiyanto
 */
public class Inspeksi extends javax.swing.JPanel {

    /**
     * Creates new form Inspeksi
     */
    private int selectedId = -1;
    private InspeksiController controller;
    private List<AlatModel> alatList;
    private final Integer IMAGE_SIZE = 128;

    public Inspeksi() {
        initComponents();

        loadJenisAlat();
        loadLokasi();

        tblInspeksi.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID",
                    "No",
                    "Tanggal",
                    "Kondisi Alat",
                    "Status Alat",
                    "Status Inspeksi",
                    "Keterangan"
                }
        ));

        // hide ID
        tblInspeksi.getColumnModel().getColumn(0).setMinWidth(0);
        tblInspeksi.getColumnModel().getColumn(0).setMaxWidth(0);
        tblInspeksi.getColumnModel().getColumn(0).setWidth(0);
        
        // hide ID
        tblInspeksi.getColumnModel().getColumn(0).setMinWidth(0);
        tblInspeksi.getColumnModel().getColumn(0).setMaxWidth(0);
        tblInspeksi.getColumnModel().getColumn(0).setWidth(0);
        
        tblInspeksi.setRowHeight(25);
        tblInspeksi.getColumnModel().getColumn(1).setPreferredWidth(10);  // No
        tblInspeksi.getColumnModel().getColumn(2).setPreferredWidth(150); // Tanggal 
        tblInspeksi.getColumnModel().getColumn(3).setPreferredWidth(150); // Kondisi Alat
        tblInspeksi.getColumnModel().getColumn(4).setPreferredWidth(120); // Status Alat
        tblInspeksi.getColumnModel().getColumn(5).setPreferredWidth(120); // Status Inspeksi
        tblInspeksi.getColumnModel().getColumn(6).setPreferredWidth(120); // Keterangan
        
        
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        tblInspeksi.getColumnModel().getColumn(1).setCellRenderer(center);
        tblInspeksi.getColumnModel().getColumn(2).setCellRenderer(center);
        tblInspeksi.getColumnModel().getColumn(3).setCellRenderer(center);
        tblInspeksi.getColumnModel().getColumn(4).setCellRenderer(center);
        tblInspeksi.getColumnModel().getColumn(5).setCellRenderer(center);
        tblInspeksi.getColumnModel().getColumn(6).setCellRenderer(center);
        

        controller = new InspeksiController(this);

        cmbPilih.removeAllItems();

        controller.loadCombo();

        cmbPilih.addActionListener(e -> pilihAlat());

        tblInspeksi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                int row = tblInspeksi.getSelectedRow();

                if (row == -1) {
                    selectedId = -1;
                } else {
                    selectedId = Integer.parseInt(
                            tblInspeksi.getValueAt(row, 0).toString()
                    );
                }
            }
        });

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

        for (AlatModel a : list) {
            model.addElement(a.getKodeAlat() + " - " + a.getNamaAlat());
        }

        cmbPilih.setModel(model);

        if (!list.isEmpty()) {
            cmbPilih.setSelectedIndex(0);
        }
    }

    private void pilihAlat() {
        int index = cmbPilih.getSelectedIndex();

        if (index >= 0) {
            int id = alatList.get(index).getIdAlat();
            controller.pilihAlat(id);
        }
    }

    public void showDetailAlat(AlatModel a) {
        txtKodeAlat.setText(a.getKodeAlat());
        txtNamaAlat.setText(a.getNamaAlat());

        cmbJenisAlat.setSelectedItem(a.getJenisAlat());
        cmbLokasi.setSelectedItem(new LokasiModel(a.getIdLokasi()));

        if (a.getTglPembelian() != null) {
            dateBeli.setDate(new java.util.Date(a.getTglPembelian().getTime()));
        } else {
            dateBeli.setDate(null);
        }

    }

    public void showTable(List<InspeksiModel> list) {
        DefaultTableModel model = (DefaultTableModel) tblInspeksi.getModel();
        model.setRowCount(0);
        int n = 0;
        for (InspeksiModel i : list) {

            model.addRow(new Object[]{
                i.getIdInspeksi(),
                ++n,
                i.getTanggalInspeksi() != null
                ? new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm")
                .format(i.getTanggalInspeksi())
                : "-",
                EnumUtil.formatEnum(i.getKondisi())  ,
                EnumUtil.formatEnum(i.getStatus()),
                EnumUtil.formatEnum(i.getStatusInspeksi()),
                i.getKeterangan()
            });
        }
    }

    public void showMessage(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDelete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbPilih = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtKodeAlat = new javax.swing.JTextField();
        txtNamaAlat = new javax.swing.JTextField();
        cmbJenisAlat = new javax.swing.JComboBox<>();
        cmbLokasi = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dateBeli = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInspeksi = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        Detail = new javax.swing.JButton();

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Pilih Alat :");

        cmbPilih.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPilih.addActionListener(this::cmbPilihActionPerformed);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Kode Alat");

        cmbJenisAlat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Nama Alat");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Jenis Alat");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Lokasi");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Tanggal Beli");

        tblInspeksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tblInspeksi.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblInspeksi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbLokasi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbJenisAlat, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaAlat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKodeAlat)
                            .addComponent(dateBeli, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)))
                    .addComponent(jLabel2)
                    .addComponent(cmbPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtKodeAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNamaAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbJenisAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateBeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(232, 31, 71));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/icons8-inspection-32.png"))); // NOI18N
        jLabel1.setText(" Inspeksi");

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        Detail.setBackground(new java.awt.Color(153, 153, 153));
        Detail.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Detail.setForeground(new java.awt.Color(255, 255, 255));
        Detail.setText("Detail");
        Detail.addActionListener(this::DetailActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(505, 505, 505)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Detail)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Detail, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int index = cmbPilih.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Pilih alat dulu!");
            return;
        }
        int idAlat = alatList.get(index).getIdAlat();

        java.awt.Frame parent = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);

        TambahInspeksiDialog dialog = new TambahInspeksiDialog(parent, true);

        dialog.setController(controller);
        dialog.setIdAlat(idAlat);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        controller.pilihAlat(idAlat);
    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPilihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPilihActionPerformed

    private void DetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetailActionPerformed

        int index = cmbPilih.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Pilih alat dulu!");
            return;
        }
        int viewRow = tblInspeksi.getSelectedRow();
        if (viewRow == -1) {
            return;
        }

        int row = tblInspeksi.convertRowIndexToModel(viewRow);

        int idInspeksi = Integer.parseInt(
                tblInspeksi.getValueAt(row, 0).toString()
        );

        InspeksiModel i = controller.getById(idInspeksi);

        java.awt.Frame parent
                = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);

        DetailInspeksiDialog dialog
                = new DetailInspeksiDialog(parent, true);

        dialog.setController(controller);
        dialog.setIdAlat(i.getIdAlat());
        dialog.setData(i);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        controller.pilihAlat(i.getIdAlat());


    }//GEN-LAST:event_DetailActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedId = tblInspeksi.getSelectedRow();

        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this,
                    "Silakan pilih data terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Detail;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JComboBox<String> cmbJenisAlat;
    private javax.swing.JComboBox<String> cmbLokasi;
    private javax.swing.JComboBox<String> cmbPilih;
    private com.toedter.calendar.JDateChooser dateBeli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInspeksi;
    private javax.swing.JTextField txtKodeAlat;
    private javax.swing.JTextField txtNamaAlat;
    // End of variables declaration//GEN-END:variables
}
