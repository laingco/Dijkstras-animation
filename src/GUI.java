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
    private JMenuItem fileMenuNew;
    private JMenuItem fileMenuSave;
    private JMenuItem fileMenuSaveAs;
    private JMenuItem fileMenuOpen;
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

        dijkstras.runDijkstras();

        graphicsPanel = dijkstras.getGraphicsPanel();
        
        //dijkstras.files.printData();

        this.getContentPane().add(this.graphicsPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void createMenuBar(){
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.editMenuStartMenu = new JMenu("Start node");
        this.editMenuEndMenu = new JMenu("End node");
        this.fileMenuNew = new JMenuItem("New");
        this.fileMenuSave = new JMenuItem("Save");
        this.fileMenuSaveAs = new JMenuItem("Save As");
        this.fileMenuOpen = new JMenuItem("Open");

        this.editMenuStartItems = new JRadioButtonMenuItem[dijkstras.files.nodes.size()];
        this.editMenuEndItems = new JRadioButtonMenuItem[dijkstras.files.nodes.size()];

        ButtonGroup startGroup = new ButtonGroup();
        ButtonGroup endGroup = new ButtonGroup();

        for (int i = 0; i < dijkstras.files.nodes.size(); i++){
            this.editMenuStartItems[i] = new JRadioButtonMenuItem(dijkstras.files.nodes.get(i)[0]);
            this.editMenuStartItems[i].addActionListener(this);
            startGroup.add(this.editMenuStartItems[i]);

            this.editMenuEndItems[i] = new JRadioButtonMenuItem(dijkstras.files.nodes.get(i)[0]);
            this.editMenuEndItems[i].addActionListener(this);
            endGroup.add(this.editMenuEndItems[i]);
        }

        this.editMenuStartItems[0].setSelected(true);
        this.editMenuEndItems[dijkstras.files.nodes.size()-1].setSelected(true);

        fileMenu.add(fileMenuNew);
        fileMenu.add(fileMenuSave);
        fileMenu.add(fileMenuSaveAs);
        fileMenu.add(fileMenuOpen);
        menuBar.add(fileMenu);

        for (int i = 0; i < dijkstras.files.nodes.size(); i++){
            this.editMenuStartMenu.add(this.editMenuStartItems[i]);
            this.editMenuEndMenu.add(this.editMenuEndItems[i]);
        }

        editMenu.add(editMenuStartMenu);
        editMenu.add(editMenuEndMenu);
        menuBar.add(editMenu);
        this.setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        JRadioButtonMenuItem item = (JRadioButtonMenuItem)e.getSource();
        System.out.println(item.getSelectedObjects()[0].toString());
        for (int i = 0; i < dijkstras.files.nodes.size(); i++){
            if (item == this.editMenuStartItems[i]){
                dijkstras.setStartNode(new DijkstrasNode(
                    dijkstras.files.nodes.get(i)[0],
                    i,
                    Integer.parseInt(dijkstras.files.nodes.get(i)[1]),
                    Integer.parseInt(dijkstras.files.nodes.get(i)[2])
                ));
            } else if (item == this.editMenuEndItems[i]) {
                dijkstras.setEndNode(new DijkstrasNode(
                    dijkstras.files.nodes.get(i)[0],
                    i,
                    Integer.parseInt(dijkstras.files.nodes.get(i)[1]),
                    Integer.parseInt(dijkstras.files.nodes.get(i)[2])
                ));
            }
        }
    }
}
