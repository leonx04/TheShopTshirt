/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import java.sql.*;
import app.connect.DBConnect;
import app.form.DetailProduct;
import app.model.BillDetailModel;
import app.model.BillModel;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.CustomerModel;
import app.model.MaterialModel;
import app.model.ProductDetailModel;
import app.model.ProductsModel;
import app.model.SizeModel;
import app.model.StaffModel;
import app.model.VoucherModer;
import java.math.BigDecimal;
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
public class SellService {

    private final BillDetailService cthdsv = new BillDetailService();
    List<BillDetailModel> listCTHD = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<BillModel> getHoaDonChoThanhToan() {
        String sql
                = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Chờ thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString("ID"),
                        rs.getDate("NgayTao"),
                        new StaffModel(rs.getString("HoTen")),
                        new CustomerModel(rs.getString("TenKhachHang")),
                        rs.getBigDecimal("TongTien"),
                        new VoucherModer(rs.getString("TenVoucher")),
                        rs.getString("HinhThucThanhToan"),
                        rs.getString("TrangThai"));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<BillDetailModel> searchByHoaDonID(String idHoaDon) {
        List<BillDetailModel> chiTietHoaDons = new ArrayList<>();

        if (idHoaDon == null || idHoaDon.trim().isEmpty()) {
            System.err.println("ID hóa đơn không hợp lệ: " + idHoaDon);
            return chiTietHoaDons; // Trả về danh sách rỗng nếu ID không hợp lệ
        }

        String sql = "SELECT SANPHAMCHITIET.ID AS MaSanPhamChiTiet, SANPHAM.TenSanPham AS TenSanPham, "
                + "SANPHAMCHITIET.GiaBan AS DonGia, HOADONCHITIET.SoLuong AS SoLuong, "
                + "HOADONCHITIET.ThanhTien AS ThanhTien "
                + "FROM HOADONCHITIET "
                + "INNER JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ? AND HOADONCHITIET.ID_HoaDon IN (SELECT ID FROM HOADON)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idHoaDon.trim());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillDetailModel chiTietHoaDon = new BillDetailModel();
                    chiTietHoaDon.setMactsp(new ProductDetailModel(rs.getString("MaSanPhamChiTiet")));
                    chiTietHoaDon.setTenSP(new ProductsModel(rs.getString("TenSanPham")));
                    chiTietHoaDon.setDonGia(new ProductDetailModel(rs.getBigDecimal("DonGia")));
                    chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                    chiTietHoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                    chiTietHoaDons.add(chiTietHoaDon);
                }
            }

        } catch (SQLException e) {
            // In lỗi ra console để tiện theo dõi
        }

        return chiTietHoaDons;
    }

    public List<BillDetailModel> getAllCTHD() {
        sql = "SELECT        HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, \n"
                + "                         HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien\n"
                + "FROM            HOADONCHITIET INNER JOIN\n"
                + "                          SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
                + "                         SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham INNER JOIN\n"
                + "                         MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN\n"
                + "                         SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN\n"
                + "                         THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN\n"
                + "                         CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BillDetailModel cthh = new BillDetailModel(
                        rs.getString(1),
                        new ProductsModel(rs.getString(2)),
                        new ColorModel(rs.getString(3)),
                        new SizeModel(rs.getString(4)),
                        new MaterialModel(rs.getString(5)),
                        new BrandModel(rs.getString(6)),
                        new ProductDetailModel(rs.getBigDecimal(7)),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listCTHD.add(cthh);
            }
            return listCTHD;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<ProductDetailModel> getAllCTSP() {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAM.TrangThai <> N'Ngừng kinh doanh'\n"
                + "AND SANPHAMCHITIET.SoLuongTon > 0";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetailModel ctsp = new ProductDetailModel(
                        rs.getString(1), // ID
                        new ProductsModel(rs.getString(2)), // TenSP
                        new ColorModel(rs.getString(3)), // MauSac
                        new SizeModel(rs.getString(4)), // Size
                        new MaterialModel(rs.getString(5)), // ChatLieu
                        new BrandModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (SQLException e) {
            return null;
        }

    }

    public List<ProductDetailModel> getAllCTSPByColorID(String colorID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu , SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAMCHITIET.SoLuongTon > 0 AND MAUSAC.ID = ?";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, colorID);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetailModel ctsp = new ProductDetailModel(
                        rs.getString(1), // ID
                        new ProductsModel(rs.getString(2)), // TenSP
                        new ColorModel(rs.getString(3)), // MauSac
                        new SizeModel(rs.getString(4)), // Size
                        new MaterialModel(rs.getString(5)), // ChatLieu
                        new BrandModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<ProductDetailModel> getAllCTSPBySizeID(String sizeID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAMCHITIET.SoLuongTon > 0 AND SIZE.ID = ?"; // Thêm điều kiện tìm kiếm theo ID kích cỡ

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sizeID);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetailModel ctsp = new ProductDetailModel(
                        rs.getString(1), // ID
                        new ProductsModel(rs.getString(2)), // TenSP
                        new ColorModel(rs.getString(3)), // MauSac
                        new SizeModel(rs.getString(4)), // Size
                        new MaterialModel(rs.getString(5)), // ChatLieu
                        new BrandModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<ProductDetailModel> getAllCTSPByBrandID(String brandID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAMCHITIET.SoLuongTon > 0 AND THUONGHIEU.ID = ?";
        // Thêm điều kiện tìm kiếm theo ID thương hiệu

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, brandID); // Thiết lập tham số cho ID thương hiệu
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetailModel ctsp = new ProductDetailModel(
                        rs.getString(1), // ID
                        new ProductsModel(rs.getString(2)), // TenSP
                        new ColorModel(rs.getString(3)), // MauSac
                        new SizeModel(rs.getString(4)), // Size
                        new MaterialModel(rs.getString(5)), // ChatLieu
                        new BrandModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (Exception e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
            return null;
        }
    }

    public String getIDByTen(String ten) {
        String idKhachHang = null;
        String sql = "SELECT ID FROM KHACHHANG WHERE HoTen = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ten);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idKhachHang = rs.getString("ID");
                }
            }
        } catch (SQLException e) {
        }
        return idKhachHang;
    }

    private String generateUniqueID() throws SQLException {
        String newID;
        boolean idExists;

        do {
            newID = "HD" + UUID.randomUUID().toString().substring(0, 8);
            idExists = checkIfIDExists(newID);
        } while (idExists);

        return newID;
    }

    /**
     * Kiểm tra xem ID đã tồn tại trong bảng HOADON hay chưa.
     *
     * Hàm này thực hiện truy vấn cơ sở dữ liệu để kiểm tra sự tồn tại của một
     * ID cụ thể trong bảng `HOADON`. Nếu ID đã tồn tại, hàm sẽ trả về true;
     * ngược lại, trả về false.
     *
     * Quy trình thực hiện: 1. Tạo câu lệnh SQL để đếm số lượng bản ghi có ID
     * khớp với giá trị đầu vào. 2. Kết nối đến cơ sở dữ liệu thông qua
     * `DBConnect.getConnection()`. 3. Tạo một đối tượng `PreparedStatement` với
     * * câu lệnh SQL. 4. Gán giá trị của tham số ID vào câu lệnh SQL để đảm bảo
     * an toàn khỏi SQL Injection. 5. Thực thi câu lệnh SQL và nhận kết quả bằng
     * `executeQuery()`. 6. Kiểm tra xem có bất kỳ bản ghi nào được trả về từ
     * truy vấn: - Nếu có, đọc giá trị đếm được và kiểm tra xem nó có lớn hơn 0
     * hay không. - Nếu có, nghĩa là ID đã tồn tại, và hàm trả về true. - Nếu
     * không có bản ghi nào, nghĩa là ID chưa tồn tại, và hàm trả về false. 7.
     * Đóng các đối tượng `ResultSet`, `PreparedStatement`, và `Connection` sau
     * khi hoàn thành.
     *
     * COUNT(*): Một hàm tổng hợp trong SQL được sử dụng để đếm số lượng bản ghi
     * trong kết quả truy vấn. Dấu * có nghĩa là đếm tất cả các bản ghi mà truy
     * vấn trả về..
     *
     * @return true nếu ID tồn tại trong bảng `HOADON`, false nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi thực hiện truy vấn cơ sở dữ
     * liệu.
     */
    private boolean checkIfIDExists(String id) throws SQLException {
        // Câu lệnh SQL để đếm số lượng bản ghi có ID khớp với giá trị đầu vào
        sql = "SELECT COUNT(*) FROM HOADON WHERE ID = ?";

        // Tạo kết nối đến cơ sở dữ liệu và chuẩn bị câu lệnh SQL
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Gán giá trị của tham số ID vào câu lệnh SQL
            ps.setString(1, id);

            // Thực thi câu lệnh SQL và nhận kết quả
            try (ResultSet rs = ps.executeQuery()) {

                // Kiểm tra nếu có bản ghi được trả về
                if (rs.next()) {
                    // Đọc số lượng bản ghi khớp và kiểm tra nếu lớn hơn 0
                    return rs.getInt(1) > 0;
                }
            }
        }

        // Nếu không có bản ghi nào được tìm thấy, trả về false
        return false;
    }

    public String getNewHD() throws SQLException {
        // Tạo ID duy nhất
        return generateUniqueID();
    }

    public int taoHoaDon(String idNhanVien, String idKhachHang) {
        String hinhThucThanhToan = "Tiền mặt";
        String sql = "INSERT INTO HOADON (ID, ID_NhanVien, ID_KhachHang, HinhThucThanhToan, TongTien, TrangThai, ID_Voucher, NgayTao, NgaySua) "
                + "VALUES (?, ?, ?, ?, ?, N'Chờ thanh toán', 'V000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, getNewHD()); // Lấy ID mới cho hóa đơn
            ps.setObject(2, idNhanVien);// Dùng id nhân viên được truyền vào 
            ps.setObject(3, idKhachHang); // Sử dụng ID của khách hàng được truyền vào
            ps.setObject(4, hinhThucThanhToan);
            ps.setObject(5, 0); // Giá trị tổng tiền ban đầu

            return ps.executeUpdate();
        } catch (SQLException e) {
        }
        return 0;
    }

    public BigDecimal getGiaBanByMaCTSP(String maCTSP) {
        BigDecimal giaBan = null;
        String sql = "SELECT GiaBan FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                giaBan = rs.getBigDecimal("GiaBan");
            }
        } catch (SQLException e) {
        }
        return giaBan;
    }

    public int laySoLuongTonByID(String maCTSP) {
        int soLuongTon = 0;
        String sql = "SELECT SoLuongTon FROM SANPHAMCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                soLuongTon = rs.getInt("SoLuongTon");
            }
        } catch (SQLException e) {
        }
        return soLuongTon;
    }

    public BillDetailModel kiemTraTrungSanPhamChiTiet(String maCTSP, String maHoaDon) {
        String sql = "SELECT ID, SoLuong FROM HOADONCHITIET WHERE ID_SanPhamChiTiet = ? AND ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            ps.setString(2, maHoaDon);
            rs = ps.executeQuery();
            if (rs.next()) {
                BillDetailModel chiTietHoaDon = new BillDetailModel();
                chiTietHoaDon.setID(rs.getString("ID"));
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                return chiTietHoaDon;
            }
        } catch (SQLException e) {
        } finally {
            // Đóng kết nối và các tài nguyên
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
            } catch (SQLException ex) {
            }
        }
        return null;
    }

    public int updateSoLuongVaThanhTienHoaDonChiTiet(String maHDCT, int soLuongMoi, BigDecimal thanhTienMoi) {
        String sql = "UPDATE HOADONCHITIET SET SoLuong = ?, ThanhTien = ? WHERE ID = ?";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, soLuongMoi);
            ps.setBigDecimal(2, thanhTienMoi);
            ps.setString(3, maHDCT);

            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public int updateSoLuongTon(String idSanPhamChiTiet, int soLuongMoi) {
        sql = "UPDATE SANPHAMCHITIET SET SoLuongTon = ? WHERE ID = ?";
        try {
            int result = DBConnect.ExcuteQuery(sql, soLuongMoi, idSanPhamChiTiet);
            return result;
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean capNhatTongTienHoaDon(String maHoaDon) {
        // Kiểm tra và gán giá trị mặc định là 0 nếu tổng tiền là null
        String sqlUpdateNullTongTien = "UPDATE HOADON SET TongTien = 0 WHERE TongTien IS NULL AND ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateNullTongTien);
            ps.setString(1, maHoaDon);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }

        // Lấy tổng tiền từ bảng HOADONCHITIET
        BigDecimal tongTien = BigDecimal.ZERO;
        String sqlGetTongTien = "SELECT SUM(ThanhTien) AS TongTien FROM HOADONCHITIET WHERE ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlGetTongTien);
            ps.setString(1, maHoaDon);
            rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal sum = rs.getBigDecimal("TongTien");
                if (sum != null) {
                    tongTien = sum;
                }
            }
        } catch (SQLException e) {
            return false;
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
            } catch (SQLException ex) {
            }
        }

        // Cập nhật tổng tiền vào bảng HOADON
        String sqlUpdateTongTien = "UPDATE HOADON SET TongTien = ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateTongTien);
            ps.setBigDecimal(1, tongTien);
            ps.setString(2, maHoaDon);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public int themSPGioHang(BillDetailModel chiTietHoaDon, String idHoaDon) {
        // Thực hiện các bước để lấy ID mới cho HOADONCHITIET
        String newHDCTID = cthdsv.getNewHDCTByID();

        String sql = "INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, ThanhTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Chưa thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, newHDCTID);
            ps.setObject(2, idHoaDon); // Sử dụng ID hóa đơn đã chọn
            ps.setObject(3, chiTietHoaDon.getMactsp().getID());
            ps.setObject(4, chiTietHoaDon.getSoLuong());
            ps.setObject(5, chiTietHoaDon.getThanhTien());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getIdByTenVoucher(String tenVoucher) {
        sql = "SELECT ID FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ID");
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public String getTenByIDVoucher(String ID) {
        sql = "SELECT TenVoucher FROM VOUCHER WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenVoucher");
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public String getLoaiVoucherByTenVoucher(String tenVoucher) {
        sql = "SELECT LoaiVoucher FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("LoaiVoucher");
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public BigDecimal getMucGiamGiaByTenVoucher(String tenVoucher) {
        sql = "SELECT MucGiamGia FROM VOUCHER WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("MucGiamGia");
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int updateIdNhanVienChoHoaDon(String maHoaDon, String maNhanVienMoi) {
        sql = "UPDATE HOADON SET ID_NhanVien = ? WHERE ID = ?";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maNhanVienMoi);
            ps.setString(2, maHoaDon);

            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean updateVoucherHoaDon(String hoaDonID, String voucherID) {
        sql = "UPDATE HOADON SET ID_Voucher = ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, voucherID);
            ps.setString(2, hoaDonID);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean updateHTTTHoaDon(String hoaDonID, String HTTT) {
        // Thực hiện cập nhật hình thức thanh toán cho hóa đơn có ID tương ứng
        sql = "UPDATE HoaDon SET HinhThucThanhToan = ? WHERE ID = ?";
        boolean isSuccess = false; // Biến để xác định việc cập nhật thành công hay không
        try {
            // Kết nối cơ sở dữ liệu và thực hiện cập nhật
            con = DBConnect.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, HTTT);
            ps.setString(2, hoaDonID);

            int rowsUpdated = ps.executeUpdate();
            isSuccess = rowsUpdated > 0;
        } catch (SQLException e) {
        }
        return isSuccess;
    }

    public int updateBillStatus(String maHoaDon, String trangThaiMoi) {
        sql = "UPDATE HOADON SET  TrangThai = ? WHERE ID = ?";
        int rowsAffected = 0;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThaiMoi);
            ps.setString(2, maHoaDon);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
        }
        return rowsAffected;
    }

    public BillModel getHoaDonByID(String hoaDonID) {
        sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai "
                + "FROM HOADON "
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID "
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID "
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID "
                + "WHERE HOADON.ID = ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hoaDonID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BillModel(
                            rs.getString("ID"),
                            rs.getDate("NgayTao"),
                            new StaffModel(rs.getString("HoTen")),
                            new CustomerModel(rs.getString("TenKhachHang")),
                            rs.getBigDecimal("TongTien"),
                            new VoucherModer(rs.getString("TenVoucher")),
                            rs.getString("HinhThucThanhToan"),
                            rs.getString("TrangThai"));
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int updateSoLuongVoucher(String tenVoucher) {
        sql = "UPDATE VOUCHER SET SoLuong = SoLuong - 1 WHERE TenVoucher = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenVoucher);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int xoaToanBoHoaDonChiTiet(String maHoaDon) {
        String sql = "DELETE FROM HOADONCHITIET WHERE ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maHoaDon);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean updateBillWhileDeleteALL(String maHoaDon) {
        BigDecimal tongTien = BigDecimal.ZERO; // Đặt giá trị mặc định là 0
        String sqlGetTongTien = "SELECT TongTien FROM HOADON WHERE ID = ?";
        String sqlUpdateTongTien = "UPDATE HOADON SET TongTien = 0 WHERE ID = ?";

        try (
                Connection con = DBConnect.getConnection(); PreparedStatement psGet = con.prepareStatement(sqlGetTongTien); PreparedStatement psUpdate = con.prepareStatement(sqlUpdateTongTien)) {

            // Lấy giá trị tổng tiền hiện tại từ cơ sở dữ liệu
            psGet.setString(1, maHoaDon);
            try (ResultSet rs = psGet.executeQuery()) {
                if (rs.next()) {
                    tongTien = rs.getBigDecimal("TongTien");
                }
            }

            // Tiến hành cập nhật tổng tiền thành 0 cho hóa đơn
            psUpdate.setString(1, maHoaDon);
            int rowsAffected = psUpdate.executeUpdate();

            // Cập nhật số lượng tồn của sản phẩm chi tiết
            List<BillDetailModel> chiTietHoaDons = searchByHoaDonID(maHoaDon);
            for (BillDetailModel chiTietHoaDon : chiTietHoaDons) {
                updateSoLuongTon(chiTietHoaDon.getMactsp().getID(),
                        laySoLuongTonByID(chiTietHoaDon.getMactsp().getID()) + chiTietHoaDon.getSoLuong());
            }

            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            return false;
        }
    }

    // Lấy HĐCT theo ID_SPCT 
    public BillDetailModel getHDCT_BY_Id_HD_Id_SPCT(String id_HD, String ma_SPCT) {
        sql = "select * from HOADONCHITIET\n"
                + "WHERE ID_SanPhamChiTiet = ? AND ID_HoaDon = ?";
        BillDetailModel hdct = new BillDetailModel();
        try {
            rs = DBConnect.getDataFromQuery(sql, ma_SPCT, id_HD);
            while (rs.next()) {
                hdct.setID(rs.getString("ID"));//ID_SPCT
                hdct.setSoLuong(rs.getInt("SoLuong"));//SL_SP
            }
            return hdct;
        } catch (SQLException e) {
            return null;
        }
    }

    // Lấy ra SPCT dựa vào ID_SPCT
    public ProductDetailModel get_SPCT_BY_Id_SPCT(String id_SPCT) {
        sql = "select * from SANPHAMCHITIET\n"
                + "where ID = ?";
        ProductDetailModel spct = new ProductDetailModel();
        try {
            rs = DBConnect.getDataFromQuery(sql, id_SPCT);
            while (rs.next()) {
                spct.setSoLuongTon(rs.getInt("SoLuongTon"));
            }
            return spct;
        } catch (SQLException e) {
            return null;
        }
    }

    public int xoaHoaDonChiTiet(String maCTSP, String maHoaDon) {
        String sql = "DELETE FROM HOADONCHITIET WHERE ID_SanPhamChiTiet = ? AND ID_HoaDon = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maCTSP);
            ps.setString(2, maHoaDon);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean updateBillWhileDeleteOne(String maHoaDon) {
        BigDecimal tongTien = BigDecimal.ZERO;

        // Lấy danh sách chi tiết hóa đơn còn lại
        List<BillDetailModel> chiTietHoaDons = searchByHoaDonID(maHoaDon);

        // Tính tổng tiền của các chi tiết hóa đơn còn lại
        for (BillDetailModel chiTietHoaDon : chiTietHoaDons) {
            tongTien = tongTien.add(chiTietHoaDon.getThanhTien());
        }

        // Cập nhật tổng tiền của hóa đơn
        String sqlUpdateTongTien = "UPDATE HOADON SET TongTien = ? WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sqlUpdateTongTien);
            ps.setBigDecimal(1, tongTien);
            ps.setString(2, maHoaDon);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            return false;
        } finally {
            // Đóng kết nối
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public boolean huyHDByID(String trangThai, String idHD) {
        sql = "UPDATE HOADON SET TrangThai = ? WHERE ID = ?";
        boolean isSuccess = false; // Biến để xác định việc cập nhật thành công hay không

        try {
            // Mở kết nối đến cơ sở dữ liệu
            con = DBConnect.getConnection();

            // Chuẩn bị câu lệnh SQL
            ps = con.prepareStatement(sql);

            // Thiết lập tham số cho câu lệnh SQL
            ps.setString(1, trangThai);
            ps.setString(2, idHD);

            // Thực thi câu lệnh SQL và nhận số dòng được cập nhật
            int rowsUpdated = ps.executeUpdate();

            // Nếu có ít nhất một dòng được cập nhật thành công, đặt isSuccess thành true
            isSuccess = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public CustomerModel findBySDT(String sdt) {
        sql = "SELECT ID, HoTen, SoDienThoai, DiaChi, Email, GioiTinh FROM KHACHHANG WHERE SoDienThoai = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new CustomerModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
