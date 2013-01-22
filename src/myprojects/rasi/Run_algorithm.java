package myprojects.rasi;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

class Run_algorithm
{
	int[][] solution, aux;
	Problem_instance pi;
	
	HashMap <String, Algorithm_partition> Alg_part;
	HashMap <String, Algorithm_solve> Alg_solve;
	String[] ap, as;
	
	void set_alg()
	{
		Alg_part = new HashMap <String, Algorithm_partition>();
		Alg_solve = new HashMap <String, Algorithm_solve>();
			
		Alg_part.put("Algo 1", new Algorithm_partition_1());
		Alg_solve.put("Algo 1", new Algorithm_solve_1());
		Alg_part.put("Random Greedy", new AlgorithmPartitionRandom());
		Alg_solve.put("Genetic", new AlgorithmSolveGenetic());
		Alg_part.put("Boruvka", new AlgorithmPartitionBoruvka());
		Alg_solve.put("MST", new AlgorithmSolveMST());
		Alg_solve.put("Brut", new AlgorithmSolveBrut());
		
		Object[] o = Alg_part.keySet().toArray();
		ap = new String[o.length];
		for(int i=0; i<o.length; i++)
			ap[i] = o[i].toString();
		
		o = Alg_solve.keySet().toArray();
		as = new String[o.length];
		for(int i=0; i<o.length; i++)
			as[i] = o[i].toString();
			
		//Object x = JOptionPane.showInputDialog(null, "abc", "def", JOptionPane.QUESTION_MESSAGE);
		/*,	
			"Wybierz algorytmy", "Algorytm",
			JOptionPane.INFORMATION_MESSAGE, null,			
			ap, ap[0]);*/
		/*
		System.out.println(x);
		
		final JOptionPane optionPane = new JOptionPane("Abc", JOptionPane.QUESTION_MESSAGE, 
			JOptionPane.YES_NO_CANCEL_OPTION, null, ap, ap[0]) ;
		
		optionPane.createDialog("tytul");
		
		optionPane.showInputDialog("ACCA");;
		
		System.out.println(x);*/
	}
	
	Run_algorithm(Problem_instance ppi)
	{
		set_alg();
		ppi.shortest_paths();
		
		if(!ppi.check_integrity())
			JOptionPane.showMessageDialog(null, "Graf jest niesp�jny",
    				"B��d", JOptionPane.ERROR_MESSAGE);
		
		this.pi = ppi;
		
		String x = JOptionPane.showInputDialog(null, "Wybierz algorytm do podzia�u", 
			"Algorytm", JOptionPane.INFORMATION_MESSAGE, null, ap, ap[0]).toString();
			
		Algorithm_partition app = Alg_part.get(x);
		
		x = JOptionPane.showInputDialog(null, "Wybierz algorytm do rozwi�zania", 
			"Algorytm", JOptionPane.INFORMATION_MESSAGE, null, as, as[0]).toString();
			
		Algorithm_solve ass = Alg_solve.get(x);
		
		int minn = pi.count_max();
		
        x = JOptionPane.showInputDialog("Podaj pojemno�� ci�ar�wki (minimum "+minn+")");
        pi.W = Integer.parseInt(x);

		System.out.println("Starting the partitioner...");
		aux = app.run(pi);
		
		solution = new int[aux.length][];
		
		System.out.println("Starting solving the pieces...");
		for(int i=0; i<aux.length; i++)
		{
			int[] res = ass.run(pi, aux[i]);
			for(int j: res)
    			System.out.print(" " + j);
    		System.out.println();
			solution[i] = extend(res);
		}
		System.out.println("W run_alg-----");	
	}
	
	int[] extend(int[] list)
	{
		ArrayList <Integer> al = new ArrayList<Integer>();
		
		append(0, list[0], al);
		al.add(-1);
		for(int i=1; i<list.length; i++)
		{
			append(list[i-1], list[i], al);
			//System.out.println("DOPISUJE -1 !!!");
			al.add(-1);
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