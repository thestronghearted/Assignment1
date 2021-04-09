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
			while(true){
				messagebytes = new byte[1024];
				tcpsocket  = serverSocket.accept();
				OutputStream tcpoutput = tcpsocket.getOutputStream();
				PrintWriter tcpwriter = new PrintWriter(tcpoutput, true);
				tcpwriter.println(port+counter.get());
				tcpsocket.close();
				DatagramPacket packet = new DatagramPacket(messagebytes,messagebytes.length);
				clientsocket = new DatagramSocket(port+counter.get());
				clientsocket.receive(packet);
				String messagereceived = new String(packet.getData(),0,packet.getLength());
				messagereceived = messagereceived.replaceAll(" ", "");
				for (int i=0;i<users.length;i++) {
					if (users[i]==null) {
						users[i] = new clientInformation(messagereceived.substring(0,9),packet.getAddress(),packet.getPort(),clientsocket);
						break;
					}
					else if (users[i].studentNumber.equals(messagereceived.substring(0,9))) {
						users[i] = new clientInformation(messagereceived.substring(0,9),packet.getAddress(),packet.getPort(),clientsocket);
						break;
					}
				}
				new serverThread(users,messagereceived.substring(0,9),messagereceived.substring(9,18)).start();
				counter.getAndIncrement();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}