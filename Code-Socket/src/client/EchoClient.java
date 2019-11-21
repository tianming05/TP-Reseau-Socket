/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.MulticastSocket;

public class EchoClient {
	private MulticastSocket socket;
	private IhmClient ihm;
	private BufferedReader socIn;
	private Inet4Address inetAddress;
	private PrintStream out;
	private int port;
	private ThreadClientMulticast client;

	public EchoClient() {
		this.ihm = new IhmClient(this);
	}

	public ThreadClientMulticast getClient() {
		return client;
	}

	public void setClient(ThreadClientMulticast client) {
		this.client = client;
	}

	public synchronized void connect(String addr, int port, String nom) throws IOException {
		socket = new MulticastSocket(port);
		// socIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// out = new PrintStream(socket.getOutputStream());
		// out.println(nom + " est connecté.");
		this.port = port;

		inetAddress = (Inet4Address) Inet4Address.getByName(addr);
		client = new ThreadClientMulticast(socket, ihm, inetAddress);
		socket.joinGroup(inetAddress);
		client.start();
		send(nom + " est connecté.");
	}

	public synchronized void disconnect(String name) throws IOException {
		if (socket != null) {
			send(name + " est déconnecté");
			socket.leaveGroup(inetAddress);
			socket.close();
			socket.close();
		}
	}

	public void send(String message) {
		try {
			byte[] buf = message.getBytes("UTF-8");
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, port); // 发送数据报
			socket.send(datagramPacket);
			// socket.close();
		} catch (Exception e) {

		}
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
