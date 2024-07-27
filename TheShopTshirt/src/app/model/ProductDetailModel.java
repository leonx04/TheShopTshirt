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
public class ProductDetailModel {
    public String ID;
    public ProductsModel tenSP = new ProductsModel();
    public ColorModel mauSac = new ColorModel();
    public SizeModel kichCo = new SizeModel();
    public MaterialModel chatLieu = new MaterialModel();
    public BrandModel thuongHieu = new BrandModel();
    public BigDecimal giaBan;
    public int SoLuongTon;
    public String MoTa;
    public int stt;

    public ProductDetailModel(String ID,
            ProductsModel tenSP,
            ColorModel mauSac,
            SizeModel kichCo,
            MaterialModel chatLieu,
            BrandModel thuongHieu,
            BigDecimal giaBan,
            int SoLuongTon,
            String MoTa) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.chatLieu = chatLieu;
        this.thuongHieu = thuongHieu;
        this.giaBan = giaBan;
        this.SoLuongTon = SoLuongTon;
        this.MoTa = MoTa;
    }

    public ProductDetailModel(BigDecimal donGia) {
        this.giaBan = donGia;
    }

    public ProductDetailModel() {
    }

    public ProductDetailModel(String ID) {
        this.ID = ID;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getID() {
        return ID;
    }

    public ProductsModel getTenSP() {
        return tenSP;
    }

    public ColorModel getMauSac() {
        return mauSac;
    }

    public SizeModel getKichCo() {
        return kichCo;
    }

    public MaterialModel getChatLieu() {
        return chatLieu;
    }

    public BrandModel getThuongHieu() {
        return thuongHieu;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenSP(ProductsModel tenSP) {
        this.tenSP = tenSP;
    }

    public void setMauSac(ColorModel mauSac) {
        this.mauSac = mauSac;
    }

    public void setKichCo(SizeModel kichCo) {
        this.kichCo = kichCo;
    }

    public void setChatLieu(MaterialModel chatLieu) {
        this.chatLieu = chatLieu;
    }

    public void setThuongHieu(BrandModel thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[]{this.stt, this.ID, this.tenSP.getTenSP(), this.mauSac.getTenMS(),
            this.kichCo.getTenSize(), this.chatLieu.getTenCL(), this.thuongHieu.getTenTH(), this.giaBan,
            this.SoLuongTon, this.MoTa};
    }

    public Object[] toData2() {
        return new Object[]{this.ID, this.tenSP.getTenSP(), this.mauSac.getTenMS(),
            this.kichCo.getTenSize(), this.chatLieu.getTenCL(), this.thuongHieu.getTenTH(), this.giaBan,
            this.SoLuongTon, this.MoTa};
    }
}
