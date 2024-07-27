package app.tabbed;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class PanelTabbed extends JPanel { // Kế thừa JPanel để tạo panel có các tab

    private final ButtonGroup buttonGroup; // Nhóm các nút chuyển đổi (toggle button)
    private final List<TabbedItem> tabs; // Danh sách các tab

    public PanelTabbed() { // Constructor của lớp PanelTabbed
        setLayout(new MigLayout("filly,insets 3 10 3 10")); // Thiết lập layout cho panel với MigLayout
        buttonGroup = new ButtonGroup(); // Khởi tạo nhóm các nút chuyển đổi
        tabs = new ArrayList<>(); // Khởi tạo danh sách các tab
    }

    public void addTab(TabbedItem item) { // Phương thức thêm tab mới
        buttonGroup.add(item); // Thêm nút chuyển đổi vào nhóm
        tabs.add(item); // Thêm tab vào danh sách
        add(item); // Thêm nút chuyển đổi vào panel
        repaint(); // Vẽ lại giao diện
        revalidate(); // Cập nhật lại giao diện
    }

    public void removeTab(TabbedItem item) { // Phương thức xóa tab
        buttonGroup.remove(item); // Xóa nút chuyển đổi khỏi nhóm
        tabs.remove(item); // Xóa tab khỏi danh sách
        remove(item); // Xóa nút chuyển đổi khỏi panel
        repaint(); // Vẽ lại giao diện
        revalidate(); // Cập nhật lại giao diện
    }

    public List<TabbedItem> getTabs() { // Phương thức trả về danh sách các tab
        return tabs;
    }
}
