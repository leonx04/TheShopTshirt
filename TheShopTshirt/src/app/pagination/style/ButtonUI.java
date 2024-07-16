package pagination.style;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonUI extends BasicButtonUI {

    private JButton button;
    private final Color BUTTON_COLOR = new Color(51, 122, 183); // Màu xanh dương

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        button = (JButton) c;
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setForeground(Color.WHITE); // Màu chữ trắng
        button.setBackground(BUTTON_COLOR);
        button.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = c.getWidth();
        int height = c.getHeight();
        Shape shape = new RoundRectangle2D.Double(0, 0, width, height, 5, 5);
        
        g2.setColor(BUTTON_COLOR);
        g2.fill(shape);
        g2.dispose();

        super.paint(g, c);
    }
}