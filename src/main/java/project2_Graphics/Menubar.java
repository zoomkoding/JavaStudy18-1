package project2_Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menubar extends JMenuBar{
	Menubar(){
		JMenu file = new JMenu("File");
		add(file);
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		JMenuItem help = new JMenu("Help");
		add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		exit.addActionListener(new ExitAction());
		
		
	}
	class ExitAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
}
