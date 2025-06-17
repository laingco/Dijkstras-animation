import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileEditor {
    int nodeCount;
    int lineCount;
    ArrayList<String[]> nodes;
    ArrayList<String[]> lines;

    public FileEditor(){
        parseData(getData());
    }

    public ArrayList<String[]> getData(){
        ArrayList<String[]> output = new ArrayList<String[]>();
        try{
            Scanner scanner = new Scanner(new File("src/graphData.csv"));
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
        for (int i = nodeCount+2; i < input.size(); i++){
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
}
