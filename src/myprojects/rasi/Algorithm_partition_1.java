
package myprojects.rasi;


import java.util.*;

class Algorithm_partition_1 implements Algorithm_partition
{
	public int[][] run(Problem_instance pi)
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