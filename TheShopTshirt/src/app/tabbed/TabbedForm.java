package app.tabbed;

import java.awt.Component;
import javax.swing.JPanel;

public class TabbedForm extends JPanel { // Kế thừa JPanel để tạo form với các tab

    public void formOpen() { // Phương thức mở form
        // Hiện tại chưa có logic trong phương thức này
    }

    public boolean formClose() { // Phương thức đóng form
        return true; // Trả về true khi form được đóng
    }

    public void fromRefresh() { // Phương thức làm mới form
         Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof TabbedItem) {
                ((TabbedItem) component).refresh(); // Gọi phương thức refresh của TabbedItem
            }
        }
    }
}
