//heavily inspired
package configuration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {
	
private static ObjectMapper objectMapper = new ObjectMapper(); //this uses the default ObjectMapper() config
	
	public static JsonNode getJsonNode(String jsonInString) throws IOException{
		return objectMapper.readTree(jsonInString);
	}
	
	public static <T> T JsonToClass(JsonNode node, Class<T> newClass) throws JsonProcessingException {
		return objectMapper.treeToValue(node, newClass);
	}
	
	public static JsonNode ClassToJson(Object object){
		return objectMapper.valueToTree(object);
	 }
	
	public static String JsonNodeToString (JsonNode json) throws JsonProcessingException {
		return objectMapper.writer().writeValueAsString(json);
	}
}
