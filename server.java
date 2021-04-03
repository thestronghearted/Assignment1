import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	public static void main(String[] args){
		DatagramSocket udpsocket;
		Socket tcpsocket;
		byte[] message = new byte[256];
		int port = 32517;
		String messagereceived;
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server is up on 127.0.1.1 listening to port 32517");
			while(true){
				tcpsocket  = serverSocket.accept();
				OutputStream tcpoutput = tcpsocket.getOutputStream();
				PrintWriter tcpwriter = new PrintWriter(tcpoutput, true);
				tcpwriter.println("You are connected");
				tcpsocket.close();
				udpsocket = new DatagramSocket(port);
				do {
					DatagramPacket packet = new DatagramPacket(message, message.length);
					udpsocket.receive(packet);
					InetAddress useraddress = packet.getAddress();
					int userport = packet.getPort();
					messagereceived = new String(packet.getData(),0,packet.getLength());
					message = messagereceived.getBytes();
					packet = new DatagramPacket(message,message.length,useraddress,userport);
					udpsocket.send(packet);
					message = new byte[256];
				}
				while(!messagereceived.equals("bye"));
				udpsocket.close();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
