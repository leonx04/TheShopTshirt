/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.ColorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public class ColorService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ColorModel> getALLMauSac() {
        sql = "SELECT ID, TenMau, MoTa FROM MAUSAC";
        List<ColorModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ColorModel ms = new ColorModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listCL.add(ms);

            }
            return listCL;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM MAUSAC WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                // Nếu count > 0, tức là ID đã tồn tại
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public boolean checkTrungTen(String tenMS) {
        sql = "SELECT COUNT(*) AS count FROM MAUSAC WHERE TenMau = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenMS);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                // Nếu count > 0, tức là tên đã tồn tại
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public List<ColorModel> getIDByTenMS(String tenMS) {
        sql = "SELECT ID, TenMau, MoTa FROM MAUSAC WHERE TenMau = ?";
        List<ColorModel> listMS = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenMS);
            rs = ps.executeQuery();
            while (rs.next()) {
                ColorModel ms = new ColorModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listMS.add(ms);
            }
        } catch (SQLException e) {
            return null;
        }
        return listMS;
    }

    public int insert(ColorModel ms) {
        sql = "INSERT INTO MAUSAC(ID, TenMau, MoTa,NgayTao, NgaySua) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ms.getID());
            ps.setObject(2, ms.getTenMS());
            ps.setObject(3, ms.getMoTa());
            return ps.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }

    public String getNewIDMS() {
        String newID;
        boolean unique = false;
        do {
            newID = "MS" + UUID.randomUUID().toString().substring(0, 8); // Tạo UUID và rút ngắn
            unique = !checkTrungID(newID); // Kiểm tra xem ID đã tồn tại hay chưa
        } while (!unique); // Tiếp tục cho đến khi tìm được ID không trùng lặp
        return newID;
    }

    public int update(ColorModel ms, String ma) {
        sql = "UPDATE MAUSAC SET TenMau = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, ms.getTenMS());
            ps.setObject(2, ms.getMoTa());
            return ps.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM MAUSAC WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<ColorModel> getALLMauSacByTen() {
        sql = "SELECT ID, TenMau, MoTa FROM MAUSAC WHERE TenMau = ? ";
        List<ColorModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ColorModel ms = new ColorModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listCL.add(ms);

            }
            return listCL;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean checkTonTaiSPCT(String maMauSac) {
        String sql = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE ID_MauSac = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maMauSac);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
