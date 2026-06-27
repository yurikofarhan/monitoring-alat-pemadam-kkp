/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Yuriko
 */
public class ImageUtil {

    private static final String BASE_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    public static BufferedImage cropSquare(BufferedImage img) {
        int size = Math.min(img.getWidth(), img.getHeight());
        int x = (img.getWidth() - size) / 2;
        int y = (img.getHeight() - size) / 2;
        return img.getSubimage(x, y, size, size);
    }

    public static String saveImage(File selectedFile, String oldImage, String folderName) throws Exception {
        if (selectedFile == null) {
            return oldImage;
        }

        String folderPath = BASE_DIR + folderName + File.separator;

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (oldImage != null && !oldImage.isEmpty()) {
            deleteImage(oldImage, folderName);
        }

        String fileName = UUID.randomUUID().toString() + ".png";
        File targetFile = new File(folderPath + fileName);

        BufferedImage original = ImageIO.read(selectedFile);
        if (original == null) {
            throw new IllegalArgumentException("File yang dipilih bukan merupakan gambar valid.");
        }
        
        BufferedImage cropped = cropSquare(original);

        ImageIO.write(cropped, "png", targetFile);

        original.flush();
        cropped.flush();

        return fileName;
    }

    public static void deleteImage(String fileName, String folderName) {
        if (fileName == null || fileName.isEmpty()) return;

        try {
            String path = BASE_DIR + folderName + File.separator + fileName;
            File file = new File(path);

            if (file.exists()) {
                System.gc(); 
                
                if (file.delete()) {
                    System.out.println("Deleted: " + path);
                } else {
                    System.out.println("Gagal menghapus file (Kemungkinan sedang digunakan): " + path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getImagePath(String fileName, String folderName) {
        if (fileName == null || fileName.isEmpty()) return null;
        return BASE_DIR + folderName + File.separator + fileName;
    }
    
    public static ImageIcon loadImage(String fileName, String folderName, int width, int height) {
        try {
            if (fileName == null || fileName.isEmpty()) {
                return null;
            }

            String path = BASE_DIR + folderName + File.separator + fileName;
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("File tidak ditemukan: " + path);
                File defaultFile = new File(BASE_DIR + "default.png");
                if (defaultFile.exists()) {
                    return resizeImageIcon(new ImageIcon(defaultFile.getAbsolutePath()), width, height);
                }
                return null;
            }

            return resizeImageIcon(new ImageIcon(path), width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);
        icon.getImage().flush(); 
        return resizedIcon;
    }
}
