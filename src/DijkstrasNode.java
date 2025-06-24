import java.util.ArrayList;

public class DijkstrasNode {
    private ArrayList<DijkstrasNode> nextNode;
    private DijkstrasNode previousNode;
    private double distanceFromStart;
    private int nodeX;
    private int nodeY;
    private String nodeName;
    private int nodeIndex;
    private boolean visited;
    
    public DijkstrasNode(){
        this.nextNode = null;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.visited = false;
    }

    public DijkstrasNode(String name, int index, int xPos, int yPos){
        this.nextNode = null;
        this.distanceFromStart = Integer.MAX_VALUE;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
        this.visited = false;
        this.nodeIndex = index;
    }

    public DijkstrasNode(String name, int xPos, int yPos, ArrayList<DijkstrasNode> nextNode, double nextDist, DijkstrasNode previousNode){
        this.previousNode = previousNode;
        this.nextNode = nextNode;
        this.distanceFromStart = nextDist;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
    }

    public void setNextNode(ArrayList<DijkstrasNode> node){
        this.nextNode = node;
        this.distanceFromStart = Integer.MAX_VALUE;
    }

    public ArrayList<DijkstrasNode> getNextNode(){
        return this.nextNode;
    }

    public boolean hasNextNode(){
        if (nextNode != null){
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

    public void setDistanceFromStart(double distance){
        this.distanceFromStart = distance;
    }

    public void setNodeName(String name){
        this.nodeName = name;
    }

    public String getNodeName(){
        return this.nodeName;
    }

    public int getIndex(){
        return this.nodeIndex;
    }

    public double getDistanceFromStart(){
        return this.distanceFromStart;
    }

    public boolean getVisited(){
        return this.visited;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public DijkstrasNode getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(DijkstrasNode previousNode) {
        this.previousNode = previousNode;
    }
}
