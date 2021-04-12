import java.io.*;
import java.net.*;
import java.awt.event.*;
public class clientReceiverThread extends Thread{
	DatagramSocket udpClientSocket;
	client_GUI gui;
	String name;
	public clientReceiverThread(DatagramSocket user,client_GUI gui,String name) {
		this.udpClientSocket=user;
		this.gui = gui;
		this.name = name;
	}
	public void run() {
		byte[] receiveData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				udpClientSocket.receive(receivePacket);
				String serverecho = new String(receivePacket.getData(), 0, receivePacket.getLength());
				if (serverecho.equals("bye")) {
					gui.txtOutput.append(name+": "+serverecho+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
					serverecho = "User has disconnected. Please close the application to begin another chat";
					gui.txtOutput.append("Server: "+serverecho+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
					serverecho = "bye";
					byte[] endchat = serverecho.getBytes();
					DatagramPacket disconnect = new DatagramPacket(endchat,endchat.length,receivePacket.getAddress(),receivePacket.getPort());
					udpClientSocket.send(disconnect);
					break;
				}
				else if(serverecho.equals("1xt872nx")) {
					gui.txtOutput.append("   (Message received)"+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
				}
				else
				{
					gui.txtOutput.append(name+": "+serverecho+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}
}
