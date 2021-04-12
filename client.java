import java.net.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

import java.io.*;

public class client{
	public static void main(String[] args){
		int port = 57234; //Port number that the server is listening on is 32517 for local server and 57234 for server hosted on 105.185.168.28
		String ip = " ";
		String connectionType;
		connectionType = JOptionPane.showInputDialog(null,"Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
		boolean test = false;
		if (connectionType.equalsIgnoreCase("Yes") || connectionType.equalsIgnoreCase("Y")) {
			ip = "localhost";
			port = 32517;
		}
		else if (connectionType.equalsIgnoreCase("No") || connectionType.equalsIgnoreCase("N")) {
			ip = "105.185.168.28"; //This server allows for people to connect to the application even if they aren't on the same network
			test = true;
		}
		JOptionPane.showMessageDialog(null,"Click ok to begin connecting...");  // User has to click and wait until the server is able to take request
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))  // TCP connection is used to give server user information and then UDP is used to send messages
		{
			//TCP
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			port = Integer.parseInt(tcpreader.readLine()); // Get the unique port number that the server will be listening on for this client
			final int portfinal = port;
			if (test) {
				port = port +(57234-32517);
			}
			InetAddress serveraddress = tcpsocket.getInetAddress();
			tcpsocket.close();
			
			//UDP
			//initialize user
			DatagramSocket udp = new DatagramSocket();
			String senderNum = JOptionPane.showInputDialog("Enter your student number"); //Will fail if you enter an invalid length student number(9 digits)
			String recieverNum = JOptionPane.showInputDialog("Enter the receivers student number"); //The identifier of the person you're having a conversation with
			String message = senderNum + " " + recieverNum;
			byte[] messageData = new byte[1024];
			messageData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(messageData, messageData.length, serveraddress, port);
			udp.send(sendPacket);
			
			client_GUI gui = new client_GUI(); //Initialise the GUI for this chat instance
			gui.getRootPane().setDefaultButton(gui.sendBtn);
			
			clientReceiverThread receiver = new clientReceiverThread(udp,gui,recieverNum);
			receiver.start();  //Starts a thread which listens for messages incoming to this client and displays them on the GUI
			gui.sendBtn.addActionListener(new ActionListener() {  //Describes the actions which must take place when the user clicks the send button
				public void actionPerformed(ActionEvent arg0) {
					String message = gui.txtInput.getText();
					byte[] messageData = new byte[1024];
					messageData = message.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(messageData,messageData.length,serveraddress,portfinal);
					gui.txtOutput.append(senderNum+": "+message);
					gui.txtInput.setText("");
					try {
						udp.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (message.equals("bye")) {
						gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));//close gui/frame
						
					}
				}
			});
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
