import java.io.*;
import java.net.*;
import java.util.*;
public class serverThread extends Thread {
	DatagramSocket udpsocket;
	byte[] message;
	String messagereceived;
	public serverThread() {
		this.message = new byte[1024];
		this.messagereceived = "";
	}
	public void run() {
		try {
			udpsocket = new DatagramSocket(32517);
			do {
				DatagramPacket packet = new DatagramPacket(message, message.length);
				udpsocket.receive(packet);
				InetAddress useraddress = packet.getAddress();
				int userport = packet.getPort();
				messagereceived = new String(packet.getData(),0,packet.getLength());
				System.out.println(messagereceived);
				message = messagereceived.getBytes();
				packet = new DatagramPacket(message,message.length,useraddress,userport);
				udpsocket.send(packet);
				message = new byte[256];
			}
			while(!messagereceived.equals("bye"));
			udpsocket.close();
		}
		catch (IOException e) {
			// e.printStackTrace();
		}
	}
}
