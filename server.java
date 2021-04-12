import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;
public class server {
	public static void main(String[] args) {
		DatagramSocket clientsocket;
		Socket tcpsocket;
		int port = 32517; // The port which the server uses to listen for new connections;
		AtomicInteger counter= new AtomicInteger(); //Ensures each user who connects is assigned a unique port which the server will listen on;
		byte[] messagebytes;
		clientInformation[] users = new clientInformation[1000];
		try (ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server is up");
			while(counter.get()<100){  // Server allows up to 100 users to be connected before it needs to be restarted
				boolean inChatAlready= false;
				messagebytes = new byte[1024];
				tcpsocket  = serverSocket.accept();
				OutputStream tcpoutput = tcpsocket.getOutputStream();
				PrintWriter tcpwriter = new PrintWriter(tcpoutput, true);
				tcpwriter.println(port+counter.get());
				tcpsocket.close();
				DatagramPacket packet = new DatagramPacket(messagebytes,messagebytes.length); 
				clientsocket = new DatagramSocket(port+counter.get()); //The socket the server will use to send and receive messages for this client must have a different port number than any other client
				clientsocket.receive(packet);
				InetAddress useraddress = packet.getAddress();
				int userport = packet.getPort();
				String messagereceived = new String(packet.getData(),0,packet.getLength());
				messagereceived = messagereceived.replaceAll(" ", "");
				if (messagereceived.length()!=18) {
					messagereceived = "Invalid student numbers entered. Please close the application and try again";
					messagebytes = messagereceived.getBytes();
					packet = new DatagramPacket(messagebytes,messagebytes.length,useraddress,userport); // Tell the user they have entered invalid information and should try again
					clientsocket.send(packet);
					continue;
				}
				for (int i=0;i<users.length;i++) {
					if (users[i]==null) {  // It is a new user and they are trying to chat with someone who isn't in a chat already
						users[i] = new clientInformation(messagereceived.substring(0,9),useraddress,userport,clientsocket,messagereceived.substring(9,18));
						break;
					}
					else if(users[i].studentNumber.equals(messagereceived.substring(9,18)) && (!(users[i].inChatWith.equals(messagereceived.substring(0,9))) || users[i].inChatWith.equals("") ))
					{
						// The user who they are trying to chat with is already chatting to someone else. Lets the user know they should close and re-launch to try again
						inChatAlready = true;
						messagereceived = "User is in chat with someone else. Please close the application and try again later";
						messagebytes = messagereceived.getBytes();
						packet = new DatagramPacket(messagebytes,messagebytes.length,useraddress,userport);
						clientsocket.send(packet);
						break;
					}
					else if (users[i].studentNumber.equals(messagereceived.substring(0,9))) {
						// Not a new user so we just need to update their information with their new connection address
						users[i] = new clientInformation(messagereceived.substring(0,9),useraddress,userport,clientsocket,messagereceived.substring(9,18));
						break;
					}
				}
				if (!inChatAlready) {
					new serverThread(users,messagereceived.substring(0,9),messagereceived.substring(9,18)).start();  //start a thread which receives messages from this client and sends it to the person they want to chat with
					counter.getAndIncrement();
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}