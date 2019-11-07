import java.security.KeyStore;
import java.util.*;

public class Graph {
    private int V;
    private HashMap<Integer,LinkedList<Integer>> graph;
    private HashMap<Integer,LinkedList<Integer>> graphInv;
    private ArrayList<Integer> aList;
    private ArrayList<Integer> bList;
    private HashMap<Integer,Boolean> visited;
    private HashMap<Integer,Boolean> visitedInv;
    private Stack<Integer> stack;
    private HashMap<Integer,LinkedList<Integer>> sccGraph;
    static int counter=1;
    Graph(ArrayList a, ArrayList b,int v){
        graph = new HashMap<>();
        graphInv = new HashMap<>();
        sccGraph = new HashMap<>();
        aList = a;
        bList = b;
        V = v;
        visited = new HashMap<>();
        visitedInv = new HashMap<>();
        //System.out.println(V);
    }

    //graph building O(1)
    private void addEdges(int A, int B,HashMap<Integer,LinkedList<Integer>> gr){
        if(!gr.containsKey(-A)){
            gr.put(-A,new LinkedList<>());
        }
        gr.get(-A).add(B);
        if(!gr.containsKey(-B)){
            gr.put(-B,new LinkedList<>());
        }
        gr.get(-B).add(A);
    }
    //set all visited to false O(n)
    private void clearVisited(HashMap<Integer,Boolean> visit){
        for(int i = -V;i<=V;i++){
            if(i!=0){
                visit.put(i,false);
            }
        }
    }
    //for n clauses, run O(1) which will give us O(n)
    private void setEdges(){
        for(int i=0;i< aList.size();i++){
            addEdges(aList.get(i),bList.get(i),graph);
            addEdges(-aList.get(i),-bList.get(i),graphInv);
        }
    }
    //
    private void fillStack(int v)
    {
        if(visited.get(v)) {
            return;
        }
        visited.put(v,true);
        if(graph.get(v)==null){
            stack.push(v);
            return;
        }
        for(int i: graph.get(v))
        {
            fillStack(i);
        }

        stack.push(v);


    }

    private void emptyStack(int v)
    {
        if(visitedInv.get(v)) {
            return;
        }
        visitedInv.put(v,true);
        if(graphInv.get(v)==null){
            addToSccGraph(v,counter,sccGraph);
            return;
        }
        for(int i :graphInv.get(v))
        {
            emptyStack(i);
        }
        addToSccGraph(v,counter,sccGraph);

    }

    private void addToSccGraph(int v, int counter, HashMap<Integer,LinkedList<Integer>> graph){
        if(!graph.containsKey(counter)){
            graph.put(counter,new LinkedList<>());
        }
        graph.get(counter).add(v);
    }
    public void createIMPGraph(){
        clearVisited(visited);
        setEdges();
    }
    boolean checkSCCisSAT(){
        for (Integer i : sccGraph.keySet()){
            for (Integer j: sccGraph.get(i)){
                if (sccGraph.get(i).contains(-j)){
                    System.out.println("FORMULA UNSATISFIABLE");
                    return false;
                }
            }
        }
        System.out.println("FORMULA SATISFIABLE");
        return true;
    }

    public void generateSCC(){

        //System.out.println(graph);
        stack = new Stack();


        for (Integer i : graph.keySet()){
            if(!visited.get(i)){
                fillStack(i);
            }
        }
        //System.out.println(stack.toString());
        clearVisited(visitedInv);

        while(!stack.isEmpty()){
            int v = stack.pop();

            if (!visitedInv.get(v)){
                emptyStack(v);
                counter++;
            }
        }


        //System.out.println(sccGraph.toString());


    }
    //use topological order to set false to variable/inversevariable
    HashMap<Integer,Boolean> getSolution(){
        HashMap<Integer,Boolean> nodeEnv =  new HashMap<>();
        HashMap<Integer,Boolean> solutionEnv =  new HashMap<>();
        for (int i = 1;i<counter; i++){
            for (Integer j : sccGraph.get(i)){
                if (!nodeEnv.containsKey(j)){
                    nodeEnv.put(j, false);
                    nodeEnv.put(-j, true);
                }
            }
        }
        for(Integer key :nodeEnv.keySet()){
            if(key>0){
                solutionEnv.put(key,nodeEnv.get(key));
            }
        }
        return solutionEnv;


    }


}
