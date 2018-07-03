package project5_GameConnect6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import project5_GameConnect6.Client.Send;




public class Frame {

	//디비 변수들
	public static final int DEFAULT = 0;
	public static final int LOGINCHECK = 1;
	public static final int CREATEACCOUNT = 2;
	public static final int REDUNTCHECK = 3;
	public static final int EDIT = 4;
	public static final int DELACCOUNT = 5;
	public static final int VIEW = 6;
	public static final int ADMINEDIT = 7;
	public static final int ADMINDEL = 8;

	GoStone gostone;


	String hey;

	String what;
	String col;
	String username1;
	String username2;
	
	String name;
	String passwd1;
	String passwd2;

	String email;
	String birthdate;
	String namecheck;
	int mode = DEFAULT;
	int check = 0;
	int checkboxnum;
	int adminLog = 0;
	static 	String url = "jdbc:mysql://127.0.0.1/Xproject?serverTimezone=UTC&&useSSL=false&user=root&password=h010638847";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Scanner sc = new Scanner(System.in);


	//변수들
	int pick = 0;
	int p1win = 0;
	int p2win = 0;
	int winchar = -1;
	int losechar = -1;
	int charactera = -1; 
	int characterb = -1;
	int total = 1;
	int win = 0;
	Stack<GoStone> stack = new Stack<GoStone>();
	int [][] p1 = new int [19][19];
	int [][] p2 = new int [19][19];
	int mx = 0;
	int my = 0;
	int x = 0;
	int y = 0;
	int p = 0;

	String [] filePath = new String [10];
	BufferedImage image;

	Image resizedImage;

	BufferedImage imageA;
	BufferedImage imageB;
	BufferedImage imageC;
	BufferedImage imageD;
	BufferedImage imageE;
	BufferedImage imageF;
	BufferedImage imageG;
	BufferedImage imageH;
	BufferedImage imageI;



	//프레임에 들어가는 요소들
	JFrame frame = new JFrame();
	Board board = new Board();
	MainPanel main;
	ResultPanel resultPanel;
	JLabel user1 = new JLabel();
	JLabel user2 = new JLabel();
	JPanel black = new JPanel();
	JPanel white = new JPanel();
	JLabel user1Record = new JLabel("0승 0패");
	JLabel user2Record = new JLabel("0승 0패");
	JPanel chat = new JPanel();
	JButton undoBtn1 = new JButton("무르기");
	JButton surrenBtn1 = new JButton("기권");
	JButton undoBtn2 = new JButton("무르기");
	JButton surrenBtn2 = new JButton("기권");
	JButton repickBtn = new JButton("재선택");
	JButton userModeBtn = new JButton("유저모드");
	JButton funModeBtn = new JButton("그냥한판!");
	StartPanel startPanel = new StartPanel();




	JFrame end = new JFrame();

	//스타트 패널에 들어가는 요소들
	FunPanel funPanel;
	JLabel startMsg = new JLabel("육목게임");
	JLabel connect6 = new JLabel("Connect 6");
	JLabel startMsg1 = new JLabel("육목게임");
	JLabel connect61 = new JLabel("Connect 6");
	JLabel startMsg2 = new JLabel("육목게임");
	JLabel connect62 = new JLabel("Connect 6");
	JLabel askMessage = new JLabel("캐릭터를 골라주세요!");
	JButton startBtn = new JButton("Start");
	JButton [] charBtn = new JButton[7];

	JLabel winLabel = new JLabel();
	JLabel loseLabel = new JLabel();
	JButton regameBtn = new JButton("재대결");
	JButton toStartBtn = new JButton("처음으로");
	JButton toStartBtn1 = new JButton("처음으로");
	JButton toStartBtn2 = new JButton("처음으로");


	LoginPanel loginPanel = new LoginPanel();

	JLabel winMsg = new JLabel("승");
	JLabel loseMsg = new JLabel("패");




	JPanel mainPanel = new JPanel();
	JLabel mainUserLabel = new JLabel("UserName");
	JTextField mainUserField1 = new JTextField("");

	JLabel pwLabel = new JLabel("Password");
	JPasswordField pwField1 = new JPasswordField();

	JButton loginBtn = new JButton("Log in");
	JButton createBtn =	new JButton("Create Account");
	JFrame adminFrame = new JFrame("Administrator");

	JFrame warnFrame = new JFrame();
	JLabel warnLabel = new JLabel();
	JButton warnBtn = new JButton("Got it");

	JFrame askFrame = new JFrame();
	JLabel askLabel = new JLabel();
	JButton askBtn1 = new JButton("Yes");
	JButton askBtn2 = new JButton("No");

	JFrame createFrame = new JFrame("Create Account");
	JPanel createPanel = new JPanel();

	JLabel [] newLabel = new JLabel[6];
	JTextField [] newField = new JTextField[6];

	JLabel [] userLabel = new JLabel[6];
	JTextField [] userField = new JTextField[6];
	JButton [] userBtn = new JButton[3];
	JLabel userModeLabel = new JLabel("UserMode");
	JLabel changeInfoLabel = new JLabel("Change Info");

	JButton [] newBtn = new JButton[3];


	String checkboxlist[] = new String[100];


	JPanel userPanel = new JPanel();
	JLabel changeLabel = new JLabel("Change your Info");

	JButton logoutbtn = new JButton("Log out");
	JButton deletebtn = new JButton("Delete(checked)");

	JFrame editFrame = new JFrame("Change data");
	JLabel editLabel = new JLabel("Change to what?");
	JTextField editText = new JTextField();
	JButton editBtn = new JButton("Okay");


	JScrollPane jScrollPane;
	JLabel adminLabel;
	JPanel adminPanel;
	JTable table;
	JCheckBox checkbox[];




	Dimension dim_Frame = new Dimension(400,600);
	Dimension dim_Label = new Dimension(300,30);
	Dimension dim_Field = new Dimension(300,40);
	Dimension dim_Button = new Dimension(300,70);
	Dimension dim_SButton = new Dimension(150,70);


	
	//네트워크
	String data = "";
	String str2;
	Socket socket = null;
	DataInputStream in = null;
	BufferedReader in2 = null;
	DataOutputStream out = null;
	public String username;
	public String passwd;
	
	
	


	Frame(){	
		frame.add(startPanel);
		startPanel.setLayout(null);
		startPanel.setBounds(0,0, 1440, 900);
		startPanel.add(connect6);
		startPanel.add(startMsg);
		startPanel.add(userModeBtn);
		startPanel.add(funModeBtn);

		filePath[0] = "psyduck.png";
		filePath[1] = "charmander.png";
		filePath[2] = "caterpie.png";
		filePath[3] = "meowth.png";
		filePath[4] = "zubat.png";
		filePath[5] = "jigglypuff.png";
		filePath[6] = "snorlax.png";
		filePath[7] = "포켓몬 게임 배경.jpg";
		filePath[8] = "포켓몬 게임 배경2.jpg";
		filePath[9] = "포켓몬 게임 배경3.jpg";

		try {
			image = ImageIO.read(new File(filePath[7]));
			resizedImage = image.getScaledInstance(1440, 900, Image.SCALE_SMOOTH);
			imageC = new BufferedImage(1440, 900, BufferedImage.TYPE_INT_ARGB);
			Graphics g2 = imageC.getGraphics();
			g2.drawImage(resizedImage, 0, 0, null);

			image = ImageIO.read(new File(filePath[8]));
			resizedImage = image.getScaledInstance(1440, 900, Image.SCALE_SMOOTH);
			imageF = new BufferedImage(1440, 900, BufferedImage.TYPE_INT_ARGB);
			g2 = imageF.getGraphics();
			g2.drawImage(resizedImage, 0, 0, null);

			image = ImageIO.read(new File(filePath[9]));
			resizedImage = image.getScaledInstance(1440, 900, Image.SCALE_SMOOTH);
			imageG = new BufferedImage(1440, 900, BufferedImage.TYPE_INT_ARGB);
			g2 = imageG.getGraphics();
			g2.drawImage(resizedImage, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		funPanel = new FunPanel();

		frame.setVisible(true);
		frame.setBounds(0, 0, 1440, 900);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		startMsg1.setBounds(450, 150, 600, 150);
		startMsg1.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));

		
		connect61.setBounds(450, 250, 600, 200);
		connect61.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 100));
		
		startMsg2.setBounds(450, 150, 600, 150);
		startMsg2.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));

		
		connect62.setBounds(450, 250, 600, 200);
		connect62.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 100));



		funPanel.setLayout(null);
		
		

		startMsg.setBounds(450, 150, 600, 150);
		startMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));

		
		connect6.setBounds(450, 250, 600, 200);
		connect6.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 100));

		askMessage.setBounds(600, 500, 700, 100);
		askMessage.setFont(new Font("Rix프리스타일 B", Font.PLAIN, 30));



		startBtn.setBounds(550, 750, 100, 50);
		startBtn.addActionListener(new MyActionListener());

		repickBtn.setBounds(650, 750, 100, 50);
		repickBtn.addActionListener(new MyActionListener());
		
		toStartBtn2.setBounds(750, 750, 100, 50);
		toStartBtn2.addActionListener(new MyActionListener());
		
		main = new MainPanel();

		main.setBounds(0,0,1440,900);
		main.setBackground(new Color(173,88,28));
		main.setLayout(null);
		board.setLayout(null);
		startPanel.setLayout(null);

		board.setBounds(40, 30, 800, 800);

		user1Record.setBounds(870, 250, 240, 100);
		user1Record.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));
		user2Record.setBounds(1200, 250, 240, 100);
		user2Record.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));
		
		user1.setBounds(870, 50, 200, 200);
		user2.setBounds(1200, 50, 200, 200);

		undoBtn1.setBounds(870, 350, 100, 50);
		undoBtn1.addActionListener(new MyActionListener());
		surrenBtn1.setBounds(970, 350, 100, 50);
		surrenBtn1.addActionListener(new MyActionListener());

		undoBtn2.setBounds(1200, 350, 100, 50);
		undoBtn2.addActionListener(new MyActionListener());
		surrenBtn2.setBounds(1300, 350, 100, 50);
		surrenBtn2.addActionListener(new MyActionListener());

		userModeBtn.setBounds(600, 600, 100, 50);
		userModeBtn.addActionListener(new MyActionListener());
		funModeBtn.setBounds(700, 600, 100, 50);
		funModeBtn.addActionListener(new MyActionListener());
		toStartBtn1.setBounds(680, 700, 100, 50);
		toStartBtn1.addActionListener(new MyActionListener());

		mainUserLabel.setBounds(500, 600, 100, 50);
		mainUserField1.setBounds(600, 600, 200, 50);

		pwLabel.setBounds(500, 650, 100, 50);
		pwField1.setBounds(600, 650, 200, 50);

		loginBtn.setBounds(800, 600, 100, 100);
		createBtn.setBounds(780, 700, 120, 50);

		loginBtn.addActionListener(new MyActionListener());
		createBtn.addActionListener(new MyActionListener());

		loginPanel.setLayout(null);
		loginPanel.setBounds(0, 0, 1440, 900);

		warnFrame.setSize(400, 200);
		warnFrame.setLocation(0, 0);
		warnFrame.setLayout(null);
		warnFrame.setResizable(false);
		warnFrame.setVisible(false);
		
		warnLabel.setLocation(10, 10);
		warnLabel.setSize(380, 100);
		warnLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		warnFrame.add(warnLabel);

		
		main.add(board);
		main.add(user1);
		main.add(user2);
		main.add(undoBtn1);
		main.add(surrenBtn1);
		main.add(undoBtn2);
		main.add(surrenBtn2);
		main.add(user1Record);
		main.add(user2Record);
		
		loginPanel.add(mainUserLabel);
		loginPanel.add(mainUserField1);
		loginPanel.add(toStartBtn1);
		loginPanel.add(pwLabel);
		loginPanel.add(pwField1);
		loginPanel.add(loginBtn);
		loginPanel.add(createBtn);
		loginPanel.add(connect62);
		loginPanel.add(startMsg2);
		
		funPanel.add(connect61);
		funPanel.add(startMsg1);
		funPanel.add(askMessage);
		funPanel.add(startBtn);
		funPanel.add(repickBtn);
		funPanel.add(toStartBtn2);
		for(int i = 0; i < 7; i ++) {
			try {
				image = ImageIO.read(new File(filePath[i]));
				resizedImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				charBtn[i] = new JButton(new ImageIcon(resizedImage));
				charBtn[i].setBounds(290 + 120*i, 600, 100, 100);
				charBtn[i].addActionListener(new MyActionListener());
				charBtn[i].setBorderPainted(false);
				funPanel.add(charBtn[i]);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			socket = new Socket("172.17.208.59", 1024);
			in = new DataInputStream(socket.getInputStream());
			in2 = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
			
			
			
		}catch(Exception e) {}
		
		try {
			while(true) {
				str2 = in.readUTF();
				if(str2.equals("Login Failed")) {
					warnFrame.setVisible(true);
					warnLabel.setText("Password is wrong.");
					System.out.println("Login Failed");
				}
				else if(str2.equals("Login Succeeded")) {
					System.out.println("Login Succededdsadasdas");
					frame.remove(loginPanel);
					frame.add(funPanel);
					frame.revalidate();
					frame.repaint();
					Thread th = new Thread(new Send(out));
					th.start();
				}
				
			}
		}catch(Exception e) {}
	}

	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();

			if(myButton == startBtn) {
				try {
					BufferedImage image1;
					Image resizeImage1;
					File input = new File(filePath[charactera]);
					image1 = ImageIO.read(input);
					resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
					imageA = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
					Graphics g = imageA.getGraphics();
					g.drawImage(resizeImage1, 0, 0, null);
					input = new File(filePath[characterb]);
					image1 = ImageIO.read(input);
					resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
					imageB = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
					g = imageB.getGraphics();
					g.drawImage(resizeImage1, 0, 0, null);
					input = new File(filePath[charactera]);
					image1 = ImageIO.read(input);
					resizeImage1 = image1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					user1.setIcon(new ImageIcon(resizeImage1));
					input = new File(filePath[characterb]);
					image1 = ImageIO.read(input);
					resizeImage1 = image1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					user2.setIcon(new ImageIcon(resizeImage1));


					g.dispose();

				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				p1[9][9] = 1;
				gostone = new GoStone(9, 9, 1);
				stack.push(gostone);
				
				frame.remove(funPanel);
				frame.add(main);
				frame.revalidate();
				frame.repaint();
			}
			else if(myButton == charBtn[0]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[0].setBorderPainted(true);
				if(pick == 1) {
					charactera = 0;
				}
				else if(pick == 2) {
					characterb = 0;
				}
			}
			else if(myButton == charBtn[1]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[1].setBorderPainted(true);
				if(pick == 1) {
					charactera = 1;
				}
				else if(pick == 2) {
					characterb = 1;
				}
			}
			else if(myButton == charBtn[2]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[2].setBorderPainted(true);
				if(pick == 1) {
					charactera = 2;
				}
				else if(pick == 2) {
					characterb = 2;
				}

			}
			else if(myButton == charBtn[3]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[3].setBorderPainted(true);
				if(pick == 1) {
					charactera = 3;
				}
				else if(pick == 2) {
					characterb = 3;
				}

			}
			else if(myButton == charBtn[4]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[4].setBorderPainted(true);
				if(pick == 1) {
					charactera = 4;
				}
				else if(pick == 2) {
					characterb = 4;
				}

			}
			else if(myButton == charBtn[5]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[5].setBorderPainted(true);
				if(pick == 1) {
					charactera = 5;
				}
				else if(pick == 2) {
					characterb = 5;
				}

			}
			else if(myButton == charBtn[6]) {
				pick ++;
				if(pick == 1 || pick == 2)charBtn[6].setBorderPainted(true);
				if(pick == 1) {
					charactera = 6;
				}
				else if(pick == 2) {
					characterb = 6;
				}
			}
			else if(myButton == surrenBtn1 || myButton == surrenBtn2) {

				if (myButton == surrenBtn1) {
					winchar = characterb;
					losechar = charactera;
					p2win ++;

				}
				else {
					winchar = charactera;
					losechar = characterb;
					p1win ++;
				}
				user1Record.setText(p1win + "승 " + p2win + "패");
				user2Record.setText(p2win + "승 " + p1win + "패");

				resultPanel = new ResultPanel();
				frame.remove(main);
				frame.add(resultPanel);
				frame.revalidate();
				frame.repaint();

			}
			else if(myButton == undoBtn1 || myButton == undoBtn2) {
				GoStone gostone1;
				gostone1 = stack.pop();
				if(gostone1.p == 1) {
					p1[gostone1.x][gostone1.y] = 0;

				}
				else if(gostone1.p == 2) {
					p2[gostone1.x][gostone1.y] = 0;
				}
				total --;

				board.repaint();
			}
			
			else if(myButton == toStartBtn) {
				init();
				frame.remove(resultPanel);
				frame.remove(loginPanel);
				frame.add(startPanel);
				frame.revalidate();
				frame.repaint();
			}

			else if(myButton == toStartBtn1) {
				frame.remove(loginPanel);
				frame.add(startPanel);
				frame.revalidate();
				frame.repaint();
			}

			else if(myButton == toStartBtn2) {
				frame.remove(funPanel);
				frame.add(startPanel);
				frame.revalidate();
				frame.repaint();
			}
			
			else if(myButton == regameBtn) {
				initRegame();
				frame.remove(resultPanel);
				frame.add(main);
				frame.revalidate();
				frame.repaint();
			}
			else if(myButton == repickBtn) {
				init();
				frame.repaint();
			}
			else if(myButton == userModeBtn) {
				frame.remove(startPanel);
				frame.add(loginPanel);
				frame.revalidate();
				frame.repaint();

			}
			else if(myButton == funModeBtn) {
				frame.remove(startPanel);
				frame.add(funPanel);
				frame.revalidate();
				frame.repaint();
			}
			else if(temp.equals("Log in")) {
				username = mainUserField1.getText();
				passwd = new String(pwField1.getPassword());
				data = username + " " + passwd;
				mainUserField1.setText("");
				pwField1.setText("");
				try {
					out.writeUTF("LOGINCHECK");
					out.writeUTF(data);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				mode = LOGINCHECK;
//				System.out.println("Login 정보 " + username + " " + passwd);
				
			}
		}
	}
	class MainPanel extends JPanel{
		MainPanel(){
		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageF, 0, 0, null);
		}
	}

	class FunPanel extends JPanel{
		FunPanel(){
		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageC, 0, 0, null);
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

			//바둑판그리기
			for(int i = 0; i < 19; i ++){
				g.drawLine(40, 40 + i*40, 760, 40 + i*40);
				g.drawLine(40 + i*40, 40, 40 + i*40, 760);
			}
			Graphics2D g2 = (Graphics2D) g;
			//바둑판 모퉁이 그리기
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					g2.fillOval(157+i*240, 157+j*240, 6, 6);
				}
			}
			
			//저장된 바둑돌 전부다 그리기
			for(int i = 0; i < stack.size(); i++) {
				tx = stack.get(i).x;
				ty = stack.get(i).y;
				tp = stack.get(i).p;

				if(tp == 1) g2.drawImage(imageA, 20+tx*40, 20+ty*40, null);
				else g2.drawImage(imageB, 20+tx*40, 20+ty*40, null);
			}
			//현재 마우스 움직이는 위치 바둑돌 그리기
			if(x>-1 && y>-1 && x<19 && y<19 && p1[x][y] != 1 && p2[x][y] != 1) {
				if(p == 1) g2.drawImage(imageA, 20+x*40, 20+y*40, null);
				else g2.drawImage(imageB, 20+x*40, 20+y*40, null);
			}
		}
	}

	class ResultPanel extends JPanel{



		ResultPanel(){
			this.setLayout(null);
			this.add(winLabel);
			this.add(loseLabel);
			this.add(winMsg);
			this.add(loseMsg);
			this.add(regameBtn);
			this.add(toStartBtn);
			try {
				BufferedImage image1;
				Image resizeImage1;
				File input;
				input = new File(filePath[winchar]);
				image1 = ImageIO.read(input);
				resizeImage1 = image1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
				winLabel.setBounds(150, 100, 500, 500);
				winLabel.setIcon(new ImageIcon(resizeImage1));
				input = new File(filePath[losechar]);
				image1 = ImageIO.read(input);
				resizeImage1 = image1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
				loseLabel.setBounds(790, 100, 500, 500);
				loseLabel.setIcon(new ImageIcon(resizeImage1));
			}catch(Exception e) {
				System.out.println(e);
			}






			winMsg.setBounds(330, 600, 200, 200);
			winMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));

			loseMsg.setBounds(970, 600, 200, 200);
			loseMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));
			loseMsg.setForeground(Color.red);


			regameBtn.setBounds(670, 500, 100, 50);
			regameBtn.addActionListener(new MyActionListener());

			toStartBtn.setBounds(670, 600, 100, 50);
			toStartBtn.addActionListener(new MyActionListener());



		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageG, 0, 0, null);
		}
	}

	class StartPanel extends JPanel{
		StartPanel(){

		}

		public void paintComponent(Graphics g) {
			g.drawImage(imageC, 0, 0, null);
		}
	}

	class LoginPanel extends JPanel{

		LoginPanel(){

		}

		public void paintComponent(Graphics g) {
			g.drawImage(imageC, 0, 0, null);
		}
	}

	class MyMouseListener implements MouseMotionListener, MouseListener{
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

			int t1=0, t2=0, t3=0, t4=0, t5=0, t6=0, t7=0, t8=0;
			if(p == 1) {
				for(t1 = 0; x + t1 < 19 && p1[x+t1][y] == 1 ; t1 ++);
				for(t2 = 0; x + t2 < 19 && y - t2 >0 && p1[x+t2][y-t2] == 1; t2 ++);
				for(t3 = 0; y - t3 > 0 && p1[x][y-t3] == 1; t3 ++);
				for(t4 = 0; x - t4 > 0 && y -t4 > 0 && p1[x-t4][y-t4] == 1; t4 ++);
				for(t5 = 0; x - t5 > 0 && p1[x-t5][y] == 1; t5 ++);
				for(t6 = 0; x - t6 > 0 && y + t6 < 19 && p1[x-t6][y+t6] == 1; t6 ++);
				for(t7 = 0; y + t7 < 19 && p1[x][y+t7] == 1; t7 ++);
				for(t8 = 0; x + t8 < 19 && y + t8 < 19 && p1[x+t8][y+t8] == 1; t8 ++);
			}
			else {
				for(t1 = 0; x + t1 < 19 && p2[x+t1][y] == 1 ; t1 ++);
				for(t2 = 0; x + t2 < 19 && y - t2 > 0 && p2[x+t2][y-t2] == 1; t2 ++);
				for(t3 = 0; y - t3 > 0 && p2[x][y-t3] == 1; t3 ++);
				for(t4 = 0; x - t4 > 0 && y -t4 > 0 && p2[x-t4][y-t4] == 1; t4 ++);
				for(t5 = 0; x - t5 > 0 && p2[x-t5][y] == 1; t5 ++);
				for(t6 = 0; x - t6 > 0 && y + t6 < 19 && p2[x-t6][y+t6] == 1; t6 ++);
				for(t7 = 0; y + t7 < 19 && p2[x][y+t7] == 1; t7 ++);
				for(t8 = 0; x + t8 < 19 && y + t8 < 19 && p2[x+t8][y+t8] == 1; t8 ++);
			}

			System.out.printf("%d %d %d\n", t4, t3, t2);
			System.out.printf("%d   %d\n", t5, t1);
			System.out.printf("%d %d %d\n", t6, t7, t8);


			if(t1 + t5 > 6 || t2 + t6 > 6 || t3 + t7 > 6 || t4 + t8 > 6) win = p; 

			if(win == 1 || win == 2) {
				if(win == 1) { 
					winchar = charactera;
					losechar = characterb; 
					p1win ++;
				}

				else {
					winchar = characterb;
					losechar = charactera;
					p2win ++;
				}
				user1Record.setText(p1win + "승 " + p2win + "패");
				user2Record.setText(p2win + "승 " + p1win + "패");
				resultPanel = new ResultPanel();
				frame.remove(main);
				frame.add(resultPanel);

				frame.revalidate();
				frame.repaint();
			}
		}

	}

	public void init() {
		pick = 0;
		p1win = 0;
		p2win = 0;
		winchar = -1;
		losechar = -1;
		charactera = -1; 
		characterb = -1;
		total = 1;
		win = 0;
		
		for(int i = 0; i < 19; i ++) {
			for(int j = 0; j < 19; j ++) {
				p1[i][j] = 0;
				p2[i][j] = 0;
			}
		}
		stack.clear();
		stack.push(new GoStone(9,9,1));
		p1[9][9] = 1;

		user1Record.setText(p1win + "승 " + p2win + "패");
		user2Record.setText(p2win + "승 " + p1win + "패");
		for(int i = 0; i < 7; i++)charBtn[i].setBorderPainted(false);
	}

	public void initRegame() {
		winchar = -1;
		losechar = -1;
		total = 1;
		win = 0;
		stack.clear();
		for(int i = 0; i < 19; i ++) {
			for(int j = 0; j < 19; j ++) {
				p1[i][j] = 0;
				p2[i][j] = 0;
			}
		}
		stack.clear();
		stack.push(new GoStone(9,9,1));
		p1[9][9] = 1;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame();
	}
	
	class Send implements Runnable{
	    DataOutputStream out;
	    BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
	    public Send(DataOutputStream out)
	    {
	        this.out = out;
	    }
	    public void run()
	    {
	        while(true)
	        {
	            // 클라이언트으이 서브 쓰레드는 키보드로부터 입력받은 내용 서버로 전송하는 것만 계속해서 반복.
	            try
	            {	
	                String msg = in2.readLine();    //키보드로부터 입력을 받음
	                out.writeUTF(msg);                //서버로 전송
	            }catch(Exception e) {}
	        }
	    }
	}

}