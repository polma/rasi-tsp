
package myprojects.rasi;

class RASI
{
	public RASI() 
	{
	}

	public static void main(String args[]) 
	{
		System.out.println("Starting RASI...");
		
		ProblemInstance pi = new ProblemInstance();
		
		MenuWindow2 w = new MenuWindow2(pi);
		
		//pi.load_from_file("example.graph");
		//pi.save_in_file("copy.graph");
	}
}
