
package myprojects.rasi;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
 
class Menu_window_2 extends JFrame 
{ 
	public Problem_instance pi;
	public JLabel label, label2;
	public DrawPanel dp;
	public JList listbox;
	public JScrollPane listScroller;
	int mode = 0;
	HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
	
    public Menu_window_2(Problem_instance ppi) 
    {
    	pi = ppi;
    
        setSize(800, 550);
        setTitle("RASI");
        //JPanel panel = new JPanel();
        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
       	this.setJMenuBar(this.createMenuBar());
        //frame.setContentPane(demo.createContentPane());
        
       	
        Box vb = Box.createVerticalBox();
        add(vb);
        vb.setVisible(true);
                
       	Box hb = Box.createHorizontalBox();
        vb.add(hb);
                
       	Box hb2 = Box.createHorizontalBox();
        vb.add(hb2);
        
        
        label = new JLabel("Etykietka", JLabel.LEFT);
        hb2.add(label);
        label.setVisible(true);
        label.setSize(400, label.getHeight());
        
        hb2.add(Box.createHorizontalGlue());
        
        label2 = new JLabel("Etykietka", JLabel.RIGHT);
        hb2.add(label2);
        label2.setVisible(true);
        label2.setSize(200, label2.getHeight());
        
        dp = new DrawPanel(pi, this);
        
        hb.add(dp);
        
        String	listData[] =
		{
			"Item 1",
			"Item 2",
			"Item 3",
			"Item 4"
		};

		listbox = new JList( listData );
		listScroller = new JScrollPane(listbox);
		hb.add( listScroller, BorderLayout.CENTER );
		listScroller.setVisible(false);
		
		
	    listbox.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent evt) {
	        if (evt.getValueIsAdjusting())
	          return;
	        System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
	        dp.actual = hm.get(evt.getLastIndex()+1);
	        dp.prev = hm.get(evt.getLastIndex());
	        
	        dp.repaint();
	      }
	    });
		
       	setSize(800, 600);

        //listbox.hide();
        

        /*
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
        
        vb.add(edit);
        vb.add(run_alg);
        vb.add(exit);
        */
         
    }
    
    public JMenuBar createMenuBar() 
    {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        
        

	    MenuListener listener = new MenuListener() 
	    {
	      public void menuCanceled(MenuEvent e) {}
	
	      public void menuDeselected(MenuEvent e) {}
	
	      public void menuSelected(MenuEvent e) {
	      	clear();
	      }
	
	      private void dumpInfo(String s, MenuEvent e) {
	        JMenu menu = (JMenu) e.getSource();
	        System.out.println(s + ": " + menu.getText());
	      }
	    };
	    

        
        menuBar = new JMenuBar();

		menu = new JMenu("Edycja grafu");

		menuItem = new JMenuItem("Dodaj wierzchołek", KeyEvent.VK_D);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(1);
		    }	  		
    	});
    	
		menuItem = new JMenuItem("Edytuj wierzchołek", KeyEvent.VK_E);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(2);
		    }	  		
    	});
    	
		menuItem = new JMenuItem("Usuń wierzchołek", KeyEvent.VK_U);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(3);
		    }	  		
    	});
    	
        
        menu.addSeparator();


		menuItem = new JMenuItem("Dodaj krawędź", KeyEvent.VK_K);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(11);
		    }	  		
    	});
    	
		menuItem = new JMenuItem("Edytuj krawędź", KeyEvent.VK_R);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(12);
		    }	  		
    	});
    	
		menuItem = new JMenuItem("Usuń krawędź", KeyEvent.VK_A);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(13);
		    }	  		
    	});
    	
        
        menu.addSeparator();


		menuItem = new JMenuItem("Zmień punkt startowy", KeyEvent.VK_K);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	setMode(21);
		    }	  		
    	});
    	

		menuBar.add(menu);
	    menu.addMenuListener(listener);



		
		menu = new JMenu("Pliki");

		menuItem = new JMenuItem("Wczytaj z pliku", KeyEvent.VK_D);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(Menu_window_2.this);
 
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                pi.load_from_file(file.getAbsolutePath());
	                dp.repaint();
	            }
		    }	  		
    	});
    	
		menuItem = new JMenuItem("Zapisz do pliku", KeyEvent.VK_E);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
            	JFileChooser fc = new JFileChooser();
            	int returnVal = fc.showOpenDialog(Menu_window_2.this);
 
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                String name = file.getAbsolutePath();
	                
	            	System.out.println(name);
	                
	                pi.save_in_file(name);
	                dp.repaint();
	            }
		    }	  		
    	});
    	

		menuBar.add(menu);
	    menu.addMenuListener(listener);



		menu = new JMenu("Rozwiązanie");

		menuItem = new JMenuItem("Znajdź rozwiązanie", KeyEvent.VK_D);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	label.setText("Algorytm uruchomiony, trwają obliczenia...");
		    	
		    	Run_algorithm ra = new Run_algorithm(pi);
		    	int[][] sol = ra.solution;
		    	update_list(sol);
		    	listScroller.setVisible(true);
		    	
		    	label.setText("Obliczenia zakończone!");
		    }	  		
    	});
    	

		menuBar.add(menu);
	    menu.addMenuListener(listener);



    	return menuBar;
    }
    
    void setMode(int nr)
    {
    	mode = nr;
    	if(mode == 1)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być dodany wierzchołek");
    	}
    	else if(mode == 2)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być edytowany wierzchołek");
    	}
    	else if(mode == 3)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być usunięty wierzchołek");
    	}
    	else if(mode == 11)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być dodana krawędź");
    	}
    	else if(mode == 12)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być edytowana krawędź");
    	}
    	else if(mode == 13)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być usunięta krawędź");
    	}
    	else if(mode == 21)
    	{
    		label.setText("Kliknij miejsce, gdzie ma być wierzchołek startowy");
    	}
    	else
    	{
    		label.setText("");
    	}
	    dp.repaint();
    }
    
    void update_list(int[][] solution)
    {
    	for(int[] a: solution){
    		for(int i: a)
    			System.out.print(" " + i);
    		System.out.println();
    	}
    	listbox.setVisible(true);
    	
    	int ile = 0, ii = 0;
    	for(int i=0; i<solution.length; i++)
    		ile += solution[i].length-1;
    		
    	String[] str = new String[ile];
    	String aux;
    	ile = 1;
    	
    	hm.clear();
    	hm.put(0, 0);
    	
    	int a, b, czas = 0, zaladowanie = 0;
    	for(int i=0; i<solution.length; i++)
    	{
    		a = solution[i][0];
    		for(int j=1; j<solution[i].length; j++)
    		{
    			b = solution[i][j];
    			aux = "";
    			if(solution[i][j] == -1)
    			{
    				j++;
    				b = solution[i][j];
    				zaladowanie += pi.w[b];
    				aux = " i załaduj";
    			}
    			System.out.println(solution[i][j]);
    			if(b == 0)
    			{
    				aux = " i rozładuj";
    				zaladowanie = 0;
    			}
    			
    			czas += pi.m[a][b];
    			str[ii] = ile + ". Jedź z " + a + " do " + b + aux +
    				 	", czas: " + czas + ", załadowanie: " +
    					zaladowanie;
    			
    			hm.put(ile, b);
    			
    			a = b;	
    			ii++;
    			ile++;
    		}
    	}
    			
    	listbox.setListData(str);
    }
    
    void clear()
    {
    	listScroller.setVisible(false);
    	dp.actual = dp.prev = -1;
	    mode = 0;
	    dp.pressed_x = dp.pressed_y = -1;
	    dp.repaint();
    }
}