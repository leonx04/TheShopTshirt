/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.StaffModel;
import app.service.StaffService;
import app.tabbed.TabbedForm;
import app.tabbed.WindowsTabbed;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class Staff extends TabbedForm {

    @Override
    public void fromRefresh() {
        // Tải lại dữ liệu cho form 
        initTable();
        loadData();
        rdoNV.setSelected(true);
        rdoNam.setSelected(true);
    }

    private final StaffService staffService = new StaffService();
    ;
    private DefaultTableModel tableModel;

    public Staff() {
        initComponents();
        initTable();
        loadData();
        rdoNV.setSelected(true);
        rdoNam.setSelected(true);
    }

    private void initTable() {
        tableModel = (DefaultTableModel) tblNhanVien.getModel();
        String[] columnNames = {"STT", "Mã Nhân Viên", "Họ Tên", "Địa Chỉ", "SĐT", "Email", "Năm Sinh", "Giới Tính", "Chức Vụ", "Mật Khẩu", "Trạng Thái"};
        tableModel.setColumnIdentifiers(columnNames);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<StaffModel> staffList = staffService.getAllStaff();
        for (int i = 0; i < staffList.size(); i++) {
            StaffModel staff = staffList.get(i);
            staff.setStt(i + 1);

            // Chuyển đổi giá trị ChucVu từ boolean sang "Quản lý" hoặc "Nhân viên"
            String chucVu = staff.isChucVu() ? "Quản lý" : "Nhân viên";

            tableModel.addRow(new Object[]{
                staff.getStt(),
                staff.getId(),
                staff.getHoTen(),
                staff.getDiaChi(),
                staff.getSdt(),
                staff.getEmail(),
                staff.getNamSinh(),
                staff.getGioiTinh(),
                chucVu,
                staff.getMatKhau(),
                staff.getTrangThai()
            });
        }
    }

    private void updateTable(List<StaffModel> staffList) {
        tableModel.setRowCount(0);
        for (int i = 0; i < staffList.size(); i++) {
            StaffModel staff = staffList.get(i);
            staff.setStt(i + 1);
            tableModel.addRow(staff.toData());
        }
    }

    StaffModel getStaffFromInput() {
        StaffModel staff = new StaffModel();
        staff.setId(txtID2.getText());
        staff.setHoTen(txtHoTen2.getText().trim());
        staff.setDiaChi(txtDiaChi2.getText().trim());
        staff.setSdt(txtSDT2.getText().trim());
        staff.setEmail(txtEmail2.getText().trim());
        staff.setNamSinh(Integer.parseInt(txtNamSinh2.getText().trim()));
        staff.setGioiTinh(rdoNam.isSelected() ? "Nam" : "Nữ");
        staff.setChucVu(rdoQL.isSelected());
        staff.setMatKhau(new String(txtMatKhau.getPassword()));
        staff.setTrangThai("Đang làm việc");

        return staff;
    }

    private void clearForm() {
        txtID2.setText("");
        txtHoTen2.setText("");
        txtDiaChi2.setText("");
        txtSDT2.setText("");
        txtEmail2.setText("");
        txtNamSinh2.setText("");
        txtMatKhau.setText("");
        loadData();
    }

    private boolean validateInput() {
        String id = txtID2.getText().trim();
        String hoTen = txtHoTen2.getText().trim();
        String email = txtEmail2.getText().trim();
        String sdt = txtSDT2.getText().trim();
        String diaChi = txtDiaChi2.getText().trim();
        String namSinhStr = txtNamSinh2.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());

        // Kiểm tra các trường bắt buộc
        if (hoTen.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập họ tên nhân viên");
            txtHoTen2.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập email nhân viên");
            txtEmail2.requestFocus();
            return false;
        }
        if (sdt.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập số điện thoại nhân viên");
            txtSDT2.requestFocus();
            return false;
        }
        if (diaChi.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập địa chỉ nhân viên");
            txtDiaChi2.requestFocus();
            return false;
        }
        if (namSinhStr.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập năm sinh nhân viên");
            txtNamSinh2.requestFocus();
            return false;
        }
        if (matKhau.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập mật khẩu nhân viên");
            txtMatKhau.requestFocus();
            return false;
        }

        // Kiểm tra độ dài
        if (id.length() > 200 || hoTen.length() > 200 || email.length() > 200 || diaChi.length() > 200 || namSinhStr.length() > 200 || matKhau.length() > 200) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Các trường thông tin không được vượt quá 200 ký tự");
            return false;
        }

        // Kiểm tra năm sinh hợp lệ
        int namSinh;
        try {
            namSinh = Integer.parseInt(namSinhStr);
            int namHienTai = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (namSinh > namHienTai) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Năm sinh không được sau năm hiện tại");
                txtNamSinh2.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Năm sinh không hợp lệ");
            txtNamSinh2.requestFocus();
            return false;
        }

        // Kiểm tra email hợp lệ
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Email không hợp lệ");
            txtEmail2.requestFocus();
            return false;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!sdt.matches("^\\d{10}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số điện thoại không hợp lệ, phải là 10 số");
            txtSDT2.requestFocus();
            return false;
        }

        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Dữ liệu hợp lệ");
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtID2 = new javax.swing.JTextField();
        txtHoTen2 = new javax.swing.JTextField();
        txtNamSinh2 = new javax.swing.JTextField();
        txtDiaChi2 = new javax.swing.JTextField();
        txtSDT2 = new javax.swing.JTextField();
        txtEmail2 = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtMatKhau = new javax.swing.JPasswordField();
        rdoNV = new javax.swing.JRadioButton();
        rdoQL = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnNghiViec = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDiLam = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        cboTrangthai = new javax.swing.JComboBox<>();

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Mã Nhân Viên");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Họ Tên:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Giới Tính:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Năm Sinh:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Địa Chỉ");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("SĐT:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Mật Khẩu:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Chức Vụ:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Email:");

        txtID2.setEditable(false);
        txtID2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID2ActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        buttonGroup2.add(rdoNV);
        rdoNV.setText("Nhân Viên");

        buttonGroup2.add(rdoQL);
        rdoQL.setText("Quản Lý");

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThem.setBackground(new java.awt.Color(102, 153, 255));
        btnThem.setText("Thêm Nhân Viên");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(102, 153, 255));
        btnSua.setText("Sửa Nhân Viên");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(102, 153, 255));
        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnNghiViec.setBackground(new java.awt.Color(102, 153, 255));
        btnNghiViec.setText("Nghỉ Việc");
        btnNghiViec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNghiViecActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 153, 255));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setText("Xóa nhân viên");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDiLam.setBackground(new java.awt.Color(102, 153, 255));
        btnDiLam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDiLam.setText("Đi làm");
        btnDiLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiLamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDiLam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnNghiViec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))))
                .addGap(46, 46, 46))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNghiViec, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDiLam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(txtHoTen2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoNam)
                        .addGap(116, 116, 116)
                        .addComponent(rdoNu)
                        .addGap(0, 138, Short.MAX_VALUE))
                    .addComponent(txtDiaChi2)
                    .addComponent(txtNamSinh2))
                .addGap(68, 68, 68)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addComponent(txtEmail2)
                        .addComponent(txtMatKhau))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoQL)))
                .addGap(135, 135, 135)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtSDT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtHoTen2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu)
                    .addComponent(txtEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtNamSinh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(rdoNV)
                    .addComponent(rdoQL))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtDiaChi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("Danh Sách Nhân Viên:");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Nhân Viên", "Họ Tên", "Địa Chỉ", "SĐT", "Email", "Năm Sinh", "Giới Tính", "Chức Vụ", "Mật Khẩu", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("Trạng Thái:");

        cboTrangthai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang làm việc", "Nghỉ việc" }));
        cboTrangthai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangthaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(245, 245, 245)
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(cboTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(cboTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateInput()) {
            // Kiểm tra ID đã tồn tại
            if (staffService.checkTrungMa(txtID2.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Mã Nhân Viên đã tồn tại");
                txtID2.requestFocus();
                return;
            }

            // Kiểm tra email đã tồn tại
            if (staffService.checkTrungEmail(txtEmail2.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Email đã tồn tại");
                txtEmail2.requestFocus();
                return;
            }

            // Kiểm tra số điện thoại đã tồn tại
            if (staffService.checkTrungSDT(txtSDT2.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Số điện thoại đã tồn tại");
                txtSDT2.requestFocus();
                return;
            }
            String newID = staffService.getNewStaffID();
            StaffModel staff = this.getStaffFromInput();
            staff.setId(newID);
            System.out.println("tên nhân viên " + staff.getHoTen());

            MessageAlerts.getInstance().showMessage("Xác nhận thêm nhân viên",
                    "Bạn có chắc muốn thêm nhân viên này?",
                    MessageAlerts.MessageType.SUCCESS,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (staffService.insert(staff) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm nhân viên thành công");

                            loadData(); // Cập nhật lại bảng dữ liệu
                            clearForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm nhân viên thất bại");
                        }
                    }
                }
            });
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateInput()) {
            StaffModel staff = getStaffFromInput();
            String id = txtID2.getText();

            MessageAlerts.getInstance().showMessage("Xác nhận cập nhật nhân viên",
                    "Bạn có chắc muốn cập nhật thông tin nhân viên này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (staffService.update(staff, id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật nhân viên thành công!");
                                loadData();
                                clearForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật nhân viên thất bại!");
                            }
                        }
                    });
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnNghiViecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNghiViecActionPerformed
        String id = txtID2.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận nghỉ việc nhân viên",
                    "Bạn có chắc muốn cho nhân viên này nghỉ việc?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (staffService.updateTrangThai(id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã cập nhật trạng thái nhân viên thành nghỉ việc!");
                                loadData();
                                clearForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật trạng thái thất bại!");
                            }
                        }
                    });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn nhân viên cần cập nhật trạng thái!");
        }
    }//GEN-LAST:event_btnNghiViecActionPerformed

    private void tblNhanVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMousePressed

    }//GEN-LAST:event_tblNhanVienMousePressed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int row = tblNhanVien.getSelectedRow();
        if (row >= 0 && evt.getClickCount() == 1) {
            txtID2.setText(tblNhanVien.getValueAt(row, 1).toString().trim());
            txtHoTen2.setText(tblNhanVien.getValueAt(row, 2).toString().trim());
            txtDiaChi2.setText(tblNhanVien.getValueAt(row, 3).toString().trim());
            txtSDT2.setText(tblNhanVien.getValueAt(row, 4).toString().trim());
            txtEmail2.setText(tblNhanVien.getValueAt(row, 5).toString().trim());
            txtNamSinh2.setText(tblNhanVien.getValueAt(row, 6).toString().trim());
            txtMatKhau.setText(tblNhanVien.getValueAt(row, 9).toString().trim());

            String gioiTinh = tblNhanVien.getValueAt(row, 7).toString().trim();
            if (gioiTinh.equalsIgnoreCase("Nam")) {
                rdoNam.setSelected(true);
            } else {
                rdoNu.setSelected(true);
            }

            String chucVu = tblNhanVien.getValueAt(row, 8).toString().trim();
            if (chucVu.equals("Quản lý")) {
                rdoQL.setSelected(true);
            } else {
                rdoNV.setSelected(true);
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<StaffModel> searchResult = staffService.searchStaff(keyword);
        if (searchResult.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Không tìm thấy nhân viên phù hợp");
            // Nếu không tìm thấy, giữ nguyên dữ liệu hiện tại
        } else {
            updateTable(searchResult);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String id = txtID2.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận xóa nhân viên",
                    "Bạn có chắc muốn xóa nhân viên này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (staffService.delete(id) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa nhân viên thành công");
                            loadData();
                            clearForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Xóa nhân viên thất bại");
                        }
                    }
                }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn nhân viên cần xóa!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDiLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiLamActionPerformed
        String id = txtID2.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận khôi phục trạng thái",
                    "Bạn có chắc muốn khôi phục trạng thái của nhân viên này thành 'Đang làm việc'?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (staffService.updateActiveTrangThai(id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã cập nhật trạng thái nhân viên thành 'Đang làm việc'!");
                                loadData();
                                clearForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật trạng thái thất bại!");
                            }
                        }
                    });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn nhân viên cần cập nhật trạng thái!");
        }
    }//GEN-LAST:event_btnDiLamActionPerformed

    private void cboTrangthaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangthaiActionPerformed
        String selectedStatus = cboTrangthai.getSelectedItem().toString();
        List<StaffModel> filteredStaff = staffService.getStaffByStatus(selectedStatus);
        updateTable(filteredStaff);
    }//GEN-LAST:event_cboTrangthaiActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtID2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID2ActionPerformed

    @Override
    public boolean formClose() {
        if (!txtID2.getText().trim().isEmpty()
                || !txtHoTen2.getText().trim().isEmpty()
                || !txtDiaChi2.getText().trim().isEmpty()
                || !txtEmail2.getText().trim().isEmpty()
                || !txtMatKhau.getText().trim().isEmpty()
                || !txtSDT2.getText().trim().isEmpty()
                || !txtNamSinh2.getText().trim().isEmpty()
                || !txtTimKiem.getText().trim().isEmpty()) {

            int opt = JOptionPane.showConfirmDialog(this, "Dữ liệu chưa được lưu, bạn có chắc chắn muốn đóng tab ? ", "Close", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);

            return opt == JOptionPane.YES_OPTION;
        }

        // Nếu tất cả các trường đều rỗng, cho phép đóng mà không cần xác nhận
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDiLam;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnNghiViec;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboTrangthai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQL;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi2;
    private javax.swing.JTextField txtEmail2;
    private javax.swing.JTextField txtHoTen2;
    private javax.swing.JTextField txtID2;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNamSinh2;
    private javax.swing.JTextField txtSDT2;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
