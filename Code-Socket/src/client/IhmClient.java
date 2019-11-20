package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IhmClient extends JFrame {

	private String name;
	JButton buttonSendMessage;
	JTextArea textAreaReceive;
	JTextArea textAreaSend;
	JScrollPane scroll;

	JTextField textFieldIp;
	JTextField textFieldPort;
	JTextField textFieldName;

	JButton buttonConnection;
	JButton buttonDeconnection;
	private EchoClient echoClient;

	public IhmClient(EchoClient echoClient) {
		this.echoClient = echoClient;

		this.setSize(1300, 800);
		this.setLayout(null);

		textFieldIp = new JTextField();
		textFieldIp.setSize(150, 50);
		textFieldIp.setLocation(25, 25);
		textFieldIp.setToolTipText("Ip serveur");

		textFieldPort = new JTextField();
		textFieldPort.setSize(100, 50);
		textFieldPort.setLocation(300, 25);
		textFieldPort.setToolTipText("Port");

		textFieldName = new JTextField();
		textFieldName.setSize(150, 50);
		textFieldName.setLocation(550, 25);
		textFieldName.setToolTipText("Votre nom");

		buttonConnection = new JButton("Connexion");
		buttonConnection.setSize(150, 50);
		buttonConnection.setLocation(800, 25);
		buttonConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip = textFieldIp.getText();
				int port = Integer.parseInt(textFieldPort.getText());
				String name = textFieldName.getText();
				IhmClient.this.name = name;
				try {
					echoClient.connect(ip, port, name);
					System.out.println(echoClient.getClient().getIsAlive());
					buttonConnection.setEnabled(false);
					textFieldIp.setEnabled(false);
					textFieldPort.setEnabled(false);
					textFieldName.setEnabled(false);
					textAreaSend.setEnabled(true);
					buttonSendMessage.setEnabled(true);
					buttonDeconnection.setEnabled(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		buttonDeconnection = new JButton("Déconnexion");
		buttonDeconnection.setSize(150, 50);
		buttonDeconnection.setLocation(1000, 25);
		buttonDeconnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					echoClient.disconnect(IhmClient.this.name);
					// System.out.println(echoClient.getClient().getIsAlive());
					textAreaReceive.setText("");
					buttonConnection.setEnabled(true);
					textFieldIp.setEnabled(true);
					textFieldPort.setEnabled(true);
					textFieldName.setEnabled(true);
					textAreaSend.setEnabled(false);
					buttonSendMessage.setEnabled(false);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonDeconnection.setEnabled(false);

		textAreaSend = new JTextArea();
		textAreaSend.setSize(400, 200);
		textAreaSend.setLocation(225, 100);
		textAreaSend.setEnabled(false);

		textAreaReceive = new JTextArea();
		textAreaReceive.setSize(400, 200);
		textAreaReceive.setLocation(125, 600);
		textAreaReceive.setLineWrap(true);
		textAreaReceive.setWrapStyleWord(true);
		textAreaReceive.setEditable(false);

		scroll = new JScrollPane(textAreaReceive);
		scroll.setSize(400, 200);
		scroll.setLocation(125, 600);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		buttonSendMessage = new JButton("Envoyer Message");
		buttonSendMessage.setFont(new Font("Arial", 0, 30));
		buttonSendMessage.setSize(300, 50);
		buttonSendMessage.setLocation(850, 500);
		buttonSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textAreaSend.getText();
				try {
					echoClient.sendMessage(IhmClient.this.name + ": " + message);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				textAreaSend.setText("");
			}
		});
		buttonSendMessage.setEnabled(false);

		this.add(textAreaSend);
		this.add(buttonSendMessage);
		this.add(scroll);
		this.add(textFieldIp);
		this.add(textFieldPort);
		this.add(textFieldName);
		this.add(buttonConnection);
		this.add(buttonDeconnection);

		this.setVisible(true);
	}

	public void onReceiveMessage(String message) {
		textAreaReceive.append(message + "\n");
	}
}