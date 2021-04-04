import java.io.*;
import java.net.*;
public class clientSenderThread extends Thread {
	InetAddress serverIPAddress;
	DatagramSocket udpClientSocket;
	int serverport;
	public clientSenderThread(InetAddress server, DatagramSocket udpclient, int portnumber) {
		this.serverIPAddress = server;
		this.udpClientSocket = udpclient;
		this.serverport = portnumber;
	}
	public void run() {
		try {
			Console input = System.console();
			while (true) {
				String message = input.readLine();
				byte[] messageData = new byte[1024];
				messageData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(messageData,messageData.length,serverIPAddress,serverport);
				udpClientSocket.send(sendPacket);
				if (message.equals("bye")) {
					break;
				}
				Thread.yield();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
