import java.io.*;
import java.net.*;
import java.util.*;
public class server {
	public static void main(String[] args) {
		Socket tcpsocket;
		int port = 32517;
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server is up");
			while(true){
				tcpsocket  = serverSocket.accept();
				OutputStream tcpoutput = tcpsocket.getOutputStream();
				PrintWriter tcpwriter = new PrintWriter(tcpoutput, true);
				tcpwriter.println("You are connected");
				new serverThread().start();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}