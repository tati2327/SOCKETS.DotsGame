package SocketClient;
import SocketServer.Server;

//Librerías que permiten establecer las conexiones 
import java.net.*;
import java.io.*;

public class Cliente extends Server{
		
		Socket client;
		String ip = "172.18.221.138";
		PrintStream output; //Enviar variables de texto
		BufferedReader keyboard; //Se utiliza para mandar un mensaje, como un oequeño telnet
		
		public void startClient() {
			
			//Se crea un try y un catch porque generalmente el programa genera errores
			try {	
				client = new Socket(ip, port); //En esa linea se hace la conexion
				
				//Canales de entrada y salida de datos
				input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				keyboard = new BufferedReader(new InputStreamReader(System.in));
				
				//Enviar datos
				String key = keyboard.readLine(); //Recibe un texto desde el teclado 
				output = new PrintStream(client.getOutputStream());
				output.println(key);
				
				//Recibir datos
				messageReceived = input.readLine();
				System.out.println(messageReceived);
				
				input.close();
				output.close();
				keyboard.close();
				client.close();
				
				} catch (Exception e) {
				System.out.println("Error: "+e.getMessage());
			}
		}
	}
}
