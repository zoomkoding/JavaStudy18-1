package project2_Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame extends JFrame{
	Point start =null;
	Point end =null;
	JPanel optionPanel = new JPanel();
	Canvas canvas = new Canvas();
	JMenuBar menubar = new JMenuBar();
	int option;
	Vector<Point> spforline = new Vector<Point>();
	Vector<Point> epforline = new Vector<Point>();
	Vector<Point> spforrect = new Vector<Point>();
	Vector<Point> epforrect = new Vector<Point>();
	
	public Frame() {
		Dimension dim = new Dimension(400,600);
		Dimension dim1 = new Dimension(400,30);
		Dimension dim2 = new Dimension(400,570);
		
		
		setTitle("Jin's Studio");
		setLocation(600, 200);
		setPreferredSize(dim);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(optionPanel, "North");
		add(canvas);
		setJMenuBar(menubar);
		
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		JMenuItem help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		exit.addActionListener(new ExitAction());
		
		
		
		
		optionPanel.setLayout(new GridLayout(1, 6));
		optionPanel.setPreferredSize(dim1);
		
		
		JButton[] button = new JButton[6]; 
		button[0] = new JButton("Line");
		button[1] = new JButton("Rect");
		button[2] = new JButton("Circle");
		button[3] = new JButton("PolyLine");
		button[4] = new JButton("Sketch");
		button[5] = new JButton("Extra");
		for(int i = 0; i<button.length; i++) {
			optionPanel.add(button[i]);
			button[i].addActionListener(new ButtonAction());
		}
	}

	class Canvas extends JPanel {

		

		MyMouseListener ml = new MyMouseListener();


		Canvas(){
			addMouseListener(ml);
			addMouseMotionListener(ml);

		}
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			if(epforline != null) {
				for(int i = 0 ; i < epforline.size(); i++) {
					g.drawLine(spforline.get(i).x, spforline.get(i).y, epforline.get(i).x, epforline.get(i).y);
				}
			}
			if(start != null && option == 1)
				g.drawLine(start.x, start.y, end.x, end.y);
			
			if(epforrect != null) {
				for(int i = 0 ; i < epforrect.size(); i++) {
					g.drawRect(spforrect.get(i).x, spforrect.get(i).y, Math.abs(epforrect.get(i).x - spforrect.get(i).x), Math.abs(epforrect.get(i).y - spforrect.get(i).y));
				}
			}
			if(start != null && option == 2)
				g.drawRect(start.x, start.y, Math.abs(end.x-start.x), Math.abs(end.y-start.y));

		}


		class MyMouseListener extends MouseAdapter implements MouseMotionListener {

			public void mousePressed(MouseEvent e) {
				if(option == 1) spforline.add(e.getPoint());
				else if(option == 2) spforrect.add(e.getPoint());
				
				start = e.getPoint();
			}

			public void mouseReleased(MouseEvent e) {
				if(option == 1) epforline.add(e.getPoint());
				else if(option == 2) spforrect.add(e.getPoint());	
				
				end = e.getPoint();
				repaint();
			}



			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				end = e.getPoint();
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}
	}

	class ButtonAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			if(temp.equals("Line")) {
				option = 1;
				new Canvas();
			}
			else if(temp.equals("Rect")) {
				option = 2;
				new Canvas();
			}
			else if(temp.equals("Circle")) {
				System.out.println("draw a Circle");
			}
			else if(temp.equals("PolyLine")) {
				System.out.println("draw a Polyline");
			}
			else if(temp.equals("Sketch")) {
				System.out.println("Sketch");
			}
			else if(temp.equals("Extra")) {
				System.out.println("not decided");
			}
		}
	}

	class ExitAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
}






