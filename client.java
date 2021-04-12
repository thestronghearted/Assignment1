import java.net.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

import java.io.*;

public class client{
	public static void main(String[] args){
		int port = 57234;
		String ip = " ";
		String connectionType;
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
		JOptionPane.showMessageDialog(null,"Connecting...");
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))
		{
			JOptionPane.getRootFrame().dispose();
			//TCP
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			port = Integer.parseInt(tcpreader.readLine());
			final int portfinal = port;
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