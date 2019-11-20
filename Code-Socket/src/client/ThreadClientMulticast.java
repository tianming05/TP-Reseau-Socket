package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ThreadClientMulticast {
	private MulticastSocket multicastSocket;
	private IhmClient ihm;
	private String addrIp;

	public ThreadClientMulticast(MulticastSocket ms, IhmClient ihm, String addr) {
		this.multicastSocket = ms;
		this.ihm = ihm;
		this.addrIp = addr;
	}

	public void run() {

		while (true) {
			byte[] buffer = new byte[1000];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			try {
				multicastSocket.receive(datagramPacket);
				String message = new String(buffer, 0, datagramPacket.getLength());
				ihm.onReceiveMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}