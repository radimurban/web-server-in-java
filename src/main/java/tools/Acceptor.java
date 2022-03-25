//written by me and partially inspired
package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor extends Thread{
	
	private int port;
	private String webroot;
	private ServerSocket serverSocket;
	
	public Acceptor(int port, String webroot) throws IOException{
		this.port = port;
		this.webroot = webroot;
		this.serverSocket = new ServerSocket(this.port);
	}
	
	@Override
	public void run() {
		try {
			
			while (this.serverSocket.isBound() && !this.serverSocket.isClosed()) {
				System.out.println("[A: "+this.getId()+"] "+"Waiting for connection...");
				//wait until the server socket receives a request (accept() waits, exe stops here)
				Socket socket = this.serverSocket.accept();
				System.out.println("[A: "+this.getId()+"] "+"Connection accepted...");
				Executor exe = new Executor(socket, webroot);
				exe.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.serverSocket.close();
				System.out.println("[A: "+this.getId()+"] "+"Finished...");
			} catch (IOException e) {}
		}
	}

}
