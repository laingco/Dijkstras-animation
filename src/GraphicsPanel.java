import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GraphicsPanel extends JPanel {

    public GraphicsPanel() {
        setBackground(Color.WHITE);               
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.fillOval(50, 50, 100, 100);

        g2d.setColor(Color.GREEN);
        g2d.fillOval(150, 150, 100, 100);

        g2d.setColor(Color.BLUE);
        g2d.fillOval(250, 250, 100, 100);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(10,10,600,600));
    }
}
