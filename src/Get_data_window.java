import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Get_data_window extends JFrame 
{ 
	int[] data;
	JLabel[] label;
	JTextField[] tf;
	int size;

    public Get_data_window(String[] str) 
    {
    	
        setSize(400, 400);
        setTitle("Podaj dane");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        
        
		Box vb = Box.createVerticalBox();
		add(vb);
		vb.setVisible(true);
		
		size = str.length;
		label = new JLabel[size];
		tf = new JTextField[size];
		data = new int[size];
		
		for(int i=0; i<size; i++)
		{
			label[i] = new JLabel(str[i]);
			tf[i] = new JTextField();
			
			label[i].setVisible(true);
			tf[i].setVisible(true);
			
			vb.add(label[i]);
			vb.add(tf[i]);
		}
		
		JButton accept = new JButton("AKCEPTUJ ZMIANY"); 
        accept.setVisible(true);
        vb.add(accept);
                
                
        
        
        class AL5 implements ActionListener
    	{
    		Get_data_window e;
    		
    		AL5(Get_data_window ee)
    		{
    			this.e = ee;
    		}
    		
            public void actionPerformed(ActionEvent ae) 
            {
            	for(int i=0; i<e.size; i++)
            	{
            		data[i] = Integer.parseInt(tf[i].getText());
            	}
            	
    			e.setVisible(false);
            }
    	}
        
        accept.addActionListener(new AL5(this));         
    }    
}