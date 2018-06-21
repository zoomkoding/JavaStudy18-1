package project2_Graphics;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LineDrawer extends Canvas implements MouseListener{
	boolean mouseClicked;
	Canvas canvas = new Canvas();

	public LineDrawer(){
		System.out.println("0");
		addMouseListener(this);
	}

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("1");

		if(!mouseClicked) {
			start = e.getPoint();
			mouseClicked = true;
		}
		System.out.println("start x: " + start.x + "start y: " +start.y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		end = e.getPoint();
		System.out.println("end x: " + end.x + "end y: " +end.y);

		Graphics g = getGraphics();
		g.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}


