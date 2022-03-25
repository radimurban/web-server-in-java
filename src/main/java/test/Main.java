package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String path = "web/errors/505.html";
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
			System.out.println(content.toString());
		} catch (FileNotFoundException e) {
			System.out.println("<!DOCTYPE html><html><head><title>404 Not Found</title></head><body><h1>404 File Not Found</h1><h2>This File Was Not Found On This Server</h2></body></html>");
		}
	}
}
