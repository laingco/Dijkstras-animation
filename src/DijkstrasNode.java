public class DijkstrasNode {
    private DijkstrasNode nextNode;
    private DijkstrasNode previousNode;
    private double nextNodeDistance;
    private int nodeX;
    private int nodeY;
    private String nodeName;
    private int nodeIndex;
    private boolean visited;
    
    public DijkstrasNode(){
        this.nextNode = null;
        this.nextNodeDistance = Integer.MAX_VALUE;
        this.visited = false;
    }

    public DijkstrasNode(String name, int index, int xPos, int yPos){
        this.nextNode = null;
        this.nextNodeDistance = Integer.MAX_VALUE;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
        this.visited = false;
        this.nodeIndex = index;
    }

    public DijkstrasNode(String name, int xPos, int yPos, DijkstrasNode nextNode, double nextDist, DijkstrasNode previousNode){
        this.previousNode = previousNode;
        this.nextNode = nextNode;
        this.nextNodeDistance = nextDist;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
    }

    public void setNextNode(DijkstrasNode node){
        this.nextNode = node;
        this.nextNodeDistance = Integer.MAX_VALUE;
    }

    public DijkstrasNode getNextNode(){
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

    public void setNextNodeDistance(double distance){
        this.nextNodeDistance = distance;
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

    public double getNextNodeDistance(){
        return this.nextNodeDistance;
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
