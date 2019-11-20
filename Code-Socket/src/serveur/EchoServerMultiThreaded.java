/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServerMultiThreaded {
	public static ArrayList<ClientThread> clientThreadList;

	/**
	 * main method
	 * 
	 * @param EchoServer port
	 * 
	 **/
	public static void main(String args[]) {
		ServerSocket listenSocket;

		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try {
			clientThreadList = new ArrayList<ClientThread>();
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); // port
			System.out.println("Server ready...");
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connection from:" + clientSocket.getInetAddress());
				ClientThread ct = new ClientThread(clientSocket);
				ct.start();
				// todo ; lire fichier text
				// ct.getSocOut().println(Utils.readAllMessages());
				clientThreadList.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}
