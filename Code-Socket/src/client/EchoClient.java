/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class EchoClient {

	private Socket socket;
	private IhmClient ihm;
	private BufferedReader stdIn;
	private BufferedReader socIn;

	private PrintStream out;

	private ThreadClient client;

	public EchoClient() {
		this.ihm = new IhmClient(this);
	}

	public ThreadClient getClient() {
		return client;
	}

	public void setClient(ThreadClient client) {
		this.client = client;
	}

	public synchronized void connect(String ipServer, int port, String nom) throws IOException {
		socket = new Socket(ipServer, port);
		stdIn = null;
		socIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
		out.println(nom + " est connecté.");
		client = new ThreadClient(this, socIn, ihm);
		client.start();
	}

	public synchronized void disconnect(String name) {
		if (socket != null) {
			try {
				sendMessage(name + " est déconnecté");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized void sendMessage(String message) {
		if (socket != null) {
			out.println(message);
		}
	}

	public void receiveMessage(String message) {
		System.out.println("echo: " + message);

	}

	/**
	 * main method accepts a connection, receives a message from client then sends
	 * an echo to the client
	 **/
	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * Socket echoSocket = null; PrintStream socOut = null; BufferedReader stdIn =
	 * null; BufferedReader socIn = null;
	 * 
	 * if (args.length != 2) { System.out.
	 * println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
	 * System.exit(1); }
	 * 
	 * try { // creation socket ==> connexion echoSocket = new Socket(args[0], new
	 * Integer(args[1]).intValue()); socIn = new BufferedReader(new
	 * InputStreamReader(echoSocket.getInputStream())); socOut = new
	 * PrintStream(echoSocket.getOutputStream()); stdIn = new BufferedReader(new
	 * InputStreamReader(System.in)); } catch (UnknownHostException e) {
	 * System.err.println("Don't know about host:" + args[0]); System.exit(1); }
	 * catch (IOException e) { System.err.println("Couldn't get I/O for " +
	 * "the connection to:" + args[0]); System.exit(1); }
	 * 
	 * String line; while (true) { line = stdIn.readLine(); if (line.equals("."))
	 * break; socOut.println(line);// "Auteur#Message" System.out.println("echo: " +
	 * socIn.readLine()); } socOut.close(); socIn.close(); stdIn.close();
	 * echoSocket.close(); }
	 */
}
