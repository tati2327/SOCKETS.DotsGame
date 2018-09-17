package NewSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

	int port;
	ServerSocket server=null;
	Socket socket=null;
	//ExecutorService proporciona automáticamente un conjunto de hilos y API para asignarle tareas.
	ExecutorService pool=null; 
	int clientCount=0;

	public static void main(String[] args) throws IOException {
		//Se crea una instancia de un servidor para iniciar el programa
		Server server = new Server(8080);
		server.startServer();
	}

	/**Constructor
	 * @param port
	 */
	public Server(int port){
		this.port=port;
		//Se crea un grupo de subprocesos con n subprocesos
		pool = Executors.newFixedThreadPool(5);
	}

	public void startServer() throws IOException {
		server = new ServerSocket(8080);
		System.out.println("Servidor esperando...");
		System.out.println("Cualquier cliente puede detener el servidor enviando -1");
		while(true){
			socket = server.accept();
			clientCount++;
			ServerThread runnable= new ServerThread(socket, clientCount, this);
			pool.execute(runnable);
		}
	}

	//Clase con los hilos
	private static class ServerThread implements Runnable {

		Server server;
		Socket socket;
		BufferedReader input; //Almacena los datos de entrada como strings
		PrintStream output; //agrega funcionalidad a otro flujo de salida
		Scanner scanner = new Scanner(System.in); //Un escáner divide su entrada en tokens usando un patrón de delimitador
		int id;
		String messageReceived;

		ServerThread(Socket socket, int count, Server server) throws IOException {

			this.socket = socket;
			this.server = server;
			this.id = count;
			System.out.println("Conexion "+id+" establecida con el cliente "+socket);

			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output=new PrintStream(socket.getOutputStream());
		}

		@Override
		public void run() {
			int x=1;
			try{
				while(true){
					messageReceived = input.readLine();
					System. out.print("Cliente("+id+") :"+messageReceived+"\n");
					System.out.print("Servidor : ");
					messageReceived = scanner.nextLine();
					
					if (messageReceived.equalsIgnoreCase("Adios")){
						output.println("Adios");
						x=0;
						System.out.println("Conexion terminada por el servidor");
						break;
					}
					output.println(messageReceived);
				}    
				input.close();
				socket.close();
				output.close();
				if(x==0) {
					System.out.println( "Servidor limpiado" );
					System.exit(0);
				}
			} 
			catch(IOException ex){
				System.out.println("Error : "+ex);
			}
		}
	}
}
