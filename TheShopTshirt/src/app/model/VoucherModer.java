/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class VoucherModer {
     public String ID;
    public String tenVoucher;
    public Integer soLuong;
    public String loaiVoucher;
    public BigDecimal mucGiamGia;
    public String moTa;
    public Date ngayBatDau;
    public Date ngayKetThuc;
    public String trangThai;
    private int STT;

    public VoucherModer(String ID, String tenVoucher, Integer soLuong, String loaiVoucher, BigDecimal mucGiamGia, String moTa, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.ID = ID;
        this.tenVoucher = tenVoucher;
        this.soLuong = soLuong;
        this.loaiVoucher = loaiVoucher;
        this.mucGiamGia = mucGiamGia;
        this.moTa = moTa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        
    }

    public VoucherModer() {
    }

    public VoucherModer(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getID() {
        return ID;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public String getLoaiVoucher() {
        return loaiVoucher;
    }

    public BigDecimal getMucGiamGia() {
        return mucGiamGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public int getSTT() {
        return STT;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public void setLoaiVoucher(String loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public void setMucGiamGia(BigDecimal mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }
    
    public Object[] toData() {
        return new Object[]{
            this.STT,
            this.ID,
            this.tenVoucher,
            this.soLuong,
            this.loaiVoucher,
            this.mucGiamGia.stripTrailingZeros().toPlainString(),
            this.moTa,
            this.ngayBatDau,
            this.ngayKetThuc,
            this.trangThai
        };
    }

    

    
}
