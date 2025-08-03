import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener{
    private GraphicsPanel graphicsPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu editMenuStartMenu;
    private JMenu editMenuEndMenu;
    private JRadioButtonMenuItem[] editMenuStartItems;
    private JRadioButtonMenuItem[] editMenuEndItems;
    private JMenuItem editMenuNewNode;
    private JMenuItem editMenuDeleteLink;
    //private JMenuItem fileMenuNew;
    private JMenuItem fileMenuSave;
    private JMenuItem fileMenuSaveAs;
    //private JMenuItem fileMenuOpen;
    private JMenuItem fileMenuRun;
    private JMenuItem fileMenuExit;
    private JMenuItem fileMenuLoad;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private Dijkstras dijkstras;
    private ButtonGroup startGroup;
    private ButtonGroup endGroup;
    private JLabel fileLabel;
    private JLabel statusLabel;

    public GUI(int SCREEN_WIDTH, int SCREEN_HEIGHT){
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public void initialize(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setTitle("Dijkstra's animation");

        this.dijkstras = new Dijkstras(this);

        createMenuBar(-1,-1);

        graphicsPanel = dijkstras.getGraphicsPanel();
        
        //dijkstras.files.printData();

        this.getContentPane().add(this.graphicsPanel, BorderLayout.CENTER);
        this.setVisible(true);
        //dijkstras.runDijkstras();
        //dijkstras.printShortestPath();
    }

    public void createMenuBar(int startIndex, int endIndex){
        if (this.editMenuStartItems != null && this.editMenuEndItems != null) {
            removeListeners();
        }

        this.setJMenuBar(null);

        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.editMenuStartMenu = new JMenu("Start node");
        this.editMenuEndMenu = new JMenu("End node");
        this.editMenuNewNode = new JMenuItem("New Node");
        this.editMenuDeleteLink = new JMenuItem("Delete Link");
        //this.fileMenuNew = new JMenuItem("New");
        this.fileMenuLoad = new JMenuItem("Load");
        this.fileMenuSave = new JMenuItem("Save");
        this.fileMenuSaveAs = new JMenuItem("Save As");
        //this.fileMenuOpen = new JMenuItem("Open");
        this.fileMenuRun = new JMenuItem("Run Dijkstra's");
        this.fileMenuExit = new JMenuItem("Exit");
        this.fileLabel = new JLabel("   Current file: " + this.dijkstras.getFiles().getFilePath());
        this.statusLabel = new JLabel("   Current action: None");

        this.editMenuStartItems = new JRadioButtonMenuItem[this.dijkstras.getFiles().getNodes().size()];
        this.editMenuEndItems = new JRadioButtonMenuItem[this.dijkstras.getFiles().getNodes().size()];

        this.fileMenuLoad.addActionListener(this);
        this.fileMenuSave.addActionListener(this);
        this.fileMenuSaveAs.addActionListener(this);
        this.fileMenuRun.addActionListener(this);
        this.fileMenuExit.addActionListener(this);
        this.editMenuNewNode.addActionListener(this);
        this.editMenuDeleteLink.addActionListener(this);

        this.startGroup = new ButtonGroup();
        this.endGroup = new ButtonGroup();

        for (int i = 0; i < this.dijkstras.getFiles().getNodes().size(); i++){
            this.editMenuStartItems[i] = new JRadioButtonMenuItem(i+1 + ". " + this.dijkstras.getFiles().getNodes().get(i)[0]);
            this.editMenuStartItems[i].addActionListener(this);
            this.startGroup.add(this.editMenuStartItems[i]);

            this.editMenuEndItems[i] = new JRadioButtonMenuItem(i+1 + ". " + this.dijkstras.getFiles().getNodes().get(i)[0]);
            this.editMenuEndItems[i].addActionListener(this);
            this.endGroup.add(this.editMenuEndItems[i]);
        }

        if (startIndex < 0 && endIndex < 0){
            this.editMenuStartItems[0].setSelected(true);
            this.editMenuEndItems[this.dijkstras.getFiles().getNodes().size()-1].setSelected(true);
        } else if (startIndex >= 0 && endIndex >= 0) {
            this.editMenuStartItems[startIndex].setSelected(true);
            this.editMenuEndItems[endIndex].setSelected(true);
        } else if (startIndex >= 0 && endIndex < 0) {
            this.editMenuStartItems[startIndex].setSelected(true);
            this.editMenuEndItems[this.dijkstras.getFiles().getNodes().size()-1].setSelected(true);
        } else if (endIndex >= 0 && startIndex < 0) {
            this.editMenuStartItems[0].setSelected(true);
            this.editMenuEndItems[endIndex].setSelected(true);
        }

        //fileMenu.add(fileMenuNew);
        this.fileMenu.add(this.fileMenuLoad);
        this.fileMenu.add(this.fileMenuSave);
        this.fileMenu.add(fileMenuSaveAs);
        //fileMenu.add(fileMenuOpen);
        this.fileMenu.add(this.fileMenuRun);
        this.fileMenu.add(this.fileMenuExit);
        this.menuBar.add(this.fileMenu);
  

        for (int i = 0; i < this.dijkstras.getFiles().getNodes().size(); i++){
            this.editMenuStartMenu.add(this.editMenuStartItems[i]);
            this.editMenuEndMenu.add(this.editMenuEndItems[i]);
        }

        this.editMenu.add(this.editMenuStartMenu);
        this.editMenu.add(this.editMenuEndMenu);
        this.editMenu.add(this.editMenuNewNode);
        this.editMenu.add(this.editMenuDeleteLink);
        this.menuBar.add(this.editMenu);
        this.menuBar.add(this.statusLabel);
        this.menuBar.add(this.fileLabel);
        this.setJMenuBar(this.menuBar);
        this.revalidate();
        this.repaint();
    }

    public void removeListeners(){
        for (int i = 0; i < this.editMenuStartItems.length; i++){
            this.editMenuStartItems[i].removeActionListener(this);
        }
        for (int i = 0; i < this.editMenuEndItems.length; i++){
            this.editMenuEndItems[i].removeActionListener(this);
        }
        this.fileMenuLoad.removeActionListener(this);
        this.fileMenuSave.removeActionListener(this);
        this.fileMenuSaveAs.removeActionListener(this);
        this.fileMenuRun.removeActionListener(this);
        this.fileMenuExit.removeActionListener(this);
        this.editMenuNewNode.removeActionListener(this);
        this.editMenuDeleteLink.removeActionListener(this);
        //this.menuBar.removeAll();
    }

    public void actionPerformed(ActionEvent e) {
        //JRadioButtonMenuItem item = (JRadioButtonMenuItem)e.getSource();
        //System.out.println(item.getSelectedObjects()[0].toString());
        for (int i = 0; i < dijkstras.getFiles().getNodes().size(); i++){
            if (e.getSource() == this.editMenuStartItems[i]){
                this.dijkstras.getGraphicsPanel().nodeColor(dijkstras.getStartNode().getIndex(), 3);
                this.dijkstras.setStartNode(dijkstras.getNodeMap().get(i));
                System.out.println("Start node set to: " + dijkstras.getNodeMap().get(i).getNodeName());
                this.dijkstras.getGraphicsPanel().nodeColor(i, 2);
                this.dijkstras.getGraphicsPanel().clearLineColors();
                this.dijkstras.getGraphicsPanel().clearNodeColors();
                this.dijkstras.getGraphicsPanel().resetSize();
            } else if (e.getSource() == this.editMenuEndItems[i]) {
                this.dijkstras.getGraphicsPanel().nodeColor(dijkstras.getEndNode().getIndex(), 3);
                this.dijkstras.setEndNode(dijkstras.getNodeMap().get(i));
                System.out.println("End node set to: " + dijkstras.getNodeMap().get(i).getNodeName());
                this.dijkstras.getGraphicsPanel().nodeColor(i, 1);
                this.dijkstras.getGraphicsPanel().clearLineColors();
                this.dijkstras.getGraphicsPanel().clearNodeColors();
                this.dijkstras.getGraphicsPanel().resetSize();
            }
        }
        if (e.getSource() == this.fileMenuRun) {
            new Thread(() -> {
                this.dijkstras.runDijkstras();
                this.dijkstras.printShortestPath();
            }).start();
        } else if (e.getSource() == this.fileMenuExit) {
            System.exit(0);
        } else if (e.getSource() == this.editMenuNewNode) {
            this.graphicsPanel.addNode(100, 100, true);
        } else if (e.getSource() == this.editMenuDeleteLink) {
            this.graphicsPanel.setDeletingLink(true);
            this.statusLabel.setText("   Current action: Deleting link");
        } else if (e.getSource() == this.fileMenuLoad) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("Select a graph data file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                selectedFilePath = selectedFilePath.replace("\\", "/");
                System.out.println("Selected file: " + selectedFilePath);
                this.dijkstras.importData(selectedFilePath);
                this.dijkstras.createPanel();
                this.graphicsPanel = dijkstras.getGraphicsPanel();
                this.createMenuBar(-1,-1);
                //this.dijkstras.getFiles().parseData(this.dijkstras.getFiles().loadData(selectedFilePath));
                //this.dijkstras.updateData();
                this.fileLabel.setText("   Selected file: " + this.dijkstras.getFiles().getFilePath());
                //this.graphicsPanel = dijkstras.getGraphicsPanel();
                this.getContentPane().removeAll();
                this.getContentPane().add(this.graphicsPanel, BorderLayout.CENTER);
                this.revalidate();
                this.repaint();
            }
        } else if (e.getSource() == this.fileMenuSave) {
            this.dijkstras.getFiles().saveData(dijkstras.getFiles().getNodes(), dijkstras.getFiles().getLines(), dijkstras.getFiles().getFilePath());
        } else if (e.getSource() == this.fileMenuSaveAs) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("Save graph data as");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!selectedFilePath.toLowerCase().endsWith(".csv")) {
                    selectedFilePath += ".csv";
                }
                this.dijkstras.getFiles().saveData(dijkstras.getFiles().getNodes(), dijkstras.getFiles().getLines(), selectedFilePath);
                this.fileLabel.setText("   File: " + this.dijkstras.getFiles().getFilePath());
            }
        }

    }

    public GraphicsPanel getGraphicsPanel() {
        return this.graphicsPanel;
    }

    public void setGraphicsPanel(GraphicsPanel graphicsPanel) {
        this.graphicsPanel = graphicsPanel;
    }

    public void setStatusLabel(String status) {
        this.statusLabel.setText("   Current action: " + status);
        this.revalidate();
        this.repaint();
    }
}