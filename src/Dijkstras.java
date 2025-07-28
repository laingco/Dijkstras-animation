import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstras {
    private FileEditor files;
    private int[] indexedLineStart;
    private int[] indexedLineEnd;
    private int[] weights;
    private Map<String, Integer> weightsMap;
    private GraphicsPanel graphicsPanel;
    private DijkstrasNode startNode;
    private DijkstrasNode endNode;
    private Map<Integer, DijkstrasNode> nodeMap;
    private Integer animationSpeed = 750;

    public Dijkstras(){
        importData();
        createPanel();
    }

    public void runDijkstras(){
        System.out.println("run dijkstras");
        this.nodeMap = new HashMap<>();
        this.startNode = createTree(this.startNode.getIndex());
        System.out.println(this.startNode.getIndex());
        this.graphicsPanel.clearLineColors();
        this.graphicsPanel.clearNodeColors();
        
        startNode.setDistanceFromStart(0);

        PQueue queue = new PQueue();
        queue.enqueue(startNode);

        while (!queue.isEmpty()) {
            DijkstrasNode currentNode = queue.dequeue();
            if (!currentNode.getVisited()) {
                if (currentNode.getNextNode() != null) {
                    this.graphicsPanel.nodeColor(currentNode.getIndex(), 4);
                    for (int i = 0; i < currentNode.getNextNode().size(); i++) {
                        DijkstrasNode nextNode = currentNode.getNextNode().get(i);
                        double newDist = currentNode.getDistanceFromStart() + /*calculateDistance(currentNode, nextNode)*/weightsMap.get(currentNode.getNodeName() + "-" + nextNode.getNodeName());
                        if (newDist < nextNode.getDistanceFromStart()) {
                            System.out.println("Updated distance for node " + (nextNode.getIndex() + 1) + ": " + newDist);
                            nextNode.setDistanceFromStart(newDist);
                            nextNode.setPreviousNode(currentNode);
                            queue.enqueue(nextNode);
                            System.out.println("Queued " + (nextNode.getIndex()+1));
                        }
                        for (int j = 0; j < this.files.getLines().size()-1; j++) {
                                if (this.indexedLineStart[j] == currentNode.getIndex() && this.indexedLineEnd[j] == nextNode.getIndex() ||
                                    this.indexedLineEnd[j] == currentNode.getIndex() && this.indexedLineStart[j] == nextNode.getIndex()) {
                                    this.graphicsPanel.lineColor(j, 4);
                                    try {
                                        Thread.sleep(animationSpeed);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    this.graphicsPanel.lineColor(j, 5);
                                }
                        }
                        
                    }
                    this.graphicsPanel.nodeColor(currentNode.getIndex(), 3);
                }
                currentNode.setVisited(true);
                this.graphicsPanel.nodeColor(currentNode.getIndex(), 5);
            }
        }
        System.out.println("Dijkstra's algorithm completed.");
    }

    public void printShortestPath() {
        DijkstrasNode currentNode = this.nodeMap.get(this.endNode.getIndex());
        ArrayList<DijkstrasNode> path = new ArrayList<>();

        int i = 0;
        while (currentNode != null) {
            path.add(i, currentNode);
            if (currentNode.getPreviousNode() != null) {
                int lineIndex = -1;
                for (int j = 0; i < this.files.getLines().size()-1; j++) {
                    if (this.indexedLineStart[j] == currentNode.getIndex() && this.indexedLineEnd[j] == currentNode.getPreviousNode().getIndex() ||
                        this.indexedLineEnd[j] == currentNode.getIndex() && this.indexedLineStart[j] == currentNode.getPreviousNode().getIndex()) {
                        lineIndex = j;
                        break;
                    }
                }
                this.graphicsPanel.lineColor(lineIndex, 1);
            }
            currentNode = currentNode.getPreviousNode();
            i++;
        }

        this.graphicsPanel.nodeColor(startNode.getIndex(), 1);
        this.graphicsPanel.nodeColor(endNode.getIndex(), 1);

        for (int j = path.size()-1; j >= 0; j--) {
            System.out.print(path.get(j).getNodeName() + " (" + Math.floor(path.get(j).getDistanceFromStart()) + ") --> " );
        }
        System.out.println("Done");
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
        this.nodeMap = new HashMap<>();
        this.startNode = createTree(0);
        this.endNode = this.nodeMap.get(files.getNodes().size() - 1);
    }

    public DijkstrasNode createTree(int nodeIndex) {
        if (this.nodeMap.containsKey(nodeIndex)) {
            return this.nodeMap.get(nodeIndex);
        }
        String name = files.getNodes().get(nodeIndex)[0];
        int x = Integer.parseInt(files.getNodes().get(nodeIndex)[1]);
        int y = Integer.parseInt(files.getNodes().get(nodeIndex)[2]);
        DijkstrasNode node = new DijkstrasNode(name, nodeIndex, x, y);
        node.setDistanceFromStart(Double.MAX_VALUE);
        node.setVisited(false);
        this.nodeMap.put(nodeIndex, node);

        ArrayList<DijkstrasNode> temp = new ArrayList<>();
        for (int j = 0; j < this.files.getLines().size(); j++) {
            if (this.indexedLineStart[j] == nodeIndex) {
                int childIndex = this.indexedLineEnd[j];
                DijkstrasNode child = createTree(childIndex);
                temp.add(child);
            }else if(this.indexedLineEnd[j] == nodeIndex) {
                int childIndex = this.indexedLineStart[j];
                DijkstrasNode child = createTree(childIndex);
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
        this.indexedLineStart = new int[files.getLines().size()];
        this.indexedLineEnd = new int[files.getLines().size()];
        this.weights = new int[files.getLines().size()];
        this.weightsMap = new HashMap<>();
        for (int i = 0; i < files.getNodes().size(); i++){
            for (int j = 0; j < files.getLines().size(); j++){
                if (files.getNodes().get(i)[0].equals(files.getLines().get(j)[0])){
                    indexedLineStart[j] = i;
                }else if (files.getNodes().get(i)[0].equals(files.getLines().get(j)[1])){
                    indexedLineEnd[j] = i;
                }
            }
        }
        for (int i = 0; i < this.files.getLines().size(); i++){
            weights[i] = Integer.parseInt(files.getLines().get(i)[2]);
            weightsMap.put(files.getLines().get(i)[0] + "-" + files.getLines().get(i)[1], Integer.parseInt(files.getLines().get(i)[2]));
            weightsMap.put(files.getLines().get(i)[1] + "-" + files.getLines().get(i)[0], Integer.parseInt(files.getLines().get(i)[2]));
        }
    }

    public void createPanel(){
        this.graphicsPanel = new GraphicsPanel();
        for (int i = 0; i < this.files.getNodes().size(); i++){
            graphicsPanel.addNode(Integer.parseInt(this.files.getNodes().get(i)[1]), Integer.parseInt(this.files.getNodes().get(i)[2]));
        }

        for (int i = 0; i < this.files.getLines().size(); i++){
            graphicsPanel.addLine(this.indexedLineStart[i], this.indexedLineEnd[i], this.weights[i]);
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
        //this.nodeMap = new HashMap<>();
        this.startNode = startNode;
    }

    public DijkstrasNode getStartNode() {
        return this.startNode;
    }

    public void setEndNode(DijkstrasNode endNode) {
        this.endNode = endNode;
    }

    public DijkstrasNode getEndNode() {
        return this.endNode;
    }

    public Map<Integer, DijkstrasNode> getNodeMap() {
        return this.nodeMap;
    }

    public FileEditor getFiles() {
        return this.files;
    }

    public int[] getIndexedLineStart() {
        return this.indexedLineStart;
    }

    public int[] getIndexedLineEnd() {
        return this.indexedLineEnd;
    }

    public int[] getWeights() {
        return this.weights;
    }

    public Map<String, Integer> getWeightsMap() {
        return this.weightsMap;
    }

    public void setWeightsMap(Map<String, Integer> weightsMap) {
        this.weightsMap = weightsMap;
    }

    public void setIndexedLineStart(int[] indexedLineStart) {
        this.indexedLineStart = indexedLineStart;
    }

    public void setIndexedLineEnd(int[] indexedLineEnd) {
        this.indexedLineEnd = indexedLineEnd;
    }

    public void setWeights(int[] weights) {
        this.weights = weights;
    }

    public void setFiles(FileEditor files) {
        this.files = files;
    }

    public void setGraphicsPanel(GraphicsPanel graphicsPanel) {
        this.graphicsPanel = graphicsPanel;
    }

    public void setNodeMap(Map<Integer, DijkstrasNode> nodeMap) {
        this.nodeMap = nodeMap;
    }
}
