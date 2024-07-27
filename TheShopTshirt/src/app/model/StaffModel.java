/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class StaffModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private String id;
    private String hoTen;
    private String diaChi;
    private String sdt;
    private String email;
    private int namSinh;
    private String gioiTinh;
    private boolean chucVu;
    private String matKhau;
    private Date ngayTao;
    private Date ngaySua;
    private String trangThai;
    public int stt;

    public StaffModel() {
    }

    public StaffModel(String id, String hoTen, String diaChi, String sdt, String email, int namSinh, String gioiTinh, boolean chucVu, String matKhau, String trangThai) {
        this.id = id;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }

    public StaffModel(String tenNV) {
        this.hoTen = tenNV;
    }

    public String getId() {
        return id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public boolean isChucVu() {
        return chucVu;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public int getStt() {
        return stt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setChucVu(boolean chucVu) {
        this.chucVu = chucVu;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
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
            this.id,
            this.hoTen,
            this.diaChi,
            this.sdt,
            this.email,
            this.namSinh,
            this.gioiTinh,
            this.chucVu,
            this.matKhau,
            this.trangThai
        };
    }

}
