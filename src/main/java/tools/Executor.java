//written by me completely
package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Executor extends Thread {
	private Socket socket;
	private int port;
	private String webroot;
	
	
	public Executor(Socket socket, String webroot) {
		this.socket = socket;
		this.webroot = webroot;
	}

	
	@Override 
	public void run() {
		
		System.out.println("[E: "+this.getId()+"] "+"Got connection running... \n");
		//streams
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			//get input and prepare output
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			//RESPOND VARIABLES (OUT OF THE WILL BE FORMED
			String statusLine;
			final String SEPARATOR = "\r\n";
			String web;
			String response;
			boolean executed = false;
			
			//READ the Request
			int currentByte = inputStream.read();
			StringBuffer request = new StringBuffer();
			int rest = 4;
			while (rest != 0) {
				request.append((char) currentByte);
				currentByte = inputStream.read();
				if (request.toString().contains("Accept-Language:")) {
					rest--;
				}
			}
			//request in string
			String sRequest = request.toString();
			Scanner scanner = new Scanner(sRequest.toString());
			//EXTRACT REQUEST LINE
			String requestLine = scanner.nextLine();
			System.out.println("[E "+this.getId()+"] NEW REQUEST: "+requestLine);
			String[] requestLineComponents = requestLine.split(" ");
			
			//COMPONENTS OF THE REQUEST LINE --------- /
			//extract method, our server will only support GET and HEADER
			String method = requestLineComponents[0];
			String url = this.webroot+requestLineComponents[1];
			web = loadFile(url);
			String httpVersion = requestLineComponents[2];
			//if method is different from supported, raise 501 Not Implemented
			
			if (!(method.equals("GET") || method.equals("HEADER"))) {
				statusLine = "HTTP/1.1 501 Not Implemented";
				web = loadFile("web/errors/501.html");
				response = statusLine 
						+ SEPARATOR 
						+ "Content-Length:" + web.getBytes().length 
						+ SEPARATOR + SEPARATOR 
						+ web 
						+ SEPARATOR + SEPARATOR;
				//send the response
				outputStream.write(response.getBytes());
				//we already executed response
				executed = true;
			//if method was ok and response was not sent
			} 
			if (!executed) {
				// extract url from request
				url = this.webroot+requestLineComponents[1];
				
				if (!(loadFile(url+"/index.html").equals("404"))) {
					url += "/index.html";
				}
				System.out.println(url);
				
				web = loadFile(url);
				
				//if the file could not be loaded, raise 404 File Not Found
				if (web.equals("404")) {
					web = loadFile("web/errors/404.html");
					statusLine = "HTTP/1.1 404 Not Found";
					response = statusLine 
							+ SEPARATOR 
							+ "Content-Length:" + web.getBytes().length 
							+ SEPARATOR + SEPARATOR 
							+ web 
							+ SEPARATOR + SEPARATOR;
					//send response
					outputStream.write(response.getBytes());
					//response was sent
					executed = true;
				}
			//but if response was not sent
			} 
			if (!executed) {
				//exctract the HTTP version
				httpVersion = requestLineComponents[2];
				//if not the correct version
				if (!httpVersion.equals("HTTP/1.1")) {
					
					System.out.println("and HTTP Failed");
					//set status line
					statusLine = "HTTP/1.1 505 HTTP Version Not Supported";
					//set web
					web = loadFile("web/errors/505.html");
					
					//compose response
					response = statusLine 
							+ SEPARATOR 
							+ "Content-Length:" + web.getBytes().length 
							+ SEPARATOR + SEPARATOR 
							+ web 
							+ SEPARATOR + SEPARATOR;
					//send response
					outputStream.write(response.getBytes());
					//response was sent
					executed = true;
				}
			} 
			if(!executed) {
				System.out.println("Nothing failed \n");
				web = loadFile(url);
				statusLine = "HTTP/1.1 200 OK";
				response = statusLine
						+ SEPARATOR
						+ "Content-Length:" + web.getBytes().length
						+ SEPARATOR + SEPARATOR 
						+ web 
						+ SEPARATOR + SEPARATOR;
				outputStream.write(response.getBytes());
			}
			executed=false;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				socket.close();
				
				System.out.println("[E: "+this.getId()+"] "+"Executed... \n");
			} catch (IOException e) {}
		}
		
		
		
		
	}
	
	public String loadFile(String path){
		FileReader fileReader;
		try {
			fileReader = new FileReader(path);
			StringBuffer content = new StringBuffer();
			int indicator;
			try {
				while ( (indicator = fileReader.read()) != -1) {
					char toAppend = (char) indicator;
					content.append(toAppend);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			return "404";
		}
	}
}


