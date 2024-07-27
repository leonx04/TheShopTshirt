/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.model.VoucherModer;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tbluc
 */
public class VoucherService {

    String sql = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection con = null;

    // Phương thức lấy tất cả các voucher từ cơ sở dữ liệu
    public List<VoucherModer> getAllVoucher() {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai FROM VOUCHER";
        List<VoucherModer> listVoucher = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer vcModer = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9));
                listVoucher.add(vcModer);
            }
            return listVoucher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức cập nhật trạng thái voucher thành "Không hoạt động" nếu ngày kết thúc nhỏ hơn ngày hiện tại
    public int updateStatusVoucher() {
        sql = "UPDATE VOUCHER SET TrangThai = N'Không hoạt động' WHERE NgayKetThuc < GETDATE()";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức cập nhật trạng thái voucher thành "Hoạt động" nếu ngày kết thúc lớn hơn hoặc bằng ngày hiện tại
    public int updateActiveVouchers() {
        int updatedCount = 0;
        String sql = "UPDATE VOUCHER SET TrangThai = N'Đang Hoạt động' WHERE NgayKetThuc >= GETDATE()";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            updatedCount = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updatedCount;
    }

    // Phương thức cập nhật trạng thái voucher thành "Không hoạt động" nếu số lượng bằng 0
    public int updateVoucherStatusByQuantity() {
        sql = "UPDATE VOUCHER SET TrangThai = N'Không hoạt động' WHERE SoLuong = 0";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức lấy tất cả các voucher đang hoạt động từ cơ sở dữ liệu
    public List<VoucherModer> getAllVoucherActive() {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE TrangThai = N'Đang Hoạt Động' "
                + "AND NgayBatDau <= GETDATE() "
                + "AND NgayKetThuc > GETDATE()";
        List<VoucherModer> listVoucher = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                listVoucher.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
        return listVoucher;
    }

    // Phương thức lấy tất cả các voucher theo trạng thái từ cơ sở dữ liệu
    public List<VoucherModer> getAllVoucherByTrangThai(String trangThai) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai FROM VOUCHER WHERE TrangThai = ? AND SoLuong > 0";
        List<VoucherModer> listV = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                listV.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
        return listV;
    }

    // Phương thức lấy voucher theo tên voucher từ cơ sở dữ liệu
    // Phương thức tạo ID mới cho voucher
    public String getNewVoucherID() {
        String newID = "VC001";
        try {
            sql = "SELECT MAX(CAST(SUBSTRING(ID, 3, LEN(ID)) AS INT)) AS maxID FROM VOUCHER";
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt("maxID");
                maxID++;
                newID = "VC" + String.format("%03d", maxID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newID;
    }

    // Phương thức thêm mới một voucher vào cơ sở dữ liệu
    public int insert(VoucherModer voucher) {
        sql = "INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai, NgayTao, NgaySua) VALUES(?,?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, voucher.getID());
            ps.setObject(2, voucher.getTenVoucher());
            ps.setObject(3, voucher.getSoLuong());
            ps.setObject(4, voucher.getLoaiVoucher());
            ps.setObject(5, voucher.getMucGiamGia());
            ps.setObject(6, voucher.getMoTa());
            ps.setObject(7, voucher.getNgayBatDau());
            ps.setObject(8, voucher.getNgayKetThuc());
            ps.setObject(9, voucher.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức cập nhật thông tin một voucher
    public int update(VoucherModer voucher, String id) {
        sql = "UPDATE VOUCHER SET TenVoucher = ?, SoLuong = ?, LoaiVoucher = ?, MucGiamGia = ?, MoTa = ?, NgayBatDau = ?, NgayKetThuc = ?, TrangThai = ? Where id = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, voucher.getTenVoucher());
            ps.setObject(2, voucher.getSoLuong());
            ps.setObject(3, voucher.getLoaiVoucher());
            ps.setObject(4, voucher.getMucGiamGia());
            ps.setObject(5, voucher.getMoTa());
            ps.setObject(6, voucher.getNgayBatDau());
            ps.setObject(7, voucher.getNgayKetThuc());
            ps.setObject(8, voucher.getTrangThai());
            ps.setObject(9, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức cập nhật số lượng voucher
    public int updateSoLuong(String id, int soLuong) {
        sql = "UPDATE VOUCHER SET SoLuong = ?, NgaySua = CURRENT_TIMESTAMP WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, soLuong);
            ps.setObject(2, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String ma) {
        sql = "DELETE FROM VOUCHER WHERE ID = ?";
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

    public List<VoucherModer> getAllVoucherByTrangThais(String sql) {

        List<VoucherModer> listVc = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString("Id"),
                        rs.getString("TenVoucher"),
                        rs.getInt("SoLuong"),
                        rs.getString("LoaiVoucher"),
                        rs.getBigDecimal("MucGiamGia"),
                        rs.getString("MoTa"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("TrangThai")
                );
                listVc.add(voucher);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return listVc;
    }

    public List<VoucherModer> getAllVouCherHoatdong() {
        sql = "SELECT * FROM VOUCHER WHERE TrangThai = N'Đang Hoạt Động'";
        return getAllVoucherByTrangThais(sql);
    }

    public List<VoucherModer> getAllVouCherKetthuc() {
        sql = "SELECT * FROM VOUCHER WHERE TrangThai = N'Không Hoạt Động'";
        return getAllVoucherByTrangThais(sql);
    }

    public List<VoucherModer> searchVoucherByName(String tenVoucher) {
        sql = "SELECT ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, MoTa, NgayBatDau, NgayKetThuc, TrangThai "
                + "FROM VOUCHER "
                + "WHERE TenVoucher LIKE ?";
        List<VoucherModer> resultList = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tenVoucher + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                VoucherModer voucher = new VoucherModer(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getString(9)
                );
                resultList.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Đóng tài nguyên kết nối
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
        return resultList;
    }

    public boolean checkTrungID(String id) {
        sql = "SELECT COUNT(*) AS count FROM VOUCHER WHERE ID = ?";
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

}
