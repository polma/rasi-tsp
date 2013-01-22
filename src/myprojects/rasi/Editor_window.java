
package myprojects.rasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Editor_window extends JFrame 
{ 
	Problem_instance pi;
	JTextArea tf;
	
    public Editor_window(Problem_instance ppi) 
    {
    	pi = ppi;
    	
        setSize(400, 400);
        setTitle("Edytuj");
        //JPanel panel = new JPanel();
        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
        Box hb = Box.createHorizontalBox();
		add(hb);
		hb.setVisible(true);
		
		tf = new JTextArea();
		hb.add(tf);
		tf.setVisible(true);
		tf.setEditable(false);
		reload();
		
		Box vb = Box.createVerticalBox();
		hb.add(vb);
		vb.setVisible(true);
		
		
		JButton add_v = new JButton("DODAJ WIERZCHOLEK"); 
        add_v.setVisible(true);
        vb.add(add_v);
        
		JButton remove_v = new JButton("USUÑ WIERZCHOLEK"); 
        remove_v.setVisible(true);
        vb.add(remove_v);
		
		JButton load = new JButton("WCZYTAJ GRAF"); 
        load.setVisible(true);
        vb.add(load);
		
		JButton save = new JButton("ZAPISZ GRAF"); 
        save.setVisible(true);
        vb.add(save);
        
		JButton accept = new JButton("AKCEPTUJ ZMIANY"); 
        accept.setVisible(true);
        vb.add(accept);
        
                
        
        class AL1 implements ActionListener
    	{
    		Editor_window e;
    		
    		AL1(Editor_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	String[] str = new String[pi.n+1];
            	
            	for(int i=0; i<pi.n; i++)
            	{
            		str[i] = new String("Podaj d³ugoœæ krawêdzi do " + i);
            	}
            	str[pi.n] = new String("Podaj wagê wierzcho³ka");
            	
            	Get_data_window gd = new Get_data_window(str);
            	
            	int[] data = gd.data;
            	
            	System.out.println("dodaje wierzcholek z waga " + data[pi.n]);
            	//pi.add_node(data[pi.n], data);
            	
            	e.reload();
            }
    	}
        
        add_v.addActionListener(new AL1(this));
        
        class AL2 implements ActionListener
    	{
    		Editor_window e;
    		
    		AL2(Editor_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	String[] str = new String[1];
            	str[0] = new String("Podaj numer wierzcho³ka do kasacji:");
            	
            	Get_data_window gd = new Get_data_window(str);
            	
            	int nr = gd.data[0];
            	
            	System.out.println("kasuje " + nr);
            	pi.remove_node(nr);
            	
            	e.reload();
            }
    	}
        
        remove_v.addActionListener(new AL2(this));
        
        
        class AL3 implements ActionListener
    	{
    		Editor_window e;
    		
    		AL3(Editor_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(Editor_window.this);
 
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                pi.load_from_file(file.getAbsolutePath());
	            }
	            
	            e.reload();
            }
    	}
        
        load.addActionListener(new AL3(this));
        
        
        class AL4 implements ActionListener
    	{
    		Editor_window e;
    		
    		AL4(Editor_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(Editor_window.this);
 
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                String name = file.getAbsolutePath();
	                
	            	System.out.println(name);
	                
	                pi.save_in_file(name);
	            }
	            
	            e.reload();
            }
    	}
        
        save.addActionListener(new AL4(this));
        
        
        class AL5 implements ActionListener
    	{
    		Editor_window e;
    		
    		AL5(Editor_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	pi.shortest_paths();
            	
    			e.dispose();
            }
    	}
        
        accept.addActionListener(new AL5(this));         
    }
    
    void reload()
    {
    	String str = new String();
    	str += pi.n + " " + pi.W + "\n\r\n\r";
    	
    	for(int i=0; i<pi.n; i++)
    	{
    		for(int j=0; j<pi.n; j++)
    			str += pi.m[i][j] + " ";
    		str += "\n\r";
    	}
    	str += "\n\r";
    	
    	for(int i=0; i<pi.n; i++)
    	{
    			str += pi.w[i] + " ";
    	}
    	str += "\n\r";
    	
    	tf.setText(str);
    }
}