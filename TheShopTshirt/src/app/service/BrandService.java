/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.BrandModel;
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
public class BrandService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<BrandModel> getALLThuongHieu() {
        sql = "SELECT ID, Ten, MoTa FROM THUONGHIEU";
        List<BrandModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BrandModel th = new BrandModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listCL.add(th);

            }
            return listCL;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean checkTrungTen(String tenTH) {
        sql = "SELECT COUNT(*) AS count FROM THUONGHIEU WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenTH);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                // Nếu count > 0, tức là tên đã tồn tại
                return count > 0;
            }
        } catch (SQLException e) {
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }


    public List<BrandModel> getIDByTenTH(String tenTH) {
        sql = "SELECT ID, Ten, MoTa FROM THUONGHIEU WHERE Ten = ?";
        List<BrandModel> listTH = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenTH);
            rs = ps.executeQuery();
            while (rs.next()) {
                BrandModel th = new BrandModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listTH.add(th);
            }
        } catch (SQLException e) {
            return null;
        }
        return listTH;
    }

    /**
     * Tạo một ID mới cho thương hiệu sử dụng UUID, đảm bảo rằng ID không bị trùng lặp.
     * 
     * Hàm này tạo một UUID mới, chuyển đổi nó thành chuỗi và rút ngắn chuỗi này để tạo ID.
     * Sau đó kiểm tra trong cơ sở dữ liệu để đảm bảo ID mới không bị trùng lặp.
     * Nếu ID bị trùng lặp, hàm sẽ tiếp tục tạo ID mới cho đến khi tìm được ID không trùng lặp.
     * 
     * @return Một ID mới không trùng lặp cho thương hiệu.
     */
    public String getNewIDTH() {
        String newID;
        boolean idExists;
        do {
            // Tạo một UUID mới và lấy phần đầu của nó để tạo ID ngắn hơn
            newID = "TH" + UUID.randomUUID().toString().substring(0, 8);

            try {
                // Kiểm tra xem ID mới có bị trùng lặp hay không
                idExists = checkTrungID(newID);
            } catch (SQLException e) {
                // Nếu có lỗi xảy ra trong quá trình kiểm tra, đặt idExists thành true để thử lại
                idExists = true;
            }
        } while (idExists); // Lặp lại cho đến khi tìm được ID không trùng lặp

        return newID;
    }

    /**
     * Kiểm tra xem ID đã tồn tại trong bảng THUONGHIEU hay chưa.
     * 
     * @param id ID cần kiểm tra.
     * @return true nếu ID tồn tại, false nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    public boolean checkTrungID(String id) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM THUONGHIEU WHERE ID = ?";
        try (Connection con = DBConnect.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Nếu count > 0, tức là ID đã tồn tại
                }
            }
        }
        return false; // Nếu không có bản ghi nào khớp
    }

    public int insert(BrandModel th) {
        sql = "INSERT INTO THUONGHIEU(ID, Ten, MoTa,NgayTao, NgaySua) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, th.getID());
            ps.setObject(2, th.getTenTH());
            ps.setObject(3, th.getMoTa());
            return ps.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }

    public int update(BrandModel th, String ma) {
        sql = "UPDATE THUONGHIEU SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, th.getTenTH());
            ps.setObject(2, th.getMoTa());
            return ps.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM THUONGHIEU WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public boolean checkTonTaiSPCT(String idThuongHieu) {
        String sql = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE ID_ThuongHieu = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idThuongHieu);
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
