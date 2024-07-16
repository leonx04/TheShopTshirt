package app.tabbed;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class TabbedItem extends JToggleButton { // Kế thừa JToggleButton để tạo item trong tab

    protected final TabbedForm component; // Khai báo biến component để chứa form của tab

    public TabbedItem(String name, TabbedForm component) { // Constructor của TabbedItem
        this.component = component; // Gán component cho biến thành viên
        init(name); // Gọi phương thức khởi tạo
    }

    private void init(String name) { // Phương thức khởi tạo
        setLayout(new MigLayout("", "[]10[]")); // Thiết lập layout cho TabbedItem
        putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;" // Không viền
                + "focusWidth:0;" // Không có đường viền khi focus
                + "innerFocusWidth:0;" // Không có đường viền bên trong khi focus
                + "background:null;" // Không có nền
                + "arc:10;" // Bo góc 10 pixel
                + "margin:2,8,2,5"); // Thiết lập lề

        JButton cmd = new JButton(new FlatSVGIcon("app/svg/close.svg", 0.8f)); // Tạo nút đóng với icon SVG
        cmd.addActionListener((ae) -> {
            WindowsTabbed.getInstance().removeTab(this); // Gọi phương thức để xóa tab khi nút đóng được bấm
        });
        cmd.putClientProperty(FlatClientProperties.STYLE, ""
                + "margin:3,3,3,3;" // Thiết lập lề
                + "borderWidth:0;" // Không viền
                + "focusWidth:0;" // Không có đường viền khi focus
                + "innerFocusWidth:0;" // Không có đường viền bên trong khi focus
                + "background:null;" // Không có nền
                + "arc:999;"); // Bo góc hoàn toàn (hình tròn)
        
        add(new JLabel(name)); // Thêm nhãn với tên tab
        add(cmd, BorderLayout.EAST); // Thêm nút đóng vào góc phải
    }

    @Override
    public void paint(Graphics grphcs) { // Ghi đè phương thức paint để vẽ item
        super.paint(grphcs); // Gọi phương thức paint của lớp cha
        if (!isSelected() && getParent().getComponentZOrder(this) != getParent().getComponentCount() - 1) { 
            // Kiểm tra nếu item không được chọn và không phải là item cuối cùng
            Graphics2D g2 = (Graphics2D) grphcs.create(); // Tạo đối tượng Graphics2D từ Graphics
            FlatUIUtils.setRenderingHints(g2); // Thiết lập các gợi ý vẽ để cải thiện chất lượng
            g2.setColor(UIManager.getColor("Component.borderColor")); // Lấy màu viền từ UIManager
            float m = UIScale.scale(5); // Thiết lập lề trên dưới
            float s = UIScale.scale(1); // Thiết lập độ dày của đường viền
            g2.fill(new Rectangle2D.Double(getWidth() - s, m, s, getHeight() - m * 2)); // Vẽ đường viền bên phải
            g2.dispose(); // Giải phóng tài nguyên Graphics2D
        }
    }

    void refresh() {
        // Viết code để làm mới toàn bộ các tab 
    }
}
