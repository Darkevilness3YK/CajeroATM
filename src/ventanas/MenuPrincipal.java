package ventanas;
/*
 * [EQUIPO 5 - INTEGRANTES]
 * - NOZ GAMBOA LUZ ANGELICA
 * - ORDOÑEZ POOL ALAN JAIR
 * - PEDRAZA SÁNCHEZ JAVIER AGUSTIN
 * - VAZQUEZ NIETO ADRIAN
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import metodos.BD_banco;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.ButtonModel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;
import java.awt.event.KeyAdapter;


public class MenuPrincipal extends JFrame implements ActionListener{
	static JLabel lblTempo = new JLabel("05:00");
	static int seg=0;
	static int min=5;
	static int cont=0; //Contador que señala si está inicializado el temporizador
	static boolean actua = false;
			
	private Container contentPane;
	private JPanel panel_izq = new JPanel();
	private JPanel panel_der = new JPanel();
	private JLabel lblImagenBanco = new JLabel("");
	private JLabel lblSeleccioneUnaOperacin = new JLabel("Seleccione una operaci\u00F3n:");
	
	private JPanel panel_historial = new JPanel();
	private JLabel lblHistorialDeTransacciones = new JLabel("Ver Historial   >>>  ");
	private JButton btn_1 = new JButton("1");
	private JPanel panel_comprobar = new JPanel();
	private JLabel lblComprobarSaldo = new JLabel("Consultar Tarjetas   >>>");
	private JButton btn_2 = new JButton("2");
	private JPanel panel_depositar = new JPanel();
	private JLabel lblDepositar = new JLabel("Depositar   >>>");
	private JButton btn_3 = new JButton("3");
	private JPanel panel_retirar = new JPanel();
	private JLabel lblRetirarEfectivo = new JLabel("Retirar Efectivo   >>>");
	private JButton btn_4 = new JButton("4");
	private JPanel panel_transferir = new JPanel();
	private JLabel lblTransferir = new JLabel("Transferir   >>>");
	private JButton btn_5 = new JButton("5");
	private JButton btnCerrarSesion = new JButton();
	private final Component glue = Box.createGlue();
	private final Component glue_1 = Box.createGlue();
	private final Component glue_2 = Box.createGlue();
	private final Component glue_3 = Box.createGlue();
	private final JLabel lblBienvenido = new JLabel("\u00A1Bienvenido/a, ");
	
	public static String id_cuenta_menu_principal = "";
	public static String nom_propietario_menu_principal = "";
	public static String ap_paterno_propietario_menu_principal = "";
	public static String ap_materno_propietario_menu_principal = "";
	public static boolean modo_dios_activado;
	public static Color colorPaneles = new Color(180, 200, 210, 150);
	private JLabel lblPregunta = new JLabel("\u00BFQu\u00E9 le gustar\u00EDa hacer?");
	private final JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private final JButton btnCuenta = new JButton();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		/*
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	        System.out.println("Error setting native LAF: " + e);
	    }*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
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
	public MenuPrincipal(String id_cuenta, String nom_propietario, String ap_paterno_propietario, String ap_materno_propietario) {
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent key) {
				System.out.println("A");
				if (key.getKeyChar() == KeyEvent.VK_1) {
					System.out.println("A");
				}
			}
		});
		MenuPrincipal.id_cuenta_menu_principal = id_cuenta;
		MenuPrincipal.nom_propietario_menu_principal = nom_propietario;
		MenuPrincipal.ap_paterno_propietario_menu_principal = ap_paterno_propietario;
		MenuPrincipal.ap_materno_propietario_menu_principal = ap_materno_propietario;
		
		/*
		if (primer_acceso) {
			try {
				BD_banco bd = new BD_banco();
				bd.conectar_a_BD();
			}
			catch (ClassNotFoundException e1) {
				
			}
			catch (SQLException e2) {
				
			}
			primer_acceso = false;
		}
		*/
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if (cont==0){
					TemporizadorHilo temp=new TemporizadorHilo (lblTempo);
					System.out.println("Inicia Hilo...");
					TemporizadorHilo.active=true;
					cont++;
					temp.start();
				} else {
					ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
					System.out.println("Inicia Actualizador...");
					actua=false;
					actu.start();
				}
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
		
		FondoMenuPrincipal f = new FondoMenuPrincipal();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setForeground(new Color(30, 144, 255));
		setTitle("Cajero ATM");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 710, 402);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{404, 298, 0};
		gbl_contentPane.rowHeights = new int[]{358, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		panel_izq.setLayout(null);
		
		GridBagConstraints gbc_panel_izq = new GridBagConstraints();
		gbc_panel_izq.fill = GridBagConstraints.BOTH;
		gbc_panel_izq.insets = new Insets(0, 0, 5, 5);
		gbc_panel_izq.gridx = 0;
		gbc_panel_izq.gridy = 0;
		contentPane.add(panel_izq, gbc_panel_izq);
		lblImagenBanco.setBounds(28, 11, 359, 263);
		lblImagenBanco.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagenBanco.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/recursos/LogoATM2.png")));
		
		panel_izq.add(lblImagenBanco);
		
		GridBagConstraints gbc_panel_der = new GridBagConstraints();
		gbc_panel_der.insets = new Insets(5, 0, 0, 0);
		gbc_panel_der.fill = GridBagConstraints.VERTICAL;
		gbc_panel_der.gridx = 1;
		gbc_panel_der.gridy = 0;
		panel_der.setBackground(new Color(173, 216, 230));
		contentPane.add(panel_der, gbc_panel_der);
		panel_der.setLayout(new GridLayout(6, 1, 0, 7));
		lblSeleccioneUnaOperacin.setFont(new Font("Dialog", Font.BOLD, 20));
		
		lblSeleccioneUnaOperacin.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccioneUnaOperacin.setOpaque(true);
		lblSeleccioneUnaOperacin.setBackground(MenuPrincipal.colorPaneles);
		lblSeleccioneUnaOperacin.setBorder(new LineBorder(new Color(100, 149, 237)));
		panel_der.add(lblSeleccioneUnaOperacin);
		
		
		panel_historial.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		panel_der.add(panel_historial);
		
		lblHistorialDeTransacciones.setFont(new Font("Arial", Font.BOLD, 17));
		panel_historial.add(lblHistorialDeTransacciones);
		
		btn_1.setForeground(Color.WHITE);
		btn_1.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_1.setBackground(new Color(65, 105, 225));
		panel_historial.add(btn_1);
		/*
		panel_historial.setOpaque(false);
		panel_agregar.setOpaque(false);
		panel_comprobar.setOpaque(false);
		panel_depositar.setOpaque(false);
		panel_retirar.setOpaque(false);
		panel_transferir.setOpaque(false);
		*/
		
		panel_historial.setBackground(MenuPrincipal.colorPaneles);
		panel_comprobar.setBackground(MenuPrincipal.colorPaneles);
		panel_depositar.setBackground(MenuPrincipal.colorPaneles);
		panel_retirar.setBackground(MenuPrincipal.colorPaneles);
		panel_transferir.setBackground(MenuPrincipal.colorPaneles);
		
		panel_historial.setBorder(new LineBorder(new Color(100, 149, 237)));
		panel_comprobar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panel_depositar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panel_retirar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panel_transferir.setBorder(new LineBorder(new Color(100, 149, 237)));
		
		panel_der.add(panel_comprobar);
		FlowLayout fl_panel_comprobar = new FlowLayout(FlowLayout.RIGHT, 10, 12);
		panel_comprobar.setLayout(fl_panel_comprobar);
		lblComprobarSaldo.setFont(new Font("Arial", Font.BOLD, 17));
		panel_comprobar.add(lblComprobarSaldo);
		
		panel_comprobar.add(glue_3);
		btn_2.setBackground(new Color(65, 105, 225));
		btn_2.setForeground(Color.WHITE);
		btn_2.setFont(new Font("Roboto", Font.BOLD, 12));
		panel_comprobar.add(btn_2);
		
		panel_der.add(panel_depositar);
		panel_depositar.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		lblDepositar.setFont(new Font("Arial", Font.BOLD, 17));
		panel_depositar.add(lblDepositar);
		
		panel_depositar.add(glue_2);
		btn_3.setBackground(new Color(65, 105, 225));
		btn_3.setForeground(Color.WHITE);
		btn_3.setFont(new Font("Roboto", Font.BOLD, 12));
		panel_depositar.add(btn_3);
		
		panel_der.add(panel_retirar);
		panel_retirar.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		lblRetirarEfectivo.setFont(new Font("Arial", Font.BOLD, 17));
		panel_retirar.add(lblRetirarEfectivo);
		
		panel_retirar.add(glue_1);
		btn_4.setBackground(new Color(65, 105, 225));
		btn_4.setForeground(Color.WHITE);
		btn_4.setFont(new Font("Roboto", Font.BOLD, 12));
		panel_retirar.add(btn_4);
		
		panel_der.add(panel_transferir);
		panel_transferir.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		lblTransferir.setFont(new Font("Arial", Font.BOLD, 17));
		panel_transferir.add(lblTransferir);
		
		panel_transferir.add(glue);
		btn_5.setBackground(new Color(65, 105, 225));
		btn_5.setForeground(Color.WHITE);
		btn_5.setFont(new Font("Roboto", Font.BOLD, 12));
		panel_transferir.add(btn_5);
		
		btn_1.addActionListener(this);
		btn_2.addActionListener(this);
		btn_3.addActionListener(this);
		btn_4.addActionListener(this);
		btn_5.addActionListener(this);
		btnCerrarSesion.addActionListener(this);
		
		this.panel_der.setOpaque(false);
		this.panel_izq.setOpaque(false);
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenido.setForeground(new Color(25, 25, 112));
		lblBienvenido.setBounds(38, 262, 338, 24);
		lblBienvenido.setText("¡Bienvenido/a, " + MenuPrincipal.nom_propietario_menu_principal + "!");
		panel_izq.add(lblBienvenido);
		lblBienvenido.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setBounds(263, 332, 60, 14);
		panel_izq.add(lblTempo);
		lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPregunta.setForeground(new Color(25, 25, 112));
		lblPregunta.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPregunta.setBounds(77, 288, 261, 24);
		
		panel_izq.add(lblPregunta);
		btnCuenta.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/recursos/icono_perfil_boton.png")));
		btnCuenta.setPressedIcon(new ImageIcon(MenuPrincipal.class.getResource("/recursos/icono_perfil_boton_presionado.png")));
		btnCuenta.setToolTipText("AJUSTES DE CUENTA");
		btnCuenta.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCuenta.setContentAreaFilled(false);
		btnCuenta.setBorder(null);
		btnCuenta.setBounds(22, 20, 45, 44);
		btnCuenta.addActionListener(this);
		
		panel_izq.add(btnCuenta);
		
		btnCerrarSesion.setContentAreaFilled(false);
		btnCerrarSesion.setBorder(null);
		btnCerrarSesion.setPressedIcon(new ImageIcon(MenuPrincipal.class.getResource("/recursos/icono_salir_boton_presionado.png")));
		btnCerrarSesion.setToolTipText("CERRAR SESI\u00D3N");
		btnCerrarSesion.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/recursos/icono_salir_boton.png")));
		btnCerrarSesion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCerrarSesion.setBounds(22, 302, 45, 44);
		panel_izq.add(btnCerrarSesion);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempoRestante.setBounds(105, 332, 160, 14);
		
		panel_izq.add(lblTiempoRestante);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		//System.out.println("Tamano: " + this.getSize());
		//System.out.println("Tamano logo: " + this.lblImagenBanco.getWidth() + "x" + this.lblImagenBanco.getHeight());
		
		// ATAJOS DE TECLADO PARA CADA UNA DE LAS OPERACIONES QUE SE PUEDEN REALIZAR EN EL MENU PRINCIPAL //
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_1) {
					btn_1.doClick();
				}
				if (e.getKeyChar() == KeyEvent.VK_2) {
					btn_2.doClick();
				}
				if (e.getKeyChar() == KeyEvent.VK_3) {
					btn_3.doClick();
				}
				if (e.getKeyChar() == KeyEvent.VK_4) {
					btn_4.doClick();
				}
				if (e.getKeyChar() == KeyEvent.VK_5) {
					btn_5.doClick();
				}
			}
		});
		// Estas dos instrucciones son necesarias para que los atajos de teclado funcionen //
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent ace) {
		VentanaHistorialTransacciones a = new VentanaHistorialTransacciones();
		VentanaAgregarTarjeta b = new VentanaAgregarTarjeta();
		VentanaConsultar c = new VentanaConsultar();
		VentanaDepositar d = new VentanaDepositar();
		VentanaRetirar e = new VentanaRetirar();
		VentanaTransferir f = new VentanaTransferir();
		LoginScreen g = new LoginScreen();
		VentanaAjustesDeCuenta h = new VentanaAjustesDeCuenta();
		BD_banco bd = new BD_banco();
		
		if (ace.getSource() == this.btn_1) {
			System.out.println("Deteniendo Actualizador...");
			actua=false;
			
			bd.mostrarHistorialDeTransacciones(MenuPrincipal.id_cuenta_menu_principal, true);
			this.setVisible(false);
			this.dispose();
			a.setVisible(true);
		}
		if (ace.getSource() == this.btn_2) {
			System.out.println("Deteniendo Actualizador...");
			actua=false;
			
			bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaConsultar.tablaTarjetas);
			this.setVisible(false);
			this.dispose();
			c.setVisible(true);
		}
		if (ace.getSource() == this.btn_3) {
			if (bd.verifSiHayTarjetasCreadas(MenuPrincipal.id_cuenta_menu_principal)) {
				System.out.println("Deteniendo Actualizador...");
				actua=false;
				
				String id_transaccion = bd.generarIDTransaccion();
				if (id_transaccion.compareToIgnoreCase("ERROR") != 0) {
					d.id_transaccion = id_transaccion;
					this.setVisible(false);
					this.dispose();
					d.setVisible(true);
				}
			}
		}
		if (ace.getSource() == this.btn_4) {
			if (bd.verifSiHayTarjetasCreadas(MenuPrincipal.id_cuenta_menu_principal)) {
				System.out.println("Deteniendo Actualizador...");
				actua=false;
				
				String id_transaccion = bd.generarIDTransaccion();
				if (id_transaccion.compareToIgnoreCase("ERROR") != 0) {
					e.id_transaccion = id_transaccion;
					this.setVisible(false);
					this.dispose();
					e.setVisible(true);
				}
			}
		}
		if (ace.getSource() == this.btn_5) {
			if (bd.verifSiHayTarjetasCreadas(MenuPrincipal.id_cuenta_menu_principal)) {
				System.out.println("Deteniendo Actualizador...");
				actua=false;
				
				String id_transaccion = bd.generarIDTransaccion();
				if (id_transaccion.compareToIgnoreCase("ERROR") != 0) {
					f.id_transaccion = id_transaccion;
					this.setVisible(false);
					this.dispose();
					f.setVisible(true);
				}
			}
		}
		if (ace.getSource() == this.btnCerrarSesion) {
			System.out.println("Deteniendo Actualizador...");
			MenuPrincipal.actua = false;
			
			bd.cerrarSesion(MenuPrincipal.id_cuenta_menu_principal);
			MenuPrincipal.id_cuenta_menu_principal = "";
			MenuPrincipal.nom_propietario_menu_principal = "";
			MenuPrincipal.ap_paterno_propietario_menu_principal = "";
			MenuPrincipal.ap_materno_propietario_menu_principal = "";
			TemporizadorHilo.active = false;
			
			JOptionPane.showMessageDialog(null, "Ha cerrado su sesión manualmente.");
			MenuPrincipal.seg = 0;
			MenuPrincipal.min = 5;
			MenuPrincipal.cont = 0;
			MenuPrincipal.lblTempo.setText("05:00");
			
			Window[] ventana = JFrame.getWindows(); // Se obtiene un vector con todas las ventanas generadas durante la ejecución de la aplicación
			for (int i = 0; i < ventana.length; i++) { // Se inicia un ciclo para acceder a cada ventana
				if (!ventana[i].getName().equals("primeraVentana")) { // Si la ventana analizada no es la primera ventana...
					ventana[i].removeAll(); // Entonces a la ventana se eliminan sus componentes
					ventana[i].dispose(); // Y se le libera la memoria reservada
				}
			}
			
			this.setVisible(false);
			this.dispose();
			g.setVisible(true);
		}
		
		if (ace.getSource() == this.btnCuenta) {
			System.out.println("Deteniendo Actualizador...");
			actua=false;
			
			this.setVisible(false);
			this.dispose();
			h.setVisible(true);
		}
	}
	
	class FondoMenuPrincipal extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoMenuPrincipal.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}

	}
}
