/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.MaterialModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dungn
 */
public class MaterialService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<MaterialModel> getALLChatLieu() {
        sql = "SELECT ID, Ten, MoTa FROM  CHATLIEU";
        List<MaterialModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MaterialModel cl = new MaterialModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                listCL.add(cl);

            }
            return listCL;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public List<MaterialModel> getIDByTenCL(String tenCL) {
        sql = "SELECT ID, Ten, MoTa FROM  CHATLIEU WHERE Ten = ?";
        List<MaterialModel> listCL = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenCL);
            rs = ps.executeQuery();
            while (rs.next()) {
                MaterialModel cLieuModel = new MaterialModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listCL.add(cLieuModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listCL;
    }

    public String getNewIDCL() {
        // Mã sản phẩm mặc định
        String newID = "CL001";
        try {
            // Truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 4, LEN(ID)) AS INT)) AS maxID FROM CHATLIEU";
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
                newID = "CL" + String.format("%03d", maxID);
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

    public int insert(MaterialModel cl) {
        sql = "INSERT INTO CHATLIEU(ID, Ten, MoTa,NgayTao, NgaySua) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, cl.getID());
            ps.setObject(2, cl.getTenCL());
            ps.setObject(3, cl.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return 0;
    }

    public int update(MaterialModel cl, String ma) {
        sql = "UPDATE CHATLIEU SET Ten = ?, MoTa = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, cl.getTenCL());
            ps.setObject(2, cl.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM CHATLIEU WHERE ID = ?";
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

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM CHATLIEU WHERE ID = ?";
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

    public boolean checkTrungTen(String tenCL) {
        sql = "SELECT COUNT(*) AS count FROM CHATLIEU WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenCL);
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

    public boolean checkTonTaiSPCT(String idChatLieu) {
        String sql = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE ID_ChatLieu = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idChatLieu);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return false;
    }
}
