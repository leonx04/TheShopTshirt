/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.connect.DBConnect;
import app.form.Products;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.MaterialModel;
import app.model.ProductDetailModel;
import app.model.ProductsModel;
import app.model.SizeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public class ProductDetailService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<ProductDetailModel> getAllCTSP() {
        String sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau, SIZE.Ten, CHATLIEU.Ten, THUONGHIEU.Ten, SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID";

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
                        rs.getBigDecimal(8), // GiaBan
                        rs.getInt(7), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

    }

    public List<ProductDetailModel> getAllCTSPSoluong0() {
        String sql = "SELECT "
                + "SANPHAMCHITIET.ID, "
                + "SANPHAM.TenSanPham, "
                + "MAUSAC.TenMau AS MauSac, "
                + "SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, "
                + "THUONGHIEU.Ten AS ThuongHieu, "
                + "SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, "
                + "SANPHAMCHITIET.MoTa "
                + "FROM "
                + "SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SANPHAMCHITIET.SoLuongTon = 0"; // Chỉ lấy các sản phẩm chi tiết có số lượng tồn bằng 0

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int soLuongTon = rs.getInt("SoLuongTon");
                if (soLuongTon == 0) { // Chỉ thêm vào danh sách nếu số lượng tồn là 0
                    ProductDetailModel ctsp = new ProductDetailModel(
                            rs.getString("ID"), // ID
                            new ProductsModel(rs.getString("TenSanPham")), // TenSP
                            new ColorModel(rs.getString("MauSac")), // MauSac
                            new SizeModel(rs.getString("Size")), // Size
                            new MaterialModel(rs.getString("ChatLieu")), // ChatLieu
                            new BrandModel(rs.getString("ThuongHieu")), // ThuongHieu
                            rs.getBigDecimal("GiaBan"), // GiaBan
                            soLuongTon, // SoLuongTon
                            rs.getString("MoTa")); // MoTa
                    listCTSP.add(ctsp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTSP;
    }

    public List<ProductDetailModel> getAllCTSPSoluongLonHon0() {
        String sql = "SELECT \n"
                + "    SANPHAMCHITIET.ID,\n"
                + "    SANPHAM.TenSanPham,\n"
                + "    MAUSAC.TenMau AS MauSac,\n"
                + "    SIZE.Ten AS Size,\n"
                + "    CHATLIEU.Ten AS ChatLieu,\n"
                + "    THUONGHIEU.Ten AS ThuongHieu,\n"
                + "    SANPHAMCHITIET.GiaBan,\n"
                + "    SANPHAMCHITIET.SoLuongTon,\n"
                + "    SANPHAMCHITIET.MoTa\n"
                + "FROM \n"
                + "    SANPHAMCHITIET\n"
                + "INNER JOIN \n"
                + "    SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN \n"
                + "    MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN \n"
                + "    SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN \n"
                + "    CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN \n"
                + "    THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE \n"
                + "    SANPHAMCHITIET.SoLuongTon > 0";

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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> searchBySanPhamID(String sanPhamID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SANPHAM.ID = ?";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sanPhamID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> searchByMauSacID(String mausacID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE MAUSAC.ID = ?";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, mausacID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> searchBySizeID(String sizeID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE SIZE.ID = ?";

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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> searchByChatLieuID(String chatLieuID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE CHATLIEU.ID = ?";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, chatLieuID);
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
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> searchByThuonghieuID(String thuonghieuID) {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS MauSac, SIZE.Ten AS Size, "
                + "CHATLIEU.Ten AS ChatLieu, THUONGHIEU.Ten AS ThuongHieu, SANPHAMCHITIET.GiaBan, "
                + "SANPHAMCHITIET.SoLuongTon, SANPHAMCHITIET.MoTa "
                + "FROM SANPHAMCHITIET "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID "
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID "
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID "
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID "
                + "WHERE THUONGHIEU.ID = ?";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, thuonghieuID);
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
            e.printStackTrace();
            return null;
        }
    }

    public String getNewSPCTID() {
        String newID;
        boolean unique = false;
        do {
            newID = "SPCT" + UUID.randomUUID().toString().substring(0, 8); // Tạo UUID và rút ngắn
            unique = !checkTrungId(newID); // Kiểm tra xem ID đã tồn tại hay chưa
        } while (!unique); // Tiếp tục cho đến khi tìm được ID không trùng lặp
        return newID;
    }

    public int insert(ProductDetailModel ctsp) {
        sql = "INSERT INTO SANPHAMCHITIET(ID, ID_SanPham, ID_MauSac, ID_Size, ID_ChatLieu, ID_ThuongHieu, GiaBan, SoLuongTon, MoTa, NgayTao, NgaySua, TrangThai)\r\n"
                + //
                "VALUES (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, N'Còn hàng')";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getID());
            ps.setObject(2, ctsp.getTenSP().getID());
            ps.setObject(3, ctsp.getMauSac().getID());
            ps.setObject(4, ctsp.getKichCo().getID());
            ps.setObject(5, ctsp.getChatLieu().getID());
            ps.setObject(6, ctsp.getThuongHieu().getID());
            ps.setObject(7, ctsp.getGiaBan());
            ps.setObject(8, ctsp.getSoLuongTon());
            ps.setObject(9, ctsp.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkTrungId(String id) {
        sql = "SELECT COUNT(*) AS count FROM SANPHAMCHITIET WHERE ID = ?";
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

    public boolean checkTrungIdCTSP(String maSPM) {
        String sql = "SELECT ID FROM SANPHAMCHITIET WHERE ID = ?";

        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSPM);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu có kết quả trả về => Mã SP đã tồn tại
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Không tìm thấy mã SP trùng
        return false;
    }

    public boolean checkTrungTenSanPham(String tenSanPham) {
        String sql = "SELECT COUNT(*) AS count FROM SANPHAM WHERE TenSanPham = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenSanPham);
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

    public boolean checkTrungMauSac(String tenMauSac) {
        String sql = "SELECT COUNT(*) AS count FROM MAUSAC WHERE TenMau = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenMauSac);
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

    public boolean checkTrungChatLieu(String tenChatLieu) {
        String sql = "SELECT COUNT(*) AS count FROM CHATLIEU WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenChatLieu);
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

    public boolean checkTrungKichCo(String tenKichCo) {
        String sql = "SELECT COUNT(*) AS count FROM SIZE WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenKichCo);
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

    public boolean checkTrungThuongHieu(String tenThuongHieu) {
        String sql = "SELECT COUNT(*) AS count FROM THUONGHIEU WHERE Ten = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, tenThuongHieu);
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

    public int update(ProductDetailModel ctsp) {
        sql = "UPDATE SANPHAMCHITIET SET ID_SanPham=?, ID_MauSac=?, ID_Size=?, ID_ChatLieu=?, ID_ThuongHieu=?, GiaBan=?, SoLuongTon=?, MoTa=?, NgaySua = CURRENT_TIMESTAMP WHERE ID=?";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getTenSP().getID());
            ps.setObject(2, ctsp.getMauSac().getID());
            ps.setObject(3, ctsp.getKichCo().getID());
            ps.setObject(4, ctsp.getChatLieu().getID());
            ps.setObject(5, ctsp.getThuongHieu().getID());
            ps.setObject(6, ctsp.getGiaBan());
            ps.setObject(7, ctsp.getSoLuongTon());
            ps.setObject(8, ctsp.getMoTa());
            ps.setObject(9, ctsp.getID());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String id) {
        String sql = "DELETE FROM SANPHAMCHITIET WHERE ID = ?";
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

    public boolean checkTonTaiHDCT(String idSPCT) {
        sql = "SELECT COUNT(*) FROM HOADONCHITIET WHERE ID_SanPhamChiTiet = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idSPCT);
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
