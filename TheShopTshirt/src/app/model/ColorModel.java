/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author dungn
 */
public class ColorModel {
    public String ID;
    public String TenMS;
    public String MoTa;
    public int stt;

    public ColorModel(String mausac) {
        this.TenMS = mausac;
    }

    public ColorModel() {
    }

    public ColorModel(String ID, String TenMS, String MoTa) {
        this.ID = ID;
        this.TenMS = TenMS;
        this.MoTa = MoTa;
    }

    public String getID() {
        return ID;
    }

    public String getTenMS() {
        return TenMS;
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

    public void setTenMS(String TenMS) {
        this.TenMS = TenMS;
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
            this.TenMS,
            this.MoTa
        };
    }

    @Override
    public String toString() {
        return TenMS;
    }
}
