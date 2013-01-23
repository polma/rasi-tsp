
package myprojects.rasi;


import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ProblemInstance
{
        public int n, W;
	public int[] w = new int[1005];
	public int[][] m = new int[1005][1005];
	public int[][] min_m = new int[1005][1005], min_to = new int[1005][1005];
	public int[] x = new int[1005], y = new int[1005];
	public boolean[] visited = new boolean[1005];
	
	public ProblemInstance() {}
	
	public void load_from_file(String file_name)
	{
		try
		{
			Scanner scanner = new Scanner(new File(file_name));
			// while(scanner.hasNextInt())
	
			n = scanner.nextInt();
			W = scanner.nextInt();
	
			for(int i=1; i<n; i++)
			{
				for(int j=0; j<i; j++)
					m[i][j] = m[j][i] = scanner.nextInt();
			}
			for(int i=0; i<n; i++)
			{
				w[i] = scanner.nextInt();
			}
			for(int i=0; i<n; i++)
			{
				x[i] = scanner.nextInt();
			}
			for(int i=0; i<n; i++)
			{
				y[i] = scanner.nextInt();
			}
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Wczytanie danych nie powid³o siê.",
    				"B³¹d pliku", JOptionPane.ERROR_MESSAGE);
	  	}
	}
	
	public void save_in_file(String file_name)
	{
		try
		{
			System.out.println("Zapisuje " + file_name);
			
			FileWriter fstream = new FileWriter(file_name);
			BufferedWriter out = new BufferedWriter(fstream);
			
			
			out.write(n + " " + W + "\n");
			for(int i=1; i<n; i++)
			{
				for(int j=0; j<i; j++)
					out.write(m[i][j] + " ");
				out.write('\n');
			}
			
			for(int i=0; i<n; i++)
			{
				out.write(w[i] + " ");
			}
			out.write('\n');
			
			for(int i=0; i<n; i++)
			{
				out.write(x[i] + " ");
			}
			out.write('\n');
			
			for(int i=0; i<n; i++)
			{
				out.write(y[i] + " ");
			}
			out.write('\n');
			
			out.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Zapis danych nie powiód³ siê.",
    				"B³¹d pliku", JOptionPane.ERROR_MESSAGE);

	  	}
	}
	
	public void shortest_paths()
	{
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			{
				min_m[i][j] = m[i][j];
				min_to[i][j] = j;
			}

		for(int a = 1; a <= 2*n; a *= 2)
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
					for(int k=0; k<n; k++)
						if(i != k && j != k)
							if(min_m[i][k] != -1 && min_m[k][j] != -1)
								if(min_m[i][j] == -1 || min_m[i][j] > min_m[i][k] + min_m[k][j])
								{
									min_m[i][j] = min_m[i][k] + min_m[k][j];
									min_to[i][j] = min_to[i][k];
								}
	}
	
	public void add_node(int weight, int xx, int yy)
	{
		n++;
		for(int i=0; i<n-1; i++)
			m[i][n-1] = m[n-1][i] = -1;
		m[n-1][n-1] = 0;
		w[n-1] = weight;
		x[n-1] = xx;
		y[n-1] = yy;
	}
	
	public void edit_node(int nr, int weight)
	{
		w[nr] = weight;
	}
	
	public void remove_node(int nr)
	{
		for(int i=0; i<n; i++)
		{
			m[i][nr] = m[i][n-1];
			m[nr][i] = m[n-1][i];
		}
		m[nr][nr] = 0;
		w[nr] = w[n-1];
		x[nr] = x[n-1];
		y[nr] = y[n-1];
		n--;
	}
	
	public void set_edge(int weight, int nr1, int nr2)
	{
		m[nr1][nr2] = m[nr2][nr1] = weight;
	}
	
	public void change_nodes(int nr1, int nr2)
	{
		int c;
		for(int i=0; i<n; i++)
		{
			c = m[i][nr1];
			m[i][nr1] = m[i][nr2]; 
			m[i][nr2] = c;
		}
			
		for(int i=0; i<n; i++)
		{
			c = m[nr1][i];
			m[nr1][i] = m[nr2][i]; 
			m[nr2][i] = c;
		}

		//m[nr][nr] = 0;
		c = w[nr1];
		w[nr1] = w[nr2];
		w[nr2] = c;
		
		c = x[nr1];
		x[nr1] = x[nr2];
		x[nr2] = c;
		
		c = y[nr1];
		y[nr1] = y[nr2];
		y[nr2] = c;
	}
	
	public int count_max()
	{
		int maks = 0;
		for(int i=0; i<n; i++)
			maks = Math.max(maks, w[i]);
							
		return maks;
	}
	
	public int find(int xx, int yy)
	{
		for(int i=0; i<n; i++)
			if(x[i] == xx && y[i] == yy)
				return i;
				
		return -1;
	}
	
	private int dfs(int nr)
	{
		int ile = 0;
		visited[nr] = true;
		
		for(int i=0; i<n; i++)
			if(!visited[i] && m[nr][i] != -1)
				ile += dfs(i);
				
		return ile+1;
	}
	
	public boolean check_integrity()
	{
		for(int i=0; i<n; i++)
			visited[i] = false;
			
		return dfs(0) == n;
	}
}
