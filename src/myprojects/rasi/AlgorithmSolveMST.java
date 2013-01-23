
package myprojects.rasi;


import java.util.concurrent.Callable;
import java.util.*;

class AlgorithmSolveMST implements AlgorithmSolve, Callable{
    private ProblemInstance pi;
    private int[] list;
    
	int[] father;
	int[] size;
	int[] res;
	boolean[][] bb;
	boolean[] visited;
	int number = 0;
	TreeSet <Pair<Integer, Pair<Integer, Integer> > > ts;
	
	int find(int nr)
	{
		if(father[nr] == -1)
			return nr;
		father[nr] = find(father[nr]);
		return father[nr];
	}
	
	boolean union(int a, int b)
	{
		a = find(a);
		b = find(b);
		
		if(a == b)   return false;
		
		if(size[a] > size[b])
		{
			size[a] += size[b]+1;
			father[b] = a;
		}
		else
		{
			size[b] += size[a]+1;
			father[a] = b;
		}
		
		return true;
	}
	
    public AlgorithmSolveMST(ProblemInstance pi, int[] list){
        this.pi = pi;
        this.list = list;
    }
    
    void dfs(int nr)
    {
    	
    }
    
    @Override
    public int[] call()
    {
    	bb = new boolean[pi.n][];
		res = new int[list.length];
		visited = new boolean[pi.n];
    	
    	for(int i=0; i<pi.n; i++)
    		for(int j=i+1; j<pi.n; j++)
    		{
    			ts.add(new Pair(pi.min_m[i][j], new Pair(i, j)));
    		}
    		
    	for(int i=0; i<pi.n; i++)
    		bb[i] = new boolean[pi.n];
    		
    	for(Pair<Integer, Pair<Integer, Integer> > elem: ts)
    	{
    		int nr1 = elem.e2.e1;
    		int nr2 = elem.e2.e2;
    		
    		if(union(nr1, nr2))
    		{
    			//bb[nr1][nr2] = bb[nr2][nr1] = elem.e1;
    		}
    	}
    	
    	dfs(0);
    		
    	return res;
    }
}