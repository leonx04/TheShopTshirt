/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.SizeModel;
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
public class SizeService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<SizeModel> getALLKichCo() {
        sql = "SELECT ID, Ten, MoTa FROM SIZE";
        List<SizeModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SizeModel kc = new SizeModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                listCL.add(kc);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM SIZE WHERE ID = ?";
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

    public boolean checkTrungTen(String tenKC) {
        sql = "SELECT COUNT(*) AS count FROM SIZE WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenKC);
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

    public List<SizeModel> getIDByTenKC(String tenKC) {
        sql = "SELECT ID, Ten, MoTa FROM SIZE WHERE Ten = ?";
        List<SizeModel> listKC = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenKC);
            rs = ps.executeQuery();
            while (rs.next()) {
                SizeModel kc = new SizeModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listKC.add(kc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listKC;
    }

    public String getNewIDKC() {
        String newID;
        boolean unique = false;
        do {
            newID = "S" + UUID.randomUUID().toString().substring(0, 8); // Tạo UUID và rút ngắn
            unique = !checkTrungID(newID); // Kiểm tra xem ID đã tồn tại hay chưa
        } while (!unique); // Tiếp tục cho đến khi tìm được ID không trùng lặp
        return newID;
    }

    public int insert(SizeModel kc) {
        sql = "INSERT INTO SIZE(ID, Ten, MoTa,NgayTao, NgaySua) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kc.getID());
            ps.setObject(2, kc.getTenSize());
            ps.setObject(3, kc.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(SizeModel kc, String ma) {
        sql = "UPDATE SIZE SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, kc.getTenSize());
            ps.setObject(2, kc.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM SIZE WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkTonTaiSPCT(String sizeID) {
        String sql = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE ID_Size = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sizeID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
