import java.net.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class client{
	public static void main(String[] args){
		
		
		int port = 57234;
		String ip = " ";
		System.out.println("Welcome to the chat service Facebook wishes they made");
		
		if(sameLan() == true ){
			ip = "localhost";
			port = 32517;
		}
		else
		{
			ip = "105.185.168.28";
		}
		
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))
		{
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			System.out.println(tcpreader.readLine());
			InetAddress serveraddress = tcpsocket.getInetAddress();
			tcpsocket.close();
			DatagramSocket udpsocket = new DatagramSocket();
			//clientSenderThread sender = new clientSenderThread(serveraddress,udpsocket,port); SENDERTHREAD REPLACED BY ACTION LISTENER BELOW
			
			client_GUI gui = new client_GUI();
			
			//SEND BTN PRESSED
			gui.getRootPane().setDefaultButton(gui.sendBtn); //PRESSING ENTER == PRESSING SEND BUTTON
			gui.sendBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = gui.txtInput.getText();
					byte[] messageData = new byte[1024];
					messageData = message.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(messageData,messageData.length,serveraddress,32517);
					gui.txtInput.setText("");
					try {
						udpsocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (message.equals("bye")) {
						//close gui
					}
				}
			});
			clientReceiverThread receiver = new clientReceiverThread(udpsocket,gui);
			receiver.start();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	static boolean sameLan() 
	{
		String connectionType = JOptionPane.showInputDialog("Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
		while (true){
			if (connectionType.equalsIgnoreCase("yes") || connectionType.equalsIgnoreCase("Y")) 
			{
				return true;
			}
			else if (connectionType.equalsIgnoreCase("no") || connectionType.equalsIgnoreCase("N")) 
			{
				return false;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please choose a correct option (Y or N): ");
				connectionType = JOptionPane.showInputDialog("Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
			}
		}	
	}
	
}
