package app.tabbed;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import raven.drawer.Drawer;
import raven.toast.Notifications;

public class WindowsTabbed {

    private static WindowsTabbed instance;
    private JMenuBar menuBar;
    private PanelTabbed panelTabbed;
    private JPanel body;
    private TabbedForm temp;

    public static WindowsTabbed getInstance() {
        if (instance == null) {
            instance = new WindowsTabbed();
        }
        return instance;
    }

    public void install(JFrame frame, JPanel body) {
        this.body = body;
        menuBar = new JMenuBar();
        menuBar.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderColor:$TitlePane.background;"
                + "border:0,0,0,0");
        panelTabbed = new PanelTabbed();
        panelTabbed.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$TitlePane.background");
        menuBar.add(createDrawerButton());
        menuBar.add(createRefreshButton());
        menuBar.add(createScroll(panelTabbed));
        frame.setJMenuBar(menuBar);
    }

    public void showTabbed(boolean show) {
        menuBar.setVisible(show);
        if (!show) {
            Drawer.getInstance().closeDrawer();
        }
    }

    private JButton createDrawerButton() {
        JButton cmd = new JButton(new FlatSVGIcon("app/svg/menu.svg", 0.9f));
        cmd.addActionListener((ae) -> {
            Drawer.getInstance().showDrawer();
        });
        cmd.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:null;"
                + "arc:5");
        return cmd;
    }

    private JButton createRefreshButton() {
        JButton cmd = new JButton(new FlatSVGIcon("app/svg/refresh.svg", 1.0f)); // Điều chỉnh tỷ lệ
        cmd.addActionListener((ae) -> {
            refreshAllTabs();
        });
        cmd.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0;"
                + "background:null;"
                + "arc:5");
        return cmd;
    }

    private JScrollPane createScroll(Component com) {
        JScrollPane scroll = new JScrollPane(com);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "width:0");
        scroll.getHorizontalScrollBar().setUnitIncrement(10);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");
        return scroll;
    }

    public void addTab(String title, TabbedForm component) {
        TabbedItem existingTab = findOpenTab(title);
        if (existingTab != null) {
            showForm(existingTab.component);
            existingTab.setSelected(true);
        } else {
            TabbedItem item = new TabbedItem(title, component);
            item.addActionListener((ActionEvent ae) -> {
                showForm(item.component);
            });
            panelTabbed.addTab(item);
            showForm(component);
            item.setSelected(true);
        }
    }

    public TabbedItem findOpenTab(String title) {
        for (TabbedItem item : panelTabbed.getTabs()) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return null;
    }

    public void removeTab(TabbedItem tab) {
        if (tab.component.formClose()) {
            if (tab.isSelected()) {
                body.removeAll();
                body.revalidate();
                body.repaint();
            }
            panelTabbed.remove(tab);
            panelTabbed.removeTab(tab);
            panelTabbed.revalidate();
            panelTabbed.repaint();
        }
    }

    public void showForm(TabbedForm component) {
        body.removeAll();
        body.add(component);
        body.repaint();
        body.revalidate();
        panelTabbed.repaint();
        panelTabbed.revalidate();
        component.formOpen();
        temp = component;
    }

    public void refreshAllTabs() {
        for (TabbedItem item : panelTabbed.getTabs()) {
            item.component.fromRefresh();
        }
        if (temp != null) {
            temp.fromRefresh();
        }
        body.revalidate();
        body.repaint();
        panelTabbed.revalidate();
        panelTabbed.repaint();
        Notifications.getInstance().show(Notifications.Type.INFO, "Đã làm mới tất cả các tab.");
    }
}
