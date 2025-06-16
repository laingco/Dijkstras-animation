import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
    private GraphicsPanel graphicsPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenuNew;
    private JMenuItem fileMenuSave;
    private JMenuItem fileMenuSaveAs;
    private JMenuItem fileMenuOpen;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    public GUI(int SCREEN_WIDTH, int SCREEN_HEIGHT){
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public void initialize(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
        this.setTitle("Dijkstra's animation");

        createMenuBar();
        graphicsPanel = new GraphicsPanel();

        this.getContentPane().add(graphicsPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenuNew = new JMenuItem("New");
        fileMenuSave = new JMenuItem("Save");
        fileMenuSaveAs = new JMenuItem("Save As");
        fileMenuOpen = new JMenuItem("Open");
        fileMenuOpen.addActionListener(e -> {});

        fileMenu.add(fileMenuNew);
        fileMenu.add(fileMenuSave);
        fileMenu.add(fileMenuSaveAs);
        fileMenu.add(fileMenuOpen);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }
}
