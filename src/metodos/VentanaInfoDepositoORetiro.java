package metodos;
/*
 * [EQUIPO 5 - INTEGRANTES]
 * - NOZ GAMBOA LUZ ANGELICA
 * - ORDOÑEZ POOL ALAN JAIR
 * - PEDRAZA SÁNCHEZ JAVIER AGUSTIN
 * - VAZQUEZ NIETO ADRIAN
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaInfoDepositoORetiro extends JFrame {

	private Container contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Create the frame.
	 */
	public VentanaInfoDepositoORetiro(String id_transaccion, String nombreTarjeta, String numTarjeta, String saldoAnterior, String saldoActual, int tipo) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 230);
		FondoInfoDepositoORetiro f = new FondoInfoDepositoORetiro();
		this.setContentPane(f);
		contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel lblTransaccionExitosa = new JLabel("", SwingConstants.CENTER);
		switch (tipo) {
			case 1:
				lblTransaccionExitosa.setText("<html><h2>¡Depósito Exitoso! (" + (Float.parseFloat(saldoActual) - Float.parseFloat(saldoAnterior))+" MXN)</h2></html>");
				setTitle("¡Depósito Exitoso!");
				break;
			case 2:
				lblTransaccionExitosa.setText("<html><h2>¡Retiro Exitoso! (" + (Float.parseFloat(saldoAnterior) - Float.parseFloat(saldoActual))+" MXN)</h2></html>");
				setTitle("¡Retiro Exitoso!");
				break;
			default: JOptionPane.showMessageDialog(null, "Este mensaje no debe mostrarse.");
		}
		
		contentPane.add(lblTransaccionExitosa);
		
		JLabel lblCuentaOrigen = new JLabel();
		contentPane.add(lblCuentaOrigen);
		lblCuentaOrigen.setText("<html><DIR><b>Información de la transacción: </b><br>- ID de Transacción: " + id_transaccion + "<br>- Propietario: " + nombreTarjeta + "<br><b>- Núm. Tarjeta: " + numTarjeta + "</b><br>- Saldo Anterior: " + saldoAnterior + " MXN<br><b>- Saldo Actual: " + saldoActual + " MXN</b></DIR></html>");
		lblCuentaOrigen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		setVisible(true);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
	}
	
	class FondoInfoDepositoORetiro extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo4.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
