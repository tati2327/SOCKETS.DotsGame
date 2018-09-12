package tec.jframes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class frmCliente extends JFrame implements ActionListener{
	
	JTextField txtMensaje;
	JButton btnEnviar;

	public frmCliente() {
		txtMensaje = new JTextField();
		txtMensaje.setBounds(10,10,200,20);
		add(txtMensaje);
		
		btnEnviar = new JButton();
		btnEnviar.setText("Enviar");
		btnEnviar.setBounds(10,40,200,20);
		btnEnviar.addActionListener(this);
		add(btnEnviar);
		
		setLayout(null);
		setSize(400,400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new frmCliente();
	}

	//Metodo sobrecargado
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnEnviar) {
			try {
				//Creado para enviar datos
				 Socket cliente = new Socket("172.18.221.138", 9090);
				 DataOutputStream flujo = new DataOutputStream(cliente.getOutputStream());
				 flujo.writeUTF(txtMensaje.getText());
				 cliente.close();
			}catch (Exception ex) {
				System.out.println("Error en cliente "+ ex.getMessage());
			}
		}
		
	}

}
