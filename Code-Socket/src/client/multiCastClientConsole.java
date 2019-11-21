package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class multiCastClientConsole {
	private static String groupHost = "224.0.0.75";
	private static int port = 1234;
	private MulticastSocket multicastSocket;
	private DatagramSocket datagramSocket;

	public multiCastClientConsole() {
	}

	public void init() {
		try {
			multicastSocket = new MulticastSocket(port);
			InetAddress inetAddress = InetAddress.getByName(groupHost); // 组地址
			multicastSocket.joinGroup(inetAddress); // 加入到组播组中
			datagramSocket = new DatagramSocket(); // DatagramSocket实例
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		receives();
		send();
	}

	public void receives() {
		new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						byte[] buf = new byte[128]; // 接收数据缓冲
						DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // 接收数据的数据报
						multicastSocket.receive(datagramPacket); // 接收数据
						System.out.println(new String(buf, "UTF-8")); // 输出接收到的数据
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	public void send(String message) {
		try {
			byte[] buf = message.getBytes("UTF-8"); // 发送信息
			InetAddress inetAddress = InetAddress.getByName(groupHost); // 组播地址
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, port); // 发送数据报
			datagramSocket.send(datagramPacket); // 发送数据
			datagramSocket.close(); // 关闭端口
		} catch (Exception e) {

		}
	}

	public void send() {
		new Thread() {

			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				while (true) {
					try {
						String message = s.nextLine();
						byte[] buf = message.getBytes("UTF-8"); // 发送信息
						InetAddress inetAddress = InetAddress.getByName(groupHost); // 组播地址
						DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, port); // 发送数据报
						datagramSocket.send(datagramPacket); // 发送数据
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}

	public void close() {
		try {
			if (null != multicastSocket) {
				multicastSocket.close();
			}
			if (null != datagramSocket) {
				datagramSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		multiCastClientConsole m = new multiCastClientConsole();
		m.init();
		m.start();
	}
}
