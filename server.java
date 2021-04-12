import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;
public class server {
	public static void main(String[] args) {
		DatagramSocket clientsocket;
		Socket tcpsocket;
		int port = 32517;
		AtomicInteger counter= new AtomicInteger();
		byte[] messagebytes;
		clientInformation[] users = new clientInformation[1000];
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server is up");
			while(counter.get()<100){
				boolean inChatAlready= false;
				messagebytes = new byte[1024];
				tcpsocket  = serverSocket.accept();
				OutputStream tcpoutput = tcpsocket.getOutputStream();
				PrintWriter tcpwriter = new PrintWriter(tcpoutput, true);
				tcpwriter.println(port+counter.get());
				tcpsocket.close();
				DatagramPacket packet = new DatagramPacket(messagebytes,messagebytes.length);
				clientsocket = new DatagramSocket(port+counter.get());
				clientsocket.receive(packet);
				InetAddress useraddress = packet.getAddress();
				int userport = packet.getPort();
				String messagereceived = new String(packet.getData(),0,packet.getLength());
				messagereceived = messagereceived.replaceAll(" ", "");
				if (messagereceived.length()!=18) {
					messagereceived = "Invalid student numbers entered. Please close the application and try again";
					messagebytes = messagereceived.getBytes();
					packet = new DatagramPacket(messagebytes,messagebytes.length,useraddress,userport);
					clientsocket.send(packet);
					continue;
				}
				for (int i=0;i<users.length;i++) {
					if (users[i]==null) {
						users[i] = new clientInformation(messagereceived.substring(0,9),useraddress,userport,clientsocket,messagereceived.substring(9,18));
						break;
					}
					else if(users[i].studentNumber.equals(messagereceived.substring(9,18)) && (!(users[i].inChatWith.equals(messagereceived.substring(0,9))) || users[i].inChatWith.equals("") ))
					{
						inChatAlready = true;
						messagereceived = "User is in chat with someone else. Please close the application and try again later";
						messagebytes = messagereceived.getBytes();
						packet = new DatagramPacket(messagebytes,messagebytes.length,useraddress,userport);
						clientsocket.send(packet);
						break;
					}
					else if (users[i].studentNumber.equals(messagereceived.substring(0,9))) {
						users[i] = new clientInformation(messagereceived.substring(0,9),useraddress,userport,clientsocket,messagereceived.substring(9,18));
						break;
					}
				}
				if (!inChatAlready) {
					new serverThread(users,messagereceived.substring(0,9),messagereceived.substring(9,18)).start();
					counter.getAndIncrement();
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}