public class Dijkstras {

    public Dijkstras(int nodeCount, int lineCount){

    }

    public void calculateDistance(DijkstrasNode node, DijkstrasNode node2){
        if (node.hasNextNode()){
            double distance;
            distance = Math.sqrt(Math.pow((node.getNodeX()-node2.getNodeX()),2) + Math.pow((node.getNodeY()-node2.getNodeY()),2));
            node.setNextNodeDistance(distance);
        }
    }
}
