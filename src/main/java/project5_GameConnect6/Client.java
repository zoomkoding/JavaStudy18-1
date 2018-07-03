package project5_GameConnect6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {
	String data = "";
	String str2;
	Socket socket = null;
	DataInputStream in = null;
	BufferedReader in2 = null;
	DataOutputStream out = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();
	}
	
	Client(){
		try {
			socket = new Socket("172.17.208.59", 1024);
			in = new DataInputStream(socket.getInputStream());
			in2 = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.print("아이디 입력 : ");
			data = in2.readLine();
			System.out.print("passwd 입력 : ");
			data = data + " " + in2.readLine();
			out.writeUTF(data);
			
		}catch(Exception e) {}
		
		try {
			while(true) {
				str2 = in.readUTF();
				if(str2.equals("Login Failed")) {
					System.out.println("Login Failed");
					System.out.print("아이디 입력 : ");
					data = in2.readLine();
					System.out.print("passwd 입력 : ");
					data = data + " " + in2.readLine();
					out.writeUTF(data);
				}
				else if(str2.equals("Login Succeeded")) {
					System.out.println("Login Succeded");
					Thread th = new Thread(new Send(out));
					th.start();
					

				}
				
			}
		}catch(Exception e) {}
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

