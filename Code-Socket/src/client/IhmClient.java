package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class IhmClient extends JFrame {

	private String name;
	JButton buttonSendMessage;
	JTextArea textAreaReceive;
	JTextArea textAreaSend;
	JScrollPane scroll;

	JLabel labelIp;
	JLabel labelPort;
	JLabel labelPseudo;

	JTextField textFieldIp;
	JTextField textFieldPort;
	JTextField textFieldName;

	JButton buttonConnection;
	JButton buttonDeconnection;
	private EchoClient echoClient;

	public IhmClient(EchoClient echoClient) {
		this.echoClient = echoClient;

		this.setSize(800, 520);
		this.setLayout(null);

		labelIp = new JLabel("Ip serveur");
		labelIp.setSize(60, 30);
		labelIp.setLocation(35, 25);

		labelPort = new JLabel("Port");
		labelPort.setSize(25, 30);
		labelPort.setLocation(205, 25);

		labelPseudo = new JLabel("Pseudo");
		labelPseudo.setSize(50, 30);
		labelPseudo.setLocation(320, 25);

		textFieldIp = new JTextField();
		textFieldIp.setSize(80, 30);
		textFieldIp.setLocation(100, 25);
		textFieldIp.setToolTipText("Ip serveur");

		textFieldPort = new JTextField();
		textFieldPort.setSize(50, 30);
		textFieldPort.setLocation(240, 25);
		textFieldPort.setToolTipText("Port");

		textFieldName = new JTextField();
		textFieldName.setSize(100, 30);
		textFieldName.setLocation(375, 25);
		textFieldName.setToolTipText("Votre pseudo");

		buttonConnection = new JButton("Connecter");
		buttonConnection.setSize(100, 30);
		buttonConnection.setLocation(500, 25);
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

		buttonDeconnection = new JButton("Déconnecter");
		buttonDeconnection.setSize(110, 30);
		buttonDeconnection.setLocation(625, 25);
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
					buttonDeconnection.setEnabled(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonDeconnection.setEnabled(false);

		textAreaReceive = new JTextArea();
		textAreaReceive.setSize(500, 200);
		textAreaReceive.setLocation(75, 100);
		textAreaReceive.setLineWrap(true);
		textAreaReceive.setWrapStyleWord(true);
		textAreaReceive.setEditable(false);

		textAreaSend = new JTextArea();
		textAreaSend.setSize(500, 50);
		textAreaSend.setLocation(75, 320);
		textAreaSend.setEnabled(false);

		scroll = new JScrollPane(textAreaReceive);
		scroll.setSize(500, 200);
		scroll.setLocation(75, 100);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		buttonSendMessage = new JButton("Envoyer");
		buttonSendMessage.setSize(100, 50);
		buttonSendMessage.setLocation(600, 320);
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
		this.add(labelIp);
		this.add(labelPort);
		this.add(labelPseudo);
		this.setVisible(true);
	}

	public void onReceiveMessage(String message) {
		textAreaReceive.append(message + "\n");
	}
}