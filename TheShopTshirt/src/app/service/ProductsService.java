/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.ProductsModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ProductsService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ProductsModel> getAllSP() {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM ORDER BY NgayTao DESC";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductsModel> getAllSPDangKinhDoanh() {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TrangThai = N'Đang kinh doanh'";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductsModel> getAllSPNgungKinhDoanh() {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TrangThai = N'Ngừng kinh doanh'";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductsModel> getAllSPSoluongLonHon0() {
        sql = "SELECT ID, TenSanPham, MoTa\n"
                + "FROM SANPHAM\n"
                + "WHERE ID IN (\n"
                + "    SELECT ID_SanPham\n"
                + "    FROM SANPHAMCHITIET\n"
                + "    GROUP BY ID_SanPham\n"
                + "    HAVING COUNT(*) > 0";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductsModel> getAllSPSoluong0() {
        sql = "SELECT ID, TenSanPham, MoTa\n"
                + "FROM SANPHAM\n"
                + "WHERE ID NOT IN (\n"
                + "    SELECT ID_SanPham\n"
                + "    FROM SANPHAMCHITIET\n"
                + "    GROUP BY ID_SanPham\n"
                + "    HAVING COUNT(*) > 0";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
            return listSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductsModel> getAllSPByTrangThai(String trangthai) {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TrangThai = ?";
        List<ProductsModel> listSPTT = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangthai);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString("1"),
                        rs.getString("2"),
                        rs.getString(3));
                listSPTT.add(sp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return listSPTT;
    }

    public List<ProductsModel> getIDByTenSP(String tenSP) {
        sql = "SELECT ID, TenSanPham, MoTa FROM SANPHAM WHERE TenSanPham = ?";
        List<ProductsModel> listSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductsModel sp = new ProductsModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listSP;
    }

    public String getNewSanPhamID() {
        // Mã sản phẩm mặc định
        String newID = "SP01";
        try {
            // Truy vấn SQL để lấy số thứ tự lớn nhất của mã sản phẩm từ cơ sở dữ liệu
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM SANPHAM";
            // trong truy vấn SQL, MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) được sử dụng
            // để lấy số thứ tự lớn nhất của các mã sản phẩm trong cơ sở dữ liệu.
            // SUBSTRING(ID, 3, LEN(ID)) được sử dụng để cắt bỏ ba ký tự đầu tiên của mã sản
            // phẩm (trong trường hợp này là "SP"),
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
                // Tạo mã sản phẩm mới từ số thứ tự lớn nhất và định dạng lại để có hai chữ số
                newID = "SP" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
        // Trả về mã sản phẩm mới hoặc mã mặc định nếu có lỗi xảy ra
        return newID;
    }

    public int insert(ProductsModel sp) {
        sql = "INSERT INTO SANPHAM(ID, TenSanPham, MoTa, NgayTao, NgaySua, TrangThai)VALUES( ?,  ?,  ?,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP,  N'Đang kinh doanh')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getID());
            ps.setObject(2, sp.getTenSP());
            ps.setObject(3, sp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(ProductsModel sp, String ma) {
        sql = "UPDATE SANPHAM SET TenSanPham = ?, MoTa = ? , NgaySua  = CURRENT_TIMESTAMP WHERE ID = ? ;";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(3, ma);
            ps.setObject(1, sp.getTenSP());
            ps.setObject(2, sp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }
    public int updateActiveTrangThai(ProductsModel sp, String ma) {
        sql = "UPDATE SANPHAM SET TrangThai = ?, NgaySua  = CURRENT_TIMESTAMP WHERE ID = ? ;";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(2, ma);
            ps.setObject(1, "Đang kinh doanh");
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int updateTrangThai(ProductsModel sp, String ma) {
        sql = "UPDATE SANPHAM SET TrangThai = ?, NgaySua  = CURRENT_TIMESTAMP WHERE ID = ? ;";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(2, ma);
            ps.setObject(1, "Ngừng kinh doanh");
            return ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String ma) {
        sql = "DELETE FROM SANPHAM WHERE ID = ? ";
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

    public boolean checkTrungMa(String maSP) {
        sql = "SELECT COUNT(*) AS count FROM SANPHAM WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu tên sản phẩm đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra hoặc tên sản phẩm không tồn tại
    }

    public boolean checkTrungTen(String tenSP) {
        sql = "SELECT COUNT(*) AS count FROM SANPHAM WHERE TenSanPham = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Trả về true nếu tên sản phẩm đã tồn tại trong cơ sở dữ liệu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra hoặc tên sản phẩm không tồn tại
    }

    public boolean checkTonTaiSPCT(String idSanPham) {
        sql = "SELECT COUNT(*) FROM SANPHAMCHITIET WHERE ID_SanPham = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idSanPham);
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
