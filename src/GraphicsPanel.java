import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    ArrayList<int[]> nodeData = new ArrayList<int[]>(); // (x, y, index, color)
    ArrayList<int[]> lineData = new ArrayList<int[]>(); // (x1, y1, x2, y2 index, color, weight)
    double nodeRadius = 12.5;

    public GraphicsPanel() {
        setBackground(Color.LIGHT_GRAY);               
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < this.lineData.size(); i ++){
                g2d.setColor(parseColor(this.lineData.get(i)[5]));
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new Line2D.Double(
                this.lineData.get(i)[0] + this.nodeRadius,
                this.lineData.get(i)[1] + this.nodeRadius,
                this.lineData.get(i)[2] + this.nodeRadius,
                this.lineData.get(i)[3] + this.nodeRadius
                ));
                g2d.setColor(Color.WHITE);
                g2d.drawString(Integer.toString(this.lineData.get(i)[6]), 
                    (int)((this.lineData.get(i)[0] + this.lineData.get(i)[2]) / 2 + 9/(12.5/this.nodeRadius)), 
                    (int)((this.lineData.get(i)[1] + this.lineData.get(i)[3]) / 2 + 18/(14/this.nodeRadius)));
        }

        for (int i = 0; i < this.nodeData.size(); i++){
            g2d.setColor(parseColor(this.nodeData.get(i)[3]));
            g2d.fillOval(this.nodeData.get(i)[0], this.nodeData.get(i)[1], (int)this.nodeRadius*2, (int)this.nodeRadius*2);
            g2d.setColor(Color.WHITE);
            g2d.drawString(Integer.toString(this.nodeData.get(i)[2]+1), (int)(this.nodeData.get(i)[0]+9/(12.5/this.nodeRadius)), (int)(this.nodeData.get(i)[1]+18/(14/this.nodeRadius)));
        }
    }

    public void addNode(int xPos, int yPos){
        int data[] = {xPos, yPos, this.nodeData.size(), 3};
        this.nodeData.add(data);
        repaint();
    }

    public void addLine(int startIndex, int endIndex, int weight) {
        //System.out.println(startIndex+" "+endIndex);
        int x1 = this.nodeData.get(startIndex)[0];
        int y1 = this.nodeData.get(startIndex)[1];
        int x2 = this.nodeData.get(endIndex)[0];
        int y2 = this.nodeData.get(endIndex)[1];
        int data[] = {x1, y1, x2, y2, this.lineData.size(), 3, weight};
        this.lineData.add(data);
        repaint();
    }

    public void nodeColor(int index, int color){
        this.nodeData.get(index)[3] = color;
        repaint();
    }

    public void lineColor(int index, int color){
        this.lineData.get(index)[5] = color;
        repaint();
    }

    public int getColor(int index){
        return this.nodeData.get(index)[3];
    }

    public int getLineColor(int index){
        return this.lineData.get(index)[5];
    }

    public void clearLineColors() {
        for (int i = 0; i < this.lineData.size(); i++) {
            this.lineData.get(i)[5] = 3;
        }
        repaint();
    }

    public Color parseColor(int color){
        switch (color) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.BLACK;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.CYAN;
            default:
                return Color.BLACK;
        }
    }
}
