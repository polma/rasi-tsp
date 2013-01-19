import java.util.*;
import java.io.*;

class Problem_instance
{
	public int n, W;
	public int[] w = new int[1005];
	public int[][] m = new int[1005][1005];
	public int[][] min_m = new int[1005][1005], min_to = new int[1005][1005];
	
	public Problem_instance() {}
	
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
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
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
			
			out.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
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
							if(min_m[i][j] > min_m[i][k] + min_m[k][j])
							{
								min_m[i][j] = min_m[i][k] + min_m[k][j];
								min_to[i][j] = min_to[i][k];
							}
	}
	
	public void add_node(int weight, int edges[])
	{
		n++;
		for(int i=0; i<n-1; i++)
			m[i][n-1] = m[n-1][i] = edges[i];
		m[n-1][n-1] = 0;
		w[n-1] = weight;
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
		n--;
	}
}
