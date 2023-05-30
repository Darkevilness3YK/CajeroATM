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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

public class VentanaInfoTransferencia extends JFrame {
	private Container contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Create the frame.
	 */
	public VentanaInfoTransferencia(String id_transaccion, String nombreOrigen, String tarjetaOrigen, String saldoOrigenAnterior, String saldoOrigenActual, String nombreDestino, String tarjetaDestino, String saldoDestinoAnterior, String saldoDestinoActual) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("¡Transferencia Exitosa!");
		setBounds(100, 100, 450, 410);
		FondoInfoTransaccion f = new FondoInfoTransaccion();
		this.setContentPane(f);
		contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblTransaccionExitosa = new JLabel("<html><h2>¡Transferencia Exitosa! (" + (Float.parseFloat(saldoOrigenAnterior) - Float.parseFloat(saldoOrigenActual))+" MXN)</h2></html>", SwingConstants.CENTER);
		contentPane.add(lblTransaccionExitosa);
		
		JLabel lblInfoTransaccion = new JLabel();
		lblInfoTransaccion.setText("<html><DIR><b>Informaci\u00F3n de la transacción: </b><br>- ID de transacción: " + id_transaccion + "<br>- Diagrama de transferencia: [" + tarjetaOrigen + "] --> [" + tarjetaDestino + "]</DIR></html>");
		lblInfoTransaccion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		getContentPane().add(lblInfoTransaccion);
		
		JLabel lblCuentaOrigen = new JLabel();
		contentPane.add(lblCuentaOrigen);
		lblCuentaOrigen.setText("<html><DIR><b>Información de la cuenta de origen: </b><br>- Propietario: " + nombreOrigen + "<br><b>- Núm. Tarjeta: " + tarjetaOrigen + "</b><br>- Saldo Anterior: " + saldoOrigenAnterior + " MXN<br><b>- Saldo Actual: " + saldoOrigenActual + " MXN</b></DIR></html>");
		lblCuentaOrigen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblCuentaDestino = new JLabel();
		contentPane.add(lblCuentaDestino);
		lblCuentaDestino.setText("<html><DIR><b>Información de la cuenta de destino: </b><br>- Propietario: " + nombreDestino + "<br><b>- Núm. Tarjeta: " + tarjetaDestino + "</b><br>- Saldo Anterior: " + saldoDestinoAnterior + " MXN<br><b>- Saldo Actual: " + saldoDestinoActual + " MXN</b></DIR></html>");
		lblCuentaDestino.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		setVisible(true);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
	}
	
	class FondoInfoTransaccion extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo5.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
