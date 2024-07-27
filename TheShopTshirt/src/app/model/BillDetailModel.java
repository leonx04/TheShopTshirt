/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class BillDetailModel {

    public String ID;
    public ProductDetailModel mactsp = new ProductDetailModel();
    public ProductsModel tenSP = new ProductsModel();
    public ColorModel mauSac = new ColorModel();
    public SizeModel size = new SizeModel();
    public MaterialModel chatLieu = new MaterialModel();
    public BrandModel thuongHieu = new BrandModel();
    public ProductDetailModel ctsp = new ProductDetailModel(BigDecimal.ONE);
    public int soLuong;
    public BigDecimal thanhTien;
    public ProductsModel maSP = new ProductsModel();
    private int stt;

    public BillDetailModel() {
    }

    public BillDetailModel(String ID,
            ProductsModel tenSP,
            ColorModel mauSacModel,
            SizeModel kichCoModel,
            MaterialModel chatLieuModel,
            BrandModel thuongHieuModel,
            ProductDetailModel donGia,
            int soLuong,
            BigDecimal thanhTien) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.mauSac = mauSacModel;
        this.size = kichCoModel;
        this.chatLieu = chatLieuModel;
        this.thuongHieu = thuongHieuModel;
        this.ctsp = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.maSP = maSP;
    }

    public Object[] toData2() {
        return new Object[]{
            this.stt,
            this.ID,
            this.tenSP.getTenSP(),
            this.mauSac.getTenMS(),
            this.size.getTenSize(),
            this.chatLieu.getTenCL(),
            this.thuongHieu.getTenTH(),
            this.ctsp.getGiaBan(),
            this.soLuong,
            this.thanhTien
        };
    }

    public BillDetailModel(ProductDetailModel mactsp, ProductsModel tenSP, ProductDetailModel donGia, int soLuong, BigDecimal thanhTien) {
        this.mactsp = mactsp;
        this.tenSP = tenSP;
        this.ctsp = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public Object[] toData4() {
        return new Object[]{
            this.mactsp.getID(),
            this.tenSP.getTenSP(),
            this.ctsp.getGiaBan(),
            this.soLuong,
            this.thanhTien
        };
    }

    public String getID() {
        return ID;
    }

    public ProductDetailModel getMactsp() {
        return mactsp;
    }

    public ProductsModel getTenSP() {
        return tenSP;
    }

    public ColorModel getMauSac() {
        return mauSac;
    }

    public SizeModel getSize() {
        return size;
    }

    public MaterialModel getChatLieu() {
        return chatLieu;
    }

    public BrandModel getThuongHieu() {
        return thuongHieu;
    }

    public ProductDetailModel getCtsp() {
        return ctsp;
    }
    
    public void setDonGia(ProductDetailModel donGia) {
        this.ctsp = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public ProductsModel getMaSP() {
        return maSP;
    }
    
    public ProductDetailModel getDonGia() {
        return ctsp;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMactsp(ProductDetailModel mactsp) {
        this.mactsp = mactsp;
    }

    public void setTenSP(ProductsModel tenSP) {
        this.tenSP = tenSP;
    }

    public void setMauSac(ColorModel mauSac) {
        this.mauSac = mauSac;
    }

    public void setSize(SizeModel size) {
        this.size = size;
    }

    public void setChatLieu(MaterialModel chatLieu) {
        this.chatLieu = chatLieu;
    }

    public void setThuongHieu(BrandModel thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public void setCtsp(ProductDetailModel ctsp) {
        this.ctsp = ctsp;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public void setMaSP(ProductsModel maSP) {
        this.maSP = maSP;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    @Override
    public String toString() {
        return "BillDetailModel{" + "ID=" + ID + ", mactsp=" + mactsp + ", tenSP=" + tenSP + ", mauSac=" + mauSac + ", size=" + size + ", chatLieu=" + chatLieu + ", thuongHieu=" + thuongHieu + ", ctsp=" + ctsp + ", soLuong=" + soLuong + ", thanhTien=" + thanhTien + ", maSP=" + maSP + ", stt=" + stt + '}';
    }

}
