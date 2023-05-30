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
import javax.swing.JPasswordField;

public class VentanaCambiarContrasena extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblTempo = new JLabel("00:00");
	private JButton btnContinuar = new JButton("Continuar");
	private JButton btnCancelar1 = new JButton("Cancelar");
	private JButton btnConfirmar = new JButton("Confirmar");
	private JButton btnCancelar2 = new JButton("Cancelar");
	private JPasswordField pswdContraActual;
	private JPanel panelContraActual = new JPanel();
	private JLabel lblContraActual = new JLabel("Contrase\u00F1a actual:");
	private JPanel panelNuevaContra = new JPanel();
	private JLabel lblNuevaContra = new JLabel("Nueva contrase\u00F1a:");
	private JPasswordField pswdNuevaContra;
	private JPanel panelConfirmarNuevaContra = new JPanel();
	private JLabel lblConfirmarNuevaContra = new JLabel("Confirmar nueva contrase\u00F1a:");
	private JPasswordField pswdConfirmarNuevaContra;
	
	private Color colorPanelesDesactivados = new Color(220, 220, 220, 150);
	private Color colorBordesPanelesDesactivados = new Color(128, 128, 128);
	private Color colorBordesPanelesActivados = new Color(100, 149, 237);
	private Color colorBotonesDesactivados = new Color(192, 192, 192);
	private Color colorBotonesActivados = new Color(100, 149, 237);
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCambiarContrasena frame = new VentanaCambiarContrasena();
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
	public VentanaCambiarContrasena() {
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
		setBounds(100, 100, 480, 410);
		setTitle("Cambiar Contraseña");
		setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		getRootPane().setDefaultButton(btnContinuar);
		
		JLabel lblCambiarContra = new JLabel("Cambiar Contrase\u00F1a");
		lblCambiarContra.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarContra.setFont(new Font("Dialog", Font.BOLD, 24));
		lblCambiarContra.setBounds(87, 15, 290, 40);
		lblCambiarContra.setOpaque(true);
		lblCambiarContra.setBackground(MenuPrincipal.colorPaneles);
		lblCambiarContra.setBorder(new LineBorder(new Color(100, 149, 237)));
		getContentPane().add(lblCambiarContra);
		
		// lblTempo //
		lblTempo.setBounds(295, 350, 46, 14);
		contentPane.add(lblTempo);
		
		JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(135, 350, 160, 14);
		getContentPane().add(lblTiempoRestante);
		
		JPanel panelIDCuenta = new JPanel();
		panelIDCuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDCuenta.setBounds(25, 65, 414, 40);
		panelIDCuenta.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelIDCuenta);
		panelIDCuenta.setLayout(null);
		
		JLabel lblIDCuenta = new JLabel("ID de cuenta:");
		lblIDCuenta.setBounds(25, 10, 110, 20);
		panelIDCuenta.add(lblIDCuenta);
		lblIDCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblMostrarIDCuenta = new JLabel(MenuPrincipal.id_cuenta_menu_principal);
		lblMostrarIDCuenta.setBounds(245, 10, 145, 20);
		panelIDCuenta.add(lblMostrarIDCuenta);
		
		// panelContraAnterior //
		panelContraActual.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelContraActual.setLayout(null);
		panelContraActual.setBounds(25, 115, 414, 40);
		panelContraActual.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panelContraActual);
		
		// lblContraAnterior //
		lblContraActual.setBounds(25, 10, 160, 20);
		panelContraActual.add(lblContraActual);
		lblContraActual.setFont(new Font("Dialog", Font.BOLD, 14));
		
		pswdContraActual = new JPasswordField();
		pswdContraActual.setBounds(245, 10, 145, 20);
		panelContraActual.add(pswdContraActual);
		
		panelConfirmarNuevaContra.setEnabled(false);
		panelConfirmarNuevaContra.setBorder(new LineBorder(new Color(128, 128, 128)));
		panelConfirmarNuevaContra.setLayout(null);
		panelConfirmarNuevaContra.setBounds(25, 265, 414, 40);
		panelConfirmarNuevaContra.setBackground(this.colorPanelesDesactivados);
		getContentPane().add(panelConfirmarNuevaContra);
		
		lblConfirmarNuevaContra.setEnabled(false);
		lblConfirmarNuevaContra.setBounds(25, 10, 220, 20);
		panelConfirmarNuevaContra.add(lblConfirmarNuevaContra);
		lblConfirmarNuevaContra.setFont(new Font("Dialog", Font.BOLD, 14));
		
		pswdConfirmarNuevaContra = new JPasswordField();
		pswdConfirmarNuevaContra.setEnabled(false);
		pswdConfirmarNuevaContra.setBounds(245, 10, 145, 20);
		panelConfirmarNuevaContra.add(pswdConfirmarNuevaContra);
		
		panelNuevaContra.setEnabled(false);
		panelNuevaContra.setBorder(new LineBorder(new Color(128, 128, 128)));
		panelNuevaContra.setLayout(null);
		panelNuevaContra.setBounds(25, 215, 414, 40);
		panelNuevaContra.setBackground(this.colorPanelesDesactivados);
		getContentPane().add(panelNuevaContra);
		
		lblNuevaContra.setEnabled(false);
		lblNuevaContra.setBounds(25, 10, 220, 20);
		panelNuevaContra.add(lblNuevaContra);
		lblNuevaContra.setFont(new Font("Dialog", Font.BOLD, 14));
		
		pswdNuevaContra = new JPasswordField();
		pswdNuevaContra.setEnabled(false);
		pswdNuevaContra.setBounds(245, 10, 145, 20);
		panelNuevaContra.add(pswdNuevaContra);
		
		// btnContinuar //
		btnContinuar.setBounds(105, 165, 125, 27);
		btnContinuar.setForeground(Color.WHITE);
		btnContinuar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnContinuar.setBackground(new Color(100, 149, 237));
		btnContinuar.addActionListener(this);
		getContentPane().add(btnContinuar);
		
		// btnConfirmar //
		btnConfirmar.setEnabled(false);
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnConfirmar.setBackground(new Color(192, 192, 192));
		btnConfirmar.setBounds(105, 315, 125, 27);
		btnConfirmar.addActionListener(this);
		getContentPane().add(btnConfirmar);
		
		btnCancelar1.setForeground(Color.WHITE);
		btnCancelar1.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar1.setBackground(new Color(100, 149, 237));
		btnCancelar1.setBounds(240, 165, 120, 27);
		btnCancelar1.addActionListener(this);
		getContentPane().add(btnCancelar1);
		
		btnCancelar2.setEnabled(false);
		btnCancelar2.setForeground(Color.WHITE);
		btnCancelar2.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar2.setBackground(new Color(192, 192, 192));
		btnCancelar2.setBounds(240, 315, 120, 27);
		btnCancelar2.addActionListener(this);
		getContentPane().add(btnCancelar2);
		
	}
	
	class FondoVentanaAjustesDeCuenta extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo2.png")).getImage();
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
		VentanaAjustesDeCuenta d = new VentanaAjustesDeCuenta();
		
		if (e.getSource() == this.btnContinuar) {
			if (bd.verifContrasena(MenuPrincipal.id_cuenta_menu_principal, this.pswdContraActual.getText())) {
				this.panelContraActual.setEnabled(false);
				this.lblContraActual.setEnabled(false);
				this.pswdContraActual.setEnabled(false);
				this.btnContinuar.setEnabled(false);
				this.btnCancelar1.setEnabled(false);
				
				
				this.panelContraActual.setBackground(colorPanelesDesactivados);
				this.panelContraActual.setBorder(new LineBorder(this.colorBordesPanelesDesactivados));
				this.btnContinuar.setBackground(this.colorBotonesDesactivados);
				this.btnCancelar1.setBackground(this.colorBotonesDesactivados);
				
				this.panelNuevaContra.setEnabled(true);
				this.panelConfirmarNuevaContra.setEnabled(true);
				this.lblNuevaContra.setEnabled(true);
				this.lblConfirmarNuevaContra.setEnabled(true);
				this.pswdNuevaContra.setEnabled(true);
				this.pswdConfirmarNuevaContra.setEnabled(true);
				this.btnConfirmar.setEnabled(true);
				this.btnCancelar2.setEnabled(true);
				
				this.getRootPane().setDefaultButton(btnConfirmar);
				this.panelNuevaContra.setBackground(MenuPrincipal.colorPaneles);
				this.panelNuevaContra.setBorder(new LineBorder(this.colorBordesPanelesActivados));
				this.panelConfirmarNuevaContra.setBackground(MenuPrincipal.colorPaneles);
				this.panelConfirmarNuevaContra.setBorder(new LineBorder(this.colorBordesPanelesActivados));
				this.btnConfirmar.setBackground(this.colorBotonesActivados);
				this.btnCancelar2.setBackground(this.colorBotonesActivados);
			}
		}
		
		try {
			if (e.getSource() == this.btnConfirmar) {
				if (bd.cambiarContrasena(MenuPrincipal.id_cuenta_menu_principal, this.pswdNuevaContra.getText(), this.pswdConfirmarNuevaContra.getText())) {
					System.out.println("Deteniendo Actualizador...");
					MenuPrincipal.actua=false;
					
					this.setVisible(false);
					this.dispose();
					d.setVisible(true);
				}
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos correctamente antes de proceder.");
		}
		
		if (e.getSource() == this.btnCancelar1 || e.getSource() == this.btnCancelar2) {
			System.out.println("Deteniendo Actualizador...");
			MenuPrincipal.actua=false;
			
			this.setVisible(false);
			this.dispose();
			d.setVisible(true);
		}
	}
}
