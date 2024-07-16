package app.tabbed;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import net.miginfocom.swing.MigLayout;

public class PanelTabbed extends JPanel { // Kế thừa JPanel để tạo panel có các tab

    private final ButtonGroup buttonGroup; // Nhóm các nút chuyển đổi (toggle button)

    public PanelTabbed() { // Constructor của lớp PanelTabbed
        setLayout(new MigLayout("filly,insets 3 10 3 10")); // Thiết lập layout cho panel với MigLayout
        buttonGroup = new ButtonGroup(); // Khởi tạo nhóm các nút chuyển đổi
    }

    public void addTab(JToggleButton item) { // Phương thức thêm tab mới
        buttonGroup.add(item); // Thêm nút chuyển đổi vào nhóm
        add(item); // Thêm nút chuyển đổi vào panel
        repaint(); // Vẽ lại giao diện
        revalidate(); // Cập nhật lại giao diện
    }
}
