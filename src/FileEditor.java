import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileEditor {
    private int nodeCount;
    private int lineCount;
    private ArrayList<String[]> nodes;
    private ArrayList<String[]> lines;
    private String filePath;

    public FileEditor(){
        this.filePath = "src/graphData.csv";
        parseData(loadData(this.filePath));
    }

    public FileEditor(String filePath){
        this.filePath = filePath;
        parseData(loadData(this.filePath));
    }

    public ArrayList<String[]> loadData(String filePath) {
        ArrayList<String[]> output = new ArrayList<String[]>();
        this.filePath = filePath;
        try{
            Scanner scanner = new Scanner(new File(filePath));
            while(scanner.hasNextLine()){
                String[] currentLine = scanner.nextLine().split(",");
                output.add(currentLine);
            }
            scanner.close();
        }catch(IOException e){
            e.printStackTrace();
        };
        return(output);
    }

    public void parseData(ArrayList<String[]> input){
        this.nodes = new ArrayList<String[]>();
        this.lines = new ArrayList<String[]>();
        this.nodeCount = Integer.parseInt(input.get(0)[0]);
        this.lineCount = Integer.parseInt(input.get(nodeCount+1)[0]);
        for (int i = 1; i < this.nodeCount+1; i++){
            this.nodes.add(input.get(i));
        }
        for (int i = nodeCount+2; i < nodeCount+lineCount+2; i++){
            this.lines.add(input.get(i));
        }
    }

    public void printData(){
        System.out.println("Number of nodes: " + nodeCount);
        System.out.println("Number of lines: " + lineCount);
        System.out.println("Nodes:");
        for (int i = 0; i < nodes.size(); i++){
            System.out.println(nodes.get(i)[0] + ", " + nodes.get(i)[1] + ", " + nodes.get(i)[2]);
        }
        System.out.println("Lines:");
        for (int i = 0; i < lines.size(); i++){
            System.out.println(lines.get(i)[0] + ", " + lines.get(i)[1] + ", " + lines.get(i)[2]);
        }
    }

    public void saveData(ArrayList<String[]> nodes, ArrayList<String[]> lines, String filePath) {
        this.filePath = filePath;
        try {
            PrintWriter writer = new PrintWriter(new File(filePath));
            writer.println(nodes.size());
            for (int i = 0; i < nodes.size(); i++) {
                String[] node = nodes.get(i);
                writer.println(String.join(",", node));
            }
            writer.println(lines.size());
            for (int i = 0; i < lines.size(); i++) {
                String[] line = lines.get(i);
                writer.println(String.join(",", line));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getNodes() {
        return nodes;
    }

    public ArrayList<String[]> getLines() {
        return lines;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setNodes(ArrayList<String[]> nodes) {
        this.nodes = nodes;
        this.nodeCount = nodes.size();
    }

    public void setLines(ArrayList<String[]> lines) {
        this.lines = lines;
        this.lineCount = lines.size();
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
