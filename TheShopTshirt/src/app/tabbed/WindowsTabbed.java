package app.tabbed;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import raven.drawer.Drawer;

public class WindowsTabbed {

    private static WindowsTabbed instance; // Singleton instance của WindowsTabbed
    private JMenuBar menuBar; // Thanh menu
    private PanelTabbed panelTabbed; // Panel chứa các tab
    private JPanel body; // Panel chính
    private TabbedForm temp; // Biến tạm để lưu trữ form

    public static WindowsTabbed getInstance() { // Phương thức lấy instance
        if (instance == null) {
            instance = new WindowsTabbed(); // Khởi tạo nếu chưa có instance
        }
        return instance; // Trả về instance
    }

    public void install(JFrame frame, JPanel body) { // Phương thức cài đặt cho JFrame và panel chính
        this.body = body; // Gán body cho biến thành viên
        menuBar = new JMenuBar(); // Tạo thanh menu
        menuBar.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderColor:$TitlePane.background;" // Thiết lập màu viền
                + "border:0,0,0,0"); // Thiết lập không viền
        panelTabbed = new PanelTabbed(); // Tạo PanelTabbed
        panelTabbed.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$TitlePane.background"); // Thiết lập màu nền
        menuBar.add(createDrawerButton()); // Thêm nút Drawer vào thanh menu
        menuBar.add(createScroll(panelTabbed)); // Thêm panelTabbed vào thanh menu dưới dạng cuộn
        frame.setJMenuBar(menuBar); // Đặt thanh menu cho frame
    }

    public void showTabbed(boolean show) { // Phương thức hiển thị hoặc ẩn thanh menu
        menuBar.setVisible(show); // Hiển thị hoặc ẩn thanh menu
        if (!show) {
            Drawer.getInstance().closeDrawer(); // Đóng Drawer nếu ẩn thanh menu
        }
    }

    private JButton createDrawerButton() { // Phương thức tạo nút Drawer
        JButton cmd = new JButton(new FlatSVGIcon("app/svg/menu.svg", 0.9f)); // Tạo nút với icon SVG
        cmd.addActionListener((ae) -> {
            Drawer.getInstance().showDrawer(); // Hiển thị Drawer khi bấm nút
        });
        cmd.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;" // Không viền
                + "focusWidth:0;" // Không có đường viền khi focus
                + "innerFocusWidth:0;" // Không có đường viền bên trong khi focus
                + "background:null;" // Không có nền
                + "arc:5"); // Bo góc 5 pixel
        return cmd; // Trả về nút Drawer
    }

    private JScrollPane createScroll(Component com) { // Phương thức tạo JScrollPane
        JScrollPane scroll = new JScrollPane(com); // Tạo JScrollPane chứa component
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Không hiển thị thanh cuộn dọc
        scroll.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "width:0"); // Thiết lập độ rộng thanh cuộn ngang là 0
        scroll.getHorizontalScrollBar().setUnitIncrement(10); // Thiết lập đơn vị cuộn ngang là 10
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0"); // Thiết lập không viền
        return scroll; // Trả về JScrollPane
    }

    public void addTab(String title, TabbedForm component) { // Phương thức thêm tab
        TabbedItem item = new TabbedItem(title, component); // Tạo TabbedItem mới
        item.addActionListener(new ActionListener() { // Thêm ActionListener cho TabbedItem
            @Override
            public void actionPerformed(ActionEvent ae) {
                showForm(item.component); // Hiển thị form khi tab được chọn
            }
        });
        panelTabbed.addTab(item); // Thêm TabbedItem vào panelTabbed
        showForm(component); // Hiển thị form
        item.setSelected(true); // Đặt tab là đã chọn
    }

    public void removeTab(TabbedItem tab) { // Phương thức xóa tab
        if (tab.component.formClose()) { // Kiểm tra nếu form có thể đóng
            if (tab.isSelected()) { // Nếu tab đang được chọn
                body.removeAll(); // Xóa hết nội dung trong body
                body.revalidate(); // Cập nhật lại giao diện
                body.repaint(); // Vẽ lại giao diện
            }
            panelTabbed.remove(tab); // Xóa tab khỏi panelTabbed
            panelTabbed.revalidate(); // Cập nhật lại giao diện panelTabbed
            panelTabbed.repaint(); // Vẽ lại panelTabbed
        }
    }

    public void showForm(TabbedForm component) { // Phương thức hiển thị form
        body.removeAll(); // Xóa hết nội dung trong body
        body.add(component); // Thêm form vào body
        body.repaint(); // Vẽ lại body
        body.revalidate(); // Cập nhật lại giao diện body
        panelTabbed.repaint(); // Vẽ lại panelTabbed
        panelTabbed.revalidate(); // Cập nhật lại giao diện panelTabbed
        component.formOpen(); // Gọi phương thức mở form
        temp = component; // Gán component cho biến tạm
    }
}
