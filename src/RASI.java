class RASI
{
	public RASI() 
	{
	}

	public static void main(String args[]) 
	{
		System.out.println("Starting RASI...");
		
		Problem_instance pi = new Problem_instance();
		
		//Main_window w = new Main_window(pi);
		
		pi.load_from_file("/home/pdr/rasi/proj/example.graph");

        Run_algorithm ra = new Run_algorithm(pi, new AlgorithmPartitionRandom(), new AlgorithmSolveGenetic(20, 20, CrossoverOperators.CX));
		//pi.save_in_file("copy.graph");
	}
}
