
package myprojects.rasi;


import java.util.concurrent.Callable;

class Algorithm_solve_1 implements Algorithm_solve, Callable{
    private Problem_instance pi;
    private int[] list;
    
    public Algorithm_solve_1(Problem_instance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }
    
    @Override
    public int[] call(){
        return list;
    }
}