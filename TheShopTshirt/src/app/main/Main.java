package app.main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.UIManager;
import raven.drawer.Drawer;
import app.drawer.MyDrawerBuilder;
import app.login.Login;
import raven.popup.GlassPanePopup;
import app.tabbed.WindowsTabbed;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import raven.toast.Notifications;

public class Main extends javax.swing.JFrame {

    public static Main main; // Khai báo biến tĩnh để giữ tham chiếu đến đối tượng Main
    private Login loginForm; // Khai báo biến cho form đăng nhập

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents(); // Gọi phương thức khởi tạo các thành phần giao diện
        init(); // Gọi phương thức khởi tạo các thành phần bổ sung
    }

    private void init() {
        GlassPanePopup.install(this); // Cài đặt GlassPanePopup cho JFrame
        Notifications.getInstance().setJFrame(this); // Thiết lập JFrame cho thông báo
        MyDrawerBuilder myDrawerBuilder = new MyDrawerBuilder(); // Tạo đối tượng MyDrawerBuilder
        Drawer.getInstance().setDrawerBuilder(myDrawerBuilder); // Thiết lập drawer builder
        WindowsTabbed.getInstance().install(this, body); // Cài đặt WindowsTabbed cho JFrame
        setIconImage(); // Thiết lập biểu tượng ứng dụng
        // applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        login(); // Gọi phương thức đăng nhập
    }

    private void setIconImage() {
        try {
            // Đọc ảnh từ tệp và đặt nó làm biểu tượng của ứng dụng
            Image image = ImageIO.read(getClass().getResource("/app/image/profile.jpg")); // Thay đổi tên tệp ảnh nếu cần
            setIconImage(image); // Đặt biểu tượng ứng dụng
        } catch (IOException e) {
            // Xử lý nếu có lỗi khi đọc ảnh
            e.printStackTrace(); // In ra lỗi
        }
    }

    public void login() {
        if (loginForm == null) { // Kiểm tra nếu form đăng nhập chưa được khởi tạo
            loginForm = new Login(); // Khởi tạo form đăng nhập
        }
        WindowsTabbed.getInstance().showTabbed(false); // Ẩn các tab
        loginForm.applyComponentOrientation(getComponentOrientation()); // Áp dụng hướng của thành phần
        setContentPane(loginForm); // Đặt nội dung của JFrame là form đăng nhập
        revalidate(); // Cập nhật lại giao diện
        repaint(); // Vẽ lại giao diện
    }

    public void showMainForm() {
        WindowsTabbed.getInstance().showTabbed(true); // Hiển thị các tab
        setContentPane(body); // Đặt nội dung của JFrame là body
        revalidate(); // Cập nhật lại giao diện
        repaint(); // Vẽ lại giao diện
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install(); // Cài đặt font Roboto
        FlatLaf.registerCustomDefaultsSource("app.themes"); // Đăng ký nguồn tùy chỉnh cho FlatLaf
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13)); // Đặt font mặc định cho ứng dụng
        FlatMacDarkLaf.setup(); // Thiết lập giao diện FlatMacDarkLaf hoặc FlatLightLaf 
        java.awt.EventQueue.invokeLater(() -> {
            main = new Main(); // Tạo đối tượng Main
            main.setVisible(true); // Hiển thị JFrame
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
