/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.form.other.BrandNew;
import app.form.other.ColorNew;
import app.form.other.MaterialNew;
import app.form.other.SizeNew;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.MaterialModel;
import app.model.ProductDetailModel;
import app.model.ProductsModel;
import app.model.SizeModel;
import app.service.BrandService;
import app.service.ColorService;
import app.service.MaterialService;
import app.service.ProductDetailService;
import app.service.ProductsService;
import app.service.SizeService;
import app.tabbed.TabbedForm;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
public final class DetailProduct extends TabbedForm {

    private DefaultTableModel model = new DefaultTableModel();
    private ProductDetailService ctsprp = new ProductDetailService();
    private ProductsService sprs = new ProductsService();
    private SizeService kcrs = new SizeService();
    private ColorService msrs = new ColorService();
    private BrandService thrs = new BrandService();
    private MaterialService clrs = new MaterialService();
    private int index = -1;

    private String selectedSanPhamID = null;
    private String selectedFilterSPItem = null;
    private String selectedMauSacID = null;
    private String selectedFilterMSItem = null;
    private String selectedSizeID = null;
    private String selectedFilterSizeItem = null;
    private String selectedChatLieuID = null;
    private String selectedFilterChatLieuItem = null;
    private String selectedThuongHieuID = null;
    private String selectedFilterThuongHieuItem = null;

    DefaultComboBoxModel dcb_MS;
    DefaultComboBoxModel dcb_CL;
    DefaultComboBoxModel dcb_TH;
    DefaultComboBoxModel dcb_KC;
    DefaultComboBoxModel dcb_SP;

    // Định nghĩa số lượng bản ghi hiển thị trên mỗi trang
    private static final int RECORDS_PER_PAGE = 20;
    private int currentPage = 1; // Trang hiện tại

    @Override
    public void fromRefresh() {
        // Tải lại dữ liệu cho form 
        init();
        fillTable(ctsprp.getAllCTSP());
        Cbo_ChatLieu();
        Cbo_KichCo();
        Cbo_MauSac();
        Cbo_ThuongHieu();
        Cbo_SanPham();
    }
    
    
    public DetailProduct() {
        initComponents();
        init();
        fillTable(ctsprp.getAllCTSP());
        Cbo_ChatLieu();
        Cbo_KichCo();
        Cbo_MauSac();
        Cbo_ThuongHieu();
        Cbo_SanPham();
        JComboBox<String> cboFilterTrangThai = new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"});
    }

    void refreshData() {
        List<ProductDetailModel> allCTSP = ctsprp.getAllCTSP(); // Lấy tất cả sản phẩm
        int totalRecords = allCTSP.size(); // Tổng số lượng bản ghi
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

        // Nếu currentPage hiện tại lớn hơn totalPages sau khi tính lại, điều chỉnh currentPage
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        setPagegination(currentPage, totalRecords); // Cập nhật thanh phân trang
        fillTable(allCTSP); // Hiển thị dữ liệu cho trang hiện tại
    }

    public void setPagegination(int current, int totalRecords) {
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        pagination1.setPagegination(current, totalPages); // Cập nhật thanh phân trang
    }

    // Thêm bộ lọc và phân trang mới khi số lượng bản ghi trên mỗi trang được thay đổi
    void fillTable(List<ProductDetailModel> listSP) {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, listSP.size());

        int index = startIndex + 1;

        for (int i = startIndex; i < endIndex; i++) {
            ProductDetailModel sp = listSP.get(i);
            sp.setStt(index++);
            model.addRow(sp.toData());
        }
    }

    private void init() {
        // Cài đặt thanh phân trang
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                currentPage = page; // Cập nhật trang hiện tại khi chuyển trang
                refreshData(); // Hiển thị dữ liệu cho trang mới
            }
        });
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        refreshData(); // Hiển thị dữ liệu ban đầu khi khởi động
    }

    void showData(int index) {
        String maCTSP = String.valueOf(tblSPCT.getValueAt(index, 1)).trim();
        String TenSP = String.valueOf(tblSPCT.getValueAt(index, 2)).trim();
        String MauSac = String.valueOf(tblSPCT.getValueAt(index, 3)).trim();
        String KichThuoc = String.valueOf(tblSPCT.getValueAt(index, 4)).trim();
        String ChatLieu = String.valueOf(tblSPCT.getValueAt(index, 5)).trim();
        String ThuongHieu = String.valueOf(tblSPCT.getValueAt(index, 6)).trim();
        String GiaBan = String.valueOf(tblSPCT.getValueAt(index, 7)).trim();
        String SoLuong = String.valueOf(tblSPCT.getValueAt(index, 8)).trim();
        String MoTa = String.valueOf(tblSPCT.getValueAt(index, 9)).trim();
        // System.out.println(String.valueOf(tblSPCT.getValueAt(index, 9)).trim());
        txtMaCTSP.setText(maCTSP);
        txtGiaBan.setText(String.valueOf(GiaBan));
        txtMoTaCTSP.setText(MoTa);
        txtSoLuong.setText(String.valueOf(SoLuong));
        cboChatLieu.setSelectedItem(ChatLieu);
        cboThuonHieu.setSelectedItem(ThuongHieu);
        cboTenSP.setSelectedItem(TenSP);
        cboMauSac.setSelectedItem(MauSac);
        cboKichThuoc.setSelectedItem(KichThuoc);
    }

    ProductDetailModel readForm() {
        ProductDetailModel ctsp = new ProductDetailModel();

        // Dữ liệu cần thiết từ các trường nhập liệu
        String maCTSP = txtMaCTSP.getText().trim();
        String tenSP = cboTenSP.getSelectedItem().toString().trim();
        String mauSac = cboMauSac.getSelectedItem().toString().trim();
        String kichThuoc = cboKichThuoc.getSelectedItem().toString().trim();
        String chatLieu = cboChatLieu.getSelectedItem().toString().trim();
        String thuongHieu = cboThuonHieu.getSelectedItem().toString().trim();
        String giaBanStr = txtGiaBan.getText().trim();
        String soLuongTonStr = txtSoLuong.getText().trim();
        String moTa = txtMoTaCTSP.getText().trim();

        // Xác định ID tương ứng với tên sản phẩm đã chọn
        List<ProductsModel> listSP = sprs.getIDByTenSP(tenSP);
        String maSP = null; //Biến này sẽ được sử dụng để lưu trữ ID của sản phẩm tương ứng nếu tìm thấy
        if (listSP.size() > 0) { // Kiểm tra xem danh sách có phần tử không
            maSP = listSP.get(0).getID();// Lấy ID của phần tử đầu tiên nếu danh sách không rỗng
        }

        // Xác định ID tương ứng với tên chất liệu đã chọn
        List<MaterialModel> listCL = clrs.getIDByTenCL(chatLieu);
        String maCL = null;
        if (listCL.size() > 0) {
            maCL = listCL.get(0).getID();
        }

        // Xác định ID tương ứng với tên kích cỡ đã chọn
        List<SizeModel> listKC = kcrs.getIDByTenKC(kichThuoc);
        String maKC = null;
        if (listKC.size() > 0) {
            maKC = listKC.get(0).getID();
        }

        // Xác định ID tương ứng với tên màu sắc đã chọn
        List<ColorModel> listMS = msrs.getIDByTenMS(mauSac);
        String maMS = null;
        if (listMS.size() > 0) {
            maMS = listMS.get(0).getID();
        }

        // Xác định ID tương ứng với tên kích cỡ đã chọn
        List<BrandModel> listTH = thrs.getIDByTenTH(thuongHieu);
        String maTH = null;
        if (listTH.size() > 0) {
            maTH = listTH.get(0).getID();
        }

        // Thiết lập dữ liệu trực tiếp vào đối tượng ChiTietSanPhamModel
        ctsp.setID(maCTSP);
        ctsp.setTenSP(new ProductsModel(maSP, tenSP, null)); // Sử dụng mã sản phẩm thay vì tên
        ctsp.setMauSac(new ColorModel(maMS, mauSac, null));
        ctsp.setKichCo(new SizeModel(maKC, kichThuoc, null)); // Sử dụng mã kích cỡ thay vì tên
        ctsp.setChatLieu(new MaterialModel(maCL, chatLieu, null));// Sử dụng mã chất liệu thay vì tên
        ctsp.setThuongHieu(new BrandModel(maTH, thuongHieu, null));
        ctsp.setGiaBan(new BigDecimal(giaBanStr));
        ctsp.setSoLuongTon(Integer.parseInt(soLuongTonStr));
        ctsp.setMoTa(moTa);

        // In dữ liệu đã nhập ra console (nếu cần)
        System.out.println("Mã sản phẩm: " + maCTSP);
        System.out.println("Mã sản phẩm: " + maSP);
        System.out.println("Tên sản phẩm: " + tenSP);
        System.out.println("Màu sắc: " + mauSac);
        System.out.println("Kích thước: " + kichThuoc);
        System.out.println("Chất liệu: " + chatLieu);
        System.out.println("Thương hiệu: " + thuongHieu);
        System.out.println("Giá bán (String): " + giaBanStr);
        System.out.println("Số lượng tồn (String): " + soLuongTonStr);
        System.out.println("Mô tả: " + moTa);

        return ctsp;
    }

    public  void Cbo_MauSac() {
        List<ColorModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboMauSac.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    void Cbo_FilterMauSac() {
        List<ColorModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboFilterMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    void Cbo_KichCo() {
        List<SizeModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterKichCo() {
        List<SizeModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboFilterKichThuoc.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_SanPham() {
        List<ProductsModel> listSP = sprs.getAllSP();
        String[] cbo = new String[listSP.size()];
        for (int i = 0; i < listSP.size(); i++) {
            cbo[i] = listSP.get(i).getTenSP();
        }
        cboTenSP.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterSP.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilTerSanPham() {
        List<ProductsModel> listSP = sprs.getAllSP();
        String[] cbo = new String[listSP.size()];
        for (int i = 0; i < listSP.size(); i++) {
            cbo[i] = listSP.get(i).getTenSP();
        }
        cboFilterSP.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_ThuongHieu() {
        List<BrandModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboThuonHieu.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterThuongHieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterThuongHieu() {
        List<BrandModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboFilterThuongHieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_ChatLieu() {
        List<MaterialModel> listCL = clrs.getALLChatLieu();
        String[] cbo = new String[listCL.size()];
        for (int i = 0; i < listCL.size(); i++) {
            cbo[i] = listCL.get(i).getTenCL();
        }
        cboChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
        cboFilterChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void Cbo_FilterChatLieu() {
        List<MaterialModel> listCL = clrs.getALLChatLieu();
        String[] cbo = new String[listCL.size()];
        for (int i = 0; i < listCL.size(); i++) {
            cbo[i] = listCL.get(i).getTenCL();
        }
        cboFilterChatLieu.setModel(new DefaultComboBoxModel<>(cbo));
    }

    void clear() {
        txtMaCTSP.setText(null);
        txtGiaBan.setText(null);
        txtMoTaCTSP.setText(null);
        txtSoLuong.setText(null);
        txtTimKiem.setText(null);
    }

    private void searchByProductName() {
        String searchText = txtTimKiem.getText().trim().toLowerCase();
        List<ProductDetailModel> filteredList = new ArrayList<>();

        for (ProductDetailModel ctsp : ctsprp.getAllCTSP()) {
            if (ctsp.getTenSP().getTenSP().toLowerCase().contains(searchText)) {
                filteredList.add(ctsp);
            }
        }

        fillTable(filteredList);
    }

    void openMauSac() {
        boolean colorAdded = ColorNew.showDialog();
        if (colorAdded) {
            reloadDetailProduct();
        }
    }

    private void reloadDetailProduct() {
        // Lưu trạng thái hiện tại
        int selectedRow = tblSPCT.getSelectedRow();
        
        String giaBan = txtGiaBan.getText();
        String soLuongTon = txtSoLuong.getText();
        String moTa =  txtMoTaCTSP.getText();
        
        // Reload dữ liệu
        Cbo_MauSac();
        Cbo_KichCo();
        Cbo_SanPham();
        Cbo_ChatLieu();
        Cbo_ThuongHieu();
        fillTable(ctsprp.getAllCTSP());
    }

    void openChatlieu() {
        boolean materialAdded = MaterialNew.showDialog();
        if (materialAdded) {
            reloadDetailProduct();
        }
    }

    void openThuonHieu() {
        boolean brandAdded = BrandNew.showDialog();
        if (brandAdded) {
            reloadDetailProduct();
        }
    }

    void openKichThuoc() {
        boolean sizeAdded = SizeNew.showDialog();
        if (sizeAdded) {
            reloadDetailProduct();
        }
    }

    public boolean validatef() {
        // Dữ liệu cần thiết từ các trường nhập liệu
        String tenSP = cboTenSP.getSelectedItem().toString().trim();
        String mauSac = cboMauSac.getSelectedItem().toString().trim();
        String kichThuoc = cboKichThuoc.getSelectedItem().toString().trim();
        String chatLieu = cboChatLieu.getSelectedItem().toString().trim();
        String thuongHieu = cboThuonHieu.getSelectedItem().toString().trim();
        String giaBanStr = txtGiaBan.getText().trim();
        String soLuongTonStr = txtSoLuong.getText().trim();
        String moTa = txtMoTaCTSP.getText().trim();

        // Kiểm tra trường nào đó có trống không
        if (tenSP.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn tên sản phẩm");
            return false;
        }

        if (mauSac.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn màu sắc");
            return false;
        }

        if (kichThuoc.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn kích thước");
            return false;
        }

        if (chatLieu.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn chất liệu");
            return false;
        }

        if (thuongHieu.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn thương hiệu");
            return false;
        }

        if (soLuongTonStr.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập số lượng tồn");
            txtSoLuong.requestFocus();
            return false;
        }

        if (giaBanStr.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập giá bán");
            txtGiaBan.requestFocus();
            return false;
        }

        if (moTa.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập mô tả");
            txtMoTaCTSP.requestFocus();
            return false;
        }
        try {
            int soLuongTon = Integer.parseInt(soLuongTonStr);

            // Kiểm tra số lượng tồn là số nguyên dương lớn hơn 0 và không vượt quá 1500
            if (soLuongTon <= 0 || soLuongTon > 1500) {
                Notifications.getInstance().show(Notifications.Type.INFO,
                        "Số lượng tồn phải là số nguyên dương lớn hơn 0 và không vượt quá 1500");
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Số lượng tồn phải là số ");
            txtSoLuong.requestFocus();
            return false;
        }

        try {
            BigDecimal giaBan = new BigDecimal(giaBanStr);

            // Kiểm tra giá bán không âm và lớn hơn 1000
            if (giaBan.compareTo(BigDecimal.valueOf(1000)) < 0) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Giá bán phải lớn hơn hoặc bằng 1000");
                txtGiaBan.requestFocus();
                return false;
            }

            // Kiểm tra giá bán không vượt quá giới hạn
            BigDecimal maxGiaBan = new BigDecimal("9999999999999999999");
            if (giaBan.compareTo(maxGiaBan) > 0) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Giá bán không được vượt quá 9999999999999999999");
                txtGiaBan.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Giá bán phải là số");
            txtGiaBan.requestFocus();
            return false;
        }

        // Kiểm tra mô tả không vượt quá 500 ký tự
        if (moTa.length() > 500) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Mô tả không được vượt quá 500 ký tự");
            txtMoTaCTSP.requestFocus();
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

        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        cboFilterMauSac = new javax.swing.JComboBox<>();
        cboFilterKichThuoc = new javax.swing.JComboBox<>();
        cboFilterChatLieu = new javax.swing.JComboBox<>();
        cboFilterThuongHieu = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cboFilterTrangThai = new javax.swing.JComboBox<>();
        cboFilterSP = new javax.swing.JComboBox<>();
        pagination1 = new pagination.Pagination();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaCTSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTaCTSP = new javax.swing.JTextArea();
        btnAddMauSac = new javax.swing.JButton();
        btnAddKichThuoc = new javax.swing.JButton();
        btnAddChatLieu = new javax.swing.JButton();
        btnAddThuongHieu = new javax.swing.JButton();
        cboMauSac = new javax.swing.JComboBox<>();
        cboKichThuoc = new javax.swing.JComboBox<>();
        cboTenSP = new javax.swing.JComboBox<>();
        cboChatLieu = new javax.swing.JComboBox<>();
        cboThuonHieu = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        btnAddCTSP = new javax.swing.JButton();
        btnUpdateCTSP = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDeleteCTSP = new javax.swing.JButton();

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblSPCT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên sản phẩm", "Màu sắc", "Size", "Chất liệu", "Hãng", "Giá bán", "Số lượng", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSPCT);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        cboFilterMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterMauSacActionPerformed(evt);
            }
        });

        cboFilterKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterKichThuocActionPerformed(evt);
            }
        });

        cboFilterChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterChatLieuActionPerformed(evt);
            }
        });

        cboFilterThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterThuongHieuActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Trạng thái");

        cboFilterTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Còn hàng", "Hết hàng" }));
        cboFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterTrangThaiActionPerformed(evt);
            }
        });

        cboFilterSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFilterSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboFilterSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFilterSPItemStateChanged(evt);
            }
        });
        cboFilterSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterSP, 0, 790, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cboFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilterSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã SPCT");

        txtMaCTSP.setEditable(false);
        txtMaCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCTSPActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Giá bán(VNĐ)");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Số lượng tồn");
        jLabel4.setToolTipText("");

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Màu sắc");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Size");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Chất liệu");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Thương hiệu");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mô tả");

        txtMoTaCTSP.setColumns(20);
        txtMoTaCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMoTaCTSP.setRows(5);
        jScrollPane1.setViewportView(txtMoTaCTSP);

        btnAddMauSac.setBackground(new java.awt.Color(51, 204, 255));
        btnAddMauSac.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddMauSac.setText("+");
        btnAddMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMauSacActionPerformed(evt);
            }
        });

        btnAddKichThuoc.setBackground(new java.awt.Color(51, 204, 255));
        btnAddKichThuoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddKichThuoc.setText("+");
        btnAddKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKichThuocActionPerformed(evt);
            }
        });

        btnAddChatLieu.setBackground(new java.awt.Color(51, 204, 255));
        btnAddChatLieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddChatLieu.setText("+");
        btnAddChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatLieuActionPerformed(evt);
            }
        });

        btnAddThuongHieu.setBackground(new java.awt.Color(51, 204, 255));
        btnAddThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddThuongHieu.setText("+");
        btnAddThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThuongHieuActionPerformed(evt);
            }
        });

        cboMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        cboKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocActionPerformed(evt);
            }
        });

        cboTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSPActionPerformed(evt);
            }
        });

        cboChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuActionPerformed(evt);
            }
        });

        cboThuonHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThuonHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaCTSP)
                    .addComponent(txtSoLuong)
                    .addComponent(txtGiaBan)
                    .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(249, 249, 249)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboKichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThuonHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAddMauSac)
                                .addGap(1, 1, 1))
                            .addComponent(btnAddThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(114, 114, 114)
                        .addComponent(jLabel9))
                    .addComponent(btnAddKichThuoc)
                    .addComponent(btnAddChatLieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(btnAddMauSac)
                                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(btnAddKichThuoc)
                                    .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(btnAddChatLieu)
                                    .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(btnAddThuongHieu)
                                    .addComponent(cboThuonHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtMaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tương tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btnAddCTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnAddCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddCTSP.setText("Thêm sản phẩm");
        btnAddCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCTSPActionPerformed(evt);
            }
        });

        btnUpdateCTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdateCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateCTSP.setText("Cập nhật sản phẩm");
        btnUpdateCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCTSPActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(51, 153, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDeleteCTSP.setBackground(new java.awt.Color(51, 153, 255));
        btnDeleteCTSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteCTSP.setText("Xóa sản phẩm");
        btnDeleteCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCTSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddCTSP)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateCTSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btnReset)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteCTSP)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(664, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(548, 548, 548))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 634, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(65, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        searchByProductName();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cboFilterMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterMauSacActionPerformed
        selectedFilterMSItem = (String) cboFilterMauSac.getSelectedItem();
        selectedMauSacID = selectedFilterMSItem;
        List<ColorModel> listMS = msrs.getIDByTenMS(selectedMauSacID);
        if (!listMS.isEmpty()) {
            selectedMauSacID = listMS.get(0).getID();
            List<ProductDetailModel> listCTSP = ctsprp.searchByMauSacID(selectedMauSacID);
            fillTable(listCTSP);
            cboFilterMauSac.setSelectedItem(selectedFilterMSItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterMauSac.setSelectedItem(selectedFilterMSItem);
        }
    }//GEN-LAST:event_cboFilterMauSacActionPerformed

    private void cboFilterKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterKichThuocActionPerformed
        selectedFilterSizeItem = (String) cboFilterKichThuoc.getSelectedItem();
        selectedSizeID = selectedFilterSizeItem;
        List<SizeModel> listKC = kcrs.getIDByTenKC(selectedSizeID);
        if (!listKC.isEmpty()) {
            selectedSizeID = listKC.get(0).getID();
            List<ProductDetailModel> listCTSP = ctsprp.searchBySizeID(selectedSizeID);
            fillTable(listCTSP);
            cboFilterKichThuoc.setSelectedItem(selectedFilterSizeItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterKichThuoc.setSelectedItem(selectedFilterSizeItem);
        }
    }//GEN-LAST:event_cboFilterKichThuocActionPerformed

    private void cboFilterChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterChatLieuActionPerformed
        selectedFilterChatLieuItem = (String) cboFilterChatLieu.getSelectedItem();
        selectedChatLieuID = selectedFilterChatLieuItem;
        List<MaterialModel> listCL = clrs.getIDByTenCL(selectedChatLieuID);
        if (listCL.size() > 0) {
            selectedChatLieuID = listCL.get(0).getID();
            List<ProductDetailModel> listCTSP = ctsprp.searchByChatLieuID(selectedChatLieuID);
            fillTable(listCTSP);
            cboFilterChatLieu.setSelectedItem(selectedFilterChatLieuItem);
        } else {
            selectedChatLieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterChatLieu.setSelectedItem(selectedFilterChatLieuItem);
        }
    }//GEN-LAST:event_cboFilterChatLieuActionPerformed

    private void cboFilterThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterThuongHieuActionPerformed
        selectedFilterThuongHieuItem = (String) cboFilterThuongHieu.getSelectedItem();
        selectedThuongHieuID = selectedFilterThuongHieuItem;
        List<BrandModel> listTH = thrs.getIDByTenTH(selectedThuongHieuID);
        if (!listTH.isEmpty()) {
            selectedThuongHieuID = listTH.get(0).getID();
            List<ProductDetailModel> listCTSP = ctsprp.searchByThuonghieuID(selectedThuongHieuID);
            fillTable(listCTSP);
            cboFilterThuongHieu.setSelectedItem(selectedFilterThuongHieuItem);
        } else {
            selectedThuongHieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterThuongHieu.setSelectedItem(selectedFilterThuongHieuItem); // Thiết lập lại giá trị của combobox
        }
    }//GEN-LAST:event_cboFilterThuongHieuActionPerformed

    private void cboFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterTrangThaiActionPerformed
        String selectedTrangThai = (String) cboFilterTrangThai.getSelectedItem();
        List<ProductDetailModel> listCTSP;

        if (selectedTrangThai.equals("Còn hàng")) {
            listCTSP = ctsprp.getAllCTSPSoluongLonHon0();
        } else if (selectedTrangThai.equals("Hết hàng")) {
            listCTSP = ctsprp.getAllCTSPSoluong0(); // Sử dụng phương thức getAllCTSPSoluong0 để lấy các sản phẩm có số
            // lượng tồn bằng 0
        } else {
            listCTSP = ctsprp.getAllCTSP();
        }

        fillTable(listCTSP);
    }//GEN-LAST:event_cboFilterTrangThaiActionPerformed

    private void cboFilterSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFilterSPItemStateChanged

    }//GEN-LAST:event_cboFilterSPItemStateChanged

    private void cboFilterSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterSPActionPerformed
        selectedFilterSPItem = (String) cboFilterSP.getSelectedItem();
        String selectedSanPham = selectedFilterSPItem;
        List<ProductsModel> listSP = sprs.getIDByTenSP(selectedSanPham);
        if (!listSP.isEmpty()) {
            selectedSanPhamID = listSP.get(0).getID();
            List<ProductDetailModel> listCTSP = ctsprp.searchBySanPhamID(selectedSanPhamID);
            fillTable(listCTSP);
            cboFilterSP.setSelectedItem(selectedFilterSPItem);
        } else {
            selectedSanPhamID = null;
            fillTable(ctsprp.getAllCTSP());
            cboFilterSP.setSelectedItem(selectedFilterSPItem);
        }
    }//GEN-LAST:event_cboFilterSPActionPerformed

    private void btnAddMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMauSacActionPerformed
        this.openMauSac();
    }//GEN-LAST:event_btnAddMauSacActionPerformed

    private void btnAddKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKichThuocActionPerformed
        this.openKichThuoc();
    }//GEN-LAST:event_btnAddKichThuocActionPerformed

    private void btnAddChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChatLieuActionPerformed
        this.openChatlieu();
    }//GEN-LAST:event_btnAddChatLieuActionPerformed

    private void btnAddThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThuongHieuActionPerformed
        this.openThuonHieu();
    }//GEN-LAST:event_btnAddThuongHieuActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichThuocActionPerformed

    private void cboTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenSPActionPerformed

    private void cboChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieuActionPerformed

    private void btnAddCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCTSPActionPerformed
        if (!validatef()) {
            return;
        }

        ProductDetailModel chiTietSanPham = readForm();

        MessageAlerts.getInstance().showMessage("Xác nhận thêm sản phẩm",
                "Bạn có chắc muốn thêm sản phẩm này?",
                MessageAlerts.MessageType.SUCCESS,
                MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                    if (option == MessageAlerts.YES_OPTION) {
                        String newID = ctsprp.getNewSPCTID();
                        chiTietSanPham.setID(newID);
                        
                        if (ctsprp.checkTrungIdCTSP(chiTietSanPham.getID())) {
                            Notifications.getInstance().show(Notifications.Type.WARNING, "Mã sản phẩm chi tiết đã tồn tại!");
                            return;
                        }
                        
                        int result = ctsprp.insert(chiTietSanPham);
                        
                        if (result > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm thành công");
                            fillTable(ctsprp.getAllCTSP());
                            clear();
                            refreshData();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm thất bại");
                        }
                    }
        });
    }//GEN-LAST:event_btnAddCTSPActionPerformed

    private void btnUpdateCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCTSPActionPerformed
        int rowSel = tblSPCT.getSelectedRow();
        if (rowSel == -1) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn một sản phẩm để cập nhật.");
            return;
        }

        if (!validatef()) {
            return;
        }

        MessageAlerts.getInstance().showMessage("Xác nhận cập nhật",
                "Bạn có chắc muốn cập nhật sản phẩm này?",
                MessageAlerts.MessageType.WARNING,
                MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                    if (option == MessageAlerts.YES_OPTION) {
                        ProductDetailModel chiTietSanPham = readForm();
                        ctsprp.update(chiTietSanPham);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật thành công");
                        fillTable(ctsprp.getAllCTSP());
                        clear();
                        refreshData();
                    }
        });
    }//GEN-LAST:event_btnUpdateCTSPActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clear();
        fillTable(ctsprp.getAllCTSP());
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCTSPActionPerformed
        int rowSel = tblSPCT.getSelectedRow();
        if (rowSel >= 0) {
            String ma = tblSPCT.getValueAt(rowSel, 1).toString();
            if (ctsprp.checkTonTaiHDCT(ma)) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Không thể xóa sản phẩm chi tiết này vì đang tồn tại trong hóa đơn chi tiết!");
                return;
            }

            MessageAlerts.getInstance().showMessage("Xác nhận xóa",
                    "Bạn có chắc muốn xóa sản phẩm này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION, (PopupController pc, int option) -> {
                        if (option == MessageAlerts.YES_OPTION) {
                            if (ctsprp.delete(ma) > 0) {
                                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xoá thành công!");
                                refreshData();
                                fillTable(ctsprp.getAllCTSP());
                                clear();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.WARNING, "Không thể xóa do sản phẩm chi tiết đang được sử dụng ở hóa đơn");
                            }
                        }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng chọn một sản phẩm để xóa");
        }
    }//GEN-LAST:event_btnDeleteCTSPActionPerformed

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        index = tblSPCT.getSelectedRow();
        this.showData(index);
    }//GEN-LAST:event_tblSPCTMouseClicked

    private void txtMaCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCTSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCTSPActionPerformed

    @Override
    public boolean formClose() {
        if (!txtMaCTSP.getText().trim().isEmpty()
                || !txtGiaBan.getText().trim().isEmpty()
                || !txtMoTaCTSP.getText().trim().isEmpty()
                || !txtSoLuong.getText().trim().isEmpty()
                || !txtTimKiem.getText().trim().isEmpty()) {

            int opt = JOptionPane.showConfirmDialog(this, "Dữ liệu chưa được lưu, bạn có chắc chắn muốn đóng tab ?", "Close", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            return opt == JOptionPane.YES_OPTION;
        }

        // Nếu tất cả các trường đều rỗng, cho phép đóng mà không cần xác nhận
        return true;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCTSP;
    private javax.swing.JButton btnAddChatLieu;
    private javax.swing.JButton btnAddKichThuoc;
    private javax.swing.JButton btnAddMauSac;
    private javax.swing.JButton btnAddThuongHieu;
    private javax.swing.JButton btnDeleteCTSP;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateCTSP;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboFilterChatLieu;
    private javax.swing.JComboBox<String> cboFilterKichThuoc;
    private javax.swing.JComboBox<String> cboFilterMauSac;
    private javax.swing.JComboBox<String> cboFilterSP;
    private javax.swing.JComboBox<String> cboFilterThuongHieu;
    private javax.swing.JComboBox<String> cboFilterTrangThai;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboTenSP;
    private javax.swing.JComboBox<String> cboThuonHieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private pagination.Pagination pagination1;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaCTSP;
    private javax.swing.JTextArea txtMoTaCTSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
