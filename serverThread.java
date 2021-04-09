import java.io.*;
import java.net.*;
public class serverThread extends Thread {
	clientInformation[] users;
	String sendernumber;
	String receivernumber;
	byte[] message;
	String messagereceived;
	int sender,receiver;
	public serverThread(clientInformation[] users, String sendernumber, String receivernumber) {
		this.message = new byte[1024];
		this.messagereceived = "";
		this.users=users;
		this.sendernumber=sendernumber;
		this.receivernumber=receivernumber;
	}
	public void run() {
		try {
			Thread.sleep(15000);
			int tempcounter=0;
			do {
				tempcounter=0;
				for (int i=0;i<users.length;i++) {
					if (users[i]==null) {
						continue;
					}
					else if(users[i].studentNumber.equals(sendernumber)) {
						sender=i;
						tempcounter++;
					}
					else if(users[i].studentNumber.equals(receivernumber)) {
						receiver=i;
						tempcounter++;
					}
					if (tempcounter==2) {
						break;
					}
				}
				if (tempcounter==1) {
					continue;
				}
				DatagramPacket packet = new DatagramPacket(message, message.length);
				users[sender].clientsocket.receive(packet);
				messagereceived = new String(packet.getData(),0,packet.getLength());
				System.out.println(messagereceived);
				message = messagereceived.getBytes();
				packet = new DatagramPacket(message,message.length,users[receiver].clientAddress,users[receiver].clientport);
				users[receiver].clientsocket.send(packet);
				message = new byte[1024];
			}
			while(!messagereceived.equals("bye"));
			users[sender].clientsocket.close();
			users[receiver].clientsocket.close();
		}
		catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
