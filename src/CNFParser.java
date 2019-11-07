import java.awt.font.GraphicAttribute;
import java.io.*;
import java.util.*;

public class CNFParser {
    BufferedReader bufferedReader;
    String s;
    ArrayList<Integer> aList;
    ArrayList<Integer> bList;
    Set<Integer> set;
    String fileName;

    CNFParser(String filename){
        fileName = filename;
        String filePath = "src/CNFtests/"+filename;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
        }
        set = new HashSet<>();
    }

    public void parse(){
        aList = new ArrayList<>();
        bList = new ArrayList<>();
        try {
            while ((s = bufferedReader.readLine()) != null) {
                if (s.charAt(0) !=('c')&&s.charAt(0) !=('p')) {
                    String[] arguments = s.trim().split("\\s+");
                    if (arguments.length > 3) {
                        throw new IOException("Not 2SAT cnf");
                    }
                    else if(arguments.length==3) {
                        int i = Integer.valueOf(arguments[0]);
                        int i2 = Integer.valueOf(arguments[1]);
                        set.add(Math.abs(i));
                        set.add(Math.abs(i2));
                        aList.add(i);
                        bList.add(i2);
                    }else if(arguments.length==2){
                        int i = Integer.valueOf(arguments[0]);
                        aList.add(i);
                        bList.add(i);
                        set.add(Math.abs(i));

                    }
                }
            }
        }catch(IOException e){
            System.out.println("Read Error");
            e.printStackTrace();
        }
        //System.out.println("A: "+ aList.toString()+"B: "+ bList.toString());
    }
    public Graph toGraph(){
        return new Graph(aList,bList,set.size());

    }
    public RandomSolver toRandSolver(){
        return new RandomSolver(aList,bList,set.size());
    }
    public void writeResult(HashMap<Integer, Boolean> solution){
        System.out.println(solution);
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("BoolAssignment.txt",false), "utf-8"));
            writer.write( "Filename: " + fileName + "\n");
            String res = solution.toString();

            String[] resArray = res.replace("{","").replace("}","").split(",");


            for (String s: resArray){
                String[] states = s.split("=");
                String line = "\n" + states[0].trim() + ":" +  states[1].trim();
                writer.write(line);
            }
            //System.out.println("File writing done");
            writer.close();
        } catch (IOException e) {
            //Exception
        }

    }



}
