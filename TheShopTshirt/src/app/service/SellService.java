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

/**
 *
 * @author ADMIN
 */
public class SellService {

    private BillDetailService cthdsv = new BillDetailService();
    List<BillDetailModel> listCTHD = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<BillModel> getAllHD1() {
        String sql = "SELECT DISTINCT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
                + "FROM HOADON\n"
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID ORDER BY NgayTao DESC";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<BillModel> getDaThanhToanHoaDon() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Đã thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            return null;
        }
    }

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

    public List<BillModel> getDaHuyHoaDon() {
        String sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
                + "FROM HOADON\n"
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "WHERE HOADON.TrangThai = N'Đã hủy'";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            return null;
        }
    }

    public List<BillDetailModel> searchByHoaDonID(String idHoaDon) {
        List<BillDetailModel> chiTietHoaDons = new ArrayList<>();
        String sql1 = "SELECT SANPHAMCHITIET.ID AS MaSanPhamChiTiet, SANPHAM.TenSanPham AS TenSanPham, SANPHAMCHITIET.GiaBan AS DonGia, HOADONCHITIET.SoLuong AS SoLuong, HOADONCHITIET.ThanhTien AS ThanhTien "
                + "FROM HOADONCHITIET "
                + "INNER JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ? AND HOADONCHITIET.ID_HoaDon IN (SELECT ID FROM HOADON)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHoaDon.trim());
            rs = ps.executeQuery();
            while (rs.next()) {
                BillDetailModel chiTietHoaDon = new BillDetailModel();
                chiTietHoaDon.setMactsp(new ProductDetailModel(rs.getString("MaSanPhamChiTiet")));
                chiTietHoaDon.setTenSP(new ProductsModel(rs.getString("TenSanPham")));
                chiTietHoaDon.setDonGia(new ProductDetailModel(rs.getBigDecimal("DonGia")));
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                chiTietHoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                chiTietHoaDons.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
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

    public String getNewHD() {
        String newID = "HD001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 4, LEN(ID)) AS INT)) AS maxID FROM HOADON";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "HD" + String.format("%03d", maxID);
            }
        } catch (SQLException e) {
        }
        return newID;
    }

    public int taoHoaDon(String idNhanVien, String idKhachHang) {
        String hinhThucThanhToan = "Tiền mặt";
        String sql = "INSERT INTO HOADON (ID, ID_NhanVien, ID_KhachHang, HinhThucThanhToan, TongTien, TrangThai, ID_Voucher, NgayTao, NgaySua) "
                + "VALUES (?, ?, ?, ?, ?, N'Chờ thanh toán', 'V00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, getNewHD()); // Lấy ID mới cho hóa đơn
            ps.setObject(2, idNhanVien);
            ps.setObject(3, idKhachHang); // Sử dụng ID của khách hàng được truyền vào
            ps.setObject(4, hinhThucThanhToan);
            ps.setObject(5, 0); // Giá trị tổng tiền ban đầu

            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
