
package myprojects.rasi;


import java.util.concurrent.Callable;

class AlgorithmSolveMST implements AlgorithmSolve, Callable{
    private ProblemInstance pi;
    private int[] list;
    
    public AlgorithmSolveMST(ProblemInstance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }
    
    @Override
    public int[] call(){
        return list;
    }
}