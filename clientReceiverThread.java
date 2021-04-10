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
					gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));//close gui/frame
					break;
				}
				gui.txtOutput.append(name+": "+serverecho+"\n");
				gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}
}
