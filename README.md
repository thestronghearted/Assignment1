# Assignment1
CSC3002F Assignment 1
- The server can be hosted on either your local machine to take local hosts, or when it is hosted on the public ip in the code, it can take users from any internet connection.  To edit the public ip address, enter the file client.java and insert the server ip address inreplace of the preset ip address, "105.185.168.28".
- The server class always has to be running in order for the client class to connect.
- The user first connects to the server via a TCP connection to ensure the server captures the clients details, and then any chat messages are sent over UDP.
- Only one user can provide their details(your student number and who you want to chat with) at a time. Any other user who tries to connect while you are doing this has to wait until you are finished. (May cause problems if there is a connected client who doesn't provide details)
- Normal user protocol:
	Start client class (with server already running)
	Choose connection type
	Click ok on connection screen and wait for further prompts
	Enter your details and other users details(student numbers must be 9 characters)
	The chat box will open and messages can be exchanged once both users are connected
	The session terminates once one of the users types exactly the message "bye" (User should not exit with red "x" unless prompted to)
-Any user who tries to chat with a user already in a chat will be prompted to try again at another time (May require the person who was in a chat to relaunch the client class to become available to the new user)
- The server confirms when messages are received by the other user through a prompt of (message received) at the end of the message you send
