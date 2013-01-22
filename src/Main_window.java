
package myprojects.rasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
class Main_window extends JFrame 
{ 
	Problem_instance pi;
	
    public Main_window(Problem_instance ppi) 
    {
    	pi = ppi;
    
        setSize(400, 200);
        setTitle("RASI");
        //JPanel panel = new JPanel();
        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JButton edit = new JButton("EDYTUJ GRAF"); 
        edit.setVisible(true);
        //add(edit);
        
        edit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	Editor_window ed = new Editor_window(pi);
            }
        });
        
        JButton run_alg = new JButton("URUCHOM ALGORYTM"); 
        run_alg.setVisible(true);
        //add(run_alg);
        
        run_alg.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	Run_window ed = new Run_window(pi);
            }
        });
        
        JButton exit = new JButton("WYJDZ"); 
        exit.setVisible(true);
        //add(exit);
        
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) 
            {
            	System.exit(0);
            }
        });
        
        Box vb = Box.createVerticalBox();
        vb.add(edit);
        vb.add(run_alg);
        vb.add(exit);
        
        add(vb);
        vb.setVisible(true);
         
    }
}