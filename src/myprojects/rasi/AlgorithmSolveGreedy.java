
package myprojects.rasi;


import java.util.concurrent.Callable;
import javax.swing.*;

class AlgorithmSolveGreedy implements AlgorithmSolve, Callable{
    int[] list;
    boolean[] toVisit;
    ProblemInstance pi;

    public AlgorithmSolveGreedy(ProblemInstance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }


    @Override
    public int[] call() throws Exception {
        int[] res = new int[list.length];
        toVisit = new boolean[pi.n];

		for(int el : list)
			toVisit[el] = true;
			
		int act = 0;
		for(int i=0; i<list.length; i++)
		{
			int minn = 1000000007, nr = -1;
			for(int j=0; j<pi.n; j++)
			{
				if(toVisit[j] && pi.min_m[act][j] < minn)
				{
					minn = pi.min_m[act][j];
					nr = j;
				}
			}
			
			toVisit[nr] = false;
			res[i] = nr;
			act = nr;
		}

        return res;
    }
}