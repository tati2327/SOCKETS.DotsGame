package NewSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements Runnable {
	
	Socket client;
	int port=8080;
	String ip = "192.168.100.4";
	String messageReceived;
	Thread hilo;
	BufferedReader output;
	PrintStream input;
	
	public Client() throws IOException, InterruptedException {
		hilo = new Thread(this);
		hilo.start();
		
		while (true){
			Thread.sleep(2000);
			if (messageReceived.equalsIgnoreCase("Adios") ){
				System.out.println("Conexion terminada por el cliente");
				break;
			}
			messageReceived = output.readLine();
			System.out.print("Servidor: "+messageReceived+"\n");
		}
	}
	
	public static void main(String args[]) throws Exception{
		new Client();
	}
	
	public void run() {
		try{
			client = new Socket(ip,port);
			output=new BufferedReader(new InputStreamReader(client.getInputStream()));
			input=new PrintStream(client.getOutputStream());
			BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Cliente: ");
			messageReceived=stdin.readLine();
			input.println(messageReceived);
			
			client.close();
			output.close();
			input.close();
			stdin.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
