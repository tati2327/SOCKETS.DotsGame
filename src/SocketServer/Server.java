package SocketServer;
//Librer�as que permiten establecer las conexiones 
import java.net.*;
import java.io.*;

public class Server {
	
	public int port = 5000; //variable para el puerto
	ServerSocket server; //este ser� nuestro servidor
	Socket socket;
	DataOutputStream output; //variable para que los datos se envian
	String messageReceived; //variable para los datos que se reciben

	public void startServer() {
		
		BufferedReader input; //Aqui guardamos los datos recibidos del exterior
		
		//Se crea un try y un catch porque generalmente el programa genera errores
		try {	
			server = new ServerSocket(port); //Puerto 5000
			socket = new Socket();
			System.out.println("Esperando una conexi�n");
			socket = server.accept(); //Esta esperando una conexi�n por parte de un cliente
			System.out.println("Un cliente se ha conectado");
			
			//Canales de entrada y salida de datos
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Confirmando conexi�n con el cliente");
			//writeUTF env�a cadenas de texto
			output.writeUTF("Conexi�n exitosa...");
			
			//Recepci�n de mensaje
			messageReceived = input.readLine();
			System.out.println(messageReceived);
			
			output.writeUTF("Se recibio un mensaje. Terminando conexi�n...");
			output.writeUTF("Gracias por conectarte. Adios");
			System.out.println("Cerrando conexi�n...");
			
			server.close(); //Aqui se cierra la conexi�n con el cliente
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
}