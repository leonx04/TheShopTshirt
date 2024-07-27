package app.drawer;

import app.form.Bill;
import app.form.Chart;
import app.form.Customer;
import app.form.DetailProduct;
import app.form.ProductAttributes;
import app.form.Products;
import app.form.Sell;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import app.form.Home;
import app.form.Staff;
import app.form.Voucher;
import app.main.Main;
import app.tabbed.TabbedForm;
import app.tabbed.TabbedItem;
import raven.swing.AvatarIcon;
import app.tabbed.WindowsTabbed;
import raven.toast.Notifications;
import app.utils.Auth; // Import lớp Auth

public class MyDrawerBuilder extends SimpleDrawerBuilder {

    public MyDrawerBuilder() {
        
    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/app/image/profile.jpg"), 80, 80, 1000))
                .setTitle("Tshirt");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String menus[][] = {
            {"~TRANG CHÍNH~"},
            {"Trang chủ"},
            {"Bán hàng"},
            {"~ỨNG DỤNG~"},
            {"Sản phẩm", "Sản phẩm", "Sản phẩm chi tiết", "Thuộc tính sản phẩm"},
            {"Hóa đơn"},
            {"Voucher"},
            {"Nhân viên"},
            {"Khách hàng"},
            {"~KHÁC~"},
            {"Charts"},
            {"Đăng xuất"}
        };

        String icons[] = {
            "home.svg",
            "sell.svg",
            "product.svg",
            "bill.svg",
            "voucher.svg",
            "staff.svg",
            "customer.svg",
            "chart.svg",
            "logout.svg"
        };

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIcons(icons)
                .setBaseIconPath("app/drawer/icon")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        String tabTitle = "";
                        TabbedForm form = null;

                        // Xác định tiêu đề tab và form dựa trên index và subIndex
                        if (index == 0) {
                            tabTitle = "Trang chủ";
                            form = new Home();
                        } else if (index == 1) {
                            tabTitle = "Bán hàng";
                            form = new Sell();
                        } else if (index == 2) {
                            // Kiểm tra nếu đây là menu cha (không có subIndex)
                            if (subIndex == -1) {
                                // Không làm gì cả vì đây là menu cha
                                return;
                            }
                            if (subIndex == 1) {
                                tabTitle = "Sản Phẩm";
                                form = new Products();
                            } else if (subIndex == 2) {
                                tabTitle = "Sản phẩm chi tiết";
                                form = new DetailProduct();
                            } else if (subIndex == 3) {
                                tabTitle = "Thuộc tính sản phẩm";
                                form = new ProductAttributes();
                            }
                        } else if (index == 3) {
                            if (Auth.isManager()) {
                                tabTitle = "Hóa đơn";
                                form = new Bill();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Bạn không đủ quyền hạn xem thông tin này");
                                return;
                            }
                        } else if (index == 4) {
                            tabTitle = "Voucher";
                            form = new Voucher();
                        } else if (index == 5) {
                            tabTitle = "Nhân viên";
                            form = new Staff();
                        } else if (index == 6) {
                            tabTitle = "Khách hàng";
                            form = new Customer();
                        } else if (index == 7) {
                            if (Auth.isManager()) {
                                tabTitle = "Charts";
                                form = new Chart();
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, "Bạn không đủ quyền hạn xem thông tin này");
                                return;
                            }
                        } else if (index == 8) {
                            Auth.clear();
                            Main.main.login();
                            Main.main.updateDrawer();
                            return;
                        }

                        if (form != null) {
                            TabbedItem existingTab = WindowsTabbed.getInstance().findOpenTab(tabTitle);
                            if (existingTab != null) {
                                Notifications.getInstance().show(Notifications.Type.INFO, "Bạn đã mở tab này rồi");
                                // Tab đã tồn tại, focus vào nó
                                existingTab.setSelected(true);
                                WindowsTabbed.getInstance().showForm(existingTab.component);
                            } else {
                                // Tab chưa tồn tại, tạo mới
                                WindowsTabbed.getInstance().addTab(tabTitle, form);
                            }
                        }
                    }
                })
                .setMenuValidation(new MenuValidation() {
                    @Override
                    public boolean menuValidation(int index, int subIndex) {
                        System.out.println("Đã đăng nhập: " + Auth.isLogin());
                        System.out.println("Chức vụ: " + (Auth.isLogin() ? Auth.user.isChucVu() : "Chưa đăng nhập"));

                        // Ẩn menu Hóa đơn và Charts nếu không phải là quản lý
                        if (!Auth.isManager() && (index == 3 || index == 7)) {
                            return false;
                        }
                        return true;
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("Trang shop Tshirt")
                .setDescription("Version 1.1.0");
    }

    @Override
    public int getDrawerWidth() {
        return 275;
    }
}
