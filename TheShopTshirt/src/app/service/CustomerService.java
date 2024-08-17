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
 * @author tbluc
 */
public class CustomerService {

    String sql = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;

    public List<CustomerModel> getAllCustomer() {
        sql = "select ID,HoTen,SoDienThoai,DiaChi,Email,GioiTinh,TrangThai From KHACHHANG";
        List<CustomerModel> listCusromer = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomerModel customerModel = new CustomerModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );

                listCusromer.add(customerModel);
            }
            return listCusromer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức tạo ID mới cho KhachHang
    public String getNewCustomerID() {
        String newID = "KH001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM KHACHHANG";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "KH" + String.format("%03d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    //check email trung
    public boolean checkTrungEmail(String email) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE Email = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //check số điện thoại trùng
    public boolean checkTrungSDT(String sdt) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE SoDienThoai = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức thêm mới một voucher vào cơ sở dữ liệu
    public int insert(CustomerModel customerModel) {
        sql = "INSERT INTO KHACHHANG(ID, HoTen, SoDienThoai, DiaChi, Email, GioiTinh, TrangThai) VALUES(?,?,?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, customerModel.getId());
            ps.setObject(2, customerModel.getTen());
            ps.setObject(3, customerModel.getSdt());
            ps.setObject(4, customerModel.getDiachi());
            ps.setObject(5, customerModel.getEmail());
            ps.setObject(6, customerModel.getGioiTinh());
            ps.setObject(7, customerModel.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<CustomerModel> searchCustomer(String keyword) {
        sql = "SELECT * FROM KHACHHANG WHERE LOWER(HoTen) LIKE ? OR LOWER(ID) LIKE ? OR LOWER(SoDienThoai) LIKE ?";
        List<CustomerModel> listCusstomer = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomerModel cuss = new CustomerModel(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("GioiTinh"),
                        rs.getString("TrangThai"));

                listCusstomer.add(cuss);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCusstomer;
    }
    
     public List<CustomerModel> searchCustomerSDT(String keyword) {
        sql = "SELECT * FROM KHACHHANG WHERE LOWER(SoDienThoai) LIKE ?";
        List<CustomerModel> listCusstomer = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomerModel cuss = new CustomerModel(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getString("GioiTinh"),
                        rs.getString("TrangThai"));

                listCusstomer.add(cuss);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCusstomer;
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM KHACHHANG WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // If count > 0, then ID already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int delete(String ma) {
        sql = "DELETE FROM KHACHHANG WHERE ID = ?";
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

    public int update(CustomerModel cussModel, String id) {
        sql = "UPDATE KHACHHANG SET HoTen = ?, SoDienThoai = ?, DiaChi = ?, Email = ?, GioiTinh = ?, TrangThai= ? Where ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, cussModel.getTen());
            ps.setObject(2, cussModel.getSdt());
            ps.setObject(3, cussModel.getDiachi());
            ps.setObject(4, cussModel.getEmail());
            ps.setObject(5, cussModel.getGioiTinh());
            ps.setObject(6, cussModel.getTrangThai());
            ps.setObject(7, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<BillModel> getHoaDonByIdKhachHang(String idKhachHang) {
    String sql = "select HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai "
               + "from HOADON "
               + "inner join KHACHHANG on HOADON.ID_KhachHang = KHACHHANG.ID "
               + "inner join NHANVIEN on HOADON.ID_NhanVien = NHANVIEN.ID "
               + "inner join VOUCHER on HOADON.ID_Voucher = VOUCHER.ID "
               + "where KHACHHANG.ID = ?";

    try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, idKhachHang); // Sử dụng chỉ số 1 cho tham số đầu tiên
        ResultSet rs = ps.executeQuery();
        List<BillModel> hoaDons = new ArrayList<>();

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

            hoaDons.add(hdModel);
        }

        return hoaDons;

    } catch (SQLException e) {
        e.printStackTrace();
        return null; // Hoặc xử lý lỗi khác tùy theo yêu cầu
    }
}

}
