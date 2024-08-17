/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.BillModel;
import app.model.CustomerModel;
import app.model.StaffModel;
import app.service.CustomerService;
import app.tabbed.TabbedForm;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class Customer extends TabbedForm {

    private CustomerService customerService;
    private DefaultTableModel dtm;

    /**
     * Creates new form Customer
     */
    public Customer() {
        initComponents();
        dtm = new DefaultTableModel();
        customerService = new CustomerService();
        initTable();
        FillTable();
    }

    private void initTable() {
        dtm = (DefaultTableModel) tblKachHang.getModel();
        String[] columnNames = {"STT", "ID", "Tên Khách Hàng", "Số Điện Thoại", "Giới Tính", "Email", "Địa Chỉ"};
        dtm.setColumnIdentifiers(columnNames);
    }

    private void FillTable() {
        dtm.setRowCount(0);
        List<CustomerModel> customerModel = customerService.getAllCustomer();
        for (int i = 0; i < customerModel.size(); i++) {
            CustomerModel cus = customerModel.get(i);
            cus.setStt(i + 1);

            dtm.addRow(new Object[]{
                cus.getStt(),
                cus.getId(),
                cus.getTen(),
                cus.getSdt(),
                cus.getGioiTinh(),
                cus.getEmail(),
                cus.getDiachi()
            });
        }

    }

    private boolean validateInput() {
        String id = txtID.getText().trim();
        String hoTen = txtHHoTen.getText().trim();
        String email = txtEmail.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        // Kiểm tra các trường bắt buộc
        if (hoTen.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập họ tên Khách Hàng");
            txtHHoTen.requestFocus();
            return false;
        }
        if (diaChi.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập địa chỉ Khách Hàng");
            txtDiaChi.requestFocus();
            return false;
        }
        if (sdt.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập số điện thoại Khách Hàng");
            txtSDT.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập email Khách Hàng");
            txtEmail.requestFocus();
            return false;
        }

        // Kiểm tra độ dài
        if (id.length() > 200 || hoTen.length() > 200 || email.length() > 200 || diaChi.length() > 200) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Các trường thông tin không được vượt quá 200 ký tự");
            return false;
        }
        if (!sdt.matches("^\\d{10}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số điện thoại không hợp lệ, phải là 10 số");
            txtSDT.requestFocus();
            return false;
        }

        // Kiểm tra email hợp lệ
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Email không hợp lệ");
            txtEmail.requestFocus();
            return false;
        }

        // Kiểm tra số điện thoại hợp lệ
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Dữ liệu hợp lệ");
        return true;
    }

    CustomerModel getCustomerFromInput() {
        CustomerModel custm = new CustomerModel();
        custm.setId(txtID.getText());
        custm.setTen(txtHHoTen.getText().trim());
        custm.setDiachi(txtDiaChi.getText().trim());
        custm.setSdt(txtSDT.getText().trim());
        custm.setEmail(txtEmail.getText().trim());
        custm.setGioiTinh(rdNam.isSelected() ? "Nam" : "Nữ");
        String TrangThai = "Hoạt Động";
        custm.setTrangThai(TrangThai);
        return custm;
    }

    private void clearForm() {
        txtID.setText("");
        txtEmail.setText("");
        txtHHoTen.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");

    }

    private void updateTable(List<CustomerModel> customerModel) {
        dtm.setRowCount(0);
        for (int i = 0; i < customerModel.size(); i++) {
            CustomerModel customerModell = customerModel.get(i);
            customerModell.setStt(i + 1);
            dtm.addRow(customerModell.toData());
        }
    }

    private void showHoaDonForSelectedKhachHang() {
        int selectedRow = tblKachHang.getSelectedRow();
        if (selectedRow >= 0) {
            String idKhachHang = (String) tblKachHang.getValueAt(selectedRow, 1);
            List<BillModel> hoaDons = customerService.getHoaDonByIdKhachHang(idKhachHang);
            fillTableHoaDon(hoaDons);
        }
    }

    private void fillTableHoaDon(List<BillModel> listHD) {
        DefaultTableModel model = (DefaultTableModel) tblLichSu.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (BillModel hd : listHD) {
            Object[] row = {
                stt++,
                hd.getID(),
                hd.getNgayTao(),
                hd.getTenNV().getHoTen(),
                hd.getTenKH().getTen(),
                hd.getTenVoucher().getTenVoucher(),
                hd.getTongTien(),
                hd.getHinhThucThanhToan(),
                hd.getTrangThai()
            };
            model.addRow(row);
        }

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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        txtID = new javax.swing.JTextField();
        txtHHoTen = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKachHang = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLichSu = new javax.swing.JTable();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("ID");

        jLabel2.setText("Họ Tên");

        jLabel3.setText("Giới Tính");

        jLabel4.setText("Địa Chỉ");

        jLabel5.setText("Số Điện Thoại");

        jLabel6.setText("Email");

        buttonGroup1.add(rdNam);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        txtID.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel9.setText("Thông Tin Khách Hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdNam)
                                        .addGap(29, 29, 29)
                                        .addComponent(rdNu))
                                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                                    .addComponent(txtHHoTen))))
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChi)
                            .addComponent(txtSDT)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtHHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(rdNam)
                    .addComponent(rdNu)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 1650, 320));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setBackground(new java.awt.Color(0, 102, 255));
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 102, 255));
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 255));
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 102, 255));
        jButton4.setText("Làm Mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setText("Tương Tác");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(39, 39, 39)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(34, 34, 34)
                .addComponent(jButton4)
                .addGap(28, 28, 28))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1710, 70, 180, 260));

        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel10.setText("Danh Sách Khách Hàng");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        tblKachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Id", "Họ Tên", "Số Điện Thoại", "Giới Tính", "Email", "Địa Chỉ"
            }
        ));
        tblKachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKachHang);

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel7.setText("Tim Kiếm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(607, 607, 607)
                .addComponent(jLabel7)
                .addGap(26, 26, 26)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(848, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách Khách Hàng", jPanel4);

        tblLichSu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Tên Nhân Viên", "tên Khách Hàng", "Tên Voucher", "Tổng Tiền", "Hình Thức Thanh Toán", "Trạng Thái"
            }
        ));
        jScrollPane2.setViewportView(tblLichSu);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1874, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lịch Sử Giao Dịch", jPanel5);

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 1880, 560));
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (validateInput()) {
            // Kiểm tra ID đã tồn tại
            if (customerService.checkTrungID(txtID.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Id Khách Hàng đã tồn tại");
                txtID.requestFocus();
                return;
            }

            // Kiểm tra email đã tồn tại
            if (customerService.checkTrungEmail(txtEmail.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Email đã tồn tại");
                txtEmail.requestFocus();
                return;
            }

            // Kiểm tra số điện thoại đã tồn tại
            if (customerService.checkTrungSDT(txtSDT.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Số điện thoại đã tồn tại");
                txtSDT.requestFocus();
                return;
            }
            String newID = customerService.getNewCustomerID();
            CustomerModel customerModel = this.getCustomerFromInput();
            customerModel.setId(newID);
            MessageAlerts.getInstance().showMessage("Xác nhận thêm Khách Hàng",
                    "Bạn có chắc muốn thêm Khách Hàng này?",
                    MessageAlerts.MessageType.SUCCESS,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (customerService.insert(customerModel) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm Khách Hàng thành công");

                            FillTable(); // Cập nhật lại bảng dữ liệu
                            clearForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm Khách Hàng thất bại");
                        }
                    }
                }
            });
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (validateInput()) {
            CustomerModel cusmer = getCustomerFromInput();
            String id = txtID.getText();

            MessageAlerts.getInstance().showMessage("Xác nhận cập nhật Khách Hàng",
                    "Bạn có chắc muốn cập nhật thông tin Khách Hàng này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (customerService.update(cusmer, id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật Khách Hàng thành công!");
                                FillTable();
                                clearForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật Khách Hàng thất bại!");
                            }
                        }
                    });
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblKachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKachHangMouseClicked
        int row = tblKachHang.getSelectedRow();
        if (row >= 0 && evt.getClickCount() == 1) {
            txtID.setText(tblKachHang.getValueAt(row, 1).toString().trim());
            txtHHoTen.setText(tblKachHang.getValueAt(row, 2).toString().trim());
            txtSDT.setText(tblKachHang.getValueAt(row, 3).toString().trim());
            String gioiTinh = tblKachHang.getValueAt(row, 4).toString().trim();
            if (gioiTinh.equalsIgnoreCase("Nam")) {
                rdNam.setSelected(true);
            } else {
                rdNu.setSelected(true);
            }
            txtEmail.setText(tblKachHang.getValueAt(row, 5).toString().trim());
            txtDiaChi.setText(tblKachHang.getValueAt(row, 6).toString().trim());
            
            

            showHoaDonForSelectedKhachHang();
        }

    }//GEN-LAST:event_tblKachHangMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton4ActionPerformed


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String id = txtID.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận xóa Khách Hàng",
                    "Bạn có chắc muốn xóa Khách Hàng này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (customerService.delete(id) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa Khách Hàng thành công");
                            FillTable();
                            clearForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Xóa Khách Hàng thất bại");
                        }
                    }
                }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn Khách Hàng cần xóa!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed

    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        List<CustomerModel> searchResult = customerService.searchCustomer(keyword);
        if (searchResult.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Không tìm thấy Khách Hàng phù hợp");
            // Nếu không tìm thấy, giữ nguyên dữ liệu hiện tại
        } else {
            updateTable(searchResult);
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JTable tblKachHang;
    private javax.swing.JTable tblLichSu;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHHoTen;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
