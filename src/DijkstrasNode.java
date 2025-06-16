public class DijkstrasNode {
    private DijkstrasNode nextNode;
    private double nextNodeDistance;
    private int nodeX;
    private int nodeY;
    
    public DijkstrasNode(){
        this.nextNode = null;
        this.nextNodeDistance = Integer.MAX_VALUE;
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
}
