package myprojects.rasi;

import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
		//Alg_solve.put("Algo 1", new Algorithm_solve_1());
		Alg_part.put("Random Greedy", new AlgorithmPartitionRandom());
		//Alg_solve.put("Genetic", new AlgorithmSolveGenetic());
		Alg_part.put("Boruvka", new AlgorithmPartitionBoruvka());
		//Alg_solve.put("MST", new AlgorithmSolveMST());
		//Alg_solve.put("Brut", new AlgorithmSolveBrut());
		
		Object[] o = Alg_part.keySet().toArray();
		ap = new String[o.length];
		for(int i=0; i<o.length; i++)
			ap[i] = o[i].toString();
		
		//o = Alg_solve.keySet().toArray();
		as = new String[4];
		as[0] = "Algo 1";
                as[1] = "Genetic";
                as[2] = "MST";
                as[3] = "Brut";
			
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
		
		String algorithmName = JOptionPane.showInputDialog(null, "Wybierz algorytm do rozwi�zania", 
			"Algorytm", JOptionPane.INFORMATION_MESSAGE, null, as, as[0]).toString();
		
                
		
		int minn = pi.count_max();
		
        x = JOptionPane.showInputDialog("Podaj pojemność ciężarówki (minimum "+minn+")");
        pi.W = Integer.parseInt(x);
        

		System.out.println("Starting the partitioner...");
		aux = app.run(pi);
		ExecutorService executor = Executors.newFixedThreadPool(aux.length);
                Future[] futures = new Future[aux.length];
		solution = new int[aux.length][];
                
                System.out.println("Starting solving the pieces...");
                switch (algorithmName)
                {
                    case "Algo 1":
                        for(int i = 0; i < aux.length; i++)
                        {
                            futures[i] = executor.submit(
                                    new Algorithm_solve_1(pi, aux[i]));
                        }
                        break;
                    case "Genetic":
                        for(int i = 0; i < aux.length; i++)
                        {
                            futures[i] = executor.submit(
                                    new AlgorithmSolveGenetic(pi, aux[i]));
                        }
                        break;
                    case "MST":
                        for (int i = 0; i < aux.length; i++)
                        {
                            futures[i] = executor.submit(
                                    new AlgorithmSolveMST(pi, aux[i]));
                        }
                        break;
                    case "Brut":
                        for (int i = 0; i < aux.length; i++)
                        {
                            futures[i] = executor.submit(
                                    new AlgorithmSolveBrut(pi, aux[i]));
                        }
                        break;
                }
		//Algorithm_solve ass = Alg_solve.get(x);
		
		
		for(int i=0; i<aux.length; i++)
		{
                    do 
                    {
                        try
                        {
                            int[] result = (int[])futures[i].get();
                            for(int j: result)
                            System.out.print(" " + j);
                            System.out.println();
                            solution[i] = extend(result);
                        }
                        catch(InterruptedException e) {}
                        catch(ExecutionException e)
                        {
                            System.out.println(e);
                        }
                    }while(!futures[i].isDone());
                }
                    //int[] res = ass.run(pi, aux[i]);
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