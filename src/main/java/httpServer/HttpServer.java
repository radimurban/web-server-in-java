//written by me and partially inspired
package httpServer;

import java.io.FileNotFoundException;
import java.io.IOException;

import configuration.Configuration;
import configuration.ConfigurationHandler;
import tools.Acceptor;

public class HttpServer {
	
	private static Acceptor acc;
	public static int port;
	public static String webroot;
	
	public static void run() {
		
		//load the handler
		ConfigurationHandler configHandler = new ConfigurationHandler();
		//load the configuration from the JSON file
		try {
			configHandler.loadConfig("src/main/resources/configuration.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// get the Configuration object with port and webroot
		Configuration configuration = configHandler.getCurrentConfig();
		
		/*
		 * You can test the config using these printouts
		 * System.out.println(configuration.getWebroot());
		 * System.out.println(configuration.getPort());
		 * 
		 * */
		
		port = configuration.getPort();
		webroot = configuration.getWebroot(); 
		
		
		try {
			acc = new Acceptor(port, webroot);
			acc.start();
			System.out.println("Server starts...");
		} catch (IOException e) {
			//server could not start for some reason
			e.printStackTrace();
		}
		 
		
		
		
		
	}
	
	public static void stop() {
		System.exit(0);
		//TODO interrupt all processes, so far system.exit
	}

}
