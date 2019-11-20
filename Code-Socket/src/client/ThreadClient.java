package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author dell
 *
 */
public class ThreadClient extends Thread {

	private EchoClient echoClient;
	private BufferedReader socIn;

	private IhmClient ihmClient;
	private boolean isAlive;

	public ThreadClient(EchoClient echoClient, BufferedReader socIn, IhmClient ihm) {
		this.isAlive = false;
		this.echoClient = echoClient;
		this.socIn = socIn;
		this.ihmClient = ihm;

	}

	public boolean getIsAlive() {
		return this.isAlive;
	}

	public synchronized void kill() {
		isAlive = false;
	}

	public void run() {
		isAlive = true;
		try {
			String line, message;
			while ((line = socIn.readLine()) != null) {
				System.out.println(line);
				ihmClient.onReceiveMessage(line);

			}
			// echoClient.onConnectionLost("Connection lost...");
		} catch (IOException e) {
			// listener.onConnectionLost("Client disconnected");
		}
		kill();
	}

}