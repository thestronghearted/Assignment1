import java.net.*;

public class clientInformation {
	InetAddress clientAddress;
	DatagramSocket clientsocket;
	int clientport;
	String studentNumber;
	String inChatWith;
	public clientInformation(String number, InetAddress clientAddress, int clientport, DatagramSocket clientsocket, String inChatWith) {
		this.studentNumber=number;
		this.clientAddress = clientAddress;
		this.clientport = clientport;
		this.clientsocket = clientsocket;
		this.inChatWith = inChatWith;
	}
}
