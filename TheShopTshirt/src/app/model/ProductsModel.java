/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;


public class ProductsModel {
    public String ID;
    public String tenSP;
    public String MoTa;
    public String TrangThai;
    private int stt;

    public ProductsModel() {
    }

    public ProductsModel(String ID, String tenSP, String MoTa) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.MoTa = MoTa;
    }

    public ProductsModel(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getID() {
        return ID;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getMoTa() {
        return MoTa;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public Object[] toData() {
        return new Object[] {
                this.stt,
                this.ID,
                this.tenSP,
                this.MoTa
        };
    }


    
}
