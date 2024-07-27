package app.tabbed;

import javax.swing.JPanel;

public class TabbedForm extends JPanel { // Kế thừa JPanel để tạo form với các tab

    public void formOpen() { // Phương thức mở form
        // Hiện tại chưa có logic trong phương thức này
    }

    public boolean formClose() { // Phương thức đóng form
        return true; // Trả về true khi form được đóng
    }

   public void fromRefresh() {
        // Phương thức mặc định, có thể để trống để ghi đè
    }
}
