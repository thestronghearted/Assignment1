import java.io.*;
import java.net.*;
public class clientReceiverThread extends Thread{
	DatagramSocket udpClientSocket;
	client_GUI gui;
	public clientReceiverThread(DatagramSocket user, client_GUI gui) {
		this.udpClientSocket=user;
		this.gui = gui;
		
	}
	public void run() {
		byte[] receiveData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				udpClientSocket.receive(receivePacket);
				String serverecho = new String(receivePacket.getData(), 0, receivePacket.getLength());
				gui.txtOutput.append(serverecho + "\n");
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
