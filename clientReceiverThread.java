import java.io.*;
import java.net.*;
public class clientReceiverThread extends Thread{
	DatagramSocket udpClientSocket;
	public clientReceiverThread(DatagramSocket user) {
		this.udpClientSocket=user;
	}
	public void run() {
		byte[] receiveData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				udpClientSocket.receive(receivePacket);
				String serverecho = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println(serverecho);
				if (serverecho.equals("bye")) {
					udpClientSocket.close();
					break;
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}
}
