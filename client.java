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
		while (true){
			if (connectionType.equals("Yes") || connectionType.equals("Y")) {
				ip = "localhost";
				port = 32517;
				break;
			}
			else if (connectionType.equals("No") || connectionType.equals("N")) {
				ip = "105.185.168.28";
				break;
			}
			else{
				connectionType = input.readLine("Please choose a correct option (Y or N): ");
			}
		}	
		try(Socket tcpsocket = new Socket(InetAddress.getByName(ip),port))
		{
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			System.out.println(tcpreader.readLine());
			InetAddress serveraddress = tcpsocket.getInetAddress();
			tcpsocket.close();
			DatagramSocket udpsocket = new DatagramSocket();
			clientSenderThread sender = new clientSenderThread(serveraddress,udpsocket,port);
			sender.start();
			clientReceiverThread receiver = new clientReceiverThread(udpsocket);
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
