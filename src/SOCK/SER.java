package SOCK;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SER extends JFrame implements Runnable {
		JTextArea txtMensajes;

		public SER() {
			txtMensajes = new JTextArea();
			txtMensajes.setBounds(10, 10, 365, 340);
			add(txtMensajes);
			
			setLayout(null);
			setSize(400,400);
			setVisible(true);
			
			Thread hilo = new Thread(this);
			hilo.start();//Este metodo llama al metodo run que hayas creado
		}

		public static void main(String[] args) {
			new SER();
		}

		//Lo que se programe aqui se ejecuta en un segundo plano
		@Override
		public void run() {
			try {
				ServerSocket servidor = new ServerSocket(5555);
				Socket cliente;
				while(true) {
					cliente = servidor.accept(); //Se queda esperando
					DataInputStream flujo = new DataInputStream(cliente.getInputStream());
					String mensajeRecibido = flujo.readUTF();
					txtMensajes.append("\n"+mensajeRecibido);
					cliente.close();
					if(mensajeRecibido == "FIN") {
						servidor.close();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}