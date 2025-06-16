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
            Scanner scanner = new Scanner(new File("graphData.csv"));
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
        this.nodeCount = Integer.parseInt(input.get(0)[0]);
        this.lineCount = Integer.parseInt(input.get(nodeCount+1)[0]);
    }
}
