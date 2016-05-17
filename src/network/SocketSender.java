package network;
import java.lang.*;
import java.io.*;
import java.net.*;

public class SocketSender {
	public static void establishConn(String ip){
		
		try {
	         Socket skt = new Socket(ip, 8888);
	         BufferedReader in = new BufferedReader(new
	            InputStreamReader(skt.getInputStream()));
	         System.out.print("Received string: '");

	         while (!in.ready()) {}
	         System.out.println(in.readLine()); // Read one line and output it

	         System.out.print("'\n");
	         in.close();
	      }
	      catch(Exception e) {
	         System.out.print("Whoops! It didn't work!\n");
	      }
		
		
	}
	
	
	
}


