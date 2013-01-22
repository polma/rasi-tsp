
package myprojects.rasi;


import java.util.*;

class AlgorithmPartitionBoruvka implements AlgorithmPartition
{
	public int[][] run(ProblemInstance pi)
	{
		int[][] list = new int[pi.n-1][];
		
		for(int i=1; i<pi.n; i++)
		{
			int[] l = new int[1];
			l[0] = i;
			
			list[i-1] = l;
		}
		
		return list;
	}
}