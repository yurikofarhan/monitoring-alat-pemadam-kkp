/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemadam.monitoring.util;

import com.pemadam.monitoring.config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yuriko
 */

public class EnumUtil {

    public static List<String> getEnumValues(String table, String column) {

        List<String> list = new ArrayList<>();

        String sql = "SHOW COLUMNS FROM " + table + " LIKE ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, column);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String type = rs.getString("Type");

                type = type.replace("enum(", "")
                           .replace(")", "")
                           .replace("'", "");

                String[] values = type.split(",");

                for (String v : values) {
                    list.add(v);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static String formatEnum(String text) {

        if (text == null) return "";

        // ganti _ jadi spasi
        text = text.replace("_", " ");

        // kapital setiap kata
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }

        return result.toString().trim();
    }
}
