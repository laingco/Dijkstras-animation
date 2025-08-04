import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    private ArrayList<int[]> nodeData = new ArrayList<int[]>(); // (x, y, index, color)
    private ArrayList<int[]> lineData = new ArrayList<int[]>(); // (x1, y1, x2, y2 index, color, weight, width, textSize)
    private double nodeRadius = 12.5;
    private double lineWidth = 2.0;
    private double textSize = 14.0;
    private boolean creatingLink = false;
    private JPopupMenu clickMenu;
    private JMenuItem addLinkItem;
    private JMenuItem deleteNodeItem;
    private JPopupMenu linkMenu;
    private JMenuItem editNodeItem;
    private JMenuItem deleteLinkItem;
    private JMenuItem editWeightItem;
    private int mouseX;
    private int mouseY;
    private int clickedNodeIndex = -1;
    private int draggedNodeIndex = -1;
    private int clickedLineIndex = -1;
    private Dijkstras dijkstras;
    private boolean deletingLink = false;

    public GraphicsPanel(Dijkstras dijkstras) {
        setBackground(Color.LIGHT_GRAY);
        this.dijkstras = dijkstras;

        this.clickMenu = new JPopupMenu();
        this.addLinkItem = new JMenuItem("Add Link");
        this.deleteNodeItem = new JMenuItem("Delete");
        this.editNodeItem = new JMenuItem("Set Name");

        this.linkMenu = new JPopupMenu();
        this.editWeightItem = new JMenuItem("Set Weight");
        this.deleteLinkItem = new JMenuItem("Delete Link");

        this.addLinkItem.addActionListener(e -> {
            this.creatingLink = true;
            dijkstras.getGui().setStatusLabel("Creating Link");
        });
        this.deleteNodeItem.addActionListener(e -> {
            if (clickedNodeIndex >= 0) {
                removeNode(clickedNodeIndex);
            }
            System.out.println("Node deleted at index: " + clickedNodeIndex);
        });
        this.editNodeItem.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter node name:");
            if (name != null && !name.trim().isEmpty() && clickedNodeIndex >= 0) {
                ArrayList<String[]> temp = this.dijkstras.getFiles().getNodes();
                String oldName = temp.get(clickedNodeIndex)[0];
                updateNodeName(oldName, name);
                temp.get(clickedNodeIndex)[0] = name;
                this.dijkstras.getFiles().setNodes(temp);
                this.dijkstras.updateData();
                repaint();
            }
        });
        this.editWeightItem.addActionListener(e -> {
            String weightStr = JOptionPane.showInputDialog("Enter link weight:");
            if (weightStr != null && !weightStr.trim().isEmpty() && clickedLineIndex >= 0) {
                try {
                    int weight = Integer.parseInt(weightStr);
                    if (weight < 0) {
                        throw new NumberFormatException();
                    }
                    this.lineData.get(clickedLineIndex)[6] = weight;
                    ArrayList<String[]> temp = this.dijkstras.getFiles().getLines();
                    temp.get(clickedLineIndex)[2] = Integer.toString(weight);
                    this.dijkstras.getFiles().setLines(temp);
                    this.dijkstras.updateData();
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid weight. Please enter a positive number.");
                }
            }
        });
        this.deleteLinkItem.addActionListener(e -> {
            if (clickedLineIndex >= 0) {
                removeLine(clickedLineIndex);
                dijkstras.updateData();
                System.out.println("Link deleted at index: " + clickedLineIndex);
            }
        });

        this.clickMenu.add(addLinkItem);
        this.clickMenu.add(deleteNodeItem);
        this.clickMenu.add(editNodeItem);

        this.linkMenu.add(editWeightItem);
        this.linkMenu.add(deleteLinkItem);
        
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                int nodeIndex = isHoveringNode(e.getX(), e.getY());
                int lineIndex = isHoveringLine(e.getX(), e.getY());
                System.out.println("Node index: " + nodeIndex);
                System.out.println("Line index: " + lineIndex);

                if (nodeIndex >= 0 && creatingLink && e.getButton() == 1 && clickedNodeIndex != nodeIndex) {
                    addLine(clickedNodeIndex, nodeIndex, 100, true);
                    dijkstras.getGui().setStatusLabel("None");
                    creatingLink = false;
                    repaint();
                } else if(lineIndex >=0 && deletingLink && e.getButton() == 1) {
                    removeLine(lineIndex);
                    deletingLink = false;
                    dijkstras.getGui().setStatusLabel("None");
                    repaint();
                } else if (nodeIndex >= 0 && e.getButton() == 1) {
                    draggedNodeIndex = nodeIndex;
                } else if (nodeIndex >= 0 && e.getButton() == 3){
                    clickMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if (lineIndex >= 0 && e.getButton() == 3) {
                    clickedLineIndex = lineIndex;
                    linkMenu.show(e.getComponent(), e.getX(), e.getY());
                } else {
                    clickedNodeIndex = -1;
                    draggedNodeIndex = -1;
                    clickedLineIndex = -1;
                }

                mouseX = e.getX();
                mouseY = e.getY();
                clickedNodeIndex = isHoveringNode(mouseX, mouseY);
            }

            public void mouseReleased(MouseEvent e) {
                if (draggedNodeIndex >= 0) {
                    draggedNodeIndex = -1;
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (draggedNodeIndex >= 0) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    updateLinePosition();
                    updateFile();
                    nodeData.get(draggedNodeIndex)[0] = mouseX - (int)nodeRadius;
                    nodeData.get(draggedNodeIndex)[1] = mouseY - (int)nodeRadius;
                    repaint();
                }
            }
        });
    }

    public void updateFile() {
        ArrayList<String[]> updatedNodeData = this.dijkstras.getFiles().getNodes();
        for (int i = 0; i < this.nodeData.size(); i++) {
            updatedNodeData.get(i)[1] = Integer.toString(this.nodeData.get(i)[0]);
            updatedNodeData.get(i)[2] = Integer.toString(this.nodeData.get(i)[1]);
        }
        this.dijkstras.getFiles().setNodes(updatedNodeData);
        this.dijkstras.updateData();
    }

    public void updateNodeName(String oldName, String newName) {
        ArrayList<String[]> lines = this.dijkstras.getFiles().getLines();
        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);
            if (line[0].equals(oldName)) {
                line[0] = newName;
            } 
            if (line[1].equals(oldName)) {
                line[1] = newName;
            }
            lines.set(i, line);
        }
        this.dijkstras.getFiles().setNodes(lines);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < this.lineData.size(); i ++){
                g2d.setColor(parseColor(this.lineData.get(i)[5]));
                g2d.setStroke(new BasicStroke(this.lineData.get(i)[7]));
                g2d.draw(new Line2D.Double(
                this.lineData.get(i)[0] + this.nodeRadius,
                this.lineData.get(i)[1] + this.nodeRadius,
                this.lineData.get(i)[2] + this.nodeRadius,
                this.lineData.get(i)[3] + this.nodeRadius
                ));
                if (this.lineData.get(i)[5] == 3){
                    g2d.setColor(Color.WHITE);
                }else{
                    g2d.setColor(Color.BLACK);
                }
                g2d.setFont(new Font("Arial", Font.PLAIN, (int)(this.lineData.get(i)[8]/(14/this.nodeRadius))));
                g2d.drawString(Integer.toString(this.lineData.get(i)[6]), 
                    (int)((this.lineData.get(i)[0] + this.lineData.get(i)[2]) / 2 + 9/(12.5/this.nodeRadius)), 
                    (int)((this.lineData.get(i)[1] + this.lineData.get(i)[3]) / 2 + 18/(14/this.nodeRadius)));
        }

        for (int i = 0; i < this.nodeData.size(); i++){
            g2d.setColor(parseColor(this.nodeData.get(i)[3]));
            g2d.fillOval(this.nodeData.get(i)[0], this.nodeData.get(i)[1], (int)this.nodeRadius*2, (int)this.nodeRadius*2);
            if (this.nodeData.get(i)[3] == 3){
                g2d.setColor(Color.WHITE);
            }else{
                g2d.setColor(Color.BLACK);
            }
            g2d.setFont(new Font("Arial", Font.PLAIN, (int)(this.textSize/(14/this.nodeRadius))));
            g2d.drawString(Integer.toString(this.nodeData.get(i)[2]+1), (int)(this.nodeData.get(i)[0]+9/(12.5/this.nodeRadius)), (int)(this.nodeData.get(i)[1]+18/(14/this.nodeRadius)));
        }
    }

    public void addNode(int xPos, int yPos, boolean updateFile) {
        int data[] = {xPos, yPos, this.nodeData.size(), 3};
        this.nodeData.add(data);
        if (updateFile) {
            ArrayList<String[]> updatedNodeData = this.dijkstras.getFiles().getNodes();
            updatedNodeData.add(new String[]{Integer.toString(this.nodeData.size()), Integer.toString(xPos), Integer.toString(yPos)});
            this.dijkstras.getFiles().setNodes(updatedNodeData);
            this.dijkstras.updateData();
        }
        repaint();
    }

    public void addLine(int startIndex, int endIndex, int weight, boolean updateFile) {
        //System.out.println(startIndex+" "+endIndex);
        int x1 = this.nodeData.get(startIndex)[0];
        int y1 = this.nodeData.get(startIndex)[1];
        int x2 = this.nodeData.get(endIndex)[0];
        int y2 = this.nodeData.get(endIndex)[1];
        int data[] = {x1, y1, x2, y2, this.lineData.size(), 3, weight, (int)this.lineWidth, (int)this.textSize};
        this.lineData.add(data);
        if (updateFile) {
            ArrayList<String[]> updatedLineData = this.dijkstras.getFiles().getLines();
            updatedLineData.add(new String[]{this.dijkstras.getFiles().getNodes().get(startIndex)[0], this.dijkstras.getFiles().getNodes().get(endIndex)[0], Integer.toString(weight)});
            this.dijkstras.getFiles().setLines(updatedLineData);
            this.dijkstras.updateData();
        }
        repaint();
    }

    public void resetSize() {
        for (int i = 0; i < this.lineData.size(); i++) {
            this.lineData.get(i)[7] = (int)this.lineWidth;
            this.lineData.get(i)[8] = (int)this.textSize;
        }
        repaint();
    }

    public int isHoveringNode(int x, int y){
        System.out.println("Checking for node at: " + x + ", " + y);
        for (int i = 0; i < nodeData.size(); i++){
            double dist = Integer.MAX_VALUE;
            
            dist = Math.sqrt(Math.pow(x-(nodeData.get(i)[0]+nodeRadius), 2) + Math.pow(y-(nodeData.get(i)[1]+nodeRadius), 2));

            if (dist <= nodeRadius){
                return i;
            }
        }
        return -1;
    }

    public int isHoveringLine(int x, int y){
        for (int i = 0; i < lineData.size(); i++){
            double dist = Integer.MAX_VALUE;
            Line2D line = new Line2D.Double(
                lineData.get(i)[0] + nodeRadius,
                lineData.get(i)[1] + nodeRadius,
                lineData.get(i)[2] + nodeRadius,
                lineData.get(i)[3] + nodeRadius
            );
            dist = line.ptSegDist(x, y);
            if (dist <= nodeRadius) {
                return i;
            }
        }
        return -1;
    }

    public void updateLinePosition() {
        for (int i = 0; i < this.lineData.size(); i++) {
            if (this.lineData.get(i)[0] == this.nodeData.get(draggedNodeIndex)[0] && this.lineData.get(i)[1] == this.nodeData.get(draggedNodeIndex)[1]) {
                this.lineData.get(i)[0] = mouseX - (int)nodeRadius;
                this.lineData.get(i)[1] = mouseY - (int)nodeRadius;
            } else if (this.lineData.get(i)[2] == this.nodeData.get(draggedNodeIndex)[0] && this.lineData.get(i)[3] == this.nodeData.get(draggedNodeIndex)[1]) {
                this.lineData.get(i)[2] = mouseX - (int)nodeRadius;
                this.lineData.get(i)[3] = mouseY - (int)nodeRadius;
            }
        }
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

    public void setLineWidth(int index, int width) {
        if (index >= 0 && index < this.lineData.size()) {
            this.lineData.get(index)[7] = width;
            repaint();
        }
    }

    public void setTextSize(int index, int size) {
        if (index >= 0 && index < this.lineData.size()) {
            this.lineData.get(index)[8] = size;
            repaint();
        }
    }

    public void clearLineColors() {
        for (int i = 0; i < this.lineData.size(); i++) {
            this.lineData.get(i)[5] = 3;
        }
        repaint();
    }

    public void clearNodeColors(){
        for (int i = 0; i < this.nodeData.size(); i++){
            if (this.dijkstras.getStartNode().getIndex() == i){
                this.nodeData.get(i)[3] = 6;
            } else if (this.dijkstras.getEndNode().getIndex() == i){
                this.nodeData.get(i)[3] = 0;
            } else{
                this.nodeData.get(i)[3] = 3;
            }
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

    public ArrayList<int[]> getNodeData() {
        return nodeData;
    }

    public ArrayList<int[]> getLineData() {
        return lineData;
    }

    public double getNodeRadius() {
        return nodeRadius;
    }

    public void setNodeRadius(double nodeRadius) {
        this.nodeRadius = nodeRadius;
        repaint();
    }

    public boolean isCreatingLink() {
        return creatingLink;
    }

    public void setCreatingLink(boolean creatingLink) {
        this.creatingLink = creatingLink;
    }

    public void removeNode(int index) {
        if (index >= 0 && index < this.nodeData.size()) {
            for (int i = this.lineData.size() - 1; i >= 0; i--) {
                if ((this.lineData.get(i)[0] == this.nodeData.get(index)[0] && this.lineData.get(i)[1] == this.nodeData.get(index)[1]) ||
                    (this.lineData.get(i)[2] == this.nodeData.get(index)[0] && this.lineData.get(i)[3] == this.nodeData.get(index)[1])) {
                    removeLine(i);
                    System.out.println("Line removed at index: " + i);
                }
            }
            ArrayList<String[]> updatedNodeData = this.dijkstras.getFiles().getNodes();
            updatedNodeData.remove(index);
            this.dijkstras.getFiles().setNodes(updatedNodeData);
            this.dijkstras.getFiles().setNodeCount(updatedNodeData.size());
            this.dijkstras.updateData();
            this.nodeData.remove(index);
            for (int i = 0; i < this.nodeData.size(); i++) {
                this.nodeData.get(i)[2] = i;
            }
            for (int i = 0; i < lineData.size(); i++) {
                this.lineData.get(i)[4] = i;
            }
            clickedNodeIndex = -1;
            draggedNodeIndex = -1;
            repaint();
        }
    }

    public void removeLine(int index) {
        if (index >= 0 && index < this.lineData.size()) {
            ArrayList<String[]> updatedLineData = this.dijkstras.getFiles().getLines();
            updatedLineData.remove(index);
            this.dijkstras.getFiles().setLines(updatedLineData);
            this.dijkstras.getFiles().setLineCount(updatedLineData.size());

            this.lineData.remove(index);
            repaint();
        }
    }

    public void clearAll() {
        this.nodeData.clear();
        this.lineData.clear();
        repaint();
    }

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    public void setDeletingLink(boolean deletingLink) {
        this.deletingLink = deletingLink;
    }

    public boolean getDeletingLink() {
        return this.deletingLink;
    }
}