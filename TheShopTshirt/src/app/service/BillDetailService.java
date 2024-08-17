/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.BillDetailModel;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.MaterialModel;
import app.model.ProductDetailModel;
import app.model.ProductsModel;
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
public class BillDetailService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    List<BillDetailModel> listCTHD = new ArrayList<>();

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

    public List<BillDetailModel> searchByHoaDonID(String hoaDonID) {
        List<BillDetailModel> listCTHD = new ArrayList<>();
        String sql = "SELECT HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, "
                + "HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien "
                + "FROM HOADONCHITIET "
                + "INNER JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID "
                + "INNER JOIN SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ?";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, hoaDonID); // Thiết lập giá trị tham số cho câu truy vấn
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BillDetailModel cthh = new BillDetailModel(
                        rs.getString("ID"),
                        new ProductsModel(rs.getString("TenSP")),
                        new ColorModel(rs.getString("TenMS")),
                        new SizeModel(rs.getString("TenSize")),
                        new MaterialModel(rs.getString("TenCL")),
                        new BrandModel(rs.getString("TenTT")),
                        new ProductDetailModel(rs.getBigDecimal("DonGia")),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("ThanhTien")
                );
                listCTHD.add(cthh);
            }

            // Đóng kết nối và trả về danh sách chi tiết hoá đơn
            rs.close();
            ps.close();
            con.close();
            return listCTHD;

        } catch (SQLException e) {
            return null;
        }
    }

    public boolean checkTrungID(String id) {
        String sql = "SELECT COUNT(*) AS count FROM HOADONCHITIET WHERE ID = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    // Nếu count > 0, tức là ID đã tồn tại
                    return count > 0;
                }
            }
        } catch (SQLException e) {
        }
        return false; // Nếu không có bản ghi nào khớp hoặc có lỗi xảy ra
    }

    public String getNewHDCTByID() {
        String newID;
        boolean unique = false;
        do {
            newID = "S" + UUID.randomUUID().toString().substring(0, 8); // Tạo UUID và rút ngắn
            unique = !checkTrungID(newID); // Kiểm tra xem ID đã tồn tại hay chưa
        } while (!unique); // Tiếp tục cho đến khi tìm được ID không trùng lặp
        return newID;
    }

    public int insert2(BillDetailModel hdct) {
        sql = "INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, DonGia, ThanhTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, N'Đã thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdct.getID());
            ps.setObject(2, hdct.getTenSP().getTenSP());
            ps.setObject(3, hdct.getMauSac().getTenMS());
            ps.setObject(4, hdct.getSize().getTenSize());
            ps.setObject(5, hdct.getChatLieu().getTenCL());
            ps.setObject(6, hdct.getThuongHieu().getTenTH());
            ps.setObject(7, hdct.getDonGia().getGiaBan());
            ps.setObject(8, hdct.getSoLuong());
            ps.setObject(9, hdct.getThanhTien());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int update2(BillDetailModel hdct) {
        sql = "UPDATE HOADONCHITIET SET ID_HoaDon = ?, ID_SanPhamChiTiet = ?, SoLuong = ?, ThanhTien = ?, NgayTao = CURRENT_TIMESTAMP, NgaySua = CURRENT_TIMESTAMP, TrangThai = ?\n"
                + "WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdct.getTenSP().getTenSP());
            ps.setObject(2, hdct.getMauSac().getTenMS());
            ps.setObject(3, hdct.getSize().getTenSize());
            ps.setObject(4, hdct.getChatLieu().getTenCL());
            ps.setObject(5, hdct.getThuongHieu().getTenTH());
            ps.setObject(6, hdct.getDonGia().getGiaBan());
            ps.setObject(7, hdct.getSoLuong());
            ps.setObject(8, hdct.getThanhTien());
            ps.setObject(9, hdct.getID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete2(String id) {
        sql = "DELETE FROM HOADONCHITIET WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

}
