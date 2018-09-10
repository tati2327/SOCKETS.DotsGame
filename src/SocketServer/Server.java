package SocketServer;

//Librerías que permiten establecer las conexiones 
import java.net.*;
import java.io.*;

public class Server {
	
	public int port = 5555; //variable para el puerto
	ServerSocket server; //este será nuestro servidor
	Socket socket;
	DataOutputStream output; //variable para que los datos se envian
	protected String messageReceived; //variable para los datos que se reciben
	protected BufferedReader input; //Aqui guardamos los datos recibidos del exterior

	public void startServer() {
		
		//Se crea un try y un catch porque generalmente el programa genera errores
		try {	
			server = new ServerSocket(port); //Puerto 5555
			socket = new Socket();
			System.out.println("Esperando una conexion");
			socket = server.accept(); //Esta esperando una conexion por parte de un cliente
			System.out.println("Un cliente se ha conectado");
			
			//Canales de entrada y salida de datos
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Confirmando conexion con el cliente");
			//writeUTF envía cadenas de texto
			output.writeUTF("Conexion exitosa...");
			
			//Recepción de mensaje
			messageReceived = input.readLine();
			System.out.println(messageReceived);
			
			output.writeUTF("Se recibio un mensaje. Terminando conexion...");
			output.writeUTF("Gracias por conectarte. Adios");
			System.out.println("Cerrando conexion...");
			
			//server.close(); //Aqui se cierra la conexión con el cliente
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
}