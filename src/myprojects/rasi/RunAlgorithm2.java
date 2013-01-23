/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myprojects.rasi;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Lukasz
 */
public class RunAlgorithm2 
{
    
    private ProblemInstance pi;
    private AlgorithmPartition ap;
    private AlgorithmSolveEnum alg;
    
    public RunAlgorithm2(ProblemInstance pi, AlgorithmPartition ap, AlgorithmSolveEnum alg) throws Exception
    {
        pi.shortest_paths();
        if (!pi.check_integrity())
            throw new Exception("Graf jest niespójny");
        this.pi = pi;
        this.ap = ap;
        this.alg = alg;
    }
    
    public int run(){
        int[][] aux = ap.run(pi);
        ExecutorService executor = Executors.newFixedThreadPool(aux.length);
        Future[] futures = new Future[aux.length];
        int[][] solution = new int[aux.length][];

        switch (alg)
        {
            case Argo1:
                for (int i = 0; i < aux.length; i++) {
                    futures[i] = executor.submit(
                            new AlgorithmSolve1(pi, aux[i]));
                }
                break;
            case Genetic:
                for (int i = 0; i < aux.length; i++) {
                    futures[i] = executor.submit(
                            new AlgorithmSolveGenetic(pi, aux[i]));
                }
                break;
            case Greedy:
                for (int i = 0; i < aux.length; i++) {
                    futures[i] = executor.submit(
                            new AlgorithmSolveGreedy(pi, aux[i]));
                }
                break;
            case Brut:
                for (int i = 0; i < aux.length; i++) {
                    futures[i] = executor.submit(
                            new AlgorithmSolveBrut(pi, aux[i]));
                }
                break;
        }
        
        for (int i = 0; i < aux.length; i++) {
            do {
                try {
                    int[] result = (int[]) futures[i].get();
                    solution[i] = extend(result);
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    System.out.println(e);
                }
            } while (!futures[i].isDone());
        }
        
        int a, b, czas = 0;
    	for(int i=0; i<solution.length; i++)
    	{
            a = solution[i][0];
            for(int j=1; j<solution[i].length; j++)
            {
                b = solution[i][j];
                if(solution[i][j] == -1)
                {
                        j++;
                        b = solution[i][j];
                }

                czas += pi.m[a][b];
                a = b;
            }
    	}
        return czas;
    }
    
    private int[] extend(int[] list) {
        ArrayList<Integer> al = new ArrayList<Integer>();

        append(0, list[0], al);
        al.add(-1);
        for (int i = 1; i < list.length; i++) {
            append(list[i - 1], list[i], al);
            //System.out.println("DOPISUJE -1 !!!");
            al.add(-1);
        }
        append(list[list.length - 1], 0, al);
        al.add(0);

        int[] ret = new int[al.size()];

        int i = 0;
        for (int elem : al)
            ret[i++] = elem;

        return ret;
    }

    private void append(int from, int to, ArrayList<Integer> al) {
        while (from != to) {
            al.add(from);
            from = pi.min_to[from][to];
        }
    }
}
