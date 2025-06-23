import java.util.ArrayList;

public class DijkstrasNodeTree {
    private ArrayList<DijkstrasNodeTree> nextNode = new ArrayList<DijkstrasNodeTree>();
    private int nodeX;
    private int nodeY;
    private String nodeName;
    private int nodeIndex;
    
    public DijkstrasNodeTree(){
        this.nextNode = null;
        this.nodeName = null;
    }

    public DijkstrasNodeTree(String name, int index, int xPos, int yPos){
        this.nextNode = null;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
        this.nodeIndex = index;
    }

    public DijkstrasNodeTree(String name, int index, int xPos, int yPos, ArrayList<DijkstrasNodeTree> nextNode){
        this.nextNode = nextNode;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
        this.nodeIndex = index;
    }

    public void setNextNode(ArrayList<DijkstrasNodeTree> node){
        this.nextNode = node;
    }

    public ArrayList<DijkstrasNodeTree> getNextNode(){
        return this.nextNode;
    }

    public boolean hasNextNode(){
        if (!this.nextNode.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public int getNodeX(){
        return this.nodeX;
    }

    public int getNodeY(){
        return this.nodeY;
    }

    public int getIndex(){
        return this.nodeIndex;
    }

    public void setNodeName(String name){
        this.nodeName = name;
    }
}
