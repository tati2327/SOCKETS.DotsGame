package tec.jframes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class frmClient extends JFrame implements ActionListener{
	
	JTextArea txtReceive;
	JTextField txtSend;
	JButton btnSend;
	
	Socket client;
	String ip = "172.18.221.138";
	int port = 9090;

	public frmClient() {
		setTitle("CLIENT");
		
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
	}

	public static void main(String[] args) {
		new frmClient();
	}

	//Metodo sobrecargado
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			try {
				//Creado para enviar datos
				 Socket client = new Socket(ip, port);
				 DataOutputStream outputFlow = new DataOutputStream(client.getOutputStream());
				 outputFlow.writeUTF(txtSend.getText());
				 
				 DataInputStream inputFlow = new DataInputStream(client.getInputStream());
				 String messageReceived = inputFlow.readUTF();
				 txtReceive.append("\n"+messageReceived);
					
				 client.close();
			}catch (Exception ex) {
				System.out.println("Error en cliente "+ ex.getMessage());
			}
		}	
	}
}