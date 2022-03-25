//
package application;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import httpServer.HttpServer;

public class GUI implements ActionListener{
	
	private static JButton startButton;
	private static JFrame window;
	private static JPanel panel;
	private static JLabel status;
	private static JLabel header;
	private static boolean serverRunning = false;
	
	
	public static void main(String[] args) {
		
		panel = new JPanel();
		window = new JFrame();
		window.setSize(550, 120);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		panel.setLayout(null);
		startButton = new JButton("Start the Server");
		panel.add(startButton);
		startButton.setBounds(10,25,150,50);
		startButton.addActionListener(new GUI());
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		status = new JLabel("Server not running...");
		status.setForeground(Color.red);
		status.setBounds(180,25,520,50);
		header = new JLabel("HTTP/1.1 Web Server [Radim Urban]");
		header.setBounds(15,5,400,15);
		panel.add(header);
		panel.add(status);
		window.setVisible(true);
		
		
	}

	public void actionPerformed(ActionEvent e) {
		if (!serverRunning) {
			startButton.setText("Stop server");
			status.setForeground(Color.BLUE);
			HttpServer.run();
			status.setText("Server running on port "+HttpServer.port+" and on webroot \""+HttpServer.webroot+"\"...");
			serverRunning = !serverRunning;
		} else if (serverRunning) {
			startButton.setText("Start server");
			status.setText("Server stopped...");
			status.setForeground(Color.red);
			serverRunning = !serverRunning;
			HttpServer.stop();
		}
		
		
	}

}
