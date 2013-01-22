
package myprojects.rasi;


import java.util.concurrent.Callable;

class AlgorithmSolve1 implements AlgorithmSolve, Callable{
    private ProblemInstance pi;
    private int[] list;
    
    public AlgorithmSolve1(ProblemInstance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }
    
    @Override
    public int[] call(){
        return list;
    }
}