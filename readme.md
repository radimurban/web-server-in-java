# Building a simple HTTP multi-threaded web server in Java
**Disclaimer:** At the beginning of every file in the [GitHub Repo of this project](https://github.com/radimurban/web-server-in-java), there is a comment on if the code is mine. At some points I was strongly inspired by others who did this already.
## How to run and configure
Please to run this server, run the main method in application package in the GUI class. You will get a window with button to start the server. To configure go to **src/main/resources** and configure the **port** or **webroot** in **configuration.json**.

## Task and Goal
The goal is to learn how to implement and actually implement a very simple version of **multi-threaded** web server in Java.

## Resources and readings
I have never done this before, so I am listing the following links which I used to learn about everything I needed to know to make this work.
- [The HTTP protocol](https://www.ibm.com/docs/en/cics-ts/5.2?topic=concepts-http-protocol)
- [HTTP Request](https://www.ibm.com/docs/en/cics-ts/5.2?topic=protocol-http-requests) 
- [HTTP Server Documentation](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpsServer.html)
- [HTTP Protocol](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3)
- https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server
- [Create a simple HTTP Web Server in Java](https://ssaurel.medium.com/create-a-simple-http-web-server-in-java-3fc12b29d5fd)
- [Build your own HTTP server in Java in less than one hour](https://dev.to/mateuszjarzyna/build-your-own-http-server-in-java-in-less-than-one-hour-only-get-method-2k02)
- [Building a simple web server in Java [YT]](https://www.youtube.com/watch?v=FqufxoA4m70)
- [On Parsing JSON files in Java](https://www.geeksforgeeks.org/how-to-setup-jackson-in-java-application/) 
- [Make a Simple HTTP Server in Java [YT]](https://www.youtube.com/watch?v=FNUdLeGfShU)

## How does an HTTP server work?
A server is a computer. It needs to be connected to the network to be able to receive the requests. It will be listening through ports (for example 80 for not encrypted and 443 for encrypted traffic). Usual requests are files or something in the file system (web root). The server will look for the file(s) that match the request and sends a response back to the browser through the established connection. After the browser receives the response, the server closes the connection. There is therefore a new connection established for every single request coming from the browser. We can now sum up what we need to do.

## What needs to be implemented
Server must be able to:
1. listen to a *configurable* port and therefore to read the configuration files (JSON) and how to write them
2. establish and handle multiple connection(s) (multi-threaded) between the browser and the server
3. parse and understand requests messages coming from browser
4. compose the response based on the request

## Implementing and handling the configurations
For keeping the configurations in one place, we will use JSON.

We need to configure: 
1. `port` (int): so far one, we can configure more later, our server will listen to this port
2. `webroot` (String): pathway of where the web files are saved

```JSON
{
	"port": 8080,
	"webroot": ""
}
```

We will now write two classes. One class for handling the configurations and one for its representation (a simple *setter* and *getter* of the `port` and `webroot`) Both will be in the package of the httpServer.

The actual configuration class is trivial and will look like this: 
 ```java
public class Configuration {
	
	private int port;
	private String webroot;
	
	public int getPort(){
		return this.port;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public String getWebroot(){
		return this.webroot;
	}
	
	public void setWebroot(String webroot){
		this.webroot = webroot;
	}
}

 ```
 This where we retrieve the configuration data from. It will basically be an object containing the configuration data.
 
 Now we need to write the configuration handler. Our handler needs to be able to (those will be separate functions):
 - load the configuration file
 - get the current configuration (and return it obviously)
 
```java
public class ConfigurationHandler{
	
	private Configuration config; //the config that will be loaded
	//default constructor
	
	//this method might throw exceptions (wrong path, permission problem and so on)
	public void loadConfig(String path){
	}

	//only if config is loaded, otherwise throws an exception
	public void getCurrentConfig() {
	}
	
}
```

Now we need to figure out a way how to parse the JSON file and create the Configuration object . This is a pretty complicated step (at least if you're doing this for the first time).

### Parsing JSON in Java
The entire project will use [Maven](https://maven.apache.org/) for easily keeping dependencies in order and for the actual parsing of the JSON file the *Jackson library* which can be used for parsing and generating JSON files. It has an Object Mapper class that can process JSON files and create Java objects out of them, which is exactly what we need.

In order to use the Jackson library, we need to define the dependencies.
#### Defining the dependencies
The pom.xml file look like this:
```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" 
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

	<modelVersion>4.0.0</modelVersion>
	
	<?Following tags will be prefilled by the template of the Maven project?>
	<groupId></groupId> 
	<artifactId></artifactId>
	<version>1.0.0-SNAPSHOT</version>
	
	<dependencies>
		<?We need to add Jackson core and databind?>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.9.9</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.9.9.3</version>
		</dependency>
	</dependencies>

```
#### Java class handling the JSON files
In this class, that we will make use of the `ObjectMapper()`  which we will import from the jackson.databind. 

There are two ways to get/use the ObjectMapper. You can either just use the default one or configure your own. If you dont want to configure it at all, you can just use `public static ObjectMapper objectMapper = new ObjectMapper();`. Otherwise create the function that returns the ObjectMapper and configure it within the function before you return it.

Another part of the Jackson library is the JsonNode. This class represents the structure of the original JSON file. We will first want to transform/map the JSON into this class (object). We define the function `getJsonNode()` to get this JsonNode representation. It will take in one argument - the string representation of the JSON file.
```java
//part of the package

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler{
	
	private static ObjectMapper objectMapper = new ObjectMapper(); //this uses the default ObjectMapper()
	
	public static JsonNode getJsonNode(String jsonInString) throws IOException{
		return objectMapper.readTree(jsonInString);
	}
}
```
Using this if we for example take our JSON configuration file to test the output of this `getJsonNode()` function. That is, for the following JSON:
```JSON
{
	"port": 8080,
	"webroot": "path"
}
```
We have string representation "{"port":8080, "webroot":"web"}" and the output of the function `getJsonNode()` is an JsonNode object on which we can for example call:
```java
//import previous class JsonHandler and JsonNode 
String jsonInString = "{\"port\":8080, \"webroot\":\"path\"}"
JsonNode myJson = Json.getJsonNode(jsonInString);

myJson.get("webroot").asText(); //returns "path"
```
At this point we need to move the JsonNode object into the Configuration object/class. For that we need to define another function in the JsonHandler class.
We can do this using the ObjectMapper and its `treeToValue(JsonNode, Class<T>)`. This function returns the class we want the JsonNode to transform into (we pass this as a second argument).
At the same time we can define a function that is the inverse to JsonToClass. This function will accept an object as an argument ans will return the JsonNode object.
Another thing that can be useful is to get a way to get the Configuration as a string, which is basically a JSON.
```java
//part of the package

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler{
	
	private static ObjectMapper objectMapper = new ObjectMapper(); //this uses the default ObjectMapper() config
	
	public static JsonNode getJsonNode(String jsonInString) throws IOException{
		return objectMapper.readTree(jsonInString);
	}
	
	public static <T> T JsonToClass(JsonNode node, Class<T> newClass) throws JsonProcessingException {
		return objectMapper.treeToValue(node, newClass);
	}
	
	public static JsonNode ClassToJson(Object object){
		return objectMapeer.valueToTree(object);
	 }
	
	public static JsonNodeToString (JsonNode json) throws JsonProcessingException {
		return objectMapper.writer().writeValueAsString(josn);
	}
}
```
Now we have everything we need to process and to work with the JSON files.
### Configuration Handler 
We can now proceed to implement the methods of the ConfigurationHandler class.

-  `loadconfig()` : we need to find the file at given path and read it completely. Good way to save its content could the [StringBuffer](https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html). Let's just simply iterate over the content of the file and use [`read()`](https://docs.oracle.com/javase/7/docs/api/java/io/InputStreamReader.html#read()) to read its content. `read()` will return $-1$ as soon as we reach the end of the file.
	After that, we can make use of the function `getJsonNode()` we defined in the previous part to get the JsonNode representation.
	
- `getCurrentConfig()`: This is just a simple method that will return the current configuration. (Mind the `NullPointerException()` ). A possible improvement here could working out the exceptions in a better way.

```java
import com.fasterxml.jackson.databind.JsonNode;
import JsonHandler; //correct path necessary

protected class ConfigurationHandler{
	
	private Configuration config; //the config that will be loaded
	//default constructor
	
	
	//this method might throw exceptions (wrong path, permission problem and so on)
	protected void loadConfig(String path) throws FileNotFoundException{
		FileReader fileReader = new FileReader(path);
		StringBuffer content = new Stringbuffer();
		
		while ( (int i = fileReader.read()) != -1) {
			char toAppend = (char) i;
			content.append(toAppend);
		}
		String stringJson = content.toString();
		JsonNode JsonNodeConfig = JsonHandler.getJsonNode(stringJson);
		this.config = JsonHandler.JsonToClass(JsonNodeConfig, Configuration.class);
	}
	
	//only if config is loaded, otherwise throws an exception
	protected Configuration getCurrentConfig() {
		if (this.config != null){
			return this.config;
		}
		throw new NullPointerException();
	}
}
```
This should be everything we need to be able to work with configurations. Now we just need to import it into the main HttpServer class and instantiate and we can use it.
```java
//all part of the same httpServer package
import ConfigurationHandler; //add correct path
import Configuration; //add correct path

public class HttpServer {
	
	public static void main(String[] args){
		//Server starts
		
		ConfigurationHandler configHandler = new ConfigurationHandler();
		configHandler.loadConfig("pathToTheJSON");
		
		Configuration config = configHanler.getCurrentConfig();
		
	}
}
```
Assuming the previous JSON Configuration this `config` would return 8080 on `congif.getPort()`  and "web" on `config.getWebrot()`. That means we now have a Configuration data in the HttpServer class. Let's move onto establishing connections.
## Establishing a connection and using (server) sockets
To establish the connection, we need to have a socket that is going to listen to a specific port. Java has a [net library](https://docs.oracle.com/javase/7/docs/api/index.html) which provides classes for networking applications. One of them is the *Server Socket*. A server socket waits for requests to come in over the network. It performs some operation based on that request, and then possibly returns a result to the requester. To create a server socket *bounded to a specific port*, we construct the object and pass the port number as a parameter. We can easily retrieve this from our Configuration object.
```java
int port = config.getPort();
ServerSocket myServerSocket = ServerSocket(port);
```
Now we can say we want the socket to wait for the connection to be established. The function `accept()` listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made. Once a connection is made, a Socket is returned. A socket is an endpoint for communication between two machines.
Socket has two methods we will want to use:
- `getInputStream()`: returns an *input stream* for reading bytes from this socket. Based on the *input stream* we will decide what to return as a response to the browser.
- `getOutputStream()`: returns an *output stream* for writing bytes to this socket. Using the *output stream* (and its method `write()`) we will be able to send the response to the browser
```java
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
/*
 * previous HttpServer class code..
 */
Socket socket = myServerSocket.accept();
InputStream inputStream = socket.getInputStream();
OutputStream outputStream = socket.getOuputStream();

/*
 * Here, we will evaluate the response and formulate an appropriate repsone
 */

inputStream.close();
outputStream.close();
socket.close();
myServerSocket.close();
```
Now that we have basically all we need for single-threaded web server - let's make it work in parallel such that multiple connections and requests can handled independently.
## Multi-threaded accepting of connections and handling multiple requests
Let's now create a different package `tools` where we will handle connections in multi-threaded scenario.
I will create two classes (both extending thread (and therefore implementing Runnable). 
-`Acceptor`: In this class I will basically create threads and wait for the connection to be made by a request coming from a browser. As soon as I establish connection and receive the `socket`, I am gonna start a new thread of an `Executor` Class.
-`Executor`: In this class I will handle the requests (understand them) and build the appropriate response. Once a response is build we can fire it back to the browser using `OutputStream` as explained above.

### Building multiple connections using `Acceptor`
`Acceptor` will look like this:
The constructor will accept the port and the web-root. As already explained we overrun the method `run()`, which is basically a main method for this thread started by `start()`. This method will wait for a connection and once it is made it will start a new thread `Executor`.  There might be some IOE, so we need to wrap it into try/catch code.
```java
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

```
Let's now build the executor.
### Handling the requests and building the `Executor`
Based on what we get through the `inputStream` we are able understand what the browser requests and form an appropriate response. As mentioned earlier, we will assume that the request is an path to a certain file.

An [HTTP Request](https://www.ibm.com/docs/en/cics-ts/5.2?topic=protocol-http-requests) consists of request line, headers and message body. But I will only have enough time to implement basic requuest line requests (not taking headers and so on into consideration):
1.  **A request line**: this is the first line, it consist of three attributes
	- Method (for example GET)
	- path of the URL 
	- an HTTP version number

We now override the `run()` method because we extend the thread class. We can parse the `InputStream` and read all three properties of a request line:

```java
      

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
System.**_out_**.println("[E "+this.getId()+"] NEW REQUEST: "+requestLine);
String[] requestLineComponents = requestLine.split(" ");
//COMPONENTS OF THE REQUEST LINE --------- /

//extract method, our server will only support GET and HEADER
String method = requestLineComponents[0];
String url = this.webroot+requestLineComponents[1];
web = loadFile(url);
String httpVersion = requestLineComponents[2];
```

A typical browser request looks similar to the following example:

```txt

GET / HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Cache-Control: max-age=0
sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="99", "Google Chrome";v="99"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
Sec-Fetch-Site: none
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Sec-Fetch-Dest: document
Accept-Encoding: gzip, deflate, br
Accept-Language: cs
```

Out of that we will only take the first line. That is the **request line**:

```txt
GET / HTTP/1.1
```

Now we have that (mind `method`="GET", `url`="/", `httpVersion`="HTTP/1.1").

### Responses
Response in general consists of `statusLine`, headers, and the message body(=`web`). These parameters must separated by a `SEPARATOR` which is by convention `\r\n`. That will be true for all reponses in general.

We will do the absolute minimum and consider GET and HEADER methods. Let's therefore check if the request uses one of these and if not we will return a 501 Not Implemented error. As soon as I identify an error I will compose an error response.

### 501 Not Implemented

```java
if (!(method.equals("GET") || method.equals("HEADER"))) {
	statusLine = "HTTP/1.1 501 Not Implemented";
	web = loadFile("web/errors/501.html");
	response = statusLine 
		+ SEPARATOR 
		+ "Content-Length:" + web.getBytes().length 
		+ SEPARATOR + SEPARAToR 
		+ web 
		+ SEPARATOR + SEPARATOR;
	//send the response
	outputStream.write(response.getBytes());
	//we already executed response
	executed = true;
}
```

### 404 File Not Found
Let's now check if we have the file the request is asking for. For that let's create a new method `loadFile` which will parse the file into STring or return "404" if such file is not in our webroot.

```java
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
```

Now let's check the url as well as the httpVersion in the `run` method.

```java
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
```
### 505 HTTP Version Not Supported
```java
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
		executed = true
	}
}
```
### Response with no errors
If nothing failed, we obviously send the correct one.
```java
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
```

## Graphical UI
Last thing (and probably least) I will be able to do is the graphical interface to start the server.
I will just create a class GUI implementing the `ActionListener` and create a main method. 

Since this a trivial code, I will just paste the code and the result.

```java
      
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
	private static JButton _startButton_;
	private static JFrame _window_;
	private static JPanel _panel_;
	private static JLabel _status_;
	private static JLabel _header_;
	private static boolean _serverRunning_ = false;
	public static void main(String[] args) {
		_panel_ = new JPanel();
		_window_ = new JFrame();
		_window_.setSize(550, 120);
		_window_.setDefaultCloseOperation(JFrame.**_EXIT_ON_CLOSE_**);
		_window_.add(_panel_);
		_panel_.setLayout(null);
		_startButton_ = new JButton("Start the Server");
		_panel_.add(_startButton_);
		_startButton_.setBounds(10,25,150,50);
		_startButton_.addActionListener(new GUI());
		_startButton_.setCursor(new Cursor(Cursor.**_HAND_CURSOR_**));
		_status_ = new JLabel("Server not running...");
		_status_.setForeground(Color.**_red_**);
		_status_.setBounds(180,25,520,50);
		_header_ = new JLabel("HTTP/1.1 Web Server [Radim Urban]");
		_header_.setBounds(15,5,400,15);
		_panel_.add(_header_);
		_panel_.add(_status_);
		_window_.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (!_serverRunning_) {
			_startButton_.setText("Stop server");
			_status_.setForeground(Color.**_BLUE_**);
			HttpServer._run_();
			_status_.setText("Server running on port "+HttpServer._port_+" and on webroot \""+HttpServer._webroot_+"\"...");
			_serverRunning_ = !_serverRunning_;
		} else if (_serverRunning_) {
			_startButton_.setText("Start server");
			_status_.setText("Server stopped...");
			_status_.setForeground(Color.**_red_**);
			_serverRunning_ = !_serverRunning_;
			HttpServer._stop_();
		}
	}
}
```

This is all I can manage to do till my deadline. The actual server is working and can be tested on localhost as described at the very beginning.

### Window before starting the server

<img width="1481" alt="image" src="https://user-images.githubusercontent.com/78273894/160201249-f0a4b75a-6ae4-4c36-b5bc-6a01dae1e131.png">

### Window after starting the server

<img width="1481" alt="image" src="https://user-images.githubusercontent.com/78273894/160201291-a989d88e-db4e-4280-8aa0-bd4e6853687d.png">

## Potential To-Do's
- images
- working with headers
- automatic /.index recognition
-

