public class DijkstrasNode {
    private DijkstrasNode nextNode;
    private double nextNodeDistance;
    private int nodeX;
    private int nodeY;
    private String nodeName;
    
    public DijkstrasNode(){
        this.nextNode = null;
        this.nextNodeDistance = Integer.MAX_VALUE;
    }

    public DijkstrasNode(String name, int xPos, int yPos){
        this.nextNode = null;
        this.nextNodeDistance = Integer.MAX_VALUE;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
    }

    public DijkstrasNode(String name, int xPos, int yPos, DijkstrasNode nextNode, double nextDist){
        this.nextNode = nextNode;
        this.nextNodeDistance = nextDist;
        this.nodeName = name;
        this.nodeX = xPos;
        this.nodeY = yPos;
    }

    public void setNextNode(DijkstrasNode node){
        this.nextNode = node;
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
}
