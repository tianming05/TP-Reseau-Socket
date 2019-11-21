package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.MulticastSocket;

public class ThreadClientMulticast extends Thread {
	private MulticastSocket multicastSocket;
	private IhmClient ihm;
	private Inet4Address addrIp;

	public ThreadClientMulticast(MulticastSocket ms, IhmClient ihm, Inet4Address addr) {
		this.multicastSocket = ms;
		this.ihm = ihm;
		this.addrIp = addr;
	}

	public synchronized void kill() {
		try {
			multicastSocket.leaveGroup(addrIp);
			multicastSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			byte[] buffer = new byte[1000];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			try {
				if (!multicastSocket.isClosed()) {
					multicastSocket.receive(datagramPacket);
					String message = new String(buffer, 0, datagramPacket.getLength());
					ihm.onReceiveMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}