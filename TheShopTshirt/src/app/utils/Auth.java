package app.utils;

import app.model.StaffModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Lớp Auth quản lý thông tin xác thực và phân quyền người dùng.
 * Nó cung cấp các phương thức để lưu trữ, tải và xóa thông tin đăng nhập.
 */
public class Auth {
    // Đối tượng StaffModel đại diện cho người dùng đang đăng nhập
    public static StaffModel user = null;

    // Tên file để lưu trữ thông tin đăng nhập
    private static final String AUTH_FILE = "auth.dat";

    // Static initializer block, chạy khi lớp được tải
    static {
        loadAuth();  // Tải thông tin đăng nhập khi khởi động ứng dụng
    }
    
    /**
     * Lưu thông tin đăng nhập vào file.
     * Được gọi sau khi đăng nhập thành công hoặc khi cần cập nhật thông tin người dùng.
     */
    public static void saveAuth() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(AUTH_FILE))) {
            out.writeObject(user);  // Ghi đối tượng user vào file
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
    }
    
    /**
     * Tải thông tin đăng nhập từ file.
     * Được gọi khi khởi động ứng dụng để khôi phục phiên đăng nhập trước đó.
     */
    private static void loadAuth() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(AUTH_FILE))) {
            user = (StaffModel) in.readObject();  // Đọc đối tượng user từ file
        } catch (Exception e) {
            // File có thể không tồn tại trong lần chạy đầu tiên, điều này là bình thường
            user = null;  // Đặt user là null nếu không đọc được file
        }
    }

    /**
     * Xóa thông tin đăng nhập.
     * Được gọi khi người dùng đăng xuất.
     */
    public static void clear() {
        user = null;  // Đặt user về null
        saveAuth();   // Lưu trạng thái null vào file
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập hay chưa.
     * @return true nếu đã đăng nhập, false nếu chưa
     */
    public static boolean isLogin() {
        return user != null;  // Người dùng đã đăng nhập nếu user không null
    }

    /**
     * Kiểm tra xem người dùng đăng nhập có phải là quản lý hay không.
     * @return true nếu là quản lý, false nếu không phải hoặc chưa đăng nhập
     */
    public static boolean isManager() {
        return isLogin() && user.isChucVu();  // Kiểm tra đăng nhập và chức vụ
    }
}