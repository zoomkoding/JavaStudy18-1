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





public class Frame {

	//디비 변수들
	public static final int DEFAULT = 0;
	public static final int LOGIN = 1;
	public static final int WAITINGADD = 2;
	public static final int FIGHTREQUEST = 3;
	public static final int FIGHTACCEPTED = 4;
	public static final int FIGHTREFUSED = 5;
	public static final int GAMEIN = 6;
	public static final int WINNERDECIDED = 7;
	public static final int UNDO = 8;
	public static final int SURRENDER = 9;
	public static final int REGAME = 10;
	public static final int RETURN = 11;







	GoStone gostone;


	String hey;

	String what;
	String col;
	String username1;
	String username2;

	String name;
	int mode = DEFAULT;

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

	String [] filePath = new String [11];
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
	BufferedImage imageJ; //게임대기배경

	JFrame end = new JFrame();

	//프레임에 들어가는 요소들
	JFrame frame = new JFrame();
	Board board = new Board();
	MainPanel main;
	ResultPanel resultPanel;
	JLabel user1 = new JLabel();
	JLabel user2 = new JLabel();
	JLabel user1Record = new JLabel("0승 0패");
	JLabel user2Record = new JLabel("0승 0패");
	JButton undoBtn1 = new JButton("무르기");
	JButton surrenBtn1 = new JButton("기권");
	JButton undoBtn2 = new JButton("무르기");
	JButton surrenBtn2 = new JButton("기권");
	JButton repickBtn = new JButton("재선택");
	JButton userModeBtn = new JButton("유저모드");
	JButton funModeBtn = new JButton("그냥한판!");


	//userMain
	UserMainPanel userMain;
	JLabel userUser1 = new JLabel();
	JLabel userUser2 = new JLabel();
	JLabel userNickname1 = new JLabel();
	JLabel userNickname2 = new JLabel();

	JLabel userUser1Record = new JLabel("0승 0패");
	JLabel userUser2Record = new JLabel("0승 0패");
	JButton userUndoBtn1 = new JButton("무르기");
	JButton userSurrenBtn1 = new JButton("기권");
	JButton userUndoBtn2 = new JButton("무르기");
	JButton userSurrenBtn2 = new JButton("기권");
	UserBoard userBoard = new UserBoard();



	//스타트 패널에 들어가는 요소들
	FunPanel funPanel;
	StartPanel startPanel = new StartPanel();
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
	JLabel winMsg = new JLabel("승");
	JLabel loseMsg = new JLabel("패");

	JLabel userWinLabel = new JLabel();
	JLabel userLoseLabel = new JLabel();
	JButton userRegameBtn = new JButton("재대결");
	JButton userToStartBtn = new JButton("대기실로");
	JLabel userWinMsg = new JLabel("승");
	JLabel userLoseMsg = new JLabel("패");




	//loginPanel
	LoginPanel loginPanel = new LoginPanel();
	JPanel mainPanel = new JPanel();
	JLabel mainUserLabel = new JLabel("UserName");
	JTextField mainUserField1 = new JTextField("");
	JLabel pwLabel = new JLabel("Password");
	JPasswordField pwField1 = new JPasswordField();
	JButton loginBtn = new JButton("Log in");
	JButton createBtn =	new JButton("Create Account");

	//addUserPanel
	AddUserPanel addUserPanel = new AddUserPanel();
	JLabel [] newLabel = new JLabel[5];
	JTextField [] newField = new JTextField[4];
	JButton [] newCharBtn = new JButton[7];
	JButton [] newBtn = new JButton[4];

	//warnFrame
	JFrame warnFrame = new JFrame();
	JLabel warnLabel = new JLabel();
	JButton warnBtn = new JButton("Got it");

	//waitingRoom
	WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel();
	JPanel myPagePanel = new JPanel();
	JLabel myLabel = new JLabel();
	JLabel myNicknameLabel = new JLabel();
	JLabel myRecord = new JLabel();
	JLabel JiuLabel = new JLabel();

	//userListPanel
	UserListPanel userListPanel = new UserListPanel();
	JButton [] userBtn;
	JLabel [] nickLabel;
	JLabel [] playLabel;
	JLabel [] recordLabel;

	//askFrame
	JFrame askFrame = new JFrame();
	JLabel askLabel = new JLabel("Challange!!!");
	JButton ask1Btn = new JButton("ACCEPT!");
	JButton ask2Btn = new JButton("Refuse");

	//네트워크
	String data = "";
	String str2;
	Socket socket = null;
	DataInputStream in = null;
	BufferedReader in2 = null;
	DataOutputStream out = null;
	public String nickname;
	public String username;
	public String passwd;
	public String myNickname;
	public String myWin;
	public String myLose;
	public String myPocketmon;
	public int myTurn;

	public String enemyUsername;
	public String enemyNickname;
	public String enemyWin;
	public String enemyLose;
	public String enemyPocketmon;


	int usercheck;
	int nickcheck;
	int newpick = -1;

	ArrayList<String> repainter = new ArrayList<String>();

	String winner;
	String loser;
	String winnerImage;
	String loserImage;
	ResultPanel1 resultPanel1;



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
		filePath[10] = "배경.png";


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

			image = ImageIO.read(new File(filePath[10]));
			resizedImage = image.getScaledInstance(750, 640, Image.SCALE_SMOOTH);
			imageJ = new BufferedImage(750, 640, BufferedImage.TYPE_INT_ARGB);
			g2 = imageJ.getGraphics();
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
		//main
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

		//userMain
		userMain = new UserMainPanel();

		userMain.setBounds(0,0,1440,900);
		userMain.setLayout(null);
		userBoard.setLayout(null);
		userBoard.setBounds(40, 30, 800, 800);

		userUser1Record.setBounds(870, 350, 240, 100);
		userUser1Record.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));
		userUser2Record.setBounds(1200, 350, 240, 100);
		userUser2Record.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));

		userUser1.setBounds(870, 50, 200, 200);
		userUser2.setBounds(1200, 50, 200, 200);

		userNickname1.setBounds(870, 250, 330, 100);
		userNickname1.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));
		userNickname1.setHorizontalAlignment(JLabel.CENTER);
		userNickname2.setBounds(1200, 250, 330, 100);
		userNickname2.setHorizontalAlignment(JLabel.CENTER);
		userNickname2.setFont(new Font("Rix프리스타일 B", Font.BOLD, 60));


		userUndoBtn1.setBounds(870, 450, 100, 50);
		userUndoBtn1.addActionListener(new MyActionListener());
		userSurrenBtn1.setBounds(970, 450, 100, 50);
		userSurrenBtn1.addActionListener(new MyActionListener());

		userUndoBtn2.setBounds(1200, 450, 100, 50);
		userUndoBtn2.addActionListener(new MyActionListener());
		userSurrenBtn2.setBounds(1300, 450, 100, 50);
		userSurrenBtn2.addActionListener(new MyActionListener());


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
		warnFrame.setLocation(500, 500);
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

		userMain.add(userBoard);
		userMain.add(userUser1);
		userMain.add(userUser2);
		userMain.add(userUndoBtn1);
		userMain.add(userSurrenBtn1);
		userMain.add(userUndoBtn2);
		userMain.add(userSurrenBtn2);
		userMain.add(userNickname1);
		userMain.add(userNickname2);
		userMain.add(userUser1Record);
		userMain.add(userUser2Record);

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




		addUserPanel.setLayout(null);
		addUserPanel.setSize(1440, 900);
		newLabel[0] = new JLabel("UserName");
		newField[0] = new JTextField();
		newLabel[1] = new JLabel("Password(should be longer than 7)");
		newField[1] = new JTextField();
		newLabel[2] = new JLabel("Rewrite Password");
		newField[2] = new JTextField();
		newLabel[3] = new JLabel("Nickname");
		newField[3] = new JTextField();
		newLabel[4] = new JLabel("Pick a Pocketmon");
		newBtn[0] = new JButton("Save");
		newBtn[1] = new JButton("Cancel");
		newBtn[2] = new JButton("중복확인");
		newBtn[3] = new JButton("중복확인");

		for(int i =  0; i< newField.length ; i++) {
			newLabel[i].setBounds(500, 40 + 120*i, 400, 60);
			addUserPanel.add(newLabel[i]);

			newField[i].setBounds(500, 120 + 120*i, 400, 60);
			addUserPanel.add(newField[i]);
		}
		for(int i = 0; i < newBtn.length -1 ; i++) {
			newBtn[i].setBounds(500+i*200, 800, 200, 60);
			newBtn[i].addActionListener(new MyActionListener());
			addUserPanel.add(newBtn[i]);
		}
		newLabel[4].setBounds(500, 40 + 520, 400, 60);
		addUserPanel.add(newLabel[4]);
		newBtn[2].setBounds(900, 120, 200, 60);
		newBtn[2].addActionListener(new MyActionListener());
		newBtn[3].setBounds(900, 480, 200, 60);
		newBtn[3].addActionListener(new MyActionListener());
		addUserPanel.add(newBtn[2]);
		addUserPanel.add(newBtn[3]);
		for(int i = 0; i < 7; i ++) {
			try {
				image = ImageIO.read(new File(filePath[i]));
				resizedImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
				newCharBtn[i] = new JButton(new ImageIcon(resizedImage));
				newCharBtn[i].setBounds(290 + 120*i, 650, 100, 100);
				newCharBtn[i].addActionListener(new MyActionListener());
				newCharBtn[i].setBorderPainted(false);
				addUserPanel.add(newCharBtn[i]);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		//waitingRoomPanel
		waitingRoomPanel.add(myPagePanel);
		waitingRoomPanel.add(JiuLabel);
		waitingRoomPanel.add(myNicknameLabel);
		waitingRoomPanel.add(myRecord);
		waitingRoomPanel.add(userListPanel);

		//myPagePanel
		myPagePanel.setBounds(1000, 80, 400, 400);
		myPagePanel.setBackground(new Color(255, 235, 204));
		myPagePanel.setLayout(null);

		myLabel.setBounds(75, 75, 250, 250);

		myNicknameLabel.setBounds(1000,480, 400, 150);
		myNicknameLabel.setFont(new Font("Rix프리스타일 B", Font.BOLD, 100));

		myRecord.setBounds(1000, 630, 400, 100);
		myRecord.setFont(new Font("Rix프리스타일 B", Font.BOLD, 80));

		myPagePanel.add(myLabel);

		askFrame.setVisible(false);
		askFrame.setBounds(500, 500, 600, 300);
		askFrame.setResizable(false);
		askFrame.setLayout(null);

		askLabel.setBounds(50, 50, 550, 100);
		askLabel.setHorizontalAlignment(JLabel.CENTER);

		ask1Btn.setBounds(200, 200, 100, 50);
		ask1Btn.addActionListener(new MyActionListener());
		ask2Btn.setBounds(300, 200, 100, 50);
		ask2Btn.addActionListener(new MyActionListener());

		askFrame.add(askLabel);
		askFrame.add(ask1Btn);
		askFrame.add(ask2Btn);

		try {
			socket = new Socket("127.0.0.1", 1024);
			in = new DataInputStream(socket.getInputStream());
			in2 = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());



		}catch(Exception e) {}

		try {
			while(true) {
				str2 = in.readUTF();
				System.out.println("UserData: " + str2 + "Mode: " + mode);
				if(str2.equals("Login Failed")) {
					warnFrame.setVisible(true);
					warnLabel.setText("Login Failed!");
				}
				else if(str2.equals("USERNAME GOOD")) {
					warnLabel.setText("Good! You can use this username!");
					warnFrame.setVisible(true);
					usercheck = 1;
				}
				else if(str2.equals("USERNAME BAD")) {
					warnLabel.setText("Username exists! Try different username!");
					warnFrame.setVisible(true);
					usercheck = 0;

				}

				else if(str2.equals("NICKNAME GOOD")) {
					warnLabel.setText("Good! You can use this nickname!");
					warnFrame.setVisible(true);
					nickcheck = 1;
				}
				else if(str2.equals("NICKNAME BAD")) {
					warnLabel.setText("nickname exists! Try different nickname!");
					warnFrame.setVisible(true);
					nickcheck = 0;
				}
				else if(str2.equals("CREATEACCOUNT GOOD")) {
					warnLabel.setText("Account is successfully created!");
					warnFrame.setVisible(true);
					frame.remove(addUserPanel);
					frame.add(loginPanel);
					frame.revalidate();
					frame.repaint();
				}
				else if(str2.equals("WAITINGADD"))mode = WAITINGADD;
				else if(str2.equals("FIGHTREQUEST"))mode = FIGHTREQUEST;
				else if(str2.equals("FIGHTACCEPTED")) mode = FIGHTACCEPTED;
				else if(str2.equals("LOGIN"))mode = LOGIN;
				else if(str2.equals("WINNERDECIDED")) mode = WINNERDECIDED;
				else if(str2.equals("REPAINT"))repainting();
				else if(str2.startsWith("addRepainter"))repainter.add(str2);
				else if(str2.equals("DEFAULT")) mode = DEFAULT;

				else if(str2.equals("RETURN")) {
					mode = RETURN;
					frame.remove(resultPanel1);
					frame.add(waitingRoomPanel);
					frame.revalidate();
					frame.repaint();
				}

				else if(str2.equals("UNDO")) {
					GoStone gostone1;
					gostone1 = stack.pop();
					if(gostone1.p == 1) {
						p1[gostone1.x][gostone1.y] = 0;

					}
					else if(gostone1.p == 2) {
						p2[gostone1.x][gostone1.y] = 0;
					}
					total --;

					userBoard.repaint();
				}

				else if(mode == WINNERDECIDED) {
					userInit();
					String data[] = str2.split(",");
					winner = data[0];
					loser = data[1];
					if(winner.equals(username)) {
						winnerImage = myPocketmon;
						loserImage = enemyPocketmon;

					}
					else {
						loserImage = myPocketmon;
						winnerImage = enemyPocketmon;
					}


					resultPanel1 = new ResultPanel1();
					frame.remove(userMain);
					frame.add(resultPanel1);
					frame.revalidate();
					frame.repaint();
					mode = DEFAULT;
				}

				else if(mode == GAMEIN) {
					String data[] = str2.split(",");
					System.out.println(enemyNickname);
					if(enemyNickname.equals(data[0])) {
						int px = Integer.parseInt(data[1]);
						int py = Integer.parseInt(data[2]);
						total++;
						p2[px][py] = 1;
						stack.push(new GoStone(px, py, 2));
						userBoard.repaint();
					}
				}




				else if(mode == LOGIN) {
					System.out.println("파싱시작");
					String loginData[] = str2.split(",");
					myNickname = loginData[0];
					myWin = loginData[1];
					myLose = loginData[2];
					myPocketmon = loginData[3];

					myNicknameLabel.setText(myNickname);
					myNicknameLabel.setHorizontalAlignment(JLabel.CENTER);
					myRecord.setText(myWin + " / " + myLose);
					myRecord.setHorizontalAlignment(JLabel.CENTER);
					BufferedImage image1;
					Image resizeImage1;
					File input = new File(filePath[Integer.parseInt(myPocketmon)]);
					image1 = ImageIO.read(input);
					resizeImage1 = image1.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
					myLabel.setIcon(new ImageIcon(resizeImage1));
					frame.remove(loginPanel);
					frame.add(waitingRoomPanel);
					frame.revalidate();
					frame.repaint();
					Thread th = new Thread(new Send(out));
					th.start();
					mode = DEFAULT;
				}


				else if(mode == WAITINGADD) {

					if(!str2.startsWith(username))repainter.add(str2);

				}

				else if(mode == FIGHTREQUEST) {
					askFrame.setVisible(true);
					enemyUsername = str2;
					mode = DEFAULT;
				}
				else if(mode == FIGHTACCEPTED) {
					String data[] = str2.split(",");
					if(data[0].equals(username)) {
						myWin = data[2];
						myLose = data[3];
					}
					else {
						enemyUsername = data[0];
						enemyNickname = data[1];
						enemyWin = data[2];
						enemyLose = data[3];
						enemyPocketmon = data[4];
						myTurn = Integer.parseInt(data[5]);
						if(myTurn == 1) {
							gostone = new GoStone(9, 9, 1);
							p1[9][9] = 1;

						}
						else {
							gostone = new GoStone(9, 9, 2);
							p2[9][9] = 1;
						}
						stack.push(gostone);

						BufferedImage image1;
						Image resizeImage1;

						File input = new File(filePath[Integer.parseInt(myPocketmon)]);
						image1 = ImageIO.read(input);
						resizeImage1 = image1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
						userUser1.setIcon(new ImageIcon(resizeImage1));
						input = new File(filePath[Integer.parseInt(enemyPocketmon)]);
						image1 = ImageIO.read(input);
						resizeImage1 = image1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
						userUser2.setIcon(new ImageIcon(resizeImage1));

						input = new File(filePath[Integer.parseInt(myPocketmon)]);

						image1 = ImageIO.read(input);
						resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
						imageA = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
						Graphics g = imageA.getGraphics();
						g.drawImage(resizeImage1, 0, 0, null);
						input = new File(filePath[Integer.parseInt(enemyPocketmon)]);
						image1 = ImageIO.read(input);
						resizeImage1 = image1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
						imageB = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
						g = imageB.getGraphics();
						g.drawImage(resizeImage1, 0, 0, null);
						g.dispose();

						userUser1Record.setText(myWin + " / " + myLose);
						userUser2Record.setText(enemyWin + " / " + enemyLose);

						userNickname1.setText(myNickname);
						userNickname2.setText(enemyNickname);

						frame.remove(waitingRoomPanel);
						frame.add(userMain);
						frame.revalidate();
						frame.repaint();

						out.writeUTF("GAMEIN");
						mode = GAMEIN;
					}

				}


			}
		}catch(Exception e) {}
	}

	void repainting() {
		userListPanel.removeAll();
		System.out.println("들어왔다~");
		String tempnick;
		String tempname;
		String tempplay;
		String tempw;
		String templ;
		String tempp;

		userBtn = new JButton [repainter.size()];
		nickLabel = new JLabel [repainter.size()];
		playLabel = new JLabel [repainter.size()];
		recordLabel = new JLabel [repainter.size()];
		System.out.println(repainter.size());
		//자기 빼주기위해서
		int j = 0;
		for(int i = 0; i < repainter.size() ; i++) {
			String data[] = repainter.get(i).split(",");
			if(data[1].equals(username)) {
				myRecord.setText(data[3] + " / " + data[4]);
				j++;
			}
			else {
				tempname = data[1];
				tempnick = data[2];
				tempw = data[3];
				templ = data[4];
				tempp = data[5];
				tempplay = data[6];
				try {
					image = ImageIO.read(new File(filePath[Integer.parseInt(tempp)]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				userBtn[i] = new JButton(tempname);
				userBtn[i].setIcon(new ImageIcon(resizedImage));
				userBtn[i].setBounds(25+((i-j)%3)*250, 20+ (i-j)/3*320, 200 , 150);
				userBtn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JButton myButton = (JButton)e.getSource();
						String temp = myButton.getText();
						try {
							out.writeUTF("FIGHTREQUEST");
							out.writeUTF(username + "," + temp);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}


				});
				nickLabel[i] = new JLabel(tempnick);
				nickLabel[i].setBounds(25+((i-j)%3)*250, 170+ (i-j)/3*320, 200, 50);
				nickLabel[i].setFont(new Font("Rix프리스타일 B", Font.BOLD, 30));

				recordLabel[i] = new JLabel(tempw + " / " + templ);
				recordLabel[i].setBounds(25+((i-j)%3)*250, 220+ (i-j)/3*320, 200, 50);
				recordLabel[i].setFont(new Font("Rix프리스타일 B", Font.BOLD, 30));

				playLabel[i] = new JLabel(tempplay);
				playLabel[i].setBounds(25+((i-j)%3)*250, 270+ (i-j)/3*320, 200, 50);
				playLabel[i].setFont(new Font("Rix프리스타일 B", Font.BOLD, 30));


				userListPanel.add(userBtn[i]);
				userListPanel.add(nickLabel[i]);
				userListPanel.add(recordLabel[i]);
				userListPanel.add(playLabel[i]);


			}

		}
		waitingRoomPanel.revalidate();
		waitingRoomPanel.repaint();
		repainter.clear();


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
			else if(myButton == loginBtn) {
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
			}
			else if(myButton == createBtn) {
				frame.remove(loginPanel);
				frame.add(addUserPanel);
				frame.revalidate();
				frame.repaint();
			}
			else if(myButton == newBtn[0]) {	//세이브			
				int admission = 1;
				String warnMessage = "";

				if(usercheck != 1 || nickcheck != 1) {
					warnMessage = "Check your username and nicknamefor redunduncy.";
					admission = 0;
				}
				else if(!newField[1].getText().equals(newField[2].getText()) ) {
					warnMessage = "Rewrite Password.";
					admission = 0;
				}
				else if(newField[1].getText().length() < 8) {
					warnMessage = "password too short.";
					admission = 0;

				}
				else if(newpick == -1) {
					warnMessage = "pick a pocketmon.";
					admission = 0;
				}

				else {
					for(int i = 0; i < newField.length; i++) {
						if(newField[i].getText().length()==0) {
							warnMessage = warnMessage + " " + newField[i].getText(); 
							admission = 0;

						}
					}
					if(admission == 0) warnMessage = "All data should be filled.";
				}
				if(admission == 0) {
					warnLabel.setText(warnMessage);
					warnFrame.setVisible(true);
				}
				else {
					username = newField[0].getText();
					passwd = newField[1].getText();
					nickname = newField[3].getText();
					data = username + "," + passwd + "," + nickname + "," + newpick;
					try {
						out.writeUTF("CREATEACCOUNT");
						out.writeUTF(data);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
			else if(myButton == newBtn[1]) {
				frame.remove(addUserPanel);
				frame.add(loginPanel);
				frame.revalidate();
				frame.repaint();
			}


			else if(myButton == newBtn[2]) {	 //유저네임 체크			

				username = newField[0].getText();
				try {
					out.writeUTF("USERNAMECHECK");
					out.writeUTF(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}

			else if(myButton == newBtn[3]) {	//닉네임 체크			
				nickname = newField[3].getText();
				try {
					out.writeUTF("NICKNAMECHECK");
					out.writeUTF(nickname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			else if(myButton == newCharBtn[0]) {
				if(newpick == -1) {
					newpick = 0;
					newCharBtn[0].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[0].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[1]) {
				if(newpick == -1) {
					newpick = 1;
					newCharBtn[1].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[1].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[2]) {
				if(newpick == -1) {
					newpick = 2;
					newCharBtn[2].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[2].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[3]) {
				if(newpick == -1) {
					newpick = 3;
					newCharBtn[3].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[3].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[4]) {
				if(newpick == -1) {
					newpick = 4;
					newCharBtn[4].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[4].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[5]) {
				if(newpick == -1) {
					newpick = 5;
					newCharBtn[5].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[5].setBorderPainted(false);
				}
			}
			else if(myButton == newCharBtn[6]) {
				if(newpick == -1) {
					newpick = 6;
					newCharBtn[6].setBorderPainted(true);
				}
				else {
					newpick = -1;
					newCharBtn[6].setBorderPainted(false);
				}
			}
			else if(myButton == ask1Btn) {
				try {
					out.writeUTF("FIGHTACCEPTED");
					out.writeUTF(username + "," + enemyUsername);
					askFrame.setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else if(myButton == ask2Btn) {
				try {
					out.writeUTF("FIGHTREFUSED");
					out.writeUTF(username + "," + enemyUsername);
					askFrame.setVisible(false);
					mode = DEFAULT;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			else if(myButton == userUndoBtn1) {
				try {
					out.writeUTF("UNDO");
					out.writeUTF(username + "," + enemyUsername);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			else if(myButton == userSurrenBtn1) {
				try {
					out.writeUTF("WINNERDECIDED");
					out.writeUTF(enemyUsername + "," + username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}

			else if(myButton == userToStartBtn) {
				try {
					out.writeUTF("RETURN");
					out.writeUTF(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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

	class UserMainPanel extends JPanel{
		UserMainPanel(){
		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageF, 0, 0, null);
		}
	}

	class UserBoard extends JPanel {
		MyMouseListener1 ml = new MyMouseListener1();

		UserBoard(){
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
				g2.drawImage(imageA, 20+x*40, 20+y*40, null);
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

	class MyMouseListener1 implements MouseMotionListener, MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();
			x = (mx-20)/40;
			y = (my-20)/40;

			if(p1[x][y] == 1 || p2[x][y] == 1) System.out.println("not accepted!");  
			else {
				if((myTurn == 1 && (total % 4 == 3 || total %4 == 0)) || myTurn == 2 && (total % 4 == 1 || total %4 == 2)) {
					p1[x][y] = 1;
					gostone = new GoStone(x, y, 1);
					stack.push(gostone);
					wincheck();
					total++;
					try {
						out.writeUTF(myNickname + "," + x + "," + y);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					userBoard.repaint();
				}
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
			p = 1;
			userBoard.repaint();

		}

		public void wincheck() {

			int t1=0, t2=0, t3=0, t4=0, t5=0, t6=0, t7=0, t8=0;
			for(t1 = 0; x + t1 < 19 && p1[x+t1][y] == 1 ; t1 ++);
			for(t2 = 0; x + t2 < 19 && y - t2 >0 && p1[x+t2][y-t2] == 1; t2 ++);
			for(t3 = 0; y - t3 > 0 && p1[x][y-t3] == 1; t3 ++);
			for(t4 = 0; x - t4 > 0 && y -t4 > 0 && p1[x-t4][y-t4] == 1; t4 ++);
			for(t5 = 0; x - t5 > 0 && p1[x-t5][y] == 1; t5 ++);
			for(t6 = 0; x - t6 > 0 && y + t6 < 19 && p1[x-t6][y+t6] == 1; t6 ++);
			for(t7 = 0; y + t7 < 19 && p1[x][y+t7] == 1; t7 ++);
			for(t8 = 0; x + t8 < 19 && y + t8 < 19 && p1[x+t8][y+t8] == 1; t8 ++);


			System.out.printf("%d %d %d\n", t4, t3, t2);
			System.out.printf("%d   %d\n", t5, t1);
			System.out.printf("%d %d %d\n", t6, t7, t8);


			if(t1 + t5 > 6 || t2 + t6 > 6 || t3 + t7 > 6 || t4 + t8 > 6) {
				try {
					out.writeUTF("WINNERDECIDED");
					out.writeUTF(username + "," + enemyUsername);


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

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

	public void userInit() {
		total = 1;
		for(int i = 0; i < 19; i ++) {
			for(int j = 0; j < 19; j ++) {
				p1[i][j] = 0;
				p2[i][j] = 0;
			}
		}
		stack.clear();
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

	class ResultPanel1 extends JPanel{



		ResultPanel1(){
			this.setLayout(null);
			this.add(userWinLabel);
			this.add(userLoseLabel);
			this.add(userWinMsg);
			this.add(userLoseMsg);
			this.add(userRegameBtn);
			this.add(userToStartBtn);
			try {
				BufferedImage image1;
				Image resizeImage1;
				File input;
				input = new File(filePath[Integer.parseInt(winnerImage)]);
				image1 = ImageIO.read(input);
				resizeImage1 = image1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
				userWinLabel.setBounds(150, 100, 500, 500);
				userWinLabel.setIcon(new ImageIcon(resizeImage1));
				input = new File(filePath[Integer.parseInt(loserImage)]);
				image1 = ImageIO.read(input);
				resizeImage1 = image1.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
				userLoseLabel.setBounds(790, 100, 500, 500);
				userLoseLabel.setIcon(new ImageIcon(resizeImage1));
			}catch(Exception e) {
				System.out.println(e);
			}






			userWinMsg.setBounds(330, 600, 200, 200);
			userWinMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));

			userLoseMsg.setBounds(970, 600, 200, 200);
			userLoseMsg.setFont(new Font("Rix프리스타일 B", Font.BOLD, 150));
			userLoseMsg.setForeground(Color.red);


			userRegameBtn.setBounds(670, 500, 100, 50);
			userRegameBtn.addActionListener(new MyActionListener());

			userToStartBtn.setBounds(670, 600, 100, 50);
			userToStartBtn.addActionListener(new MyActionListener());



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

	class AddUserPanel extends JPanel{
		AddUserPanel(){
		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageC, 0, 0, null);
		}
	}

	class WaitingRoomPanel extends JPanel{


		WaitingRoomPanel(){
			this.setBounds(0, 0, 1440, 900);
			this.setLayout(null);


		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageC, 0, 0, null);
		}
	}

	class UserListPanel extends JPanel{
		UserListPanel(){
			this.setLayout(null);
			this.setBounds(250, 80, 750, 640);
		}
		public void paintComponent(Graphics g) {
			g.drawImage(imageJ, 0, 0, null);
		}
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame();
	}

}
