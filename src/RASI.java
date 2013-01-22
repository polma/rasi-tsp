
package myprojects.rasi;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


class RASI
{
	public RASI() 
	{
	}

	public static void main(String args[]) 
	{
		System.out.println("Starting RASI...");
		
		Problem_instance pi = new Problem_instance();
		
		Menu_window_2 w = new Menu_window_2(pi);
		
		//pi.load_from_file("example.graph");
		//pi.save_in_file("copy.graph");
	}
}
