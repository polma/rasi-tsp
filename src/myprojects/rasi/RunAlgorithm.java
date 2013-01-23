package myprojects.rasi;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class RunAlgorithm {
    int[][] solution, aux;
    ProblemInstance pi;

    HashMap<String, AlgorithmPartition> Alg_part;
    HashMap<String, AlgorithmSolve> Alg_solve;
    String[] ap, as;

    void set_alg() {
        Alg_part = new HashMap<String, AlgorithmPartition>();
        Alg_solve = new HashMap<String, AlgorithmSolve>();

        Alg_part.put("Simple", new AlgorithmPartition1());
        //Alg_solve.put("Algo 1", new Algorithm_solve_1());
        Alg_part.put("Random Greedy", new AlgorithmPartitionRandom());
        //Alg_solve.put("Genetic", new AlgorithmSolveGenetic());
        Alg_part.put("Boruvka", new AlgorithmPartitionBoruvka());
        //Alg_solve.put("MST", new AlgorithmSolveMST());
        //Alg_solve.put("Brut", new AlgorithmSolveBrut());

        Object[] o = Alg_part.keySet().toArray();
        ap = new String[o.length];
        for (int i = 0; i < o.length; i++)
            ap[i] = o[i].toString();

        //o = Alg_solve.keySet().toArray();
        as = new String[4];
        as[0] = "Simple";
        as[1] = "Genetic";
        as[2] = "Greedy";
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

    RunAlgorithm(ProblemInstance ppi) {
        set_alg();
        ppi.shortest_paths();
        
        solution = new int[0][];

        if (!ppi.check_integrity())
        {
            JOptionPane.showMessageDialog(null, "Graf jest niespójny",
                    "B³¹d", JOptionPane.ERROR_MESSAGE);
                    
            return ;
        }

        this.pi = ppi;

        Object o = JOptionPane.showInputDialog(null, "Wybierz algorytm do podzia³u",
                "Algorytm", JOptionPane.INFORMATION_MESSAGE, null, ap, ap[0]);
		if(o == null)
			return;
		String x = o.toString();
		
        AlgorithmPartition app = Alg_part.get(x);

        o = JOptionPane.showInputDialog(null, "Wybierz algorytm do rozwi¹zania",
                "Algorytm", JOptionPane.INFORMATION_MESSAGE, null, as, as[0]);
		if(o == null)
			return;
		String algorithmName = o.toString();

        int minn = pi.count_max();

        x = JOptionPane.showInputDialog("Podaj pojemnoœæ ciê¿arówki (minimum " + minn + ")");
        if(x == null)
			return;
		
		try{	
        	pi.W = Integer.parseInt(x);
        	if(pi.W < minn)
        		throw new Exception("Zle dane");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Niepoprawna wartoœæ",
    			"B³¹d danych", JOptionPane.ERROR_MESSAGE);
    		return ;
		}

        System.out.println("Starting the partitioner...");
        aux = app.run(pi);
        ExecutorService executor = Executors.newFixedThreadPool(aux.length);
        Future[] futures = new Future[aux.length];
        solution = new int[aux.length][];

        System.out.println("Starting solving the pieces...");

        if (algorithmName.equalsIgnoreCase("simple")) {
            for (int i = 0; i < aux.length; i++) {
                futures[i] = executor.submit(
                        new AlgorithmSolve1(pi, aux[i]));
            }
        } else if (algorithmName.equalsIgnoreCase("genetic")) {
            for (int i = 0; i < aux.length; i++) {
                futures[i] = executor.submit(
                        new AlgorithmSolveGenetic(pi, aux[i]));
            }
        } else if (algorithmName.equalsIgnoreCase("greedy")) {
            for (int i = 0; i < aux.length; i++) {
                futures[i] = executor.submit(
                        new AlgorithmSolveGreedy(pi, aux[i]));
            }
        } else if (algorithmName.equalsIgnoreCase("brut")) {
            for (int i = 0; i < aux.length; i++) {
                futures[i] = executor.submit(
                        new AlgorithmSolveBrut(pi, aux[i]));
            }
        }


        for (int i = 0; i < aux.length; i++) {
            do {
                try {
                    int[] result = (int[]) futures[i].get();
                    for (int j : result)
                        System.out.print(" " + j);
                    System.out.println();
                    solution[i] = extend(result);
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    System.out.println(e);
                }
            } while (!futures[i].isDone());
        }
        System.out.println("W run_alg-----");
    }

    int[] extend(int[] list) {
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

    void append(int from, int to, ArrayList<Integer> al) {
        while (from != to) {
            al.add(from);
            from = pi.min_to[from][to];
        }
    }
}