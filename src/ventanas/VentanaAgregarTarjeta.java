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

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
// Import para archivos:
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import javax.swing.SwingConstants;

import metodos.BD_banco;
import metodos.RegistroTarjetas;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class VentanaAgregarTarjeta extends JFrame implements ActionListener {

	private Container contentPane;
	private JPanel panelIDPropietario = new JPanel();
	private JLabel lblIDPropietario = new JLabel("ID del propietario/a:");
	public JLabel lblMostrarIDTarjeta = new JLabel();
	private JPanel panelIDTarjeta = new JPanel();
	private JLabel lblIDTarjeta = new JLabel("ID de tarjeta:");
	private JPanel panelNomPropietario = new JPanel();
	private JLabel lblSaldoInicial = new JLabel("Saldo inicial (MXN):");
	private JButton btnAgregarTarjeta = new JButton("Agregar tarjeta");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	static boolean origen = false;// detecta de dónde proviene y a donde regresa.
	private JPanel panelSaldo = new JPanel();
	private JLabel lblNomPropietario = new JLabel("Nombre del propietario/a:");
	private JTextField txtSaldo = new JTextField();
	private JLabel lblMostrarIDPropietario = new JLabel();
	private JScrollPane scrollPaneNombre = new JScrollPane();
	private JLabel lblMostrarNomPropietario = new JLabel();
	private JPanel panelCVV_CVC = new JPanel();
	private JPanel panelFechaVencimiento = new JPanel();
	private JTextField txtCVV_CVC;
	private JLabel lblCVV_CVC = new JLabel("CVV/CVC:");
	private JLabel lblFechaVencimiento = new JLabel("Fecha de vencimiento:");
	private JLabel lblMostrarFechaVencimiento = new JLabel();
	private JButton btnGenerar = new JButton("Generar");
	
	//int longitud_de_nombre = 0;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAgregarTarjeta frame = new VentanaAgregarTarjeta();
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
	public VentanaAgregarTarjeta() {
		this.lblMostrarIDPropietario.setText(MenuPrincipal.id_cuenta_menu_principal);
		/*
		longitud_de_nombre = MenuPrincipal.nom_propietario_menu_principal.length() + MenuPrincipal.ap_paterno_propietario_menu_principal.length() + MenuPrincipal.ap_materno_propietario_menu_principal.length();
		if (longitud_de_nombre > 35) {
			scrollPaneNombre.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPaneNombre.setBounds(235, 2, 210, 45);
			panelNomPropietario.add(scrollPaneNombre);
			scrollPaneNombre.setViewportView(lblMostrarNomPropietario);
		}
		else {
			
		}
		if (longitud_de_nombre > 25 && longitud_de_nombre <= 35) {
			lblMostrarNomPropietario.setFont(new Font("Dialog", Font.BOLD, 11));
		}
		*/
		lblMostrarNomPropietario.setBounds(205, 10, 230, 20);
		panelNomPropietario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelNomPropietario.setBounds(30, 170, 414, 40);
		panelNomPropietario.add(lblMostrarNomPropietario);
		this.lblMostrarNomPropietario.setText(MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal);
		txtSaldo.setBounds(205, 12, 144, 20);
		txtSaldo.setColumns(10);
		
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
		
		FondoVentanaAgregar f = new FondoVentanaAgregar();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setTitle("Agregar Tarjeta");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 488, 460);
		getContentPane().setLayout(null);
		panelIDTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDTarjeta.setBounds(30, 70, 414, 40);
		
		contentPane.add(panelIDTarjeta);
		panelIDTarjeta.setLayout(null);
		lblIDTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblIDTarjeta.setBounds(10, 10, 144, 20);
		panelIDTarjeta.add(lblIDTarjeta);
		lblMostrarIDTarjeta.setBounds(205, 10, 151, 20);
		panelIDTarjeta.add(lblMostrarIDTarjeta);
		panelIDPropietario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDPropietario.setBounds(30, 120, 414, 40);
		
		contentPane.add(panelIDPropietario);
		panelIDPropietario.setLayout(null);
		
		contentPane.add(panelNomPropietario);
		panelNomPropietario.setLayout(null);
		panelSaldo.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelSaldo.setBounds(30, 220, 414, 40);
		
		contentPane.add(panelSaldo);
		panelSaldo.setLayout(null);
		lblSaldoInicial.setBounds(10, 10, 190, 20);
		panelSaldo.add(lblSaldoInicial);
		lblSaldoInicial.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelSaldo.add(txtSaldo);
		panelCVV_CVC.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelCVV_CVC.setBounds(30, 270, 414, 40);
		
		contentPane.add(panelCVV_CVC);
		panelFechaVencimiento.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelFechaVencimiento.setBounds(30, 320, 414, 40);
		
		contentPane.add(panelFechaVencimiento);
		btnGenerar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		/*
		this.panelIDPropietario.setOpaque(false);
		this.panelIDTarjeta.setOpaque(false);
		this.panelNomPropietario.setOpaque(false);
		this.panelSaldo.setOpaque(false);
		this.panelCVV_CVC.setOpaque(false);
		this.panelFechaVencimiento.setOpaque(false);
		this.panelBotones.setOpaque(false);
		*/
		
		this.panelIDPropietario.setBackground(MenuPrincipal.colorPaneles);
		this.panelIDTarjeta.setBackground(MenuPrincipal.colorPaneles);
		this.panelNomPropietario.setBackground(MenuPrincipal.colorPaneles);
		this.panelSaldo.setBackground(MenuPrincipal.colorPaneles);
		this.panelCVV_CVC.setBackground(MenuPrincipal.colorPaneles);
		this.panelFechaVencimiento.setBackground(MenuPrincipal.colorPaneles);
		panelCVV_CVC.setLayout(null);
		
		lblCVV_CVC.setBounds(10, 10, 190, 20);
		lblCVV_CVC.setFont(new Font("Dialog", Font.BOLD, 14));
		panelCVV_CVC.add(lblCVV_CVC);
		
		txtCVV_CVC = new JTextField();
		txtCVV_CVC.setToolTipText("Puedes escribir un CVV/CVC o generar uno");
		txtCVV_CVC.setBounds(205, 12, 86, 20);
		txtCVV_CVC.setColumns(10);
		panelCVV_CVC.add(txtCVV_CVC);
		btnGenerar.setBounds(305, 12, 85, 20);
		panelCVV_CVC.add(btnGenerar);
		
		btnGenerar.setForeground(Color.WHITE);
		btnGenerar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGenerar.setBackground(new Color(100, 149, 237));
		panelFechaVencimiento.setLayout(null);
		
		int anio_sumado = 0;
		SimpleDateFormat anio_obtenido = new SimpleDateFormat("yyyy");
		String anio = anio_obtenido.format(new Date());
		anio_sumado = Integer.parseInt(anio);
		anio_sumado += 4;
		anio = Integer.toString(anio_sumado);
		
		SimpleDateFormat fecha_sin_anio = new SimpleDateFormat("MM/dd");
		String fecha = anio + "/" + fecha_sin_anio.format(new Date());
		System.out.println(fecha);
		lblFechaVencimiento.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblFechaVencimiento.setBounds(10, 10, 190, 20);
		lblFechaVencimiento.setFont(new Font("Dialog", Font.BOLD, 14));
		panelFechaVencimiento.add(lblFechaVencimiento);
		lblMostrarFechaVencimiento.setHorizontalAlignment(SwingConstants.LEFT);
		lblMostrarFechaVencimiento.setText(fecha);
		lblMostrarFechaVencimiento.setBounds(205, 12, 151, 20);
		
		panelFechaVencimiento.add(lblMostrarFechaVencimiento);
		
		lblIDPropietario.setBounds(10, 10, 190, 20);
		panelIDPropietario.add(lblIDPropietario);
		lblIDPropietario.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarIDPropietario.setBounds(205, 10, 151, 20);
		
		panelIDPropietario.add(lblMostrarIDPropietario);
		lblNomPropietario.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomPropietario.setBounds(10, 10, 190, 20);
		
		panelNomPropietario.add(lblNomPropietario);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		lblTempo.setBounds(307, 403, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTempo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTempo.setOpaque(false);
		btnAgregarTarjeta.setBounds(85, 370, 178, 27);
		getContentPane().add(btnAgregarTarjeta);
		btnAgregarTarjeta.setForeground(Color.WHITE);
		btnAgregarTarjeta.setBackground(new Color(100, 149, 237));
		btnAgregarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		btnAgregarTarjeta.addActionListener(this);
		this.getRootPane().setDefaultButton(btnAgregarTarjeta);
		btnCancelar.setBounds(270, 370, 120, 27);
		getContentPane().add(btnCancelar);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(135, 403, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		lblTituloAgregarTarjeta.setOpaque(true);
		lblTituloAgregarTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloAgregarTarjeta.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloAgregarTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloAgregarTarjeta.setBackground(new Color(180, 200, 210, 150));
		lblTituloAgregarTarjeta.setBounds(112, 15, 250, 40);
		
		getContentPane().add(lblTituloAgregarTarjeta);
		btnCancelar.addActionListener(this);
	}
	
	int CVV_CVC = 0;
	double CVV_CVC_generado = 0.0d;
	private final JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private final JLabel lblTituloAgregarTarjeta = new JLabel("Agregar Tarjeta");
	
	public int generarCVV_CVC() {
		CVV_CVC_generado = 100 + (Math.random() * 1000);
		if (CVV_CVC_generado >= 1000) {
			CVV_CVC = generarCVV_CVC();
		}
		CVV_CVC = (int)CVV_CVC_generado;
		return CVV_CVC;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		VentanaAjustesDeCuenta b = new VentanaAjustesDeCuenta();
		try {
			BD_banco bd = new BD_banco();
			if (e.getSource() == this.btnGenerar) {
				this.txtCVV_CVC.setText(Integer.toString(this.generarCVV_CVC()));
			}
			if(e.getSource() == this.btnAgregarTarjeta) {
				if (Integer.parseInt(txtCVV_CVC.getText()) < 100 || Integer.parseInt(txtCVV_CVC.getText()) > 999) {
					JOptionPane.showMessageDialog(null, "El CVV/CVC no puede tener un valor menor a 100 ni mayor a 999. Intente de nuevo.");
				}
				else {
					//if (bd.agregarRegistro(this.txtNomPropietario.getText(), this.txtNumTarjeta.getText(), Float.parseFloat(this.textSaldo.getText()), this.txtContra.getText())) {
					if (bd.agregarTarjeta(this.lblMostrarIDPropietario.getText(), Integer.parseInt(this.txtCVV_CVC.getText()), this.lblMostrarFechaVencimiento.getText(), MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal, this.lblMostrarIDTarjeta.getText(), Float.parseFloat(this.txtSaldo.getText()))) {
						this.setVisible(false);
						this.dispose();
						System.out.println("Deteniendo Actualizador...");
						MenuPrincipal.actua=false;
						
						if (origen == true) {
							this.setVisible(false);
							this.dispose();
							b.setVisible(true);
							origen = false;
						}
						else {
							this.setVisible(false);
							this.dispose();
							a.setVisible(true);
						}
					}
				}
			}
			if(e.getSource() == this.btnCancelar) {
				System.out.println("Deteniendo Actualizador...");
				MenuPrincipal.actua=false;
				
				if (origen == true) {
					this.setVisible(false);
					this.dispose();
					b.setVisible(true);
					origen = false;
				}
				else {
					this.setVisible(false);
					this.dispose();
					a.setVisible(true);
				}
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos correctamente antes de proceder.");
		}
	}
	
	class FondoVentanaAgregar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo1.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
