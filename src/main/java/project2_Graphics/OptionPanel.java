package project2_Graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class OptionPanel extends JPanel {
	OptionPanel(){

		Dimension dim = new Dimension(400,30);
		//button
		setLayout(new GridLayout(1, 6));
		setPreferredSize(dim);
		JButton[] button = new JButton[6]; 
		button[0] = new JButton("Line");
		button[1] = new JButton("Rect");
		button[2] = new JButton("Circle");
		button[3] = new JButton("PolyLine");
		button[4] = new JButton("Sketch");
		button[5] = new JButton("Extra");
		for(int i = 0; i<button.length; i++) {
			add(button[i]);
			button[i].addActionListener(new ButtonAction());
		}

	}
	class ButtonAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			if(temp.equals("Line")) {
				System.out.println("");
				//Canvas.drawline();
			}
			else if(temp.equals("Rect")) {
				System.out.println("draw a Rectangle");
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

}
