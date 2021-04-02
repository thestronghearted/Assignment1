import java.net.*;
import java.io.*;
import java.util.*;

public class client{
	public static void main(String[] args){
		int port = 32517;
		try(Socket tcpsocket = new Socket(InetAddress.getLocalHost().getHostName(),port))
		{
			OutputStream output = tcpsocket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			Console input = System.console();
			String text;
			do{
				text = input.readLine("Enter text: ");
				writer.println(text);
				InputStream in = tcpsocket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String response = reader.readLine();
				System.out.println(response);
			}
			while (!text.equals("bye"));
			tcpsocket.close();
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
