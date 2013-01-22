
package myprojects.rasi;


import javax.swing.*;
import java.awt.*;
import java.util.*;

class AlgorithmSolveBrut implements Algorithm_solve
{
	int[] tab, best;
	int result, sum;
	Problem_instance pi;
	
	void generate(int nr)
	{
		if(nr == tab.length)
		{
			if(sum + pi.min_m[tab[nr-1]][0] < result)
			{
				result = sum + pi.min_m[tab[nr-1]][0];
				for(int j=0; j<tab.length; j++)
					best[j] = tab[j];
			}
			for(int j=0; j<tab.length; j++)
				System.out.print(tab[j] + " ");
			System.out.print("\n");
			
			return ;
		}
		
		if(nr == 0)
			sum += pi.min_m[0][tab[nr]];
		else
		{
			//System.out.println((nr-1) + " " + nr);
			//System.out.println(tab[(nr-1)] + " " + tab[nr]);
			sum += pi.min_m[tab[nr-1]][tab[nr]];
		}
			
		for(int i=nr; i<tab.length; i++)
		{
			int c = tab[nr];
			tab[nr] = tab[i];
			tab[i] = c;
			
			generate(nr+1);
			
			c = tab[nr];
			tab[nr] = tab[i];
			tab[i] = c;
		}
		
		if(nr == 0)
			sum -= pi.min_m[0][tab[nr]];
		else
			sum -= pi.min_m[tab[nr-1]][tab[nr]];
	}
	
	public int[] run(Problem_instance ppi, int[] list)
	{
		for(int i=0; i<list.length; i++)
		pi = ppi;
		
		tab = list;
		best = new int[list.length];
		
		if(pi.n > 10)
			JOptionPane.showMessageDialog(null, "n > 10, bêdzie d³ugo dzia³a³o",
    				"Warning", JOptionPane.ERROR_MESSAGE);
		
		result = 1000000007;
		
		generate(0);
		
		return best;
	}
}