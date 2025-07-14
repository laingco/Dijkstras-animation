import java.util.ArrayList;

public class Dijkstras {
    //may make private
    FileEditor files;
    int[] indexedLineStart;
    int[] indexedLineEnd;
    int[] weights;
    GraphicsPanel graphicsPanel;
    private DijkstrasNode startNode;
    private DijkstrasNode endNode;
    //private DijkstrasNodeTree dijkstrasTree;

    public Dijkstras(){
        importData();
        createPanel();
    }

    public void runDijkstras(){
        System.out.println("run dijkstras");
        startNode.setDistanceFromStart(0);

        Queue queue = new Queue();
        queue.enqueue(startNode);

        while (!queue.isEmpty()) {
            DijkstrasNode currentNode = queue.dequeue();
            if (!currentNode.getVisited()) {
                if (currentNode.getNextNode() != null) {
                    for (int i = 0; i < currentNode.getNextNode().size(); i++) {
                        DijkstrasNode nextNode = currentNode.getNextNode().get(i);
                        double newDist = currentNode.getDistanceFromStart() + calculateDistance(currentNode, nextNode);
                        if (newDist < nextNode.getDistanceFromStart()) {
                            System.out.println("Updated distance for node " + (nextNode.getIndex() + 1) + ": " + newDist);
                            nextNode.setDistanceFromStart(newDist);
                            nextNode.setPreviousNode(currentNode);
                            queue.enqueue(nextNode);
                        }
                    }
                }
                currentNode.setVisited(true);
            }
        }
    }

    public double calculateDistance(DijkstrasNode node, DijkstrasNode node2){
        if (node.hasNextNode()){
            double distance;
            distance = Math.sqrt(Math.pow((node.getNodeX()-node2.getNodeX()),2) + Math.pow((node.getNodeY()-node2.getNodeY()),2));
            return distance;
        }else {
            return 0;
        }
    }

    public void importData(){
        this.files = new FileEditor();
        indexData();
        this.startNode = new DijkstrasNode(files.nodes.get(0)[0], 
            0, 
            Integer.parseInt(files.nodes.get(0)[1]), 
            Integer.parseInt(files.nodes.get(0)[2]));
        this.endNode = new DijkstrasNode(files.nodes.get(files.nodes.size()-1)[0], 
            files.nodes.size()-1, 
            Integer.parseInt(files.nodes.get(files.nodes.size()-1)[1]), 
            Integer.parseInt(files.nodes.get(files.nodes.size()-1)[2]));
        this.startNode = createTree(startNode);
        //printTree(this.startNode);
    }

    public DijkstrasNode createTree(DijkstrasNode tree){
        ArrayList<DijkstrasNode> temp = new ArrayList<DijkstrasNode>();
        for (int j = 0; j < this.files.lines.size(); j++){
            if (this.indexedLineStart[j] == tree.getIndex()){
                DijkstrasNode temp2 = new DijkstrasNode(
                    this.files.nodes.get(indexedLineEnd[j])[0], 
                    indexedLineEnd[j], 
                    Integer.parseInt(this.files.nodes.get(indexedLineEnd[j])[1]), 
                    Integer.parseInt(this.files.nodes.get(indexedLineEnd[j])[2])
                );
                temp2.setDistanceFromStart(Double.MAX_VALUE);
                temp2.setVisited(false);
                temp.add(createTree(temp2));
            }
        }
        tree.setNextNode(temp);
        return tree;
    }

    public void printTree(DijkstrasNode tree){
        //System.out.println((tree.getIndex()+1) + " " + tree.hasNextNode());
        if (tree.hasNextNode()){
            System.out.println("node: " + (tree.getIndex()+1));
            for (int i = 0; i < tree.getNextNode().size(); i++){
                System.out.println("child: " + (tree.getNextNode().get(i).getIndex()+1));
            }
            for (int i = 0; i < tree.getNextNode().size(); i++){
                if (!tree.getNextNode().get(i).getVisited()){
                    printTree(tree.getNextNode().get(i));
                }
            }
            tree.setVisited(true);
        }
    }

    public void indexData(){
        this.indexedLineStart = new int[files.lines.size()];
        this.indexedLineEnd = new int[files.lines.size()];
        this.weights = new int[files.lines.size()];
        for (int i = 0; i < files.nodes.size(); i++){
            for (int j = 0; j < files.lines.size(); j++){
                if (files.nodes.get(i)[0].equals(files.lines.get(j)[0])){
                    indexedLineStart[j] = i;
                }else if (files.nodes.get(i)[0].equals(files.lines.get(j)[1])){
                    indexedLineEnd[j] = i;
                }
            }
            weights[i] = Integer.parseInt(files.lines.get(i)[2]);
        } 

    }

    public void createPanel(){
        this.graphicsPanel = new GraphicsPanel();
        for (int i = 0; i < this.files.nodes.size(); i++){
            graphicsPanel.addNode(Integer.parseInt(this.files.nodes.get(i)[1]), Integer.parseInt(this.files.nodes.get(i)[2]));
        }

        for (int i = 0; i < this.files.lines.size(); i++){
            graphicsPanel.addLine(this.indexedLineStart[i], this.indexedLineEnd[i]);
        }
    }

    public GraphicsPanel getGraphicsPanel() {
        return graphicsPanel;
    }

    public void setStartNode(DijkstrasNode startNode) {
        this.startNode = createTree(startNode);
    }

    public DijkstrasNode getStartNode() {
        return startNode;
    }

    public void setEndNode(DijkstrasNode endNode) {
        this.endNode = endNode;
    }

    public DijkstrasNode getEndNode() {
        return endNode;
    }
}
