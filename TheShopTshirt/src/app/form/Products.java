/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.ProductsModel;
import app.service.ProductsService;
import app.tabbed.TabbedForm;
import com.formdev.flatlaf.FlatClientProperties;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import pagination.EventPagination;
import pagination.style.PaginationItemRenderStyle1;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class Products extends TabbedForm {

    private DefaultTableModel model = new DefaultTableModel();
    private final ProductsService sprs = new ProductsService();

    // Định nghĩa số lượng bản ghi hiển thị trên mỗi trang
    private static final int RECORDS_PER_PAGE = 20;
    private int currentPage = 1; // Trang hiện tại

    @Override
    public void fromRefresh() {
        // Tải lại dữ liệu cho form 
        init();
    }

    public Products() {
        initComponents();
        init();
    }

    void refreshData() {
        List<ProductsModel> allSP = sprs.getAllSP(); // Lấy tất cả sản phẩm
        int totalRecords = allSP.size(); // Tổng số lượng bản ghi
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

        // Nếu currentPage hiện tại lớn hơn totalPages sau khi tính lại, điều chỉnh
        // currentPage
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        setPagegination(currentPage, totalRecords); // Cập nhật thanh phân trang
        fillTable(allSP); // Hiển thị dữ liệu cho trang hiện tại
    }

    public void setPagegination(int current, int totalRecords) {
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        pagination1.setPagegination(current, totalPages); // Cập nhật thanh phân trang
    }

    // Thêm bộ lọc và phân trang mới khi số lượng bản ghi trên mỗi trang được thay
    // đổi
    void fillTable(List<ProductsModel> listSP) {
        model = (DefaultTableModel) tblSP.getModel();
        model.setRowCount(0);

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, listSP.size());

        int index = startIndex + 1;

        for (int i = startIndex; i < endIndex; i++) {
            ProductsModel sp = listSP.get(i);
            sp.setStt(index++);
            model.addRow(sp.toData());
        }
    }

    private void init() {
        // Cài đặt thanh phân trang
        pagination1.addEventPagination((int page) -> {
            currentPage = page; // Cập nhật trang hiện tại khi chuyển trang
            refreshData(); // Hiển thị dữ liệu cho trang mới
        });
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        refreshData(); // Hiển thị dữ liệu ban đầu khi khởi động
    }


    ProductsModel read() {
        ProductsModel sp = new ProductsModel();
        sp.setID(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setMoTa(txtMoTa.getText());
        return sp;
    }

    void clear() {
        txtMaSP.setText(null);
        txtTenSP.setText(null);
        txtMoTa.setText(null);
        txtTimKiem.setText(null);
    }

    private boolean checkForm() {
        String tenSP = txtTenSP.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenSP.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập tên sản phẩm!");
            txtTenSP.requestFocus();
            return false;
        }

        if (tenSP.length() > 100) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Tên sản phẩm không được vượt quá 100 ký tự!");

            txtTenSP.requestFocus();
            return false;
        }

        if (moTa.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập mô tả sản phẩm!");
            txtMoTa.requestFocus();
            return false;
        }

        if (moTa.length() > 255) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Mô tả sản phẩm không được vượt quá 255 ký tự!");
            txtMoTa.requestFocus();
            return false;
        }

        return true;
    }

    private void searchByProductName() {
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        List<ProductsModel> filteredList = new ArrayList<>();

        for (ProductsModel sp : sprs.getAllSP()) {
            if (sp.getTenSP().toLowerCase().contains(searchText)) {
                filteredList.add(sp);
            }
        }

        fillTable(filteredList);
    }
    /**
     * Lọc dữ liệu trong bảng theo biểu thức chính quy.
     *
     * @param query Biểu thức chính quy sử dụng để lọc dữ liệu.
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        Cbo_TrangThai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnAddSP = new javax.swing.JButton();
        btnUpdateSP = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDeleteSP = new javax.swing.JButton();
        btnHuySP = new javax.swing.JButton();
        btnKhoiPhuc = new javax.swing.JButton();
        pagination1 = new pagination.Pagination();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên sản phẩm:");

        txtMaSP.setEditable(false);
        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mô tả:");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

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
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(57, 57, 57)
                        .addComponent(txtTenSP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
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
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Mô tả sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        Cbo_TrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Cbo_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang kinh doanh", "Ngừng kinh doanh" }));
        Cbo_TrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cbo_TrangThaiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Trạng thái");

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
                        .addGap(161, 161, 161)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(Cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(), "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddSP.setBackground(new java.awt.Color(51, 153, 255));
        btnAddSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddSP.setText("Thêm sản phẩm");
        btnAddSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSPActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 28, 193, -1));

        btnUpdateSP.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdateSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdateSP.setText("Cập nhật sản phẩm");
        btnUpdateSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSPActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpdateSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 67, 193, -1));

        btnReset.setBackground(new java.awt.Color(51, 153, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel3.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 112, 193, -1));

        btnDeleteSP.setBackground(new java.awt.Color(51, 153, 255));
        btnDeleteSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteSP.setText("Xóa");
        btnDeleteSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSPActionPerformed(evt);
            }
        });
        jPanel3.add(btnDeleteSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 151, 193, -1));

        btnHuySP.setBackground(new java.awt.Color(51, 153, 255));
        btnHuySP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuySP.setText("Ngừng hoạt động");
        btnHuySP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuySPActionPerformed(evt);
            }
        });
        jPanel3.add(btnHuySP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 193, -1));

        btnKhoiPhuc.setBackground(new java.awt.Color(51, 153, 255));
        btnKhoiPhuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKhoiPhuc.setText("Khôi phục hoạt động");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });
        jPanel3.add(btnKhoiPhuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 193, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(562, 562, 562))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
        int row = this.tblSP.getSelectedRow();
        if (model.getRowCount() > 0 && evt.getClickCount() == 1) {
            txtMaSP.setText(this.tblSP.getValueAt(row, 1).toString().trim());
            txtTenSP.setText(this.tblSP.getValueAt(row, 2).toString().trim());
            txtMoTa.setText(this.tblSP.getValueAt(row, 3).toString().trim());
        }
    }//GEN-LAST:event_tblSPMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        searchByProductName();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void Cbo_TrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cbo_TrangThaiActionPerformed
        String selectedTrangThai = (String) Cbo_TrangThai.getSelectedItem();
        List<ProductsModel> listSP;

        if (selectedTrangThai.equals("Đang kinh doanh")) {
            listSP = sprs.getAllSPDangKinhDoanh();
        } else if (selectedTrangThai.equals("Ngừng kinh doanh")) {
            listSP = sprs.getAllSPNgungKinhDoanh();
        } else {
            listSP = sprs.getAllSP();
        }

        fillTable(listSP);
    }//GEN-LAST:event_Cbo_TrangThaiActionPerformed

    private void btnAddSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSPActionPerformed
        if (sprs.checkTrungMa(txtMaSP.getText().trim())) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Mã sản phẩm đã tồn tại!");
            txtMaSP.requestFocus();
            return;
        }
        if (sprs.checkTrungTen(txtTenSP.getText().trim())) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Tên sản phẩm đã tồn tại!");
            txtMaSP.requestFocus();
            return;
        }
        if (!checkForm()) {
            return;
        }
        String newID = sprs.getNewSanPhamID();
        ProductsModel spmd = this.read();
        spmd.setID(newID);

        MessageAlerts.getInstance().showMessage("Xác nhận thêm sản phẩm",
                "Bạn có chắc muốn thêm sản phẩm này?",
                MessageAlerts.MessageType.SUCCESS,
                MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (sprs.insert(spmd) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm thành công");
                            refreshData();
                            fillTable(sprs.getAllSP());
                            clear();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm thất bại");
                        }
                    }
        });
    }//GEN-LAST:event_btnAddSPActionPerformed

    private void btnUpdateSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSPActionPerformed
        if (!checkForm()) {
        } else {
            ProductsModel spmd = this.read();
            ProductsService sprs = new ProductsService();

            MessageAlerts.getInstance().showMessage("Xác nhận cập nhật",
                    "Bạn có chắc muốn cập nhật thông tin sản phẩm này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (sprs.update(spmd, spmd.getID()) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật thông tin sản phẩm thành công");
                                fillTable(sprs.getAllSP());
                                clear();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật thất bại");
                            }
                        }
            });
        }
    }//GEN-LAST:event_btnUpdateSPActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        this.clear();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSPActionPerformed
        int rowDC = tblSP.getSelectedRow();
        if (rowDC >= 0) {
            String ID = tblSP.getValueAt(rowDC, 1).toString();

            if (sprs.checkTonTaiSPCT(ID)) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Không thể xóa sản phẩm này vì nó đang tồn tại trong sản phẩm chi tiết!");
                return;
            }

            MessageAlerts.getInstance().showMessage("Xác nhận xóa",
                    "Bạn có chắc muốn xóa sản phẩm này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (sprs.delete(ID) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xoá thành công sản phẩm!");
                                refreshData();
                                fillTable(sprs.getAllSP());
                                clear();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Xoá thất bại");
                            }
                        }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn một sản phẩm để xóa");
        }
    }//GEN-LAST:event_btnDeleteSPActionPerformed

    private void btnHuySPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuySPActionPerformed
        int rowDC = tblSP.getSelectedRow();
        if (rowDC >= 0) {
            String ID = tblSP.getValueAt(rowDC, 1).toString();

            MessageAlerts.getInstance().showMessage("Xác nhận ngừng kinh doanh",
                    "Bạn có chắc muốn ngừng kinh doanh sản phẩm này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            ProductsModel sp = new ProductsModel();
                            if (sprs.updateTrangThai(sp, ID) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã ngừng kinh doanh sản phẩm!");
                                refreshData();
                                fillTable(sprs.getAllSP());
                                clear();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật trạng thái thất bại");
                            }
                        }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn một sản phẩm để ngừng kinh doanh");
        }
    }//GEN-LAST:event_btnHuySPActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        int rowDC = tblSP.getSelectedRow();
        if (rowDC >= 0) {
            String ID = tblSP.getValueAt(rowDC, 1).toString();

            MessageAlerts.getInstance().showMessage("Xác nhận khôi phục kinh doanh",
                    "Bạn có chắc muốn khôi phục trạng thái kinh doanh sản phẩm này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            ProductsModel sp = new ProductsModel();
                            if (sprs.updateActiveTrangThai(sp, ID) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã khôi phục trạng thái thành đã kinh doanh cho sản phẩm!");
                                refreshData();
                                fillTable(sprs.getAllSP());
                                clear();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật trạng thái thất bại");
                            }
                        }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn một sản phẩm để khôi phục trạng thái");
        }
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    @Override
    public boolean formClose() {
        if (!txtMaSP.getText().trim().isEmpty()
                || !txtTenSP.getText().trim().isEmpty()
                || !txtMoTa.getText().trim().isEmpty()) {

            int opt = JOptionPane.showConfirmDialog(this, "Dữ liệu chưa được lưu, bạn có chắc chắn muốn đóng tab ? ", "Close", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            return opt == JOptionPane.YES_OPTION;
        }

        // Nếu tất cả các trường đều rỗng, cho phép đóng mà không cần xác nhận
        return true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Cbo_TrangThai;
    private javax.swing.JButton btnAddSP;
    private javax.swing.JButton btnDeleteSP;
    private javax.swing.JButton btnHuySP;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateSP;
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
    private pagination.Pagination pagination1;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
