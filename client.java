import java.net.*;
import java.io.*;
import java.util.*;

public class client{
	public static void main(String[] args){
		int port = 57234;
		String ip = " ";
		String connectionType;
		Console input = System.console();
		System.out.println("Welcome to the chat service Facebook wishes they made");
		connectionType = input.readLine("Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
		boolean test = false;
		if (connectionType.equals("Yes") || connectionType.equals("Y")) {
			ip = "localhost";
			port = 32517;
		}
		else if (connectionType.equals("No") || connectionType.equals("N")) {
			ip = "105.185.168.28";
			test = true;
		}
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))
		{
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			port = Integer.parseInt(tcpreader.readLine());
			if (test) {
				port = port +(57234-32517);
			}
			InetAddress serveraddress = tcpsocket.getInetAddress();
			tcpsocket.close();
			DatagramSocket udp = new DatagramSocket();
			clientSenderThread sender = new clientSenderThread(serveraddress, udp, port);
			sender.start();
			clientReceiverThread receiver = new clientReceiverThread(sender.getudp());
			receiver.start();
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
