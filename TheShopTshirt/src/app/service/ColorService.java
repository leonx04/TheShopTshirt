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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dungn
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
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public String getNewIDMS() {
        // Mã sản phẩm mặc định
        String newID = "MS001";
        try {
            // Truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 4, LEN(ID)) AS INT)) AS maxID FROM MAUSAC";
            // trong truy vấn SQL, MAX(CAST(SUBSTRING(ID, 4, LEN(ID)) AS INT)) được sử dụng
            // để lấy số thứ tự lớn nhất của các mã sản phẩm trong cơ sở dữ liệu.
            // SUBSTRING(ID, 4, LEN(ID)) được sử dụng để cắt bỏ ba ký tự đầu tiên của mã
            // chất
            // liệu (trong trường hợp này là "CL"),
            // sau đó chuyển thành kiểu số nguyên bằng CAST.
            // Kết nối đến cơ sở dữ liệu
            con = DBConnect.getConnection();
            // Tạo đối tượng PreparedStatement từ truy vấn SQL
            ps = con.prepareStatement(sql);
            // Thực hiện truy vấn và lưu kết quả vào ResultSet
            rs = ps.executeQuery();
            // Kiểm tra xem ResultSet có kết quả hay không
            if (rs.next()) {
                // Nếu có kết quả, lấy giá trị số thứ tự lớn nhất từ cột "maxID"
                int maxID = rs.getInt("maxID");
                // Tăng giá trị số thứ tự lên một đơn vị
                maxID++;
                // Tạo mã mới từ số thứ tự lớn nhất và định dạng lại để có hai chữ số
                newID = "MS" + String.format("%03d", maxID);
                // %03d là định dạng cho số nguyên với độ dài tối thiểu là 3 chữ số. Điều này
                // đảm bảo rằng số thứ tự sẽ được đặt sau chuỗi "CL" và luôn có ít nhất 3 chữ
                // số, được điền bằng số 0 nếu cần.
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
        // Trả về mã sản phẩm mới hoặc mã mặc định nếu có lỗi xảy ra
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
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return false; 
}
}
