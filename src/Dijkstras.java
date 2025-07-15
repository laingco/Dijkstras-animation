import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstras {
    //may make private
    FileEditor files;
    int[] indexedLineStart;
    int[] indexedLineEnd;
    int[] weights;
    GraphicsPanel graphicsPanel;
    private DijkstrasNode startNode;
    private DijkstrasNode endNode;

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
        System.out.println("Dijkstra's algorithm completed.");
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
        Map<Integer, DijkstrasNode> nodeMap = new HashMap<>();
        this.startNode = createTree(0, nodeMap);
        this.endNode = nodeMap.get(files.nodes.size() - 1);
    }

    public DijkstrasNode createTree(int nodeIndex, Map<Integer, DijkstrasNode> nodeMap) {
        if (nodeMap.containsKey(nodeIndex)) {
            return nodeMap.get(nodeIndex);
        }
        String name = files.nodes.get(nodeIndex)[0];
        int x = Integer.parseInt(files.nodes.get(nodeIndex)[1]);
        int y = Integer.parseInt(files.nodes.get(nodeIndex)[2]);
        DijkstrasNode node = new DijkstrasNode(name, nodeIndex, x, y);
        node.setDistanceFromStart(Double.MAX_VALUE);
        node.setVisited(false);
        nodeMap.put(nodeIndex, node);

        ArrayList<DijkstrasNode> temp = new ArrayList<>();
        for (int j = 0; j < this.files.lines.size(); j++) {
            if (this.indexedLineStart[j] == nodeIndex) {
                int childIndex = this.indexedLineEnd[j];
                DijkstrasNode child = createTree(childIndex, nodeMap);
                temp.add(child);
            }
        }
        node.setNextNode(temp);
        return node;
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
        if (startNode == null) {
            this.startNode = null;
            return;
        }
        Map<Integer, DijkstrasNode> nodeMap = new HashMap<>();
        this.startNode = createTree(startNode.getIndex(), nodeMap);
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
