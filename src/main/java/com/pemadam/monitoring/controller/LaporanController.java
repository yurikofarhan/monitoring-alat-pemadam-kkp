/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.controller;

/**
 *
 * @author Yuriko
 */
import com.pemadam.monitoring.config.Koneksi;
import com.pemadam.monitoring.model.*;
import com.pemadam.monitoring.dao.*;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 *
 * @author Yuriko
 */
public class LaporanController {

    LaporanDAO dao = new LaporanDAO();

    public List<AlatModel> getLaporanAlat() {
        return dao.tampilkanDataAlat();
    }

    public List<InspeksiModel> getLaporanInspeksi() {
        return dao.tampilkanDataInspeksi();
    }

    public List<MaintenanceModel> getLaporanMaintenance() {
        return dao.tampilkanDataMaintenance();
    }

    public List<PenggunaModel> getLaporanPengguna() {
        return dao.tampilkanDataPengguna();
    }

    public List<LogAktivitasModel> getLaporanAktivitas() {
        return dao.tampilkanDataAktivitas();
    }

    public enum JenisLaporan {
        ALAT,
        PENGGUNA,
        INSPEKSI,
        MAINTENANCE,
        AKTIVITAS
    }

    public enum FormatLaporan {
        PREVIEW,
        PDF,
        XLS,
        XLSX,
        DOCX,
        HTML,
        CSV
    }

    public void cetakLaporan(JenisLaporan jenis, FormatLaporan format) {
        cetakLaporan(jenis, format, new HashMap<>());
    }

    public void cetakLaporan(JenisLaporan jenis,
            FormatLaporan format,
            Map<String, Object> parameter) {

        try {

            Connection conn = Koneksi.getConnection();

            String report = getReportName(jenis);

            InputStream is = loadReport(report);

            JasperPrint jp = JasperFillManager.fillReport(
                    is,
                    parameter,
                    conn
            );

            switch (format) {

                case PREVIEW:
                    JasperViewer.viewReport(jp, false);
                    break;

                case PDF:
                    exportPDF(jp);
                    break;
                case XLS:
                    exportXLS(jp);
                    break;
                case XLSX:
                    exportXLSX(jp);
                    break;

                case DOCX:
                    exportDOCX(jp);
                    break;

                case HTML:
                    exportHTML(jp);
                    break;

                case CSV:
                    exportCSV(jp);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }

    private String getReportName(JenisLaporan jenis) {

        switch (jenis) {

            case ALAT:
                return "LaporanDataAlat.jasper";

            case PENGGUNA:
                return "LaporanDataPengguna.jasper";

            case INSPEKSI:
                return "LaporanInspeksiAlat.jasper";

            case MAINTENANCE:
                return "LaporanMaintenanceAlat.jasper";

            case AKTIVITAS:
                return "LaporanAktivitasPengguna.jasper";

            default:
                throw new IllegalArgumentException("Laporan tidak tersedia.");

        }

    }

    private InputStream loadReport(String file) {

        InputStream is = getClass().getResourceAsStream("/reports/" + file);

        if (is == null) {
            throw new RuntimeException("Report tidak ditemukan : " + file);
        }

        return is;
    }

    private File pilihFile(String ext) {

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(new File("Laporan." + ext));

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            if (!file.getAbsolutePath().toLowerCase().endsWith("." + ext)) {
                file = new File(file.getAbsolutePath() + "." + ext);
            }

            return file;
        }

        return null;

    }

    private void exportPDF(JasperPrint jp) throws JRException {

        File file = pilihFile("pdf");

        if (file == null) {
            return;
        }

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(file));

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "PDF berhasil disimpan.");

    }

    private void exportXLSX(JasperPrint jp) throws JRException {

        File file = pilihFile("xlsx");

        if (file == null) {
            return;
        }

        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(file));

        SimpleXlsxReportConfiguration config
                = new SimpleXlsxReportConfiguration();

        config.setDetectCellType(true);
        config.setCollapseRowSpan(false);
        config.setOnePagePerSheet(false);
        config.setRemoveEmptySpaceBetweenRows(true);

        exporter.setConfiguration(config);

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "Excel berhasil disimpan.");

    }

    private void exportDOCX(JasperPrint jp) throws JRException {

        File file = pilihFile("docx");

        if (file == null) {
            return;
        }

        JRDocxExporter exporter = new JRDocxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(file));

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "Word berhasil disimpan.");

    }

    private void exportHTML(JasperPrint jp) throws JRException {

        File file = pilihFile("html");

        if (file == null) {
            return;
        }

        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleHtmlExporterOutput(file));

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "HTML berhasil disimpan.");

    }

    private void exportCSV(JasperPrint jp) throws JRException {

        File file = pilihFile("csv");

        if (file == null) {
            return;
        }

        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleWriterExporterOutput(file));

        exporter.setConfiguration(
                new SimpleCsvExporterConfiguration());

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "CSV berhasil disimpan.");

    }

    private void exportXLS(JasperPrint jp) throws JRException {

        File file = pilihFile("xls");

        if (file == null) {
            return;
        }

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));

        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(file));

        SimpleXlsReportConfiguration config
                = new SimpleXlsReportConfiguration();

        config.setDetectCellType(true);
        config.setCollapseRowSpan(false);
        config.setOnePagePerSheet(false);
        config.setRemoveEmptySpaceBetweenRows(true);
        config.setWhitePageBackground(false);

        exporter.setConfiguration(config);

        exporter.exportReport();

        JOptionPane.showMessageDialog(null, "Excel (.xls) berhasil disimpan.");
    }

}
