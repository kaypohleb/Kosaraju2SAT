import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class RandomSolver {
    // list of clauses with literals
    // set all to false
    // go through the list, check if the clause is unsat
    // randomly flip the literal in the clause
    // go through again until you are tired of flipping coin (after n^2 iteration)
    private HashMap<Integer, Boolean> assignment = new HashMap<>();
    private int numVar;
    private int numClause;
    private int runCount = 0;
    private Random random;
    private ArrayList<Integer> aList;
    private ArrayList<Integer> bList;

    public RandomSolver(ArrayList<Integer> aList, ArrayList<Integer> bList, int numVar){
        this.aList = aList;
        this.bList = bList;
        this.numVar = numVar;
        this.numClause = aList.size();
        this.random = new Random();
        if (random.nextInt(2)==1){
            setAllTrue();
        }
        else{
            setAllFalse();
        }

    }

    private boolean checkClause(int a, int b){
        boolean[] truth = new boolean[2];
        if (a < 0){
            truth[0] = !assignment.get(a*-1);
        }
        else {
            truth[0] = assignment.get(a);
        }

        if (b < 0){
            truth[1] = !assignment.get(b*-1);
        }
        else{
            truth[1] = assignment.get(b);
        }
        for (boolean t:truth){
            if (t) return true;
        }
        return false;
    }
    private void setAllFalse(){
        for (int i=1; i<numVar+1; i++){
            assignment.put(i,false);
        }
    }

    private void setAllTrue(){
        for (int i=1; i<numVar+1; i++){
            assignment.put(i,true);
        }
    }
    private void randomLiteralChange(int a, int b){
        if (random.nextInt(2)==1){
            flipLit(a);
        }
        else{
            flipLit(b);
        }
    }
    private void flipLit(Integer l){
        if (l<0){
            assignment.put(l*-1,!assignment.get(l*-1));
        }
        else{
            assignment.put(l,!assignment.get(l));
        }
    }
    public void solve(){
        boolean done = false;
        while(!done){
            this.runCount+=1;
            if(run()){
                done = true;
                System.out.println("FORMULA SATISFIABLE");
                System.out.println(assignment.toString());
            }
            else if(Math.pow(this.runCount,0.5)>=10*this.numVar){
                done = true;
                System.out.println("FORMULA UNSATISFIABLE");
            }
        }

    }

    private boolean run(){
        for (int i = 0; i < numClause; i++){
            int a = aList.get(i);
            int b = bList.get(i);
            if (!checkClause(a,b)){
                randomLiteralChange(a,b); //change a variable value in that false clause at random.
                return false;
            }
        }
        return true;
    }


}
