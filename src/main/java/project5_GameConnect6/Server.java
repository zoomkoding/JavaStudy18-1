package project5_GameConnect6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Server {
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
	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	HashMap<String, DataOutputStream> players = new HashMap<String, DataOutputStream>();
	String [] loginData = new String[2];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();


	}

	public Server() {
		ServerSocket server_socket = null;

		int count = 0;
		Thread thread[] = new Thread[10];


		try {
			server_socket = new ServerSocket(1024);
			while(true) {
				socket = server_socket.accept();
				thread[count] = new Thread(new Receiver(socket));
				thread[count].start();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	class Receiver implements Runnable{


		public Receiver(Socket socket) throws IOException {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true) {
					String temp = in.readUTF();
					
					if(temp.equals("DEFAULT"))mode = DEFAULT;
					else if(temp.equals("LOGINCHECK"))mode = LOGINCHECK;
					else if(temp.equals("CREATEACCOUNT"))mode = CREATEACCOUNT;


					if(mode == LOGINCHECK) {
						loginData = in.readUTF().split(" ");
						command();
					}
				}
			}catch(Exception e) {}
		}

	}


	public void command() throws IOException {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
			System.out.println("드라이버 연결 성공!" + mode);

			conn = DriverManager.getConnection(url);
			System.out.println("데이터베이스 연결 성공!");

			stmt = conn.createStatement();


			String useConnect6 = "use Connect6";
			stmt.executeUpdate(useConnect6);

			if(mode == LOGINCHECK) {
				System.out.println("check");
				String search = "select * from user where username like '" + loginData[0] +"';";
				rs = stmt.executeQuery(search);
				System.out.println(loginData[0]);
				System.out.println(loginData[1]);
				if(rs.next()) {
					if(loginData[1].equals(rs.getString("passwd"))) {
						addPlayer(loginData[0], socket);
						out.writeUTF("Login Succeeded");
					}
					else {
						out.writeUTF("Login Failed");
					}
				}
				else {
					out.writeUTF("Login Failed");


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

	public void addPlayer(String name, Socket socket) throws IOException {
		players.put(name, new DataOutputStream(socket.getOutputStream()));
		System.out.println("플레이어 인원: " + players.size());

	}

	public void removePlayer(String name) {
		players.remove(name);
	}

	//전체에게 명령 전달
	public void sendCmdAll(String cmd) {
		Iterator iterator = players.keySet().iterator();

		while(iterator.hasNext()) {
			String playername = (String)iterator.next();
			try {
				players.get(playername).writeUTF(cmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//한명에게 명 전달
	public void sendCmd(String cmd, String name) {
		try {
			players.get(name).writeUTF(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//메세지 전
	public void sendMsg(String msg, String sender, String receiver) {
		try {
			players.get(receiver).writeUTF(sender + ": " + msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


