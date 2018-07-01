package project5_GameConnect6;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame {

	//변수들
	int mx = 0;
	int my = 0;
	int x = 0;
	int y = 0;
	int p = 0;
	int total = 0;
	int [][] p1 = new int [19][19];
	int [][] p2 = new int [19][19];
	Stack<GoStone> stack = new Stack<GoStone>();
	int turn = 1;
	int win = 0;
	String [] filePath = new String [8];
	BufferedImage image;
	
	
	BufferedImage imageA;
	BufferedImage imageB;

	//프레임에 들어가는 요소들
	JFrame frame = new JFrame();
	Board board = new Board();
	JPanel main = new JPanel();
	JPanel user1 = new JPanel();
	JPanel user2 = new JPanel();
	JPanel black = new JPanel();
	JPanel white = new JPanel();
	JPanel chat = new JPanel();
	JButton undoBtn = new JButton("무르기");
	JButton surrenBtn = new JButton("기권");

	JFrame end = new JFrame();
	
	//스타트 패널에 들어가는 요소들
	JPanel startPanel = new JPanel();
	JLabel startMsg = new JLabel("육목게임");
	JLabel connect6 = new JLabel("Connect 6");
	JLabel askMessage = new JLabel("캐릭터를 골라주세요!");
	JButton startBtn = new JButton("Start");
	JButton [] charBtn = new JButton[7];
	
	

	Frame(){	
		filePath[0] = "/Users/jeongjinhyeog/Downloads/psyduck.png";
		filePath[1] = "/Users/jeongjinhyeog/Downloads/charmander.png";
		filePath[2] = "/Users/jeongjinhyeog/Downloads/caterpie.png";
		filePath[3] = "/Users/jeongjinhyeog/Downloads/meowth.png";
		filePath[4] = "/Users/jeongjinhyeog/Downloads/zubat.png";
		filePath[5] = "/Users/jeongjinhyeog/Downloads/jigglypuff.png";
		filePath[6] = "/Users/jeongjinhyeog/Downloads/snorlax.png";
		filePath[7] = "/Users/jeongjinhyeog/Downloads/포켓몬 게임 배경.jpg";

		
		
		
		
		frame.setVisible(true);
		frame.setBounds(0, 0, 1440, 900);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(startPanel);
		startPanel.setLayout(null);
		
		try {
			Graphics g3 = startPanel.getGraphics();
			image = ImageIO.read(new File(filePath[7]));
			g3.drawImage(image, 0, 0, null);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		startPanel.add(askMessage);
		startPanel.add(connect6);
		startPanel.add(startMsg);
		startPanel.add(startBtn);
		
		startMsg.setBounds(450, 150, 600, 150);
		startMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));
		
		
		connect6.setBounds(450, 250, 600, 200);
		connect6.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 100));

		askMessage.setBounds(600, 500, 700, 100);
		askMessage.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 30));

		Image resizedImage;
		
		for(int i = 0; i < 7; i ++) {
			try {
				image = ImageIO.read(new File(filePath[i]));
				resizedImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				charBtn[i] = new JButton(new ImageIcon(resizedImage));
				charBtn[i].setBounds(290 + 120*i, 600, 100, 100);
				charBtn[i].addActionListener(new MyActionListener());
				startPanel.add(charBtn[i]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		startBtn.setBounds(650, 750, 100, 50);
		startBtn.addActionListener(new MyActionListener());
		
		
		main.setBounds(0,0,1440,900);
		main.setBackground(new Color(173,88,28));
		main.setLayout(null);
	

		board.setBounds(40, 30, 800, 800);

		board.setLayout(null);

		user1.setBounds(870, 50, 200, 300);
		user1.setBackground(Color.white);
		user2.setBounds(1200, 50, 200, 300);
		user2.setBackground(Color.white);

		
		

		main.add(board);
		main.add(user1);
		main.add(user2);

		
		
		try {
			BufferedImage image1;
			Image resizeImage1;
			File input = new File(filePath[0]);
			image1 = ImageIO.read(input);
			resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			imageA = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
			Graphics g = imageA.getGraphics();
			g.drawImage(resizeImage1, 0, 0, null);
			input = new File(filePath[1]);
			image1 = ImageIO.read(input);
			resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			imageB = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
			g = imageB.getGraphics();
			g.drawImage(resizeImage1, 0, 0, null);
			
			g.dispose();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			
			if(temp.equals("Start")) {
				frame.remove(startPanel);
				frame.add(main);
				frame.revalidate();
				frame.repaint();
				
				
			}
			
		}
		
	}


	class Board extends JPanel {
		MyMouseListener ml = new MyMouseListener();
		Board(){
			this.addMouseListener(ml);
			this.addMouseMotionListener(ml);
		}

		public void paintComponent(Graphics g){
			int tx, ty, tp;
			this.setBackground(new Color(230,149,21));
			for(int i = 0; i < 19; i ++){
				g.drawLine(40, 40 + i*40, 760, 40 + i*40);
				g.drawLine(40 + i*40, 40, 40 + i*40, 760);
			}
			Graphics2D g2 = (Graphics2D) g;

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					g2.fillOval(157+i*240, 157+j*240, 6, 6);
				}
			}
			for(int i = 0; i < stack.size(); i++) {
				tx = stack.get(i).x;
				ty = stack.get(i).y;
				tp = stack.get(i).p;
				
//				if(tp == 1) g2.setPaint(Color.black);
//				else g2.setPaint(Color.white);
//
//				g2.fillOval(20+tx*40, 20+ty*40, 40, 40);
				
				if(tp == 1) g2.drawImage(imageA, 20+tx*40, 20+ty*40, null);
				else g2.drawImage(imageB, 20+tx*40, 20+ty*40, null);
			}

			if(x>-1 && y>-1 && x<19 && y<19 && p1[x][y] != 1 && p2[x][y] != 1) {
				if(p == 1) g2.drawImage(imageA, 20+x*40, 20+y*40, null);
				else g2.drawImage(imageB, 20+x*40, 20+y*40, null);
			}

		}

	}


	class MyMouseListener implements MouseMotionListener, MouseListener{
		GoStone gostone;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();
			x = (mx-20)/40;
			y = (my-20)/40;

			if(p1[x][y] == 1 || p2[x][y] == 1) System.out.println("not accepted!");  
			else {
				if(p == 1) p1[x][y] = 1;
				else p2[x][y] = 1;

				gostone = new GoStone(x, y, p);
				stack.push(gostone);
				wincheck();
				if(win == 1) System.out.println("p1 win!");
				else if(win == 2) System.out.println("p2 win!");
				total++;

				board.repaint();
			}


		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();
			x = (mx-20)/40;
			y = (my-20)/40;
			if(total%4 == 0 || total%4 == 3) p = 1;
			else if(total%4 == 1 || total%4 == 2) p = 2;
			board.repaint();

		}

		public void wincheck() {

			int t1, t2, t3, t4, t5, t6, t7, t8;
			if(p == 1) {
				for(t1 = 0; p1[x+t1][y] == 1; t1 ++);
				for(t2 = 0; p1[x-t2][y+t2] == 1; t2 ++);
				for(t3 = 0; p1[x][y-t3] == 1; t3 ++);
				for(t4 = 0; p1[x-t4][y-t4] == 1; t4 ++);
				for(t5 = 0; p1[x-t5][y] == 1; t5 ++);
				for(t6 = 0; p1[x-t6][y+t6] == 1; t6 ++);
				for(t7 = 0; p1[x][y+t7] == 1; t7 ++);
				for(t8 = 0; p1[x+t8][y+t8] == 1; t8 ++);
			}
			else {
				for(t1 = 0; p2[x+t1][y] == 1; t1 ++);
				for(t2 = 0; p2[x-t2][y+t2] == 1; t2 ++);
				for(t3 = 0; p2[x][y-t3] == 1; t3 ++);
				for(t4 = 0; p2[x-t4][y-t4] == 1; t4 ++);
				for(t5 = 0; p2[x-t5][y] == 1; t5 ++);
				for(t6 = 0; p2[x-t6][y+t6] == 1; t6 ++);
				for(t7 = 0; p2[x][y+t7] == 1; t7 ++);
				for(t8 = 0; p2[x+t8][y+t8] == 1; t8 ++);
			}

			System.out.printf("%d %d %d\n", t4, t3, t2);
			System.out.printf("%d   %d\n", t5, t1);
			System.out.printf("%d %d %d\n", t6, t7, t8);


			if(t1 + t5 > 6 || t2 + t6 > 6 || t3 + t7 > 6 || t4 + t8 > 6) {
				win = p;
				
				
			}
			

		}

	}



















	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame();
	}
}
