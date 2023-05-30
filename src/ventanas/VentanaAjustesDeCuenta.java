package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import metodos.BD_banco;
import ventanas.VentanaTransferir.FondoVentanaTransferir;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;

public class VentanaAjustesDeCuenta extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblTempo = new JLabel("00:00");
	private JButton btnVolver = new JButton("Volver a menu principal");
	private JButton btnEliminarCuenta = new JButton("Eliminar cuenta");
	private JButton btnCerrarSesion = new JButton("Cerrar sesi\u00F3n");
	private JCheckBox chckbxModoDios = new JCheckBox("Modo dios");
	private JButton btnAgregarTarjeta = new JButton("Agregar tarjeta");
	private JButton btnRenovarTarjeta = new JButton("Renovar tarjeta");
	private JButton btnEliminarTarjeta = new JButton("Eliminar tarjeta");
	private JButton btnCambiarContra = new JButton("Cambiar contrase\u00F1a");
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAjustesDeCuenta frame = new VentanaAjustesDeCuenta();
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
	public VentanaAjustesDeCuenta() {
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
		
		// Fondo y contentPane //
		FondoVentanaAjustesDeCuenta f = new FondoVentanaAjustesDeCuenta();
		this.setContentPane(f);
		contentPane = getContentPane();
		contentPane.setLayout(null);
		
		// ESTA VENTANA //
		setDefaultCloseOperation(0);
		setBounds(100, 100, 480, 465);
		setTitle("Ajustes de Cuenta");
		setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		
		// lblTempo //
		lblTempo.setBounds(295, 405, 46, 14);
		contentPane.add(lblTempo);
		
		JLabel lblAjustesCuenta = new JLabel("Ajustes de Cuenta");
		lblAjustesCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjustesCuenta.setFont(new Font("Dialog", Font.BOLD, 24));
		lblAjustesCuenta.setBounds(102, 15, 260, 40);
		lblAjustesCuenta.setOpaque(true);
		lblAjustesCuenta.setBackground(MenuPrincipal.colorPaneles);
		lblAjustesCuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		getContentPane().add(lblAjustesCuenta);
		
		JPanel panelIDCuenta = new JPanel();
		panelIDCuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDCuenta.setBounds(25, 65, 414, 40);
		panelIDCuenta.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelIDCuenta);
		panelIDCuenta.setLayout(null);
		
		JLabel lblIDCuenta = new JLabel("ID de cuenta:");
		lblIDCuenta.setBounds(10, 10, 110, 20);
		panelIDCuenta.add(lblIDCuenta);
		lblIDCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblMostrarIDCuenta = new JLabel(MenuPrincipal.id_cuenta_menu_principal);
		lblMostrarIDCuenta.setBounds(230, 10, 170, 20);
		panelIDCuenta.add(lblMostrarIDCuenta);
		
		JPanel panelNomUsuario = new JPanel();
		panelNomUsuario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelNomUsuario.setLayout(null);
		panelNomUsuario.setBounds(25, 115, 414, 40);
		panelNomUsuario.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelNomUsuario);
		
		JLabel lblNomUsuario = new JLabel("Nombre de usuario:");
		lblNomUsuario.setBounds(10, 10, 160, 20);
		panelNomUsuario.add(lblNomUsuario);
		lblNomUsuario.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblMostrarNomUsuario = new JLabel(MenuPrincipal.nom_propietario_menu_principal);
		lblMostrarNomUsuario.setBounds(230, 10, 170, 20);
		panelNomUsuario.add(lblMostrarNomUsuario);
		
		JPanel panelApPaternoUsuario = new JPanel();
		panelApPaternoUsuario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelApPaternoUsuario.setLayout(null);
		panelApPaternoUsuario.setBounds(25, 165, 414, 40);
		panelApPaternoUsuario.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelApPaternoUsuario);
		
		JLabel lblApPaternoUsuario = new JLabel("Apellido paterno de usuario:");
		lblApPaternoUsuario.setBounds(10, 10, 220, 20);
		panelApPaternoUsuario.add(lblApPaternoUsuario);
		lblApPaternoUsuario.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblMostrarApPaternoUsuario = new JLabel(MenuPrincipal.ap_paterno_propietario_menu_principal);
		lblMostrarApPaternoUsuario.setBounds(230, 10, 170, 20);
		panelApPaternoUsuario.add(lblMostrarApPaternoUsuario);
		
		JPanel panelApMaternoUsuario = new JPanel();
		panelApMaternoUsuario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelApMaternoUsuario.setLayout(null);
		panelApMaternoUsuario.setBounds(25, 215, 414, 40);
		panelApMaternoUsuario.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelApMaternoUsuario);
		
		JLabel lblApMaternoUsuario = new JLabel("Apellido materno de usuario:");
		lblApMaternoUsuario.setBounds(10, 10, 220, 20);
		panelApMaternoUsuario.add(lblApMaternoUsuario);
		lblApMaternoUsuario.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblMostrarApMaternoUsuario = new JLabel(MenuPrincipal.ap_materno_propietario_menu_principal);
		lblMostrarApMaternoUsuario.setBounds(230, 10, 170, 20);
		panelApMaternoUsuario.add(lblMostrarApMaternoUsuario);
		
		// btnVolver //
		btnVolver.setBounds(117, 265, 230, 27);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Dialog", Font.BOLD, 14));
		btnVolver.setBackground(new Color(100, 149, 237));
		btnVolver.addActionListener(this);
		getContentPane().add(btnVolver);
		
		// btnCambiarContra //
		btnCambiarContra.setForeground(Color.WHITE);
		btnCambiarContra.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCambiarContra.setBackground(new Color(100, 149, 237));
		btnCambiarContra.setBounds(132, 300, 200, 27);
		btnCambiarContra.addActionListener(this);
		getContentPane().add(btnCambiarContra);
		
		// btnEliminarCuenta //
		btnEliminarCuenta.setForeground(Color.WHITE);
		btnEliminarCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnEliminarCuenta.setBackground(new Color(205, 92, 92));
		btnEliminarCuenta.setBorder(new LineBorder(new Color(208, 48, 48)));
		btnEliminarCuenta.setBounds(269, 370, 170, 27);
		btnEliminarCuenta.addActionListener(this);
		getContentPane().add(btnEliminarCuenta);
		
		// btnCerrarSesion //
		btnCerrarSesion.setForeground(Color.WHITE);
		btnCerrarSesion.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCerrarSesion.setBackground(new Color(100, 149, 237));
		btnCerrarSesion.setBounds(157, 335, 150, 27);
		btnCerrarSesion.addActionListener(this);
		getContentPane().add(btnCerrarSesion);
		
		JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(135, 405, 160, 14);
		getContentPane().add(lblTiempoRestante);
		
		// chckbxModoDios //
		if (MenuPrincipal.modo_dios_activado) {
			btnVolver.setBounds(25, 265, 230, 27);
			btnCambiarContra.setBounds(40, 300, 200, 27);
			btnCerrarSesion.setBounds(65, 335, 150, 27);
			this.chckbxModoDios.setSelected(true);
			this.btnAgregarTarjeta.setVisible(true);
			this.btnRenovarTarjeta.setVisible(true);
			this.btnEliminarTarjeta.setVisible(true);
			this.btnEliminarCuenta.setVisible(true);
		}
		else {
			btnVolver.setBounds(117, 265, 230, 27);
			btnCambiarContra.setBounds(132, 300, 200, 27);
			btnCerrarSesion.setBounds(157, 335, 150, 27);
			this.chckbxModoDios.setSelected(false);
			this.btnAgregarTarjeta.setVisible(false);
			this.btnRenovarTarjeta.setVisible(false);
			this.btnEliminarTarjeta.setVisible(false);
			this.btnEliminarCuenta.setVisible(false);
		}
		this.chckbxModoDios.setOpaque(false);
		this.chckbxModoDios.setHorizontalAlignment(SwingConstants.CENTER);
		this.chckbxModoDios.setFont(new Font("Dialog", Font.BOLD, 12));
		this.chckbxModoDios.setBounds(370, 24, 85, 23);
		this.chckbxModoDios.addActionListener(this);
		getContentPane().add(this.chckbxModoDios);
		
		// btnAgregarTarjeta //
		btnAgregarTarjeta.setForeground(Color.WHITE);
		btnAgregarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAgregarTarjeta.setBackground(new Color(100, 149, 237));
		btnAgregarTarjeta.setBounds(269, 265, 170, 27);
		btnAgregarTarjeta.addActionListener(this);
		getContentPane().add(btnAgregarTarjeta);
		
		// btnRenovarTarjeta //
		btnRenovarTarjeta.setForeground(Color.WHITE);
		btnRenovarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRenovarTarjeta.setBackground(new Color(100, 149, 237));
		btnRenovarTarjeta.setBounds(269, 300, 170, 27);
		btnRenovarTarjeta.addActionListener(this);
		getContentPane().add(btnRenovarTarjeta);
		
		// btnEliminarTarjeta //
		btnEliminarTarjeta.setForeground(Color.WHITE);
		btnEliminarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnEliminarTarjeta.setBorder(new LineBorder(new Color(208, 48, 48)));
		btnEliminarTarjeta.setBackground(new Color(205, 92, 92));
		btnEliminarTarjeta.setBounds(269, 335, 170, 27);
		btnEliminarTarjeta.addActionListener(this);
		getContentPane().add(btnEliminarTarjeta);
	}
	
	class FondoVentanaAjustesDeCuenta extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo3.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		BD_banco bd = new BD_banco();
		LoginScreen c = new LoginScreen();
		VentanaCambiarContrasena d = new VentanaCambiarContrasena();
		VentanaAgregarTarjeta f = new VentanaAgregarTarjeta();
		VentanaRenovar g = new VentanaRenovar();
		VentanaEliminarTarjeta h = new VentanaEliminarTarjeta();
		if (e.getSource() == this.btnVolver) {
			System.out.println("Deteniendo Actualizador...");
			MenuPrincipal.actua = false;
			
			this.setVisible(false);
			this.dispose();
			a.setVisible(true);
		}
		
		if (e.getSource() == this.btnCambiarContra) {
			System.out.println("Deteniendo Actualizador...");
			MenuPrincipal.actua = false;
			
			this.setVisible(false);
			this.dispose();
			d.setVisible(true);
		}
		
		if (e.getSource() == this.btnCerrarSesion) {
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
			c.setVisible(true);
		}
		
		if (e.getSource() == this.chckbxModoDios) {
			if (this.chckbxModoDios.isSelected()) {
				this.chckbxModoDios.setSelected(false);
				int opcion = JOptionPane.showConfirmDialog(null, "<html>Está a punto de activar el Modo Dios.<br>Al activar este modo, activará las siguientes opciones:<ul><li>Agregar tarjeta</li><li>Eliminar tarjeta</li><li>Renovar tarjeta</li><li>Eliminar cuenta</li></ul>Estas opciones desaparecerán al desactivar el Modo Dios.<br>¿Continuar?</html>", "Activar Modo Dios", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					this.chckbxModoDios.setSelected(true);
					MenuPrincipal.modo_dios_activado = true;
					btnVolver.setBounds(25, 265, 230, 27);
					btnCambiarContra.setBounds(40, 300, 200, 27);
					btnCerrarSesion.setBounds(65, 335, 150, 27);
					this.btnAgregarTarjeta.setVisible(true);
					this.btnRenovarTarjeta.setVisible(true);
					this.btnEliminarTarjeta.setVisible(true);
					this.btnEliminarCuenta.setVisible(true);
				}
				else {
					this.chckbxModoDios.setSelected(false);
					MenuPrincipal.modo_dios_activado = false;
				}
			}
			else {
				MenuPrincipal.modo_dios_activado = false;
				btnVolver.setBounds(117, 265, 230, 27);
				btnCambiarContra.setBounds(132, 300, 200, 27);
				btnCerrarSesion.setBounds(157, 335, 150, 27);
				this.btnAgregarTarjeta.setVisible(false);
				this.btnRenovarTarjeta.setVisible(false);
				this.btnEliminarTarjeta.setVisible(false);
				this.btnEliminarCuenta.setVisible(false);
			}
		}
		if (e.getSource() == this.btnAgregarTarjeta) {
			String id_tarjeta = bd.generarIDTarjeta();
			if (id_tarjeta.compareToIgnoreCase("ERROR") != 0) {
				System.out.println("Deteniendo Actualizador...");
				MenuPrincipal.actua=false;
				
				VentanaAgregarTarjeta.origen = true;
				f.lblMostrarIDTarjeta.setText(id_tarjeta);
				this.setVisible(false);
				this.dispose();
				f.setVisible(true);
			}
		}
		if (e.getSource() == this.btnRenovarTarjeta) {
			if (bd.mostrarTarjetasVencidas(MenuPrincipal.id_cuenta_menu_principal)) {
				System.out.println("Deteniendo Actualizador...");
				MenuPrincipal.actua = false;
				this.setVisible(false);
				this.dispose();
				g.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Usted no tiene ninguna tarjeta vencida en esta cuenta.");
			}
		}
		if (e.getSource() == this.btnEliminarTarjeta) {
			if (bd.verifSiHayTarjetasCreadas(MenuPrincipal.id_cuenta_menu_principal)) {
				System.out.println("Deteniendo Actualizador...");
				MenuPrincipal.actua = false;
				
				bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaEliminarTarjeta.tablaTarjetas);
				this.setVisible(false);
				this.dispose();
				h.setVisible(true);
			}
		}
		if (e.getSource() == this.btnEliminarCuenta) {
			int opcion = JOptionPane.showConfirmDialog(null, "<html>Además de su cuenta, se eliminará del sistema lo siguiente:<ul><li>TODAS sus tarjetas (también desaparecerán de las transferencias realizadas con otros usuarios)</li><li>TODAS sus transacciones</li><li>TODOS sus datos</li></ul>¿Está seguro/a de querer eliminar esta cuenta (ID: " + MenuPrincipal.id_cuenta_menu_principal + ")?</html>", "Eliminar cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {
				int opcion2 = JOptionPane.showConfirmDialog(null, "<html>Por seguridad, se le volverá a preguntar.<br>¿Está REALMENTE SEGURO/A de querer ELIMINAR ESTA CUENTA (ID: " + MenuPrincipal.id_cuenta_menu_principal + ")?<br><br>Además de su cuenta, se eliminará del sistema lo siguiente:<ul><li>TODAS sus tarjetas (también desaparecerán de las transferencias realizadas con otros usuarios)</li><li>TODAS sus transacciones</li><li>TODOS sus datos</li></ul>Una vez presione \"SÍ\", no habrá forma de revertir el proceso.</html>", "ELIMINAR CUENTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (opcion2 == JOptionPane.YES_OPTION) {
					bd.eliminarCuentaUsuario(MenuPrincipal.id_cuenta_menu_principal);
					System.out.println("Deteniendo Actualizador...");
					MenuPrincipal.actua = false;
					
					MenuPrincipal.id_cuenta_menu_principal = "";
					MenuPrincipal.nom_propietario_menu_principal = "";
					MenuPrincipal.ap_paterno_propietario_menu_principal = "";
					MenuPrincipal.ap_materno_propietario_menu_principal = "";
					TemporizadorHilo.active = false;
					
					JOptionPane.showMessageDialog(null, "Cuenta eliminada exitósamente. Regresará a la pantalla de login.");
					MenuPrincipal.seg = 0;
					MenuPrincipal.min = 5;
					MenuPrincipal.cont = 0;
					MenuPrincipal.lblTempo.setText("05:00");
					
					this.setVisible(false);
					this.dispose();
					c.setVisible(true);
				}
			}
		}
	}
}
