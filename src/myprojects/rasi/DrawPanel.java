
package myprojects.rasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
 


class DrawPanel extends JPanel implements MouseListener, MouseMotionListener
{   
	Menu_window_2 parent;
	Problem_instance pi;    	
	int sq = 10;
	int pressed_x = -1, pressed_y = -1, pos_x = -1, pos_y = -1;
	int actual = -1, prev = -1;
	//boolean[][] visited = new boolean[1005][1005];

	DrawPanel(Problem_instance ppi, Menu_window_2 par)
	{
		pi = ppi;
		parent = par;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}

    public void paintComponent(Graphics g) 
    {
		super.paintComponent(g);
		repaint(g);
	}
    
    void repaint(Graphics g)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	
	    Dimension size = getSize();
	    Insets insets = getInsets();
	
	    int w = size.width - insets.left - insets.right -1;
	    int h = size.height - insets.top - insets.bottom -1;
	    
	    w = w/sq*sq;
	    h = h/sq*sq;
	    
	    g2d.setColor(Color.blue);
	    
	    for(int i=0; i<=w; i+=sq)
	    {
	    	g2d.drawLine(i, 0, i, h);
	    }
	    
	    for(int i=0; i<=h; i+=sq)
	    {
	    	g2d.drawLine(0, i, w, i);
	    }
	    
	    g2d.setColor(Color.black);
	    for(int i=0; i<pi.n; i++)
		    for(int j=i+1; j<pi.n; j++)
		    	if(pi.m[i][j] > 0)
			    	g2d.drawLine(sq*pi.x[i]+sq/2, sq*pi.y[i]+sq/2, 
			    				 sq*pi.x[j]+sq/2, sq*pi.y[j]+sq/2);
			    				 
		if(pressed_x != -1 && pressed_y != -1)
			g2d.drawLine(sq*pressed_x+sq/2, sq*pressed_y+sq/2, 
			    	sq*pos_x+sq/2, sq*pos_y+sq/2);
		
		if(actual != -1)
		{
			g2d.setColor(Color.red);
			g2d.drawLine(sq*pi.x[actual]+sq/2, sq*pi.y[actual]+sq/2, 
			    	sq*pi.x[prev]+sq/2, sq*pi.y[prev]+sq/2);
		}
		
		if(pi.n > 0)
		{
			g2d.setColor(Color.yellow);
			g2d.fillOval(sq*pi.x[0], sq*pi.y[0], sq, sq);
		} 
		
		g2d.setColor(Color.green);
		for(int i=1; i<pi.n; i++)
		{
		   	g2d.fillOval(sq*pi.x[i], sq*pi.y[i], sq, sq);
		}
		
		if(actual != -1)
		{
			g2d.setColor(Color.red);
		   	g2d.fillOval(sq*pi.x[actual], sq*pi.y[actual], sq, sq);
		}

/*	    
	
	    for (int i = 0; i <= 1000; i++) {
	
	            Random r = new Random();
	            int x = Math.abs(r.nextInt()) % w;
	            int y = Math.abs(r.nextInt()) % h;
	            g2d.drawLine(x, y, x, y);
	    }*/
    }
    
    void eventOutput(String eventDescription, MouseEvent e) {
        parent.label.setText(eventDescription + " detected on "
                + e.getX() + ", " + e.getY() + "\n");
        //textArea.setCaretPosition(textArea.getDocument().getLength());
    }
     
    public void mousePressed(MouseEvent e) {
        pressed_x = e.getX()/sq;
        pressed_y = e.getY()/sq;
    }
     
    public void mouseReleased(MouseEvent e) {
        eventOutput("Mouse released (# of clicks: "
                + e.getClickCount() + ")", e);
                
        int px = e.getX()/sq;
        int py = e.getY()/sq;
                
        if(pressed_x != px || pressed_y != py)
        {
        	if(parent.mode == 11)
        	{
        		String in = JOptionPane.showInputDialog("Podaj wag� kraw�dzi");
        		int nr1 = pi.find(pressed_x, pressed_y);
        		int nr2 = pi.find(px, py);
        		pi.set_edge(Integer.parseInt(in), nr1, nr2);
        	}
        	else if(parent.mode == 12)
        	{
        		String in = JOptionPane.showInputDialog("Podaj now� wag� kraw�dzi");
        		int nr1 = pi.find(pressed_x, pressed_y);
        		int nr2 = pi.find(px, py);
        		pi.set_edge(Integer.parseInt(in), nr1, nr2);
        	}
        	else if(parent.mode == 13)
        	{
        		//String in = JOptionPane.showInputDialog("Podaj wag� kraw�dzi");
        		int nr1 = pi.find(pressed_x, pressed_y);
        		int nr2 = pi.find(px, py);
        		pi.set_edge(-1, nr1, nr2);
        	}
        	
        	pressed_x = pressed_y = -1;
        	parent.setMode(0);
    	}
	    repaint();
    }
     
    public void mouseEntered(MouseEvent e) {}
     
    public void mouseExited(MouseEvent e) {}
     
    public void mouseClicked(MouseEvent e) {
        eventOutput("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);
        
        int px = e.getX()/sq;
        int py = e.getY()/sq;
        
        if (e.getClickCount() == 2)
        {
            int nr = pi.find(px, py);
            if (nr == -1)
            {
                String in = JOptionPane.showInputDialog("Podaj wagę wierzcho�ka");
        	if (in != null)
                    pi.add_node(Integer.parseInt(in), px, py);
            }
            else
            {
                String in = JOptionPane.showInputDialog("Podaj nową wagę wierzcho�ka");
                if (in != null)
                    pi.edit_node(Integer.parseInt(in), nr);
            }
        }
        
        if(parent.mode == 1)
        {
        	String in = JOptionPane.showInputDialog("Podaj wag� wierzcho�ka");
        	if (in != null)
                    pi.add_node(Integer.parseInt(in), px, py);
        }
        else if(parent.mode == 2)
        {
        	String in = JOptionPane.showInputDialog("Podaj now� wag� wierzcho�ka");
        	int nr = pi.find(px, py);
                if (in != null)
                    pi.edit_node(Integer.parseInt(in), nr);
        }
        else if(parent.mode == 3)
        {
        	//String in = JOptionPane.showInputDialog("Podaj now� wag� wierzcho�ka");
        	int nr = pi.find(px, py);
        	pi.remove_node(nr);
        }
        else if(parent.mode == 21)
        {
        	int nr = pi.find(px, py);
        	if(nr != 0)
        		pi.change_nodes(0, nr);
        }
        
        pressed_x = pressed_y = -1;
        parent.setMode(0);
	    repaint();
    }
     
    public void mouseMoved(MouseEvent e) {
        pos_x = e.getX()/sq;
        pos_y = e.getY()/sq;
        
        int nr = pi.find(pos_x, pos_y);
        String str = "";
        
        if(nr != -1)
        	str = "nr: " + nr + ", waga: " + pi.w[nr] + ",\t ";
        str += "x: " + pos_x + ", y: " + pos_y;
        
        parent.label2.setText(str);
        repaint();
    }
     
    public void mouseDragged(MouseEvent e) {
        pos_x = e.getX()/sq;
        pos_y = e.getY()/sq;
        
        int nr = pi.find(pos_x, pos_y);
        String str = "";
        
        if(nr != -1)
        	str = "nr: " + nr + ", waga: " + pi.w[nr] + ",\t ";
        str += "x: " + pos_x + ", y: " + pos_y;
        
        parent.label2.setText(str);
        repaint();
    }
}