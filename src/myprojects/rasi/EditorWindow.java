
package myprojects.rasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class EditorWindow extends JFrame 
{ 
	ProblemInstance pi;
	JTextArea tf;
	
    public EditorWindow(ProblemInstance ppi) 
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
        
		JButton remove_v = new JButton("USU� WIERZCHOLEK"); 
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
    		EditorWindow e;
    		
    		AL1(EditorWindow ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	String[] str = new String[pi.n+1];
            	
            	for(int i=0; i<pi.n; i++)
            	{
            		str[i] = new String("Podaj d�ugo�� kraw�dzi do " + i);
            	}
            	str[pi.n] = new String("Podaj wag� wierzcho�ka");
            	
            	GetDataWindow gd = new GetDataWindow(str);
            	
            	int[] data = gd.data;
            	
            	System.out.println("dodaje wierzcholek z waga " + data[pi.n]);
            	//pi.add_node(data[pi.n], data);
            	
            	e.reload();
            }
    	}
        
        add_v.addActionListener(new AL1(this));
        
        class AL2 implements ActionListener
    	{
    		EditorWindow e;
    		
    		AL2(EditorWindow ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	String[] str = new String[1];
            	str[0] = new String("Podaj numer wierzcho�ka do kasacji:");
            	
            	GetDataWindow gd = new GetDataWindow(str);
            	
            	int nr = gd.data[0];
            	
            	System.out.println("kasuje " + nr);
            	pi.remove_node(nr);
            	
            	e.reload();
            }
    	}
        
        remove_v.addActionListener(new AL2(this));
        
        
        class AL3 implements ActionListener
    	{
    		EditorWindow e;
    		
    		AL3(EditorWindow ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(EditorWindow.this);
 
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
    		EditorWindow e;
    		
    		AL4(EditorWindow ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(EditorWindow.this);
 
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
    		EditorWindow e;
    		
    		AL5(EditorWindow ee)
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