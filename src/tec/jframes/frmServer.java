package tec.jframes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class frmServer extends JFrame implements Runnable, ActionListener{

	JTextArea txtReceive;
	JTextField txtSend;
	JButton btnSend;

	int port = 9090;
	ServerSocket server;
	Socket socket;

	public frmServer() {
		txtReceive = new JTextArea();
		txtReceive.setBounds(10, 90, 200, 200);
		add(txtReceive);

		txtSend = new JTextField();
		txtSend.setBounds(10,10,200,20);
		add(txtSend);

		btnSend = new JButton();
		btnSend.setText("Enviar");
		btnSend.setBounds(10,40,200,20);
		btnSend.addActionListener(this);
		add(btnSend);

		setLayout(null);
		setSize(250,350);
		setVisible(true);

		Thread thread = new Thread(this);
		thread.start();//Este metodo llama al metodo run que hayas creado

	}

	public static void main(String[] args) {
		new frmServer();
	}

	//Lo que se programe aqui se ejecuta en un segundo plano
	@Override
	public void run() {
		try {
			while(true) {
				server = new ServerSocket(port);
				socket = server.accept(); //Se queda esperando
				DataInputStream inputFlow = new DataInputStream(socket.getInputStream());
				String messageReceived = inputFlow.readUTF();
				txtReceive.append("\n"+messageReceived);
				socket.close();
				if(messageReceived == "FIN") {
					server.close();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			try {
				DataOutputStream outputFlow = new DataOutputStream(socket.getOutputStream());
				outputFlow.writeUTF(txtSend.getText());
				socket.close();
			}catch (Exception ex) {
				System.out.println("Error en cliente "+ ex.getMessage());
			}
		}
	}
}