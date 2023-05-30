package ventanas;
/*
 * [EQUIPO 5 - INTEGRANTES]
 * - NOZ GAMBOA LUZ ANGELICA
 * - ORDOÑEZ POOL ALAN JAIR
 * - PEDRAZA SÁNCHEZ JAVIER AGUSTIN
 * - VAZQUEZ NIETO ADRIAN
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import javax.swing.SwingConstants;

import metodos.BD_banco;
import metodos.RegistroTarjetas;
import javax.swing.border.LineBorder;

import com.mxrck.autocompleter.TextAutoCompleter;
public class VentanaTransferir extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtIDTarjetaOrigen = new JTextField();
	private JTextField txtIDTarjetaDestino = new JTextField();;
	private JTextField txtMonto = new JTextField();
	private JPanel panelIDTarjetaOrigen = new JPanel();
	private JLabel lblTarjetaDeOrigen = new JLabel("ID de tarjeta de origen:");
	private JPanel panelIDCuentaDestino = new JPanel();
	private JLabel lblIDTarjetaDestino = new JLabel("ID de tarjeta de destino:");
	private JPanel panelMonto = new JPanel();
	private JLabel lblMontoATransferir = new JLabel("Monto a transferir (MXN):");
	private JButton btnTransferir = new JButton("Confirmar transferencia");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	private JPanel panelIDCuentaOrigen = new JPanel();
	private JLabel lblIDCuentaOrigen = new JLabel("ID de cuenta de origen:");
	private JLabel lblMostrarIDCuentaOrigen = new JLabel("");
	
	public String id_transaccion = "";
	private final JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTransferir frame = new VentanaTransferir();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public VentanaTransferir() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		
		addWindowListener (new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int opcion = JOptionPane.showConfirmDialog(null, "¿Salir de Cajero ATM? Se cerrará tu sesión.", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					BD_banco bd1 = new BD_banco();
					bd1.cerrarSesion(MenuPrincipal.id_cuenta_menu_principal);
					System.exit(0);
				}
			}
		});
		
		FondoVentanaTransferir f = new FondoVentanaTransferir();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setTitle("Transferencia");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 488, 385);
		getContentPane().setLayout(null);
		panelIDCuentaOrigen.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDCuentaOrigen.setBounds(30, 75, 414, 40);
		
		getContentPane().add(panelIDCuentaOrigen);
		panelIDCuentaOrigen.setLayout(null);
		lblIDCuentaOrigen.setBounds(25, 10, 200, 20);
		lblIDCuentaOrigen.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelIDCuentaOrigen.add(lblIDCuentaOrigen);
		
		// lblMostrarcuentaOrigen //
		lblMostrarIDCuentaOrigen.setText(MenuPrincipal.id_cuenta_menu_principal);
		lblMostrarIDCuentaOrigen.setBounds(235, 10, 150, 20);
		panelIDCuentaOrigen.add(lblMostrarIDCuentaOrigen);
		panelIDTarjetaOrigen.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDTarjetaOrigen.setBounds(30, 130, 414, 40);
		
		contentPane.add(panelIDTarjetaOrigen);
		panelIDTarjetaOrigen.setLayout(null);
		lblTarjetaDeOrigen.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblTarjetaDeOrigen.setBounds(25, 10, 200, 20);
		panelIDTarjetaOrigen.add(lblTarjetaDeOrigen);
		txtIDTarjetaOrigen.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtIDTarjetaOrigen.setBounds(235, 10, 150, 20);
		panelIDTarjetaOrigen.add(txtIDTarjetaOrigen);
		txtIDTarjetaOrigen.setColumns(10);
		
		BD_banco bd1 = new BD_banco();
		TextAutoCompleter ac = new TextAutoCompleter(this.txtIDTarjetaOrigen);
		ac.addItems(bd1.getListaTarjetasDeUsuario(MenuPrincipal.id_cuenta_menu_principal));
		
		panelIDCuentaDestino.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDCuentaDestino.setBounds(30, 185, 414, 40);
		
		contentPane.add(panelIDCuentaDestino);
		panelIDCuentaDestino.setLayout(null);
		lblIDTarjetaDestino.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblIDTarjetaDestino.setBounds(25, 10, 200, 20);
		panelIDCuentaDestino.add(lblIDTarjetaDestino);
		txtIDTarjetaDestino.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtIDTarjetaDestino.setBounds(235, 10, 150, 20);
		panelIDCuentaDestino.add(txtIDTarjetaDestino);
		txtIDTarjetaDestino.setColumns(10);
		
		TextAutoCompleter ac2 = new TextAutoCompleter(this.txtIDTarjetaDestino);
		ac2.addItems(bd1.getListaTarjetasDeBD());
		
		panelMonto.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelMonto.setBounds(30, 240, 414, 40);
		
		contentPane.add(panelMonto);
		panelMonto.setLayout(null);
		lblMontoATransferir.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblMontoATransferir.setBounds(25, 10, 200, 20);
		panelMonto.add(lblMontoATransferir);
		txtMonto.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtMonto.setBounds(235, 10, 150, 20);
		panelMonto.add(txtMonto);
		txtMonto.setColumns(10);
		
		this.setLocationRelativeTo(null);
		this.panelIDCuentaDestino.setBackground(MenuPrincipal.colorPaneles);
		this.panelIDTarjetaOrigen.setBackground(MenuPrincipal.colorPaneles);
		this.panelMonto.setBackground(MenuPrincipal.colorPaneles);
		this.panelIDCuentaOrigen.setBackground(MenuPrincipal.colorPaneles);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		btnTransferir.setBounds(61, 295, 225, 27);
		getContentPane().add(btnTransferir);
		btnTransferir.setForeground(Color.WHITE);
		btnTransferir.setBackground(new Color(100, 149, 237));
		btnTransferir.setFont(new Font("Dialog", Font.BOLD, 14));
		
		btnTransferir.addActionListener(this);
		this.getRootPane().setDefaultButton(btnTransferir);
		btnCancelar.setBounds(294, 295, 120, 27);
		getContentPane().add(btnCancelar);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTempo.setBounds(307, 328, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(135, 328, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		
		JLabel lblTituloTransferencia = new JLabel("Transferencia");
		lblTituloTransferencia.setOpaque(true);
		lblTituloTransferencia.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloTransferencia.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloTransferencia.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloTransferencia.setBackground(new Color(180, 200, 210, 150));
		lblTituloTransferencia.setBounds(129, 17, 217, 40);
		getContentPane().add(lblTituloTransferencia);
		btnCancelar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		BD_banco bd = new BD_banco();
		String[] info_cuenta_destino = new String[5];
		try {
			if (e.getSource() == this.btnTransferir) {
				info_cuenta_destino = bd.getInfoCuentaMedianteTarjeta(this.txtIDTarjetaDestino.getText(), "destino");
				System.out.println("¿Hubo error? = [" + info_cuenta_destino[0] + "]");
				System.out.println("ID TARJETA DESTINO DE TEXTFIELD: " + this.txtIDTarjetaDestino.getText());
				if(!info_cuenta_destino[0].equals("ERROR")) {
					if ((!bd.verifSiYaVencioLaTarjeta(this.txtIDTarjetaOrigen.getText(), "origen")) && (!bd.verifSiYaVencioLaTarjeta(this.txtIDTarjetaDestino.getText(), "destino"))) {
						if(bd.realizarTransaccion(MenuPrincipal.id_cuenta_menu_principal, info_cuenta_destino[1], this.id_transaccion, 3, bd.getFechaYHoraActual(), Float.parseFloat(this.txtMonto.getText()), this.txtIDTarjetaOrigen.getText(), MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal, this.txtIDTarjetaDestino.getText(), info_cuenta_destino[2], info_cuenta_destino[3], info_cuenta_destino[4])) {
							this.setVisible(false);
							this.dispose();
							a.setVisible(true);
						}
					}
				}
			}
			if (e.getSource() == this.btnCancelar) {
				this.setVisible(false);
				this.dispose();
				a.setVisible(true);
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos correctamente antes de proceder.");
		}
	}
	
	class FondoVentanaTransferir extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo6.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
