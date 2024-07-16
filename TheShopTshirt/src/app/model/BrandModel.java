/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author dungn
 */
public class BrandModel {
    public String ID;
    public String TenTH;
    public String MoTa;
    public int stt;

    public BrandModel() {
    }

    public BrandModel(String ID, String TenTH, String MoTa) {
        this.ID = ID;
        this.TenTH = TenTH;
        this.MoTa = MoTa;
    }

    public BrandModel(String thuonghieu) {
        this.TenTH = thuonghieu;
    }

    public String getID() {
        return ID;
    }

    public String getTenTH() {
        return TenTH;
    }

    public String getMoTa() {
        return MoTa;
    }

    public int getStt() {
        return stt;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenTH(String TenTH) {
        this.TenTH = TenTH;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.ID,
            this.TenTH,
            this.MoTa
        };
    }
}
