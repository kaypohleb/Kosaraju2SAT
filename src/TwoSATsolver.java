import javax.sound.midi.SysexMessage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class TwoSATsolver {
    public static void main(String[] args) {
        CNFParser cnfParser = new CNFParser("ex5.cnf");
        cnfParser.parse();
        Graph graph = cnfParser.toGraph();
        RandomSolver randomSolver = cnfParser.toRandSolver();

        long timeStart = System.nanoTime();
        graph.createIMPGraph();
        graph.generateSCC();
        if(graph.checkSCCisSAT()){
            HashMap<Integer,Boolean> solution = graph.getSolution();
            //cnfParser.writeResult(solution);
        }
        System.out.println(String.format("Kosaraju 2SAT: %d ms",(System.nanoTime()-timeStart)/1000000));
        timeStart = System.nanoTime();
        randomSolver.solve();
        System.out.println(String.format("RandSolver 2SAT: %d ms",(System.nanoTime()-timeStart)/1000000));
    }
}
