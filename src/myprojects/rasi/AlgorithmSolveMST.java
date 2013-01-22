
package myprojects.rasi;


import java.util.concurrent.Callable;

class AlgorithmSolveMST implements Algorithm_solve, Callable{
    private Problem_instance pi;
    private int[] list;
    
    public AlgorithmSolveMST(Problem_instance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }
    
    @Override
    public int[] call(){
        return list;
    }
}