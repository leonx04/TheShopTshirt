/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.BillModel;
import app.model.CustomerModel;
import app.model.StaffModel;
import app.model.VoucherModer;
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
public class BillService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<BillModel> getAll() {
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

    public List<BillModel> findDate(java.sql.Date ngayBD, java.sql.Date ngayKT) {
        List<BillModel> listHD = new ArrayList<>(); // Khởi tạo danh sách hóa đơn trước khi sử dụng
        String sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan\n"
                + "FROM HOADON INNER JOIN\n"
                + "NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID INNER JOIN\n"
                + "KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID INNER JOIN\n"
                + "VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "WHERE HOADON.NgayTao BETWEEN ? AND ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ngayBD);
            ps.setObject(2, ngayKT);
            rs = ps.executeQuery();

            while (rs.next()) {
                BillModel hoaDonModel = new BillModel(
                        rs.getString("ID"),
                        rs.getDate("NgayTao"),
                        new StaffModel(rs.getString("HoTen")),
                        new CustomerModel(rs.getString("TenKhachHang")),
                        new VoucherModer(rs.getString("TenVoucher")),
                        rs.getBigDecimal("TongTien"),
                        rs.getString("HinhThucThanhToan")
                );
                listHD.add(hoaDonModel);
            }
        } catch (SQLException e) {
            return null;
        }
        return listHD;
    }

    public List<BillModel> searchBills(String keyword) {
        List<BillModel> searchResult = new ArrayList<>();
        String sql = "SELECT DISTINCT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai "
                + "FROM HOADON "
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID "
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID "
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID "
                + "WHERE HOADON.ID LIKE ? OR NHANVIEN.HoTen LIKE ? OR KHACHHANG.HoTen LIKE ? "
                + "ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillModel hdModel = new BillModel(
                            rs.getString("ID"),
                            rs.getDate("NgayTao"),
                            new StaffModel(rs.getString("HoTen")),
                            new CustomerModel(rs.getString("TenKhachHang")),
                            rs.getBigDecimal("TongTien"),
                            new VoucherModer(rs.getString("TenVoucher")),
                            rs.getString("HinhThucThanhToan"),
                            rs.getString("TrangThai")
                    );
                    searchResult.add(hdModel);
                }
            }
        } catch (SQLException e) {
        }
        return searchResult;
    }


    public String getNewHD() {
        String newID = "HD001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 5, LEN(ID)) AS INT)) AS maxID FROM HOADON";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "HD" + String.format("%02d", maxID);
            }
        } catch (SQLException e) {
        }
        return newID;
    }

    public int insert(BillModel hdm) {
        sql = "INSERT INTO HOADON(ID, ID_NhanVien, ID_KhachHang, ID_Voucher, HinhThucThanhToan, TongTien, NgayTao, NgaySua, TrangThai) "
                + "VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán')";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdm.getID());
            ps.setObject(2, hdm.getNgayTao());
            ps.setObject(3, hdm.getTenNV().getId());
            ps.setObject(4, hdm.getTenKH().getId());
            ps.setObject(5, hdm.getTenVoucher().getID());
            ps.setObject(6, hdm.getTongTien());
            ps.setObject(7, hdm.getHinhThucThanhToan());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int update(BillModel hdm) {
        sql = "UPDATE HOADON SET ID_NhanVien = ?, ID_KhachHang = ?, ID_Voucher = ?, HinhThucThanhToan = ?, TongTien = ?, NgayTao = CURRENT_TIMESTAMP, NgaySua = CURRENT_TIMESTAMP, TrangThai = ?\n"
                + "WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hdm.getNgayTao());
            ps.setObject(2, hdm.getTenNV().getId());
            ps.setObject(3, hdm.getTenKH().getId());
            ps.setObject(4, hdm.getTenVoucher().getID());
            ps.setObject(6, hdm.getTongTien());
            ps.setObject(5, hdm.getHinhThucThanhToan());
            ps.setObject(7, hdm.getID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(String ID) {
        sql = "DELETE FROM HOADON WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
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

    public List<BillModel> getDaHuyHoaDon() {
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
                + "                WHERE HOADON.TrangThai = N'Đã hủy'\n"
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
                + "                WHERE HOADON.TrangThai = N'Chờ thanh toán'\n"
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

    public List<BillModel> getHDTienMat() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Tiền mặt'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
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

    public List<BillModel> getHDTienChuyenKhoan() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Chuyển khoản'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
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

    public List<BillModel> getHDKetHop() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "                VOUCHER.TenVoucher,\n"
                + "                HOADON.TongTien,\n"
                + "                HOADON.HinhThucThanhToan,\n"
                + "                HOADON.TrangThai\n"
                + "                                FROM HOADON\n"
                + "                                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "                                WHERE HOADON.HinhThucThanhToan = N'Kết hợp'\n"
                + "                                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";
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

    public List<BillModel> searchByDateRange(java.sql.Date startDate, java.sql.Date endDate) {
        List<BillModel> result = new ArrayList<>();
        String sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai "
                + "FROM HOADON "
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID "
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID "
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID "
                + "WHERE HOADON.NgayTao BETWEEN ? AND ?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillModel billModel = new BillModel(
                            rs.getString("ID"),
                            rs.getDate("NgayTao"),
                            new StaffModel(rs.getString("HoTen")),
                            new CustomerModel(rs.getString("TenKhachHang")),
                            rs.getBigDecimal("TongTien"),
                            new VoucherModer(rs.getString("TenVoucher")),
                            rs.getString("HinhThucThanhToan"),
                            rs.getString("TrangThai")
                    );
                    result.add(billModel);
                }
            }
        } catch (SQLException e) {
        }
        return result;
    }
}
