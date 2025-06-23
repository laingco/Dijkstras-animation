public class Dijkstras {
    FileEditor files;
    int[] indexedLineStart;
    int[] indexedLineEnd;
    int[] weights;
    GraphicsPanel graphicsPanel;

    public Dijkstras(){
        importData();
        createPanel();
    }

    public void calculateDistance(DijkstrasNode node, DijkstrasNode node2){
        if (node.hasNextNode()){
            double distance;
            distance = Math.sqrt(Math.pow((node.getNodeX()-node2.getNodeX()),2) + Math.pow((node.getNodeY()-node2.getNodeY()),2));
            node.setNextNodeDistance(distance);
        }
    }

    public void importData(){
        this.files = new FileEditor();
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
        indexData();
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
}
