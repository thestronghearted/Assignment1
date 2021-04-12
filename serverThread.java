import java.io.*;
import java.net.*;
public class serverThread extends Thread {
	clientInformation[] users;
	String sendernumber;
	String receivernumber;
	byte[] message;
	String messagereceived;
	int sender,receiver;
	String packetlosstest;
	public serverThread(clientInformation[] users, String sendernumber, String receivernumber) {
		this.message = new byte[1024];
		this.messagereceived = "";
		this.users=users;
		this.sendernumber=sendernumber;
		this.receivernumber=receivernumber;
		this.packetlosstest = "1xt872nx";
	}
	public void run() {
		try {
			int tempcounter=0;
			boolean tryingToConnect = false; //Ensures that the user only receives one message informing them that they must wait for the other user
			boolean connected=false;
			do {
				tempcounter=0;
				DatagramPacket packet = new DatagramPacket(message, message.length);
				for (int i=0;i<users.length;i++) {
					if (users[i]==null) {
						if (!tryingToConnect && !connected) {
							messagereceived = "Waiting for other user to connect...";   // Lets the user know they must wait for the other person to connect
							message = messagereceived.getBytes();
							packet = new DatagramPacket(message,message.length,users[sender].clientAddress,users[sender].clientport);
							users[sender].clientsocket.send(packet);
							tryingToConnect=true;
						}
						break;
					}
					else if(users[i].studentNumber.equals(sendernumber)) {
						sender=i;   // Get the index of the person who's sending messages to this server thread
						tempcounter++;
					}
					else if(users[i].studentNumber.equals(receivernumber)) {
						receiver=i;  //Get the index of the person who's receiving messages from this server thread
						tempcounter++;
					}
					if (tempcounter==2) {
						if(!connected) {
							messagereceived = "Both users connected";
							message = messagereceived.getBytes();
							packet = new DatagramPacket(message,message.length,users[sender].clientAddress,users[sender].clientport);
							users[sender].clientsocket.send(packet);
							connected=true;
							break;
						}
					}
				}
				if (tempcounter==1) {
					continue;
				}
				packet = new DatagramPacket(message, message.length);
				users[sender].clientsocket.receive(packet);    // Wait at this line until the sender sends a message
				message = packetlosstest.getBytes();
				DatagramPacket packetloss = new DatagramPacket(message,message.length,users[sender].clientAddress,users[sender].clientport);
				users[sender].clientsocket.send(packetloss);  // Confirm to the sender that their message was delivered
				messagereceived = new String(packet.getData(),0,packet.getLength());
				message = messagereceived.getBytes();
				packet = new DatagramPacket(message,message.length,users[receiver].clientAddress,users[receiver].clientport);
				if (messagereceived.equals("bye")) {   //Signals the end of a chat and so the users sockets must be closed and they must indicate they are no longer in a chat
					users[sender].clientsocket.close();
					users[sender].inChatWith="";
					users[receiver].inChatWith="";;
				}
				users[receiver].clientsocket.send(packet);
				message = new byte[1024];  // Clear the bytearray which holds each individual incoming message
			}
			while(!messagereceived.equals("bye"));
		}
		catch (IOException e ) {
		} 
	}
}