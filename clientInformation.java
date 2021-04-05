import java.net.*;

public class clientInformation {
	InetAddress clientAddress;
	DatagramSocket clientsocket;
	int clientport;
	String studentNumber;
	public clientInformation(String number, InetAddress clientAddress, int clientport, DatagramSocket clientsocket) {
		this.studentNumber=number;
		this.clientAddress = clientAddress;
		this.clientport = clientport;
		this.clientsocket = clientsocket;
	}
}
