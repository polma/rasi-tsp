
package myprojects.rasi;


import java.util.*;

class AlgorithmPartitionBoruvka implements AlgorithmPartition
{
	int[] father;
	int[] size;
	int[] weight;
	
	int find(int nr)
	{
		if(father[nr] == -1)
			return nr;
		father[nr] = find(father[nr]);
		return father[nr];
	}
	
	boolean union(int a, int b)
	{
		System.out.println("Union " + a + " " + b);
		a = find(a);
		b = find(b);
		System.out.println("   -> " + a + " " + b);
		
		if(a == b)   return false;
		
		if(size[a] > size[b])
		{
			size[a] += size[b]+1;
			weight[a] += weight[b];
			father[b] = a;
		}
		else
		{
			size[b] += size[a]+1;
			weight[b] += weight[a];
			father[a] = b;
		}
		
		//System.out.println("w: " + weight[a] + ",  " + b);
		
		return true;
	}
	
	public int[][] run(ProblemInstance pi)
	{
		int[][] list = new int[pi.n-1][];
		father = new int[pi.n];
		size = new int[pi.n];
		weight = new int[pi.n];
		boolean[] visited = new boolean[pi.n];
		
		for(int i=0; i<pi.n; i++)	
		{
			father[i] = -1;
			weight[i] = pi.w[i];
		}
		
		for(int i=1; i<2*pi.n; i *= 2)
		{
			for(int j=1; j<pi.n; j++)
			{
				int minn = 1000000007, nr = -1;
				
				for(int k=1; k<pi.n; k++)
				{
					if(find(j) != find(k) && weight[find(j)] + weight[find(k)]
							<= pi.W && pi.min_m[j][k] < minn)
					{
						minn = pi.min_m[j][k];
						nr = k;
					}
				}
				
				if(nr != -1)
					union(j, nr);
			}
		}
		
		ArrayList<ArrayList<Integer> > all = new ArrayList<ArrayList<Integer> >();
		for(int i=1; i<pi.n; i++)
		{
			if(!visited[find(i)])
			{
				visited[find(i)] = true;
				ArrayList <Integer> al = new ArrayList<Integer>();

				for(int j=1; j<pi.n; j++)
				{
					if(find(i) == find(j))
						al.add(j);
				}
				
				all.add(al);
			}
		}
		
		list = new int[all.size()][];
		
		int ile = 0, il2 = 0;
		for(ArrayList <Integer> al : all)
		{
			il2 = 0;
			list[ile] = new int[al.size()];
			for(int el : al)
				list[ile][il2++] = el;
				
			ile++;
		}
		
		System.out.println("Boruvka:");
		for(int[] l : list)
		{
			for(int el : l)
				System.out.print(el + " ");
			System.out.println();
		}
		
		return list;
	}
}