/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class BillModel {
    public String ID;
    public Date ngayTao;
    public StaffModel tenNV = new StaffModel();
    public CustomerModel tenKH = new CustomerModel();
    public VoucherModer tenVoucher = new VoucherModer();
    public BigDecimal tongTien;
    public String hinhThucThanhToan;
    public String trangThai;
    private int stt;

    public BillModel(String ID, Date ngayTao, BigDecimal tongTien, String hinhThucThanhToan, String trangThai, int stt) {
        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.trangThai = trangThai;
        this.stt = stt;
    }

    public BillModel() {
    }

    public BillModel(String ID, Date ngayTao, StaffModel tenNV, CustomerModel tenKH, BigDecimal tongTien, VoucherModer tenVoucher, String hinhThucThanhToan, String trangThai) {
        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tenVoucher = tenVoucher;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.trangThai = trangThai;
    }

    public BillModel(String ID, Date ngayTao, StaffModel tenNV, CustomerModel tenKH, VoucherModer tenVoucher, BigDecimal tongTien, String hinhThucThanhToan) {
        this.ID = ID;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.tenKH = tenKH;
        this.tenVoucher = tenVoucher;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getID() {
        return ID;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public StaffModel getTenNV() {
        return tenNV;
    }

    public CustomerModel getTenKH() {
        return tenKH;
    }

    public VoucherModer getTenVoucher() {
        return tenVoucher;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setTenNV(StaffModel tenNV) {
        this.tenNV = tenNV;
    }

    public void setTenKH(CustomerModel tenKH) {
        this.tenKH = tenKH;
    }

    public void setTenVoucher(VoucherModer tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    
    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.ID,
            this.ngayTao,
            this.tenNV.getHoTen(),
            this.tenKH.getTen(),
            this.tenVoucher.getTenVoucher(),
            this.tongTien,
            this.hinhThucThanhToan};

    }

    public Object[] toData2() {
        return new Object[]{
            this.stt,
            this.ID,
            this.ngayTao,
            this.tenNV.getHoTen(),
            this.tenKH.getTen(),
            this.tenVoucher.getTenVoucher(),
            this.tongTien,
            this.hinhThucThanhToan,
            this.trangThai
        };
    }
}
