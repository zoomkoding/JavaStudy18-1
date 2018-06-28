package project3_DataBase;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

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


public class Login {

	public static final int DEFAULT = 0;
	public static final int LOGINCHECK = 1;
	public static final int CREATEACCOUNT = 2;
	public static final int REDUNTCHECK = 3;
	public static final int EDIT = 4;
	public static final int DELACCOUNT = 5;
	public static final int VIEW = 6;
	public static final int ADMINEDIT = 7;
	public static final int ADMINDEL = 8;

	ArrayList<String> data = new ArrayList<String>();

	String hey;

	String what;
	String col;
	String username;
	String name;
	String passwd;
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

	JFrame frame = new JFrame("Login Program");
	JPanel mainPanel = new JPanel();
	JLabel mainUserLabel = new JLabel("UserName");
	JTextField mainUserField = new JTextField("");
	JLabel pwLabel = new JLabel("Password");
	JPasswordField pwField = new JPasswordField();
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


	public static void main(String[] args) {
		new Login();
	}

	public Login() {

		frame.setVisible(true);
		frame.setSize(dim_Frame);
		frame.setLocation(0, 0);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel.setLayout(null);
		mainUserLabel.setLocation(50, 10);
		mainUserLabel.setSize(dim_Label);
		mainUserField.setLocation(50, 40);
		mainUserField.setSize(dim_Field);
		pwLabel.setSize(dim_Label);
		pwLabel.setLocation(50, 110);
		pwField.setSize(dim_Field);
		pwField.setLocation(50, 140);
		loginBtn.setSize(dim_Button);
		loginBtn.setLocation(50, 210);
		createBtn.setSize(dim_Button);
		createBtn.setLocation(50, 280);

		loginBtn.addActionListener(new ButtonAction());
		createBtn.addActionListener(new ButtonAction());
		
		mainPanel.setFont(new Font("Arial", Font.BOLD, 20));

		frame.add(mainPanel);
		
		mainPanel.add(mainUserLabel);
		mainPanel.add(mainUserField);
		mainPanel.add(pwLabel);
		mainPanel.add(pwField);
		mainPanel.add(loginBtn);
		mainPanel.add(createBtn);

		newLabel[0] = new JLabel("UserName");
		newField[0] = new JTextField();
		newLabel[1] = new JLabel("Password(should be longer than 7)");
		newField[1] = new JTextField();
		newLabel[2] = new JLabel("Rewrite Password");
		newField[2] = new JTextField();
		newLabel[3] = new JLabel("Name");
		newField[3] = new JTextField();
		newLabel[4] = new JLabel("Birth Date");
		newField[4] = new JTextField();
		newLabel[5] = new JLabel("E-mail");
		newField[5] = new JTextField();
		newBtn[0] = new JButton("Save");
		newBtn[1] = new JButton("Cancel");
		newBtn[2] = new JButton("중복확인");

		userLabel[0] = new JLabel("Current Password");
		userField[0] = new JTextField();
		userLabel[1] = new JLabel("New Password");
		userField[1] = new JTextField();
		userLabel[2] = new JLabel("Re-Password");
		userField[2] = new JTextField();
		userLabel[3] = new JLabel("Name");
		userField[3] = new JTextField();
		userLabel[4] = new JLabel("Birth Date");
		userField[4] = new JTextField();
		userLabel[5] = new JLabel("E-mail");
		userField[5] = new JTextField();
		userBtn[0] = new JButton("Edit");
		userBtn[1] = new JButton("Cancel");
		userBtn[2] = new JButton("Delete Account");

		warnFrame.setSize(400, 200);
		warnFrame.setLocation(0, 0);
		warnFrame.setLayout(null);
		warnFrame.setResizable(false);
		warnFrame.setVisible(false);
		
		warnLabel.setLocation(10, 10);
		warnLabel.setSize(380, 100);
		warnLabel.setFont(new Font("Arial", Font.PLAIN, 15));

		warnBtn.setSize(100, 50);
		warnBtn.setLocation(150, 100);
		warnBtn.addActionListener(new ButtonAction());
		
		
		warnFrame.add(warnLabel);
		warnFrame.add(warnBtn);

		askFrame.setSize(400, 300);
		askFrame.setLocation(600, 500);
		askFrame.setResizable(false);
		askFrame.setVisible(false);
		askFrame.setLayout(null);
		askFrame.add(askLabel);
		askFrame.add(askBtn1);
		askFrame.add(askBtn2);

		
		editFrame.setSize(200, 300);
		editFrame.setLocation(200, 500);
		editFrame.setResizable(false);
		editFrame.setVisible(false);
		editFrame.setLayout(null);
		
		
		editLabel.setSize(150, 50);
		editLabel.setLocation(25, 0);
		
		editText.setSize(150, 50);
		editText.setLocation(25, 50);
		
		editBtn.setSize(100, 50);
		editBtn.setLocation(50, 100);
		editBtn.addActionListener(new ButtonAction());
		
		editFrame.add(editLabel);
		editFrame.add(editText);
		editFrame.add(editBtn);
		
		
		askLabel.setText("Is it okay to delete?");
		askLabel.setLocation(50,10);
		askLabel.setSize(300, 50);
		askBtn1.addActionListener(new ButtonAction());
		askBtn2.addActionListener(new ButtonAction());
		askBtn1.setLocation(50, 60);
		askBtn1.setSize(dim_SButton);
		askBtn2.setLocation(200, 60);
		askBtn2.setSize(dim_SButton);

	}

	class ButtonAction implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();

			warnFrame.setVisible(false);


			if(temp.equals("Log in")) {
				username = mainUserField.getText();
				passwd = new String(pwField.getPassword());
				mainUserField.setText("");
				pwField.setText("");

				mode = LOGINCHECK;
				System.out.println("Login 정보 " + username + " " + passwd);
				command();
			}
			else if(temp.equals("Create Account")) {
				mode = CREATEACCOUNT;
				createPanel.setLayout(null);
				frame.remove(mainPanel);
				frame.add(createPanel);
				frame.revalidate();
				frame.repaint();

				int x = 50, y = 10;

				for(int i =  0; i< newLabel.length ; i++) {
					newLabel[i].setLocation(x, y);
					newLabel[i].setSize(dim_Label);
					createPanel.add(newLabel[i]);
					y += 30;
					newField[i].setLocation(x, y);
					newField[i].setSize(dim_Field);
					createPanel.add(newField[i]);
					y += 40;
					if(i==0) y += 50; 

				}

				for(int i = 0; i < newBtn.length -1 ; i++) {
					newBtn[i].setLocation(x, y);
					newBtn[i].setSize(dim_SButton);
					newBtn[i].addActionListener(this);
					createPanel.add(newBtn[i]);
					x += 150;
				}
				newBtn[2].setLocation(230, 80);
				newBtn[2].setSize(120, 50);
				newBtn[2].addActionListener(this);
				createPanel.add(newBtn[2]);

			}

			else if(temp.equals("Save")) {				
				int admission = 1;
				String warnMessage = "";

				if(check == 0) {
					warnMessage = "Check your username for redunduncy.";
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
					System.out.println("new account added");
					username = newField[0].getText();
					passwd = newField[1].getText();
					name = newField[3].getText();
					birthdate = newField[4].getText();
					email = newField[5].getText();

					for(int i = 0; i < newField.length; i++) {
						newField[i].setText("");
					}
					command();
					warnLabel.setText("Account created! Login with your Account!");
					warnFrame.setVisible(true);
					frame.remove(createPanel);
					frame.add(mainPanel);
					frame.revalidate();
					frame.repaint();
					mode = DEFAULT;
					check = 0;
				}
			}

			else if(temp.equals("Cancel")) {
				frame.remove(createPanel);
				frame.remove(userPanel);
				frame.add(mainPanel);
				frame.revalidate();
				frame.repaint();
				for(int i = 0; i < newField.length; i++) {
					newField[i].setText("");
				}
				for(int i = 0; i < userField.length; i++) {
					userField[i].setText("");
				}
				mode = DEFAULT;
			}
			else if(temp.equals("중복확인")) {
				System.out.println("hey");
				mode = REDUNTCHECK;
				namecheck = newField[0].getText();
				command();
				if(check == 0) {
					warnLabel.setText("Username exists! Try different username!");
					warnFrame.setVisible(true);
				}
				else {
					warnLabel.setText("Good! You can use this username!");
					warnFrame.setVisible(true);
				}
				mode = CREATEACCOUNT;
			}

			else if(temp.equals("Edit")) {
				int admission = 1;
				String warnMessage = "";

				if(!userField[0].getText().equals(passwd)) {
					warnMessage = "Current Password is wrong.";
					admission = 0;
				}
				else if(!userField[1].getText().equals(userField[2].getText()) ) {
					warnMessage = "Correctly rewrite Password.";
					admission = 0;
				}
				else if(userField[1].getText().length() < 8) {
					warnMessage = "password too short.";
					admission = 0;

				}

				else {
					for(int i = 1; i < userField.length; i++) {
						if(userField[i].getText().length()==0) {
							warnMessage = warnMessage + " " + userField[i].getText(); 
							admission = 0;

						}
					}
					if(admission == 0) warnMessage = warnMessage + "All should be filled.";
				}

				if(admission == 0) {
					warnLabel.setText(warnMessage);
					warnFrame.setVisible(true);
				}
				else {
					System.out.println("new account added");
					passwd = userField[1].getText();
					name = userField[3].getText();
					birthdate = userField[4].getText();
					email = userField[5].getText();
					
					mode = EDIT;
					command();
					warnLabel.setText("Account edited! Login with your Account!");
					warnFrame.setVisible(true);
					for(int i = 0; i < userField.length; i++) {
						userField[i].setText("");
					}
					frame.remove(createPanel);
					frame.remove(userPanel);
					frame.add(mainPanel);
					frame.revalidate();
					frame.repaint();
					mode = DEFAULT;
					check = 0;
				}
			}
			else if(temp.equals("Delete Account")){
				mode = DELACCOUNT;
				askFrame.setVisible(true);
				

			}
			else if(temp.equals("Yes")) {
				
				askFrame.setVisible(false);
				frame.remove(userPanel);
				frame.remove(createPanel);
				frame.add(mainPanel);
				frame.revalidate();
				frame.repaint();

				command();
			}
			else if(temp.equals("No")) {
				askFrame.setVisible(false);
			}
			
			else if(temp.equals("Delete(checked)")){
				mode = ADMINDEL;
				askFrame.setVisible(true);
				
			}
			else if(temp.equals("Log out")) {
				adminFrame.setVisible(false);
				frame.setVisible(true);
				frame.remove(mainPanel);
				frame.remove(createPanel);
				frame.remove(userPanel);
				frame.add(mainPanel);
				
				frame.revalidate();
				frame.repaint();
			}
			else if(temp.equals("Okay")){
				editFrame.setVisible(false);
				what = editText.getText();
				editText.setText("");
				mode = ADMINEDIT;
				command();
			}
			else if(temp.equals("Got it")){
				warnFrame.setVisible(false);
			}
		}
	}

	public void command() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
			System.out.println("드라이버 연결 성공!" + mode);

			conn = DriverManager.getConnection(url);
			System.out.println("데이터베이스 연결 성공!");

			stmt = conn.createStatement();


			String useXproject = "use Xproject";
			stmt.executeUpdate(useXproject);

			if(mode == LOGINCHECK) {
				col = "username";
				what = username;
				//username으로 검색하기
				String search = "select * from user where " + col + " like '" + what +"';";
				//username으로 받은 데이터가 rs에 저장되고 그 내용을 확인!
				rs = stmt.executeQuery(search);
				if(rs.next()) {
					if(passwd.equals(rs.getString("passwd"))){
						System.out.println("login succeeded");
						if(username.equals("Admin")) {
							System.out.println("Admin login");
							
							if(adminLog == 0) {
								adminmode();
								adminLog ++;
							}
							else {
								data.clear();
								readmin();
							}
						}
						else usermode();
					}
					else {
						System.out.println("login failed");
						warnFrame.setVisible(true);
						warnLabel.setText("Password is wrong.");
					}
				}
				else {
					System.out.println("login failed");
					warnFrame.setVisible(true);
					warnLabel.setText("UserName does not exist.");


				}
			}

			else if(mode == CREATEACCOUNT) {

				String adduser = "insert into user(username, passwd, name, birthdate, email) values('"+ username + "', '" + passwd + "', '"
						+ name + "', '" + birthdate + "', '" + email + "');";
				System.out.println(adduser);
				stmt.executeUpdate(adduser);
			}
			else if(mode == REDUNTCHECK) {
				String search = "select * from user where username like '" + namecheck +"';";
				rs = stmt.executeQuery(search);
				if(rs.next()) {
					System.out.println(rs.getString("username"));
					check = 0;
				}
				else {
					System.out.println("check changed");
					check = 1;
				}
			}
			else if(mode == EDIT) {
				String Update = "update user set passwd = '" + passwd + "' where username = '"+ username+"' ; ";
				stmt.executeUpdate(Update);
				System.out.println(Update);

				Update = "update user set name = '" + name + "' where username = '"+ username+"' ; ";
				stmt.executeUpdate(Update);
				System.out.println(Update);

				Update = "update user set email = '" + email + "' where username = '"+ username+"' ; ";
				stmt.executeUpdate(Update);
				System.out.println(Update);

				Update = "update user set birthdate = '" + birthdate + "' where username = '"+ username+"' ; ";
				stmt.executeUpdate(Update);
				System.out.println(Update);
				usermode();
			}
			else if(mode == DELACCOUNT) {
				String Delete = "delete from user where username = '" + username + "';";
				System.out.println(Delete);
				stmt.executeUpdate(Delete);
			}
			else if(mode == VIEW) {
				String View = "select* from user;";
				rs = stmt.executeQuery(View);
				while(rs.next()) {
					data.add(rs.getString("username"));
					data.add(rs.getString("passwd"));
					data.add(rs.getString("name"));
					data.add(rs.getString("birthdate"));
					data.add(rs.getString("email"));
				}
			}

			else if(mode == ADMINDEL) {
				for(int i = 0; i < data.size()/5; i++) {
					if(checkboxlist[i] != null) {
						String Delete = "delete from user where username = '" + checkboxlist[i] + "';";
						System.out.println(Delete);
						stmt.executeUpdate(Delete);
					}
				}
				data.clear();

				readmin();

			}

			else if(mode == ADMINEDIT) {
				String Update = "update user set " + hey + " = '" + what + "' where username = '"+ username+"' ; ";
				System.out.println(Update);
				stmt.executeUpdate(Update);
				data.clear();
				
				readmin();
			}

		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace(); 
		}
	}

	public void usermode() {
		frame.remove(mainPanel);
		frame.remove(createPanel);
		frame.add(userPanel);
		frame.revalidate();
		frame.repaint();

		int x = 50, y = 10;


		userModeLabel.setLocation(x, y);
		userModeLabel.setSize(dim_Label);
		userPanel.add(userModeLabel);
		y += 30;
		changeInfoLabel.setSize(dim_Label);
		changeInfoLabel.setLocation(x, y);
		userPanel.add(changeInfoLabel);
		y += 30;

		for(int i =  0; i< userLabel.length ; i++) {
			userLabel[i].setLocation(x, y);
			userLabel[i].setSize(dim_Label);
			userPanel.add(userLabel[i]);
			y += 30;
			userField[i].setLocation(x, y);
			userField[i].setSize(dim_Field);
			userPanel.add(userField[i]);
			y += 40;


		}

		for(int i = 0; i < userBtn.length-1 ; i++) {
			userBtn[i].setLocation(x, y);
			userBtn[i].setSize(dim_SButton);
			userBtn[i].addActionListener(new ButtonAction());
			userPanel.add(userBtn[i]);
			x += 150;
		}
		userBtn[2].setLocation(200, 10);
		userBtn[2].setSize(dim_SButton);
		userBtn[2].addActionListener(new ButtonAction());
		userPanel.add(userBtn[2]);
	}

	public void adminmode() {
		
		adminLabel = new JLabel("Admin Mode");
		adminFrame.setSize(800, 600);
		adminFrame.setLocation(0, 0);
		adminFrame.setResizable(false);
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.revalidate();
		adminFrame.repaint();
		mode = VIEW;
		command();
		frame.setVisible(false);
		adminFrame.remove(adminLabel);
		adminFrame.setVisible(true);
		adminPanel = new JPanel();
		adminFrame.add(adminPanel);
		
		adminPanel.setLayout(null);
		adminPanel.setLocation(0,0);
		adminPanel.add(adminLabel);
		adminPanel.setSize(800, 600);
		adminLabel.setSize(800, 30);

		String header[] = {"username", "passwd", "name", "birthdate", "email"};
		String contents[][] = new String[data.size()/5][5];

		for(int i = 0; i < data.size()/5; i++) {
			for(int j =0 ; j< 5; j++) {
				contents[i][j] = data.get(i*5 + j);
			}
		}
		table = new JTable(contents, header);

		checkbox = new JCheckBox[data.size()/5];

		for(int i = 0; i < checkbox.length; i++) {
			checkbox[i] = new JCheckBox(Integer.toString(i));
			checkbox[i].setLocation(10, 75 + 20*i);
			checkbox[i].setSize(30, 20);
			adminPanel.add(checkbox[i]);
			checkbox[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					JCheckBox mycheckbox = (JCheckBox)e.getSource();
					String temp = mycheckbox.getText();
					if(e.getStateChange() == 1) {
						checkboxlist[Integer.parseInt(temp)] = contents[Integer.parseInt(temp)][0];
						System.out.println("add");
					}
					else if(e.getStateChange() == 2) {
						checkboxlist[Integer.parseInt(temp)] = null;
						System.out.println("pop");
					}
				}
			});
		}

		jScrollPane = new JScrollPane(table);
		jScrollPane.setSize(750,300);
		jScrollPane.setLocation(50, 50);
		logoutbtn.setSize(dim_Button);
		deletebtn.setLocation(100, 350);
		deletebtn.setSize(dim_Button);
		logoutbtn.setLocation(400, 350);

		logoutbtn.addActionListener(new ButtonAction());
		deletebtn.addActionListener(new ButtonAction());

		JTableHeader theader = table.getTableHeader();
		theader.setFont(new Font("Arial", Font.PLAIN, 20));

		table.setRowHeight(20);
		table.getColumn("username").setPreferredWidth(30);
		table.getColumn("name").setPreferredWidth(30);
		table.getColumn("birthdate").setPreferredWidth(20);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable t = (JTable)e.getSource();
				Point pt = e.getPoint();
				int i = t.rowAtPoint(pt);
				int j = t.columnAtPoint(pt);
				mode = ADMINEDIT;
				hey = header[j];
				username = (String) table.getValueAt(i, 0);
				editFrame.setVisible(true);
			}
		});
		adminPanel.add(jScrollPane);
		adminPanel.add(logoutbtn);
		adminPanel.add(deletebtn);
	}
	
	public void readmin() {
		adminFrame.setVisible(true);
		frame.setVisible(false);
		adminFrame.revalidate();
		adminFrame.repaint();
		mode = VIEW;
		command();
		for(int i = 0; i < checkbox.length; i++) {
			adminPanel.remove(checkbox[i]);
		}
		adminPanel.remove(jScrollPane);
		jScrollPane.remove(table);
		
		String header[] = {"username", "passwd", "name", "birthdate", "email"};
		String contents[][] = new String[data.size()/5][5];

		for(int i = 0; i < data.size()/5; i++) {
			for(int j =0 ; j< 5; j++) {
				contents[i][j] = data.get(i*5 + j);
			}
		}
		table = new JTable(contents, header);
		JTableHeader theader = table.getTableHeader();
		theader.setFont(new Font("Arial", Font.PLAIN, 20));

		table.setRowHeight(20);
		table.getColumn("username").setPreferredWidth(30);
		table.getColumn("name").setPreferredWidth(30);
		table.getColumn("birthdate").setPreferredWidth(20);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable t = (JTable)e.getSource();
				Point pt = e.getPoint();
				int i = t.rowAtPoint(pt);
				int j = t.columnAtPoint(pt);
				hey = header[j];
				username = (String) table.getValueAt(i, 0);
				editFrame.setVisible(true);

			}
		});
		
		checkbox = new JCheckBox[data.size()/5];

		for(int i = 0; i < checkbox.length; i++) {
			checkbox[i] = new JCheckBox(Integer.toString(i));
			checkbox[i].setLocation(10, 75 + 20*i);
			checkbox[i].setSize(30, 20);
			adminPanel.add(checkbox[i]);
			checkbox[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					JCheckBox mycheckbox = (JCheckBox)e.getSource();
					String temp = mycheckbox.getText();
					if(e.getStateChange() == 1) {
						checkboxlist[Integer.parseInt(temp)] = contents[Integer.parseInt(temp)][0];
						System.out.println("add");
					}
					else if(e.getStateChange() == 2) {
						checkboxlist[Integer.parseInt(temp)] = null;
						System.out.println("pop");
					}
				}
			});
		}
		jScrollPane = new JScrollPane(table);
		jScrollPane.setSize(750,300);
		jScrollPane.setLocation(50, 50);
		
		adminPanel.add(jScrollPane);
	}
}