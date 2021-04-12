import java.net.*;

public class clientInformation { //Holds the socket information for every user who is connected as well as whether they are in a chat with someone
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
