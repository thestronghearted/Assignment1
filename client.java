import java.net.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

import java.io.*;

public class client{
	public static void main(String[] args){
		int port = 57234;
		String ip = " ";
		String connectionType;
		System.out.println("Welcome to the chat service Facebook wishes they made");
		connectionType = JOptionPane.showInputDialog(null,"Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
		boolean test = false;
		if (connectionType.equalsIgnoreCase("Yes") || connectionType.equalsIgnoreCase("Y")) {
			ip = "localhost";
			port = 32517;
		}
		else if (connectionType.equalsIgnoreCase("No") || connectionType.equalsIgnoreCase("N")) {
			ip = "105.185.168.28";
			test = true;
		}
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))
		{
			//TCP
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			port = Integer.parseInt(tcpreader.readLine());
			System.out.println(port);
			if (test) {
				port = port +(57234-32517);
			}
			InetAddress serveraddress = tcpsocket.getInetAddress();
			tcpsocket.close();
			
			//UDP
			//initialize user
			DatagramSocket udp = new DatagramSocket();
			String senderNum = JOptionPane.showInputDialog("Enter your student number");
			String recieverNum = JOptionPane.showInputDialog("Enter the receivers student number");
			String message = senderNum + " " + recieverNum;
			byte[] messageData = new byte[1024];
			messageData = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(messageData, messageData.length, serveraddress, port);
			udp.send(sendPacket);
			//JOptionPane.showMessageDialog(null,"Please wait 15 seconds...");
			//Thread.sleep(15000);
			//JOptionPane.showMessageDialog(null,"You may now type your message");
			
			client_GUI gui = new client_GUI();
			gui.getRootPane().setDefaultButton(gui.sendBtn);
			
			//clientSenderThread sender = new clientSenderThread(serveraddress, udp, port,gui);
			//sender.start();
			clientReceiverThread receiver = new clientReceiverThread(udp,gui,recieverNum);
			receiver.start();
			gui.sendBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = gui.txtInput.getText();
					byte[] messageData = new byte[1024];
					messageData = message.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(messageData,messageData.length,serveraddress,32517);
					gui.txtOutput.append(senderNum+": "+message+"\n");
					gui.txtInput.setText("");
					try {
						udp.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
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
		//catch (InterruptedException e){
	//		e.printStackTrace();
	//	}
	}
}