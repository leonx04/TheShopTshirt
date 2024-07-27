/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author ADMIN
 */
public class CustomerModel {
    private String id ;
    private String ten;
    private String sdt;
    private String diachi;
    private String email;
    private String gioiTinh;
    private int stt;

    public CustomerModel(String id, String ten, String sdt, String diachi, String email, String gioiTinh, int stt) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.stt = stt;
    }

    public CustomerModel() {
    }

    public CustomerModel(String tenKH) {
        this.ten = tenKH;
    }

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getEmail() {
        return email;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public int getStt() {
        return stt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
    
    public Object[] toData() {
        return new Object[]{
            this.stt,
            this.id,
            this.ten,
            this.sdt,
            this.diachi,
            this.email,
            this.gioiTinh
        };
    }
    
}
