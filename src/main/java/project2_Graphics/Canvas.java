package project2_Graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;



public class Canvas extends JPanel{
	Point start;
	Point end;
	
	public void drawline() {
		this.addMouseListener(new MyMouseListener());
	}
	
	class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			start = e.getPoint();
			System.out.println("start x: " + start.x + "start y: " +start.y);
		}
		public void mouseReleased(MouseEvent e) {
			end = e.getPoint();
			System.out.println("end x: " + end.x + "end y: " +end.y);

			Graphics g = getGraphics();
			g.drawLine(start.x, start.y, end.x, end.y);
		}
		
	}
	class CanMouseListener extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent e) {
			start = e.getPoint();
			System.out.println("start x: " + start.x + "start y: " +start.y);
		}
	
		public void mouseReleased(MouseEvent e) {
			end = e.getPoint();
			System.out.println("end x: " + end.x + "end y: " +end.y);
			end = e.getPoint();
			Graphics g = getGraphics();
			
		}
		
	}
}

