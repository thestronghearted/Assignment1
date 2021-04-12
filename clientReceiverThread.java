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
				if (serverecho.equals("bye")) { //Signals the other person has ended the chat and that the user should close and re-open the application to start another chat
					gui.txtOutput.append(name+": "+serverecho+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
					serverecho = "User has disconnected. Please close the application to begin another chat";
					gui.txtOutput.append("Server: "+serverecho+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
					serverecho = "bye";
					byte[] endchat = serverecho.getBytes();
					DatagramPacket disconnect = new DatagramPacket(endchat,endchat.length,receivePacket.getAddress(),receivePacket.getPort());
					udpClientSocket.send(disconnect); //Signals to the server thread that listens for messages from this user that it can cease
					break;
				}
				else if(serverecho.equals("1xt872nx")) {   //Obscure message which the server sends to signal a message was received
					gui.txtOutput.append("   (Message received)"+"\n");
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
				}
				else if(serverecho.equals("Waiting for other user to connect...") || serverecho.equals("Both users connected"))
				{
					gui.txtOutput.append("Server: "+serverecho+"\n");  //Connection messages from server to signal both users are connected
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
				}
				else
				{
					gui.txtOutput.append(name+": "+serverecho+"\n");   //Write the name of the person who sendt the message to you followed by their message
					gui.txtOutput.setCaretPosition(gui.txtOutput.getDocument().getLength());
					String confirm = "1xt872nx";
					byte[] confirmation = confirm.getBytes();
					DatagramPacket sendconfirmation = new DatagramPacket(confirmation,confirmation.length,receivePacket.getAddress(),receivePacket.getPort());
					udpClientSocket.send(sendconfirmation);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			Thread.yield();
		}
	}
}
