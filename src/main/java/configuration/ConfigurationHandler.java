//heavily inspired
package configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigurationHandler {
	//default constructor
	
	private Configuration config; //the config that will be loaded
	
	
	//this method might throw exceptions (wrong path, permission problem and so on)
	public void loadConfig(String path) throws FileNotFoundException{
		
		//load the file, may raise FileNotFoundException
		FileReader fileReader = new FileReader(path);
		//initialize StringBuffer to which we'll add char after char till we reach end of the file
		StringBuffer content = new StringBuffer();
		//indicator will save the ascii representation if next char is there, if not -1 is returned
		int indicator;
		try {
			while ( (indicator = fileReader.read()) != -1) {
				char toAppend = (char) indicator;
				content.append(toAppend);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//convert to string
		String stringJson = content.toString();
		//initialize JsonNode object
		JsonNode JsonNodeConfig = null;
		//get JsonNode representation
		try {
			JsonNodeConfig = JsonHandler.getJsonNode(stringJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//create a Configuration object using the jackson methods in JsonHandler
		try {
			this.config = JsonHandler.JsonToClass(JsonNodeConfig, Configuration.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	//only if config is loaded, otherwise throws an exception
	public Configuration getCurrentConfig() {
		if (this.config != null){
			return this.config;
		}
		throw new NullPointerException();
	}

}
