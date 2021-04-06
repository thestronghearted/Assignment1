import java.io.*;
import java.net.*;
public class clientSenderThread extends Thread {
	InetAddress serverIPAddress;
	DatagramSocket udpClientSocket;
	int serverport;
	public clientSenderThread(InetAddress serveraddress,DatagramSocket clientsocket, int portnumber) {
		this.serverIPAddress=serveraddress;
		this.serverport = portnumber;
		this.udpClientSocket = clientsocket;
	}
	public void run() {
		try {
			Console input = System.console();
			String message = input.readLine("Enter your student number followed by a space followed by the receivers student number: \n");
			byte[] messageData = new byte[1024];
			messageData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(messageData,messageData.length,serverIPAddress,serverport);
			udpClientSocket.send(sendPacket);
			System.out.println("Please wait 15 seconds...");
			Thread.sleep(15000);
			System.out.println("You may now type your message");
			while (true) {
				message = input.readLine();
				messageData = new byte[1024];
				messageData = message.getBytes();
				sendPacket = new DatagramPacket(messageData,messageData.length,serverIPAddress,serverport);
				udpClientSocket.send(sendPacket);
				if (message.equals("bye")) {
					break;
				}
			}
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public DatagramSocket getudp() {
		return this.udpClientSocket;
	}
}
