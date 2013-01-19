import java.util.*;

class Run_algorithm
{
	int[][] solution, aux;
	Problem_instance pi;
	
	Run_algorithm(Problem_instance ppi, Algorithm_partition ap, Algorithm_solve as)
	{
		this.pi = ppi;

		aux = ap.run(pi);
		
		solution = new int[aux.length][];
		
		for(int i=0; i<aux.length; i++)
			solution[i] = extend(aux[i]);
	}
	
	int[] extend(int[] list)
	{
		ArrayList <Integer> al = new ArrayList<Integer>();
		
		append(0, list[0], al);
		for(int i=1; i<list.length; i++)
		{
			append(list[i-1], list[i], al);
		}
		append(list[list.length-1], 0, al);
		al.add(0);
		
		int[] ret = new int[al.size()];
		
		int i = 0;
		for(int elem : al)
			ret[i++] = elem;
		
		return ret; 
	}
	
	void append(int from, int to, ArrayList <Integer> al)
	{
		while(from != to)
		{
			al.add(from);
			from = pi.min_to[from][to];
		}
	}
}