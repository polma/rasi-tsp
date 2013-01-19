class RASI
{
	public RASI() 
	{
	}

	public static void main(String args[]) 
	{
		System.out.println("Starting RASI...");
		
		Problem_instance pi = new Problem_instance();
		
		Main_window w = new Main_window(pi);
		
		pi.load_from_file("example.graph");
		//pi.save_in_file("copy.graph");
	}
}
