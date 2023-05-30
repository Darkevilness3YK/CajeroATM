package ventanas;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.sql.*;

import metodos.BD_banco;
import metodos.RegistroTarjetas;
import javax.swing.border.LineBorder;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;


public class LoginScreen extends JFrame implements ActionListener {
	
	private Container contentPane;
	private JPasswordField pswdContrasena;
	private JFormattedTextField txtIDCuenta = new JFormattedTextField();
	private JButton btnlogin = new JButton("Iniciar Sesi\u00F3n");
	private JButton btnCrearCuenta = new JButton("Crear Cuenta");
	private JLabel lblIDCuenta = new JLabel("ID de cuenta:");
	private JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
	private JPanel panelIDCuenta = new JPanel();
	private JPanel panelContrasena = new JPanel();
	private JLabel lblIniciarSesion = new JLabel("<html>Inicie sesi\u00F3n con su<br><center>cuenta bancaria</center></html>");
	private final JLabel lbloCreeUna = new JLabel("O cree una nueva cuenta");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
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
	public LoginScreen() {
		this.setName("primeraVentana");
		System.out.println(this.getName());
		FondoLoginScreen f = new FondoLoginScreen();
		this.setContentPane(f);
		contentPane = getContentPane();
		setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		setTitle("Pantalla de Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300); <-- Tamaño anterior
		setBounds(100, 100, 710, 450);
		contentPane.setLayout(null);

		btnlogin.setBounds(460, 180, 160, 27);
		btnlogin.setForeground(Color.WHITE);
		btnlogin.setBorder(new LineBorder(new Color(255, 120, 0)));
		btnlogin.setBackground(new Color(255, 180, 30, 200));
		btnlogin.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(btnlogin);
		
		
		btnCrearCuenta.setBounds(463, 290, 154, 27);
		btnCrearCuenta.setForeground(Color.WHITE);
		btnCrearCuenta.setBorder(new LineBorder(new Color(255, 120, 0)));
		btnCrearCuenta.setBackground(new Color(255, 180, 30, 200));
		btnCrearCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(btnCrearCuenta);
		btnlogin.addActionListener(this);
		btnCrearCuenta.addActionListener(this);
		this.getRootPane().setDefaultButton(this.btnlogin);
		panelIDCuenta.setBorder(new LineBorder(new Color(255, 152, 0, 200)));
		
		panelIDCuenta.setBounds(390, 80, 300, 40);
		panelIDCuenta.setBackground(new Color(255, 215, 156, 220));
		getContentPane().add(panelIDCuenta);
		
		panelIDCuenta.setLayout(null);
		lblIDCuenta.setBounds(15, 10, 110, 20);
		panelIDCuenta.add(lblIDCuenta);
		
		lblIDCuenta.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		txtIDCuenta.setBounds(135, 10, 144, 20);
		panelIDCuenta.add(txtIDCuenta);
		
		
		txtIDCuenta.setToolTipText("Introduzca su ID de cuenta...");
		txtIDCuenta.setHorizontalAlignment(SwingConstants.LEFT);
		panelContrasena.setBorder(new LineBorder(new Color(255, 152, 0, 200)));
		panelContrasena.setBounds(390, 130, 300, 40);
		panelContrasena.setBackground(new Color(255, 215, 156, 220));
		getContentPane().add(panelContrasena);
		
		panelContrasena.setLayout(null);
		lblContrasea.setBounds(15, 10, 110, 20);
		panelContrasena.add(lblContrasea);
		
		// lblContrasea //
		lblContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblContrasea.setFont(new Font("Dialog", Font.BOLD, 14));
		
		// pswdContrasena //
		pswdContrasena = new JPasswordField();
		pswdContrasena.setBounds(135, 10, 144, 20);
		panelContrasena.add(pswdContrasena);
		pswdContrasena.setHorizontalAlignment(SwingConstants.LEFT);
		pswdContrasena.setToolTipText("Introduzca su contrase\u00F1a...");
		
		// lblIniciarSesion //
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblIniciarSesion.setFont(new Font("Dialog", Font.BOLD, 18));
		lblIniciarSesion.setBounds(390, 10, 300, 60);
		lblIniciarSesion.setOpaque(true);
		lblIniciarSesion.setBackground(new Color(255, 215, 156, 220));
		lblIniciarSesion.setBorder(new LineBorder(new Color(255, 152, 0, 200)));
		getContentPane().add(lblIniciarSesion);
		lbloCreeUna.setOpaque(true);
		lbloCreeUna.setHorizontalAlignment(SwingConstants.CENTER);
		lbloCreeUna.setFont(new Font("Dialog", Font.BOLD, 16));
		lbloCreeUna.setBorder(new LineBorder(new Color(255, 152, 0, 200)));
		lbloCreeUna.setBackground(new Color(255, 215, 156, 220));
		lbloCreeUna.setBounds(415, 240, 250, 40);
		getContentPane().add(lbloCreeUna);
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		VentanaRegistrarCuenta b = new VentanaRegistrarCuenta();
		BD_banco bd = new BD_banco();
		if (e.getSource() == this.btnlogin){
			try {
				bd.conectar_a_BD();
			}
			catch (ClassNotFoundException e1) {
				System.out.println("CONEXIÓN FALLIDA");
			}
			catch (CommunicationsException e2) {
				JOptionPane.showMessageDialog(null, "Hubo un error al conectarse con el servidor.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
			}
			catch (SQLException e3) {
				System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			}
			
			String miCuenta = String.valueOf(txtIDCuenta.getText());
			String miContra = String.valueOf(pswdContrasena.getPassword());
			MenuPrincipal a = new MenuPrincipal(miCuenta, bd.getNomPropietarioDeIDCuenta(miCuenta), bd.getApPaternoPropietarioDeIDCuenta(miCuenta), bd.getApMaternoPropietarioDeIDCuenta(miCuenta));
			//System.out.println("ID_cuenta = " + miCuenta);
			if (bd.autenticacion_exitosa(miCuenta, miContra)) {
				//System.out.println("ID_cuenta = " + miCuenta);
				MenuPrincipal.modo_dios_activado = false;
				this.setVisible(false);
				this.dispose();
				a.setVisible(true);
			}
		}
		if (e.getSource() == this.btnCrearCuenta) {
			try {
				bd.conectar_a_BD();
			}
			catch (ClassNotFoundException e1) {
				System.out.println("CONEXIÓN FALLIDA");
			}
			catch (CommunicationsException e2) {
				JOptionPane.showMessageDialog(null, "Hubo un error al conectarse con el servidor.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
			}
			catch (SQLException e3) {
				System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			}
			
			String id_cuenta = bd.generarIDCuenta();
			if (id_cuenta.compareToIgnoreCase("ERROR") != 0) {
				b.lblmostrarIDcuenta.setText(id_cuenta);
				this.setVisible(false);
				this.dispose();
				b.setVisible(true);
			}
		}
	}
	
	class FondoLoginScreen extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo7-1.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
