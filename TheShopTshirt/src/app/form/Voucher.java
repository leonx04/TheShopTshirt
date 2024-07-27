/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.ProductDetailModel;
import app.model.StaffModel;
import app.model.VoucherModer;
import app.service.VoucherService;
import app.tabbed.TabbedForm;
import java.math.BigDecimal;
import java.util.Date;
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
public class Voucher extends TabbedForm {

    private DefaultTableModel dtmm;
    private final VoucherService sreService;
    private final int currentPage = 1;
    private static final int RECORDS_PER_PAGE = 10;

    /**
     * Creates new form Voucher
     */
    public Voucher() {
        initComponents();
        dtmm = new DefaultTableModel();
        sreService = new VoucherService();
        initTable();
        FillTable();
    }

    private void initTable() {
        dtmm = (DefaultTableModel) tblvoucher.getModel();
        String[] columnNames = {"STT", "ID", "tên Voucher", "Số Lượng", "Loại Voucher", "Mức giảm giá", "Ngày Bắt Đầu", "Ngày Kết Thúc", "mô tả", "Trạng Thái"};
        dtmm.setColumnIdentifiers(columnNames);
    }

    private void FillTable() {
        dtmm.setRowCount(0);
        List<VoucherModer> vcModers = sreService.getAllVoucher();
        for (int i = 0; i < vcModers.size(); i++) {
            VoucherModer vcc = vcModers.get(i);
            vcc.setSTT(i + 1);

            dtmm.addRow(new Object[]{
                vcc.getSTT(),
                vcc.getID(),
                vcc.getTenVoucher(),
                vcc.getSoLuong(),
                vcc.getLoaiVoucher(),
                vcc.getMucGiamGia().stripTrailingZeros().toPlainString(),
                vcc.getNgayBatDau(),
                vcc.getNgayKetThuc(),
                vcc.getMoTa(),
                vcc.getTrangThai()

            });

        }

    }

    public boolean ValidateVoucher() {
        String tenVC = txtTen.getText().trim();
        String soLuong = txtSoLuong.getText().trim();
        String mucGiamGia = txtMucGG.getText().trim();
        String moTa = taMota.getText().trim();
        String trangthai = comboxLoai.getSelectedItem().toString().trim();
        String loaiVoucher = comboxLoai.getSelectedItem().toString().trim();

        Date NgayHoatDong = JDBD.getDate();
        Date NgayKetThuc = jdKT.getDate();
        if (tenVC.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập tên voucher");
            return false;
        }
        if (tenVC.length() > 200 || soLuong.length() > 200 || mucGiamGia.length() > 200) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Các trường thông tin không được vượt quá 200 ký tự");
            return false;
        }
        if (moTa.length() > 250) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Mô Tả không được vượt quá 200 ký tự");
            return false;
        }

        try {
            BigDecimal giaTriPhanTram = new BigDecimal(mucGiamGia.trim());

            if (loaiVoucher.equals("Giảm theo phần trăm")) {
                if (giaTriPhanTram.compareTo(BigDecimal.valueOf(100)) > 0) {

                    Notifications.getInstance().show(Notifications.Type.WARNING, "Giảm Theo Phần trăm không được vượt quá 100");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Vui lòng nhập một số hợp lệ cho phần trăm giảm giá.");
            return false;
        }

        try {

            if (loaiVoucher.equals("Giảm theo giá tiền")) {
                if (mucGiamGia.length() > 38) {

                    Notifications.getInstance().show(Notifications.Type.WARNING, "Giảm Theo Giá tiền không được vượt quá 38 số");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Vui lòng nhập một số hợp lệ cho giảm giá.");
            return false;
        }

        if (trangthai.equals("Tất cả")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng Chọn Trạng Thái");
            return false;
        }
        try {
            if (soLuong.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Số Lượng");
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, " so luong phai Là Số");
            return false;
        }

        try {
            if (mucGiamGia.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Muc giam gia");
                return false;
            }

        } catch (NumberFormatException e) {

            Notifications.getInstance().show(Notifications.Type.WARNING, " Muc Giam gia phai la so");
            return false;
        }

        if (moTa.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Mô tả");
            return false;
        }

        if (NgayHoatDong != null && NgayKetThuc != null) {
            if (NgayKetThuc.before(NgayHoatDong)) {

                Notifications.getInstance().show(Notifications.Type.WARNING, " Ngày Kết Thúc không được Nhỏ Hơn hơn ngày Hoạt Động");
                return false;
            }
        } else {

            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn đầy đủ ngày hoạt động và ngày kết thúc.");
            return false;
        }

        if (NgayHoatDong != null && NgayKetThuc != null) {
            if (NgayHoatDong.after(NgayKetThuc)) {

                Notifications.getInstance().show(Notifications.Type.WARNING, " Ngày hoạt động không được lớn hơn ngày kết thúc");
                return false;
            }
        } else {

            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn đầy đủ ngày hoạt động và ngày kết thúc.");
            return false;
        }

        Notifications.getInstance().show(Notifications.Type.WARNING, "du lieu hop le");
        return true;

    }

    VoucherModer getVoucherFromInput() {
        VoucherModer voucher = new VoucherModer();
        voucher.setTenVoucher(txtTen.getText());
        voucher.setLoaiVoucher((String) comboxLoai.getSelectedItem());
        voucher.setSoLuong(Integer.valueOf(txtSoLuong.getText().trim()));
       
        BigDecimal mucgiamgia = new BigDecimal(txtMucGG.getText().trim()).stripTrailingZeros();
        voucher.setMucGiamGia(mucgiamgia);
                
       voucher.setMoTa(taMota.getText().trim());
        voucher.setNgayBatDau(new java.sql.Date(JDBD.getDate().getTime()));
        voucher.setNgayKetThuc(new java.sql.Date(jdKT.getDate().getTime()));
        voucher.setTrangThai((String) comboxLoai.getSelectedItem());
        voucher.setLoaiVoucher((String) comboxLoai.getSelectedItem());
        return voucher;
    }

    public void cleanForm() {
        txtMucGG.setText("");
        txtSoLuong.setText("");
        txtTen.setText("");
        txtID.setText("");
        taMota.setText("");
    }

    private void updateTable(List<VoucherModer> voucherModers) {
        dtmm.setRowCount(0);
        for (int i = 0; i < voucherModers.size(); i++) {
            VoucherModer voucherModer = voucherModers.get(i);
            voucherModer.setSTT(i + 1);
            dtmm.addRow(voucherModer.toData());
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
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvoucher = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        comboxLoai = new javax.swing.JComboBox<>();
        jdKT = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        JDBD = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMucGG = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taMota = new javax.swing.JTextArea();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSua.setBackground(new java.awt.Color(51, 153, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(51, 153, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Kết Thúc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(28, 28, 28)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tìm Kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        tblvoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblvoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblvoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblvoucher);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Mã Voucher");

        jLabel1.setText("Tên Voucher");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Số lượng");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtID.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        comboxLoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        comboxLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm theo phần trăm", "Giảm theo giá tiền" }));

        jdKT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jdKT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Loại Voucher");

        JDBD.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        JDBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày bắt đầu");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Ngày kết thúc");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mức giảm giá( % / VND )");

        txtMucGG.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMucGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMucGGActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mô tả");

        taMota.setColumns(20);
        taMota.setRows(5);
        jScrollPane2.setViewportView(taMota);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(txtTen)
                    .addComponent(txtSoLuong))
                .addGap(186, 186, 186)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(327, 327, 327))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jdKT, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboxLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JDBD, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(252, 252, 252)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtMucGG, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 334, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(JDBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(49, 49, 49)
                                    .addComponent(jdKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboxLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtMucGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    void showdata(int index) {
        txtID.setText(tblvoucher.getValueAt(index, 1).toString().trim());
        txtTen.setText(tblvoucher.getValueAt(index, 2).toString().trim());
        txtMucGG.setText((String) tblvoucher.getValueAt(index, 5));
        txtSoLuong.setText(tblvoucher.getValueAt(index, 3).toString().trim());
        taMota.setText(tblvoucher.getValueAt(index, 8).toString().trim());
        String loaiVoucher = String.valueOf(tblvoucher.getValueAt(index, 4)).trim();
        comboxLoai.setSelectedItem(loaiVoucher);
        String trangthai = String.valueOf(tblvoucher.getValueAt(index, 9)).trim();
        comboxLoai.setSelectedItem(trangthai);
        Object dateObj = tblvoucher.getValueAt(index, 6);
        try {
            if (dateObj instanceof java.sql.Date) {
                JDBD.setDate(new java.util.Date(((java.sql.Date) dateObj).getTime()));
            } else if (dateObj instanceof java.util.Date) {
                JDBD.setDate((java.util.Date) dateObj);
            } else {
                JDBD.setDate(null);
            }
        } catch (IllegalArgumentException e) {
            JDBD.setDate(null);
        }

        // Xử lý ngày kết thúc
        Object dateObj2 = tblvoucher.getValueAt(index, 7);
        try {
            if (dateObj2 instanceof java.sql.Date) {
                jdKT.setDate(new java.util.Date(((java.sql.Date) dateObj2).getTime()));
            } else if (dateObj2 instanceof java.util.Date) {
                jdKT.setDate((java.util.Date) dateObj2);
            } else {
                jdKT.setDate(null);
            }
        } catch (IllegalArgumentException e) {
            jdKT.setDate(null);
        }

    }
    private void tblvoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvoucherMouseClicked


        int index = -1;
        index = tblvoucher.getSelectedRow();
        this.showdata(index);
    }//GEN-LAST:event_tblvoucherMouseClicked

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        String keyword = txtTimKiem.getText().trim();
        List<VoucherModer> searchResult = sreService.searchVoucherByName(keyword);
        updateTable(searchResult);
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cleanForm();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String id = txtID.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận xóa Voucher",
                    "Bạn có chắc muốn xóa Voucher này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (sreService.delete(id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa Voucher thành công");
                                FillTable();
                                cleanForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Xóa voucher thất bại");
                            }
                        }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn Voucher cần xóa!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (ValidateVoucher()) {
            if (sreService.checkTrungID(txtID.getText())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Id Voucher đã tồn tại");
                txtID.requestFocus();
                return;
            }
            String newID = sreService.getNewVoucherID();
            VoucherModer voucherModer = this.getVoucherFromInput();
            voucherModer.setID(newID);

            MessageAlerts.getInstance().showMessage("Xác nhận thêm voucher",
                    "Bạn có chắc muốn thêm Voucher này?",
                    MessageAlerts.MessageType.SUCCESS,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (sreService.insert(voucherModer) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm VouCher thành công");
                                
                                FillTable(); // Cập nhật lại bảng dữ liệu
                                cleanForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm Voucher thất bại");
                            }
                        }
            });
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        if (ValidateVoucher()) {
            VoucherModer voucherModer = getVoucherFromInput();
            String id = txtID.getText();

            MessageAlerts.getInstance().showMessage("Xác nhận cập nhật nhân viên",
                    "Bạn có chắc muốn cập nhật thông tin nhân viên này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (sreService.update(voucherModer, id) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật nhân viên thành công!");
                                FillTable();
                                cleanForm();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật nhân viên thất bại!");
                            }
                        }
            });

        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed

    }//GEN-LAST:event_txtIDActionPerformed

    private void txtMucGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMucGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMucGGActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JDBD;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboxLoai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdKT;
    private javax.swing.JTextArea taMota;
    private javax.swing.JTable tblvoucher;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMucGG;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
