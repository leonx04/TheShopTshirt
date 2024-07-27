package app.login;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import app.main.Main;
import app.model.StaffModel;
import app.service.StaffService;
import app.utils.Auth;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import raven.toast.Notifications;

public class Login extends JPanel { // Kế thừa JPanel để tạo giao diện đăng nhập

    public Login() { // Constructor của lớp Login
        init(); // Gọi phương thức init để khởi tạo giao diện
    }

    private void init() { // Phương thức khởi tạo giao diện
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]")); // Thiết lập layout với MigLayout
        txtUsername = new JTextField(); // Khởi tạo trường nhập liệu tên người dùng
        txtPassword = new JPasswordField(); // Khởi tạo trường nhập liệu mật khẩu
        cmdLogin = new JButton("Login"); // Khởi tạo nút đăng nhập

        // Thêm key binding để nhấn Enter tương đương với nhấn nút Login
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "login");
        getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmdLogin.doClick();
            }
        });

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 35 45", "fill,250:280")); // Khởi tạo panel với MigLayout
        panel.putClientProperty(FlatClientProperties.STYLE, "" // Thiết lập các thuộc tính giao diện cho panel
                + "arc:20;"
                + "[light]background:darken(@background,3%);"
                + "[dark]background:lighten(@background,3%)");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" // Thiết lập thuộc tính hiển thị nút reveal cho trường mật khẩu
                + "showRevealButton:true");

        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" // Thiết lập các thuộc tính giao diện cho nút đăng nhập
                + "[light]background:darken(@background,10%);"
                + "[dark]background:lighten(@background,10%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        cmdLogin.addActionListener((e) -> {
            String email = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            if (email.isEmpty() && pass.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập tên đăng nhập và Mật Khẩu");
                txtUsername.requestFocus();
            } else if (email.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập tên đăng nhập");
                txtUsername.requestFocus();
            } else if (pass.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập mật khẩu.");
                txtPassword.requestFocus();
            } else {
                if (staffService.isAccountInactive(email)) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Bạn đang có trạng thái là Nghỉ việc nên không thể đăng nhập");
                } else {
                    StaffModel staff = staffService.checklogin(email, pass);
                    if (staff != null) {
                        String position = staff.isChucVu() ? "Quản lý" : "Nhân viên";
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đăng nhập thành công.");
                        
                        Auth.user = staff;
                        Auth.saveAuth();  // Lưu trạng thái đăng nhập
                        
                        Main.main.showMainForm();
                        Main.main.updateDrawer();  // Cập nhật drawer sau khi đăng nhập
                        Notifications.getInstance().show(Notifications.Type.INFO, "Chào mừng trở lại: " + position);
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng thử lại.");
                    }
                }
            }
        });

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email"); // Thiết lập văn bản gợi ý cho trường tên người dùng
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password"); // Thiết lập văn bản gợi ý cho trường mật khẩu

        JLabel lbTitle = new JLabel("Welcome - The Shop Tshirt "); // Khởi tạo nhãn tiêu đề
        JLabel description = new JLabel("Please sign in to access your account"); // Khởi tạo nhãn mô tả
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" // Thiết lập thuộc tính giao diện cho nhãn tiêu đề
                + "font:bold +10");

        description.putClientProperty(FlatClientProperties.STYLE, "" // Thiết lập thuộc tính giao diện cho nhãn mô tả
                + "[light]foreground:lighten(@foreground,30%);"
                + "[dark]foreground:darken(@foreground,30%)");

        panel.add(lbTitle); // Thêm nhãn tiêu đề vào panel
        panel.add(description); // Thêm nhãn mô tả vào panel
        panel.add(new JLabel("Username"), "gapy 8"); // Thêm nhãn "Username" vào panel với khoảng cách trên là 8px
        panel.add(txtUsername); // Thêm trường nhập liệu tên người dùng vào panel
        panel.add(new JLabel("Password"), "gapy 8"); // Thêm nhãn "Password" vào panel với khoảng cách trên là 8px
        panel.add(txtPassword); // Thêm trường nhập liệu mật khẩu vào panel
        panel.add(cmdLogin, "gapy 10"); // Thêm nút đăng nhập vào panel với khoảng cách trên là 10px

        add(panel); // Thêm panel vào JPanel chính
    }

    private JTextField txtUsername; // Khai báo biến cho trường nhập liệu tên người dùng
    private JPasswordField txtPassword; // Khai báo biến cho trường nhập liệu mật khẩu
    private JButton cmdLogin; // Khai báo biến cho nút đăng nhập
    private StaffService staffService = new StaffService();
}
