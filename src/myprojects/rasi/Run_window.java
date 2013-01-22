
package myprojects.rasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Run_window extends JFrame 
{ 
	Problem_instance pi;
	JTextArea tf;
	JComboBox cb1, cb2;
	
	HashMap <String, Algorithm_partition> Alg_part;
	HashMap <String, Algorithm_solve> Alg_solve;
	String[] ap, as;
	
	void set_alg()
	{
		Alg_part = new HashMap <String, Algorithm_partition>();
		Alg_solve = new HashMap <String, Algorithm_solve>();
			
		Alg_part.put("Algo 1", new Algorithm_partition_1());
		Alg_solve.put("Algo 1", new Algorithm_solve_1());
		
		Object[] o = Alg_part.keySet().toArray();
		ap = new String[o.length];
		for(int i=0; i<o.length; i++)
			ap[i] = o[i].toString();
		
		o = Alg_solve.keySet().toArray();
		as = new String[o.length];
		for(int i=0; i<o.length; i++)
			as[i] = o[i].toString();
			
		Object x = JOptionPane.showInputDialog(null, "abc", "def", JOptionPane.QUESTION_MESSAGE);
		/*,	
			"Wybierz algorytmy", "Algorytm",
			JOptionPane.INFORMATION_MESSAGE, null,			
			ap, ap[0]);*/
		
		System.out.println(x);
		
		final JOptionPane optionPane = new JOptionPane("Abc", JOptionPane.QUESTION_MESSAGE, 
			JOptionPane.YES_NO_CANCEL_OPTION, null, ap, ap[0]) ;
		
		optionPane.createDialog("tytul");
		
		optionPane.showInputDialog("ACCA");;
		
		System.out.println(x);
	}
	
    public Run_window(Problem_instance ppi) 
    {
    	set_alg();
    	
    	pi = ppi;
    	
        setSize(800, 400);
        setTitle("Edytuj");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
		Box vb = Box.createVerticalBox();
		add(vb);
		vb.setVisible(true);
		
        Box hb = Box.createHorizontalBox();
		vb.add(hb);
		hb.setVisible(true);
		
		tf = new JTextArea();
		vb.add(tf);
		tf.setVisible(true);
		tf.setEditable(false);


		JLabel label1 = new JLabel("Algorytm podzialu:");
		hb.add(label1);
		label1.setVisible(true);
		
		cb1 = new JComboBox(ap);
		hb.add(cb1);
		cb1.setVisible(true);

		JLabel label2 = new JLabel("Algorytm rozwiazania:");
		hb.add(label2);
		label2.setVisible(true);
		
		cb2 = new JComboBox(as);
		hb.add(cb2);
		cb2.setVisible(true);
		
		
		JButton run = new JButton("URUCHOM"); 
        run.setVisible(true);
        hb.add(run);
        
		JButton exit = new JButton("WSTECZ"); 
        exit.setVisible(true);
        hb.add(exit);
        
                
        
        class AL1 implements ActionListener
    	{
    		Run_window e;
    		
    		AL1(Run_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	Algorithm_partition ap1 = Alg_part.get((String)(e.cb1.getSelectedItem()));
            	Algorithm_solve as1 = Alg_solve.get((String)(e.cb2.getSelectedItem()));
            	
            	//myprojects.rasi.Run_algorithm ra = new myprojects.rasi.Run_algorithm(pi, ap1, as1);
            	
            	//e.show_solution(ra.solution);
            }
    	}
        
        run.addActionListener(new AL1(this));
        
        
        class AL5 implements ActionListener
    	{
    		Run_window e;
    		
    		AL5(Run_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {	
            	Frame f = new Frame();
            	Object x = -77;
            	
		    	JOptionPane.showMessageDialog(f, "alert", "alert", JOptionPane.ERROR_MESSAGE);
		
			    JOptionPane.showMessageDialog(null, "information",
		
		            "information", JOptionPane.INFORMATION_MESSAGE);
		
				System.out.println(x);
			    x = JOptionPane.showConfirmDialog(null,
		
		            "choose one", "choose one", JOptionPane.YES_NO_OPTION);
		
			    System.out.println(x);
			    x = JOptionPane.showConfirmDialog(null,
		
		            "please choose one", "information",
		
		        	JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		            
		        System.out.println(x);
			    x = JOptionPane.showConfirmDialog(null,

			            "please choose one", "information",
			
			            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			    Object[] options = { "OK", "CANCEL" };
			    System.out.println(x);
			    x = JOptionPane.showOptionDialog(null, "Click OK to continue", "Warning",
			
			            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			
			            null, options, options[0]);
			
			    System.out.println(x);
			    x = JOptionPane.showInputDialog("Please input a value");
			
			    Object[] possibleValues = { "First", "Second", "Third" };
			    System.out.println(x);
			    x = JOptionPane.showInputDialog(null,
			
			            "Choose one", "Input",
			
			            JOptionPane.INFORMATION_MESSAGE, null,
			
			            possibleValues, possibleValues[0]);
				System.out.println(x);
			    

            
    			e.dispose();
            }
    	}
        
        exit.addActionListener(new AL5(this)); 
    }
    
    void show_solution(int[][] solution)
    {
    	String str = "";
    	
    	for(int i=0; i < solution.length; i++)
    	{
    		for(int el : solution[i])
    			str += el + " ";

    		str += '\n';
    	}
    	
    	tf.setText(str);
    }
}