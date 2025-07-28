import javax.swing.*;
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
    private JMenuItem fileMenuNew;
    private JMenuItem fileMenuSave;
    private JMenuItem fileMenuSaveAs;
    private JMenuItem fileMenuOpen;
    private JMenuItem fileMenuRun;
    private JMenuItem fileMenuExit;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private Dijkstras dijkstras;

    public GUI(int SCREEN_WIDTH, int SCREEN_HEIGHT){
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public void initialize(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setTitle("Dijkstra's animation");

        this.dijkstras = new Dijkstras();

        createMenuBar();

        graphicsPanel = dijkstras.getGraphicsPanel();
        
        //dijkstras.files.printData();

        this.getContentPane().add(this.graphicsPanel, BorderLayout.CENTER);
        this.setVisible(true);
        //dijkstras.runDijkstras();
        //dijkstras.printShortestPath();
    }

    public void createMenuBar(){
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.editMenuStartMenu = new JMenu("Start node");
        this.editMenuEndMenu = new JMenu("End node");
        this.editMenuNewNode = new JMenuItem("New Node");
        this.editMenuDeleteLink = new JMenuItem("Delete Link");
        this.fileMenuNew = new JMenuItem("New");
        this.fileMenuSave = new JMenuItem("Save");
        this.fileMenuSaveAs = new JMenuItem("Save As");
        this.fileMenuOpen = new JMenuItem("Open");
        this.fileMenuRun = new JMenuItem("Run Dijkstra's");
        this.fileMenuExit = new JMenuItem("Exit");

        this.editMenuStartItems = new JRadioButtonMenuItem[dijkstras.getFiles().getNodes().size()];
        this.editMenuEndItems = new JRadioButtonMenuItem[dijkstras.getFiles().getNodes().size()];

        this.fileMenuRun.addActionListener(this);
        this.fileMenuExit.addActionListener(this);
        this.editMenuNewNode.addActionListener(this);
        this.editMenuDeleteLink.addActionListener(this);

        ButtonGroup startGroup = new ButtonGroup();
        ButtonGroup endGroup = new ButtonGroup();

        for (int i = 0; i < dijkstras.getFiles().getNodes().size(); i++){
            this.editMenuStartItems[i] = new JRadioButtonMenuItem(dijkstras.getFiles().getNodes().get(i)[0]);
            this.editMenuStartItems[i].addActionListener(this);
            startGroup.add(this.editMenuStartItems[i]);

            this.editMenuEndItems[i] = new JRadioButtonMenuItem(dijkstras.getFiles().getNodes().get(i)[0]);
            this.editMenuEndItems[i].addActionListener(this);
            endGroup.add(this.editMenuEndItems[i]);
        }

        this.editMenuStartItems[0].setSelected(true);
        this.editMenuEndItems[dijkstras.getFiles().getNodes().size()-1].setSelected(true);

        fileMenu.add(fileMenuNew);
        fileMenu.add(fileMenuSave);
        fileMenu.add(fileMenuSaveAs);
        fileMenu.add(fileMenuOpen);
        fileMenu.add(fileMenuRun);
        fileMenu.add(fileMenuExit);
        menuBar.add(fileMenu);

        for (int i = 0; i < dijkstras.getFiles().getNodes().size(); i++){
            this.editMenuStartMenu.add(this.editMenuStartItems[i]);
            this.editMenuEndMenu.add(this.editMenuEndItems[i]);
        }

        editMenu.add(editMenuStartMenu);
        editMenu.add(editMenuEndMenu);
        editMenu.add(editMenuNewNode);
        editMenu.add(editMenuDeleteLink);
        menuBar.add(editMenu);
        this.setJMenuBar(menuBar);
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
            } else if (e.getSource() == this.editMenuEndItems[i]) {
                this.dijkstras.getGraphicsPanel().nodeColor(dijkstras.getEndNode().getIndex(), 3);
                this.dijkstras.setEndNode(dijkstras.getNodeMap().get(i));
                System.out.println("End node set to: " + dijkstras.getNodeMap().get(i).getNodeName());
                this.dijkstras.getGraphicsPanel().nodeColor(i, 1);
                this.dijkstras.getGraphicsPanel().clearLineColors();
                this.dijkstras.getGraphicsPanel().clearNodeColors();
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

        } else if (e.getSource() == this.editMenuDeleteLink) {

        }

    }
}
