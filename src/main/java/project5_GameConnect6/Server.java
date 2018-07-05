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
	public static final int USERNAMECHECK = 3;
	public static final int NICKNAMECHECK = 4;
	public static final int FIGHTREQUEST = 5;
	public static final int FIGHTACCEPTED = 6;
	public static final int FIGHTREFUSED = 7;
	public static final int GAMEIN = 8;
	public static final int WINNERDECIDED = 9;
	public static final int UNDO = 10;
	public static final int SURRENDER = 11;
	public static final int REGAME = 12;
	public static final int RETURN = 13;




	static 	String url = "jdbc:mysql://127.0.0.1/Xproject?serverTimezone=UTC&&useSSL=false&user=root&password=h010638847";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Scanner sc = new Scanner(System.in);
	Socket socket;

	String fighterA;
	String fighterB;
	HashMap<String, DataOutputStream> players = new HashMap<String, DataOutputStream>();


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
				System.out.println(count);
				thread[count] = new Thread(new Receiver(socket));
				thread[count].start();
				count++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	class Receiver implements Runnable{
		String username;
		String passwd;
		String nickname;
		String pocketmon;
		String win;
		String lose;
		String [] loginData = new String[2];
		String [] newData = new String[4];
		int mode = DEFAULT;
		int check = 0;
		DataInputStream in;
		DataOutputStream out;
		

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
					System.out.println("Data: " + temp);

					if(temp.equals("DEFAULT"))mode = DEFAULT;
					else if(temp.equals("LOGINCHECK"))mode = LOGINCHECK;
					else if(temp.equals("CREATEACCOUNT"))mode = CREATEACCOUNT;
					else if(temp.equals("USERNAMECHECK"))mode = USERNAMECHECK;
					else if(temp.equals("NICKNAMECHECK"))mode = NICKNAMECHECK; 
					else if(temp.equals("FIGHTREQUEST"))mode = FIGHTREQUEST; 
					else if(temp.equals("FIGHTACCEPTED"))mode = FIGHTACCEPTED; 
					else if(temp.equals("FIGHTREFUSED"))mode = FIGHTREFUSED; 
					else if(temp.equals("GAMEIN"))mode = GAMEIN;
					else if(temp.equals("WINNERDECIDED"))mode = WINNERDECIDED;
					else if(temp.equals("UNDO"))mode = UNDO;
					else if(temp.equals("RETURN"))mode = RETURN;
					
					else if(mode == RETURN) {
						changeStatus(temp, "대기중");
						sendCmd("RETURN", temp);
						sendRepaintAll();
						sendCmdAll("REPAINT");

					}
					
					else if(mode == UNDO) {
						sendCmd("UNDO", fighterA);
						sendCmd("UNDO", fighterB);
						mode = GAMEIN;
					}

					else if(mode == LOGINCHECK) {
						loginData = temp.split(" ");
						command();
					}
					else if(mode  == USERNAMECHECK) {
						username = temp;
						command();
					}
					else if(mode == NICKNAMECHECK) {
						nickname = temp;
						command();
					}
					else if(mode == CREATEACCOUNT) {
						newData = temp.split(",");
						command();
					}
					else if(mode == FIGHTREQUEST) {
						String fight[] = temp.split(",");
						sendCmd("FIGHTREQUEST", fight[1]);
						sendCmd(fight[0], fight[1]);
						
						
					}
					else if(mode == FIGHTACCEPTED) {
						String fight[] = temp.split(",");
						fighterA = fight[0];
						fighterB = fight[1];
						
						System.out.println("fighter A , figherB : )" + fighterA + fighterB);
						sendCmd("FIGHTACCEPTED", fighterA);
						sendCmd("FIGHTACCEPTED", fighterB);
						command();
					}
					else if(mode == GAMEIN) {
						
						sendCmd(temp, fighterA);
						sendCmd(temp, fighterB);
					}
					
					else if(mode == WINNERDECIDED) {
						String data[] = temp.split(",");
						sendCmd("WINNERDECIDED", fighterA);
						sendCmd("WINNERDECIDED", fighterB);
						sendCmd(temp, fighterA);
						sendCmd(temp, fighterB);
						changeRecord(data[0], data[1]);
						mode = DEFAULT;
						
					}
				}
			}catch(Exception e) {
				removePlayer(this.loginData[0]);
				System.out.println(loginData[0] + "이 나갔다.");
				changeStatus(loginData[0], "대기중");
				sendRepaintAll();
				e.printStackTrace();

			}
		}
		public void command() throws IOException {
			try {

				Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
				System.out.println("현재모드" + mode);

				conn = DriverManager.getConnection(url);

				stmt = conn.createStatement();


				String useConnect6 = "use Connect6";
				stmt.executeUpdate(useConnect6);

				if(mode == LOGINCHECK) {
					String search = "select * from user where username like '" + loginData[0] +"';";
					rs = stmt.executeQuery(search);
					if(rs.next()) {
						String data = rs.getString("nickname") + "," + rs.getString("win") + "," + rs.getString("lose") + "," + rs.getString("pocketmon");
						System.out.println(data);


						if(loginData[1].equals(rs.getString("passwd"))) {
							addPlayer(loginData[0], socket);
							sendCmd("LOGIN", loginData[0]);
							sendCmd(data, loginData[0]);
							sendCmdAll("WAITINGADD");
							sendRepaintAll();
							sendCmdAll("REPAINT");

						}
						else {
							out.writeUTF("Login Failed");
						}
					}
					else {
						out.writeUTF("Login Failed");
					}
				}

				else if(mode == USERNAMECHECK) {
					String search = "select * from user where username like '" + username +"';";
					rs = stmt.executeQuery(search);
					if(rs.next()) {
						out.writeUTF("USERNAME BAD");
					}
					else {
						out.writeUTF("USERNAME GOOD");
					}
				}
				else if(mode == NICKNAMECHECK) {
					String search = "select * from user where nickname like '" + nickname +"';";
					System.out.println(search);
					rs = stmt.executeQuery(search);
					if(rs.next()) {
						System.out.println("BAD");
						out.writeUTF("NICKNAME BAD");
					}
					else {
						System.out.println("GOOD");
						out.writeUTF("NICKNAME GOOD");
					}
				}
				else if(mode == CREATEACCOUNT) {
					username =newData[0];
					passwd = newData[1];
					nickname =newData[2];
					pocketmon = newData[3];
					String adduser = "insert into user(username, passwd, nickname, win, lose, pocketmon) values('" 
							+ username + "', '" + passwd + "', '" + nickname + "', '0', '0', '" + pocketmon + "');";
					System.out.println(adduser);
					stmt.executeUpdate(adduser);
					out.writeUTF("CREATEACCOUNT GOOD");
				}
				
				else if(mode == FIGHTACCEPTED) {

					String search1 = "select * from user where username like '" + fighterA +"';";
					rs = stmt.executeQuery(search1);
					if(rs.next()) {
						String data = rs.getString("username")  + "," + rs.getString("nickname") + "," + rs.getString("win") + "," + rs.getString("lose") + "," + rs.getString("pocketmon") + "," + "1";
						System.out.println("Fighter B : " + fighterB + " FighterA info " + data);
						sendCmd(data, fighterB);
						sendCmd(data, fighterA);
						
					}
					String search2 = "select * from user where username like '" + fighterB +"';";
					rs = stmt.executeQuery(search2);
					if(rs.next()) {
						String data = rs.getString("username")  + "," + rs.getString("nickname") + "," + rs.getString("win") + "," + rs.getString("lose") + "," + rs.getString("pocketmon") + "," + "2";
						System.out.println("Fighter A : " + fighterA + " FighterB info " + data);
						sendCmd(data, fighterB);
						sendCmd(data, fighterA);
					}
					
					String Update = "update user set playing = '게임중' where username = '"+ fighterA +"'; ";
					stmt.executeUpdate(Update);
					System.out.println(Update);
					Update = "update user set playing = '게임중' where username = '"+ fighterB +"'; ";
					stmt.executeUpdate(Update);
					sendRepaintAll();
					sendCmdAll("REPAINT");
					

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
	
	
	public void changeRecord(String winner, String loser) throws SQLException {
		String win, lose;
		String search = "select * from user where username like '" + winner +"';";
		rs = stmt.executeQuery(search);
		if(rs.next()) {
			int temp = Integer.parseInt(rs.getString("win"));
			temp ++;
			win = String.valueOf(temp);
			String Update = "update user set win = '" + win + "' where username = '"+ winner +"'; ";
			stmt.executeUpdate(Update);
		}
		search = "select * from user where username like '" + loser +"';";
		rs = stmt.executeQuery(search);
		if(rs.next()) {
			int temp = Integer.parseInt(rs.getString("lose"));
			temp ++;
			lose = String.valueOf(temp);
			String Update = "update user set lose = '" + lose + "' where username = '"+ loser +"'; ";
			stmt.executeUpdate(Update);
		}
	}
	
	
	public void changeStatus(String name, String status) {
		String Update = "update user set playing = '" + status + "' where username = '"+ name +"'; ";
		try {
			stmt.executeUpdate(Update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Update);
	}

	public void addPlayer(String name, Socket socket) throws IOException {
		System.out.println(name);
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

	public void sendRepaintAll() {
		Iterator iterator = players.keySet().iterator();

		while(iterator.hasNext()) {
			String playername = (String)iterator.next();
			String search = "select * from user where username like '" + playername +"';";
			try {
				rs = stmt.executeQuery(search);
				if(rs.next()) {
					String data = "addRepainter" + "," + rs.getString("username") + "," + rs.getString("nickname") + "," + rs.getString("win") + "," + rs.getString("lose") + "," + rs.getString("pocketmon") + "," + rs.getString("playing");
					sendCmdAll(data);
				}
			} catch (SQLException e) {
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


