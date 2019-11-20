/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket clientSocket;
	private PrintStream socOut;

	public PrintStream getSocOut() {
		return socOut;
	}

	public void setSocOut(PrintStream socOut) {
		this.socOut = socOut;
	}

	ClientThread(Socket s) {
		this.clientSocket = s;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	public void run() {
		try {
			BufferedReader socIn = null;
			socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socOut = new PrintStream(clientSocket.getOutputStream());
			socOut.println(Utils.readAllMessages());
			while (true) {
				String line = socIn.readLine();
				System.out.println(line);
				// creer objet message
				// socOut.println(line);
				for (ClientThread client : EchoServerMultiThreaded.clientThreadList) {
					System.out.println("size:" + EchoServerMultiThreaded.clientThreadList.size());
					client.getSocOut().println(line);
				}
				// "Auteur-Message"
				// todo; ecrire dans le fichier si contient guillemets
				if (line.contains(":")) {
					Utils.writeSingleMessage(line);
				}

			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

}
