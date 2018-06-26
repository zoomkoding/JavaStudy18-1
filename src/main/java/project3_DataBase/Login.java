package project3_DataBase;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import project2_Graphics.ShapeRepository;
	

public class Login {
	
	public static final int DEFAULT = 0;
	public static final int LOGINCHECK = 1;

	
	String what;
	String col;
	String username;
	String name;
	String passwd;
	String email;
	String birthdate;
	int mode = DEFAULT;


	static 	String url = "jdbc:mysql://127.0.0.1/Xproject?serverTimezone=UTC&&useSSL=false&user=root&password=h010638847";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Scanner sc = new Scanner(System.in);

	JFrame frame = new JFrame("Login Program");
	JPanel mainPanel = new JPanel();
	JLabel userLabel = new JLabel("UserName");
	JTextField userField = new JTextField();
	JLabel pwLabel = new JLabel("Password");
	JPasswordField pwField = new JPasswordField();
	JButton loginBtn = new JButton("Log in");
	JButton createBtn = new JButton("Create Account");
	
	
	JFrame createframe = new JFrame("Create Account");
	JPanel createPanel = new JPanel();
	JLabel newUserLabel = new JLabel("UserName");
	JTextField newUser = new JTextField();
	JLabel newPWLabel = new JLabel("Password");
	JPasswordField newPW = new JPasswordField();
	JLabel newNameLabel = new JLabel("Name");
	JTextField newName = new JTextField();
	JLabel newBDayLabel = new JLabel("Birth Date");
	JTextField newBirthDate = new JTextField();
	JLabel emailLabel = new JLabel("E-mail");
	JTextField newEmail = new JTextField();
	JButton save = new JButton("Save");
	JButton cancel = new JButton("Cancel");

	public static void main(String[] args) {
		new Login();
	}


	public Login() {

		Dimension dim_Frame = new Dimension(400,600);
		
		frame.setVisible(true);
		frame.setSize(dim_Frame);
		frame.setLocation(500, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		Dimension dim1 = new Dimension(300,30);
		Dimension dim2 = new Dimension(300,70);
		Dimension dim3 = new Dimension(300,30);
		Dimension dim4 = new Dimension(300,70);
		Dimension dim5 = new Dimension(300,70);
		Dimension dim6 = new Dimension(300,70);
		
		mainPanel.setLayout(null);
		userLabel.setLocation(50, 10);
		userLabel.setSize(dim1);
		userField.setLocation(50, 40);
		userField.setSize(dim2);

		pwLabel.setSize(dim3);
		pwLabel.setLocation(50, 110);
		pwField.setSize(dim4);
		pwField.setLocation(50, 140);
		loginBtn.setSize(dim5);
		loginBtn.setLocation(50, 210);
		createBtn.setSize(dim6);
		createBtn.setLocation(50, 280);
		
		loginBtn.addActionListener(new ButtonAction());
		createBtn.addActionListener(new ButtonAction());


		frame.add(mainPanel);
		mainPanel.add(userLabel);
		mainPanel.add(userField);
		mainPanel.add(pwLabel);
		mainPanel.add(pwField);
		mainPanel.add(loginBtn);
		mainPanel.add(createBtn);

		
		
	}
	
	class ButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			if(temp.equals("Log in")) {
				username = userField.getText();
				passwd = new String(pwField.getPassword());
				mode = LOGINCHECK;
				System.out.println("Login 정보 " + username + " " + passwd);
				command();
			}
			else if(temp.equals("Create Account")) {
				
			}
			
		}
		
	}
	
	public void command() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
			System.out.println("드라이버 연결 성공!");

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
					}
					else System.out.println("login failed");
				}
			}

			else if(mode == 1) {
				System.out.print("username ");
				username = sc.next();
				System.out.print("name ");


				name = sc.next();

				System.out.print("password ");
				passwd = sc.next();

				System.out.print("bithdate ");
				birthdate = sc.next();

				System.out.print("email ");
				email = sc.next();

				String adduser = "insert into user(username, passwd, name, birthdate, email) values('"+ username + "', '" + passwd + "', '"
						+ name + "', '" + birthdate + "', '" + email + "');";
				System.out.println(adduser);
				stmt.executeUpdate(adduser);


				if(mode == 2) {

				}



			}


		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace(); 
		}

	}
}