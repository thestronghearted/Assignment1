import java.io.*;
import java.util.*;
import java.net.*;
import java.net.UnknownHostException;
public class getIp{
	public static void main(String[] args){
		try{
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println(localhost.getHostAddress());
			System.out.println(localhost.getHostName());
	
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
	}
}
