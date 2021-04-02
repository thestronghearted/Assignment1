import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	public static void main(String[] args){
		DatagramSocket[] udpsocket;
		Socket tcpsocket;
		String message;
		int port = 32517;
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server is up on 127.0.1.1 listening to port 32517");
			while(true){
				tcpsocket  = serverSocket.accept();
				System.out.println("New Client");
				InputStream input = tcpsocket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				OutputStream output = tcpsocket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);

				do {
					message = reader.readLine();
					String text = new StringBuilder(message).reverse().toString();
					writer.println("Server: " + text);
				}
				while(!message.equals("bye"));
				tcpsocket.close();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
