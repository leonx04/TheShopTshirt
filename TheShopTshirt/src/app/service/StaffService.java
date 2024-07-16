package app.service;

import app.connect.DBConnect;
import app.model.StaffModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StaffService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<StaffModel> getAllStaff() {
        sql = "SELECT * FROM NHANVIEN ORDER BY NgayTao DESC";
        List<StaffModel> listStaff = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                StaffModel staff = new StaffModel(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getInt("NamSinh"),
                        rs.getString("GioiTinh"),
                        rs.getBoolean("ChucVu"),
                        rs.getString("MatKhau"),
                        rs.getString("TrangThai")
                );
                listStaff.add(staff);
            }
            return listStaff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StaffModel> getAllStaffDangLamViec() {
        sql = "SELECT * FROM NHANVIEN WHERE TrangThai = N'Đang làm việc'";
        return getStaffByTrangThai(sql);
    }

    public List<StaffModel> getAllStaffNghiViec() {
        sql = "SELECT * FROM NHANVIEN WHERE TrangThai = N'Nghỉ việc'";
        return getStaffByTrangThai(sql);
    }

    private List<StaffModel> getStaffByTrangThai(String sql) {
        List<StaffModel> listStaff = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                StaffModel staff = new StaffModel(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getInt("NamSinh"),
                        rs.getString("GioiTinh"),
                        rs.getBoolean("ChucVu"),
                        rs.getString("MatKhau"),
                        rs.getString("TrangThai")
                );
                listStaff.add(staff);
            }
            return listStaff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StaffModel> getStaffByHoTen(String hoTen) {
        sql = "SELECT * FROM NHANVIEN WHERE HoTen = ?";
        List<StaffModel> listStaff = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, hoTen);
            rs = ps.executeQuery();
            while (rs.next()) {
                StaffModel staff = new StaffModel(
                        rs.getString("ID"),
                        rs.getString("HoTen"),
                        rs.getString("DiaChi"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getInt("NamSinh"),
                        rs.getString("GioiTinh"),
                        rs.getBoolean("ChucVu"),
                        rs.getString("MatKhau"),
                        rs.getString("TrangThai")
                );
                listStaff.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listStaff;
    }

    public String getNewStaffID() {
        String newID = "NV01";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM NHANVIEN";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "NV" + String.format("%02d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    public int insert(StaffModel staff) {
        System.out.println("ID: " + staff.getId());
        System.out.println("HoTen: " + staff.getHoTen());
        System.out.println("DiaChi: " + staff.getDiaChi());
        System.out.println("SDT: " + staff.getSdt());
        System.out.println("Email: " + staff.getEmail());
        System.out.println("NamSinh: " + staff.getNamSinh());
        System.out.println("GioiTinh: " + staff.getGioiTinh());
        System.out.println("ChucVu: " + staff.isChucVu());
        System.out.println("MatKhau: " + staff.getMatKhau());
        System.out.println("TrangThai: " + staff.getTrangThai());
        sql = "INSERT INTO NHANVIEN(ID, HoTen, DiaChi, SoDienThoai, Email, NamSinh, GioiTinh, ChucVu, MatKhau, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, staff.getId());
            ps.setString(2, staff.getHoTen());
            ps.setString(3, staff.getDiaChi());
            ps.setString(4, staff.getSdt());
            ps.setString(5, staff.getEmail());
            ps.setInt(6, staff.getNamSinh());
            ps.setString(7, staff.getGioiTinh());
            ps.setBoolean(8, staff.isChucVu());
            ps.setString(9, staff.getMatKhau());
            ps.setString(10, "Đang làm việc");
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(StaffModel staff, String id) {
        sql = "UPDATE NHANVIEN SET HoTen=?, DiaChi=?, SoDienThoai=?, Email=?, NamSinh=?, GioiTinh=?, ChucVu=?, MatKhau=?, NgaySua=CURRENT_TIMESTAMP WHERE ID=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, staff.getHoTen());
            ps.setString(2, staff.getDiaChi());
            ps.setString(3, staff.getSdt());
            ps.setString(4, staff.getEmail());
            ps.setInt(5, staff.getNamSinh());
            ps.setString(6, staff.getGioiTinh());
            ps.setBoolean(7, staff.isChucVu());
            ps.setString(8, staff.getMatKhau());
            ps.setString(9, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateActiveTrangThai(String id) {
        sql = "UPDATE NHANVIEN SET TrangThai = N'Đang làm việc', NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateTrangThai(String id) {
        sql = "UPDATE NHANVIEN SET TrangThai = N'Nghỉ việc', NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String id) {
        sql = "DELETE FROM NHANVIEN WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkTrungMa(String id) {
        sql = "SELECT COUNT(*) AS count FROM NHANVIEN WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
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

    public boolean checkTrungSDT(String sdt) {
        sql = "SELECT COUNT(*) AS count FROM NHANVIEN WHERE SoDienThoai = ?";
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

    public boolean checkTrungEmail(String email) {
        sql = "SELECT COUNT(*) AS count FROM NHANVIEN WHERE Email = ?";
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
}
