package network;

import java.io.*;
import java.net.*;




public class SocketSender {
	
public static Socket soc;
public static DataOutputStream dout; 

	public static void establishConn(String ip) throws UnknownHostException, IOException{
		
		soc= new Socket(ip, 8888);
	}  
		
	public static void sendCommand(String command) throws IOException{
		
		
		dout = new DataOutputStream(soc.getOutputStream());  
		dout.writeBytes(command);
	    dout.flush();
	    
		
	}
	
	public static void closeConn() throws IOException{
		soc.close();
	}
	
	
	
}


