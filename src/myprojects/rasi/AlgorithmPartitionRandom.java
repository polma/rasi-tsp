
package myprojects.rasi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: pdr
 * Date: 1/20/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlgorithmPartitionRandom implements Algorithm_partition {
    @Override
    public int[][] run(Problem_instance pi) {
        Random rnd = new Random();
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int picked = 0;
        boolean[] alreadyPicked = new boolean[pi.n];


        while (picked < pi.n - 1) {
            System.out.println("Picked so far: " + picked + "/" + pi.n);
            int currentTotal = 0;
            List<Integer> current = new ArrayList<Integer>();
            while (true) {
                int r = rnd.nextInt(pi.n-1) + 1;
                if(!alreadyPicked[r]){
                    if(currentTotal + pi.w[r] <= pi.W){
                        alreadyPicked[r] = true;
                        ++picked;
                        currentTotal += pi.w[r];
                        current.add(r);
                    }
                    else{
                        result.add(current);
                        break;
                    }
                }
                if(picked == pi.n - 1){
                    result.add(current);
                    picked = pi.n - 1;
                    break;
                }
            }
        }
        //convert to int[][]
        int [][] resultArray = new int[result.size()][];
        int index  = 0;
        for(List a: result){
            resultArray[index++] = listToArray(a);
        }

        return resultArray;
    }

    private int[] listToArray(List<Integer> list){
    	System.out.println("W Greedy'm");
        int [] result = new int[list.size()];
        for(int i =0; i<list.size(); ++i)
            result[i] = list.get(i);
            
        for(int i=0; i < result.length; i++)
        	System.out.print(result[i] + " ");
        System.out.println(" ");
        return result;
    }
}
