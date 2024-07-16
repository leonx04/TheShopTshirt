/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.BrandModel;
import app.model.ColorModel;
import app.model.MaterialModel;
import app.model.SizeModel;
import app.service.BrandService;
import app.service.ColorService;
import app.service.MaterialService;
import app.service.ProductDetailService;
import app.service.ProductsService;
import app.service.SizeService;
import app.tabbed.TabbedForm;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class ProductAttributes extends TabbedForm {

    private DefaultTableModel model = new DefaultTableModel();
    private ProductDetailService ctsprp = new ProductDetailService();
    private ProductsService sprs = new ProductsService();
    private SizeService kcrs = new SizeService();
    private ColorService msrs = new ColorService();
    private BrandService thrs = new BrandService();
    private MaterialService clrs = new MaterialService();
    private ColorModel msmd = new ColorModel();
    private BrandModel thmd = new BrandModel();
    private MaterialModel clmd = new MaterialModel();
    private SizeModel kcmd = new SizeModel();
    private int index = -1;

    /**
     * Creates new form ProductAttributes
     */
    public ProductAttributes() {
        initComponents();
        fillMS();
    }

    public void fillMS() {
        rdoMauSac.setSelected(true);
        model = (DefaultTableModel) this.TblTT.getModel();
        model.setRowCount(0);
        List<ColorModel> MS = msrs.getALLMauSac();
        int index = 1;
        for (ColorModel ms : MS) {
            ms.setStt(index++);
            model.addRow(ms.toData());
        }
    }

    public void fillTH() {
        rdoThuongHieu.setSelected(true);
        model = (DefaultTableModel) this.TblTT.getModel();
        model.setRowCount(0);
        List<BrandModel> thmd = thrs.getALLThuongHieu();
        int index = 1;
        for (BrandModel th : thmd) {
            th.setStt(index++);
            model.addRow(th.toData());
        }
    }

    public void fillKC() {
        rdoKichCo.setSelected(true);
        model = (DefaultTableModel) this.TblTT.getModel();
        model.setRowCount(0);
        List<SizeModel> kcmd = kcrs.getALLKichCo();
        int index = 1;
        for (SizeModel kc : kcmd) {
            kc.setStt(index++);
            model.addRow(kc.toData());
        }
    }

    public void fillCL() {
        rdoChatLieu.setSelected(true);
        model = (DefaultTableModel) this.TblTT.getModel();
        model.setRowCount(0);
        List<MaterialModel> clmd = clrs.getALLChatLieu();
        int index = 1;
        for (MaterialModel cl : clmd) {
            cl.setStt(index++);
            model.addRow(cl.toData());
        }
    }

    public void clear() {
        txtMaTT.setText(null);
        txtTenTT.setText(null);
        txtMoTaTT.setText(null);

    }

    public void search(String query) {
        // Tạo một đối tượng TableRowSorter và gán cho bảng tblSP.
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        TblTT.setRowSorter(tableRowSorter);

        // Thiết lập bộ lọc sử dụng biểu thức chính quy và áp dụng cho TableRowSorter.
        // Sử dụng biểu thức chính quy không phân biệt chữ hoa chữ thường
        tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }

    private boolean checkForm() {
        String tenTT = txtTenTT.getText().trim();
        String moTaTT = txtMoTaTT.getText().trim();

        // Kiểm tra tên thuộc tính không được rỗng
        if (tenTT.isEmpty()) {
           Notifications.getInstance().show(Notifications.Type.WARNING,"Vui lòng nhập tên thuộc tính!");
            txtTenTT.requestFocus();
            return false;
        }

        // Kiểm tra độ dài tối đa của tên thuộc tính là 100 ký tự
        if (tenTT.length() > 100) {
            Notifications.getInstance().show(Notifications.Type.WARNING,"Tên thuộc tính không được vượt quá 100 ký tự!");
            txtTenTT.requestFocus();
            return false;
        }

        // Kiểm tra mô tả thuộc tính không được rỗng
        if (moTaTT.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING,"Vui lòng nhập mô tả thuộc tính!");
            txtMoTaTT.requestFocus();
            return false;
        }

        // Kiểm tra độ dài tối đa của mô tả thuộc tính là 255 ký tự
        if (moTaTT.length() > 255) {
           Notifications.getInstance().show(Notifications.Type.WARNING, "Mô tả thuộc tính không được vượt quá 255 ký tự!");
            txtMoTaTT.requestFocus();
            return false;
        }

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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaTT = new javax.swing.JTextField();
        txtTenTT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTaTT = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoKichCo = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        rdoThuongHieu = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        btnAddTT = new javax.swing.JButton();
        btnUpdateTT = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDeleteTT = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblTT = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin thuộc tính sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã thuộc tính:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên thuộc tính:");

        txtMaTT.setEditable(false);
        txtMaTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mô tả:");

        txtMoTaTT.setColumns(20);
        txtMoTaTT.setRows(5);
        jScrollPane2.setViewportView(txtMoTaTT);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Thuộc tính");

        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMauSacActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKichCo);
        rdoKichCo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoKichCo.setText("Size");
        rdoKichCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKichCoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChatLieuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoThuongHieu);
        rdoThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoThuongHieu.setText("Thương hiệu");
        rdoThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoThuongHieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(60, 60, 60)
                        .addComponent(txtMaTT, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(57, 57, 57)
                        .addComponent(txtTenTT)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoMauSac)
                            .addComponent(rdoKichCo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoThuongHieu)
                            .addComponent(rdoChatLieu))
                        .addGap(237, 237, 237))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 68, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoChatLieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoKichCo)
                    .addComponent(rdoThuongHieu))
                .addGap(8, 8, 8))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnAddTT.setBackground(new java.awt.Color(0, 204, 51));
        btnAddTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddTT.setText("Thêm thuộc tính");
        btnAddTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTTActionPerformed(evt);
            }
        });

        btnUpdateTT.setBackground(new java.awt.Color(0, 153, 153));
        btnUpdateTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateTT.setText("Cập nhật thuộc tính");
        btnUpdateTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTTActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 153, 153));
        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDeleteTT.setBackground(new java.awt.Color(255, 0, 0));
        btnDeleteTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteTT.setText("Xóa");
        btnDeleteTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddTT)
                .addGap(27, 27, 27)
                .addComponent(btnUpdateTT)
                .addGap(28, 28, 28)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnDeleteTT)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Danh sách thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        TblTT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TblTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblTTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblTT);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(312, 312, 312))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(469, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMauSacActionPerformed
        fillMS();
    }//GEN-LAST:event_rdoMauSacActionPerformed

    private void rdoKichCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKichCoActionPerformed
        fillKC();
    }//GEN-LAST:event_rdoKichCoActionPerformed

    private void rdoChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChatLieuActionPerformed
        fillCL();
    }//GEN-LAST:event_rdoChatLieuActionPerformed

    private void rdoThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoThuongHieuActionPerformed
        fillTH();
    }//GEN-LAST:event_rdoThuongHieuActionPerformed

    private void btnAddTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTTActionPerformed
        String maTT = txtMaTT.getText();
        String tenTT = txtTenTT.getText();
        String moTaTT = txtMoTaTT.getText();

        if (rdoMauSac.isSelected()) {
            String newIDMS = msrs.getNewIDMS();
            msmd.setID(newIDMS);
            msmd.setTenMS(tenTT);
            msmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (msrs.checkTrungID(txtMaTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Mã thuộc tính đã tồn tại!");
                txtMaTT.requestFocus();
                return;
            }
            if (msrs.checkTrungTen(txtTenTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Tên thuộc tính đã tồn tại!");
                txtTenTT.requestFocus();
                return;
            }
            if (msrs.insert(msmd) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Thêm màu sắc thành công !");
                fillMS();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm thất bại !");
            }
        } else if (rdoChatLieu.isSelected()) {
            String newIDCL = clrs.getNewIDCL();
            clmd.setID(newIDCL);
            clmd.setTenCL(tenTT);
            clmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (clrs.checkTrungID(txtMaTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO,"Mã thuộc tính đã tồn tại!");
                txtMaTT.requestFocus();
                return;
            }
            if (clrs.checkTrungTen(txtTenTT.getText().trim())) {
               Notifications.getInstance().show(Notifications.Type.INFO,"Tên thuộc tính đã tồn tại!");
                txtTenTT.requestFocus();
                return;
            }
            if (clrs.insert(clmd) > 0) {
               Notifications.getInstance().show(Notifications.Type.SUCCESS,"Thêm chất liệu thành công !");
                fillCL();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Thêm thất bại !");
            }
        } else if (rdoKichCo.isSelected()) {
            String newIDKC = kcrs.getNewIDKC();
            kcmd.setID(newIDKC);
            kcmd.setTenSize(tenTT);
            kcmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (kcrs.checkTrungID(txtMaTT.getText().trim())) {
               Notifications.getInstance().show(Notifications.Type.INFO,"Mã thuộc tính đã tồn tại!");
                txtMaTT.requestFocus();
                return;
            }
            if (kcrs.checkTrungTen(txtTenTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO,"Tên thuộc tính đã tồn tại!");
                txtTenTT.requestFocus();
                return;
            }
            if (kcrs.insert(kcmd) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Thêm kích cỡ thành công !");
                fillKC();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Thêm thất bại !");
            }
        } else if (rdoThuongHieu.isSelected()) {
            String newIDTH = thrs.getNewIDTH();
            thmd.setID(newIDTH);
            thmd.setTenTH(tenTT);
            thmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (thrs.checkTrungID(txtMaTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO,"Mã thuộc tính đã tồn tại!");
                txtMaTT.requestFocus();
                return;
            }
            if (thrs.checkTrungTen(txtTenTT.getText().trim())) {
                Notifications.getInstance().show(Notifications.Type.INFO,"Tên thuộc tính đã tồn tại!");
                txtTenTT.requestFocus();
                return;
            }
            if (thrs.insert(thmd) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Thêm thương hiệu thành công !");
                fillTH();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm thất bại !");
            }
        }
    }//GEN-LAST:event_btnAddTTActionPerformed

    private void btnUpdateTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTTActionPerformed
        String maTT = txtMaTT.getText();
        String tenTT = txtTenTT.getText();
        String moTaTT = txtMoTaTT.getText();

        if (rdoMauSac.isSelected()) {
            msmd.setID(maTT);
            msmd.setTenMS(tenTT);
            msmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (msrs.update(msmd, maTT) > 0) {
               Notifications.getInstance().show(Notifications.Type.SUCCESS,"Cập nhật màu sắc thành công !");
                fillMS();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Cập nhật thất bại !");
            }
        } else if (rdoChatLieu.isSelected()) {
            clmd.setID(maTT);
            clmd.setTenCL(tenTT);
            clmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (clrs.update(clmd, maTT) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Cập nhật chất liệu thành công !");
                fillCL();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Cập nhật thất bại !");
            }
        } else if (rdoKichCo.isSelected()) {
            kcmd.setID(maTT);
            kcmd.setTenSize(tenTT);
            kcmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (kcrs.update(kcmd, maTT) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Cập nhật kích cỡ thành công !");
                fillKC();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Cập nhật thất bại !");
            }
        } else if (rdoThuongHieu.isSelected()) {
            thmd.setID(maTT);
            thmd.setTenTH(tenTT);
            thmd.setMoTa(moTaTT);
            if (!checkForm()) {
                return;
            }
            if (thrs.update(thmd, maTT) > 0) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Cập nhật thương hiệu thành công !");
                fillTH();
                clear();
            } else {
               Notifications.getInstance().show(Notifications.Type.ERROR,"Cập nhật thất bại !");
            }
        }
    }//GEN-LAST:event_btnUpdateTTActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clear();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTTActionPerformed
        String maTT = txtMaTT.getText();

        if (rdoMauSac.isSelected()) {
            if (msrs.checkTonTaiSPCT(maTT)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thuộc tính này vì đang tồn tại trong sản phẩm chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int del = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa màu sắc này ?"
                    + (msrs.delete(maTT) > 0), "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (del == JOptionPane.YES_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Xóa thành công !");
                fillMS();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Xóa thất bại!");
            }
        } else if (rdoChatLieu.isSelected()) {
            if (clrs.checkTonTaiSPCT(maTT)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thuộc tính này vì đang tồn tại trong sản phẩm chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int del = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa chất liệu này ?"
                    + (clrs.delete(maTT) > 0), "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (del == JOptionPane.YES_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Xóa thành công !");
                fillCL();
                clear();
            } else {
               Notifications.getInstance().show(Notifications.Type.ERROR,"Xóa thất bại!");
            }
        } else if (rdoKichCo.isSelected()) {
            if (kcrs.checkTonTaiSPCT(maTT)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thuộc tính này vì đang tồn tại trong sản phẩm chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int del = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kích cỡ này ?"
                    + (kcrs.delete(maTT) > 0), "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (del == JOptionPane.YES_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Xóa thành công !");
                fillKC();
                clear();
            } else {
               Notifications.getInstance().show(Notifications.Type.ERROR,"Xóa thất bại!");
            }
        } else if (rdoThuongHieu.isSelected()) {
            if (thrs.checkTonTaiSPCT(maTT)) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thuộc tính này vì đang tồn tại trong sản phẩm chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int del = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thương hiệu này ?"
                    + (thrs.delete(maTT) > 0), "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (del == JOptionPane.YES_OPTION) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS,"Xóa thành công !");
                fillTH();
                clear();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR,"Xóa thất bại!");
            }
        }
    }//GEN-LAST:event_btnDeleteTTActionPerformed

    private void TblTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTTMouseClicked
        int rowSel = this.TblTT.getSelectedRow();
        if (model.getRowCount() > 0 && evt.getClickCount() == 1) {
            txtMaTT.setText(String.valueOf(TblTT.getValueAt(rowSel, 1)).toString().trim());
            txtTenTT.setText(String.valueOf(TblTT.getValueAt(rowSel, 2)).trim());
            txtMoTaTT.setText(String.valueOf(TblTT.getValueAt(rowSel, 3)).trim());
        }
    }//GEN-LAST:event_TblTTMouseClicked

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        String query = txtTimKiem.getText();
        search(query);
    }//GEN-LAST:event_txtTimKiemKeyPressed

    @Override
    public boolean formClose() {
        if (!txtMaTT.getText().trim().isEmpty()
                || !txtTenTT.getText().trim().isEmpty()
                || !txtMoTaTT.getText().trim().isEmpty()) {

            int opt = JOptionPane.showConfirmDialog(this, "Dữ liệu chưa được lưu, bạn có chắc chắn muốn đóng tab ?", "Close",JOptionPane.WARNING_MESSAGE ,JOptionPane.YES_NO_OPTION);
            return opt == JOptionPane.YES_OPTION;
        }

        // Nếu tất cả các trường đều rỗng, cho phép đóng mà không cần xác nhận
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblTT;
    private javax.swing.JButton btnAddTT;
    private javax.swing.JButton btnDeleteTT;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoKichCo;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JRadioButton rdoThuongHieu;
    private javax.swing.JTextField txtMaTT;
    private javax.swing.JTextArea txtMoTaTT;
    private javax.swing.JTextField txtTenTT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
