package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import metodos.BD_banco;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class VentanaHistorialTransacciones extends JFrame implements ActionListener {

	private Container contentPane;
	private JScrollPane scrollPaneHistorial = new JScrollPane();
	public static JTable tableHistorial = new JTable();
	private JLabel lblTempo = new JLabel("00:00");
	private JButton btnAceptar = new JButton("Aceptar");
	private JLabel lblHistorialDeTransacciones = new JLabel("Historial de transacciones de:");
	private JLabel lblMostrarNombrePropietario = new JLabel();
	private JLabel lblOrdenar = new JLabel("Ordenar por:");
	private JRadioButton rdbtnMenosReciente = new JRadioButton("Menos reciente");
	private JRadioButton rdbtnMasReciente = new JRadioButton("M\u00E1s reciente");
	private final JPanel panellblTransacciones = new JPanel();
	private final JPanel panelOrdenar = new JPanel();
	private final JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private final JLabel lblTituloHistorialDeTransacciones = new JLabel("Historial de Transacciones");
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaHistorialTransacciones frame = new VentanaHistorialTransacciones();
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
	public VentanaHistorialTransacciones() {
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
		
		FondoVentanaHistorialTransacciones f = new FondoVentanaHistorialTransacciones();
		this.setContentPane(f);
		contentPane = getContentPane();
		getContentPane().setLayout(null);
		
		// scrollPaneHistorial //
		scrollPaneHistorial.setBounds(50, 145, 900, 250);
		scrollPaneHistorial.setBorder(new LineBorder(new Color(100, 149, 237)));
		scrollPaneHistorial.setViewportView(tableHistorial);
		getContentPane().add(scrollPaneHistorial);
		
		// lblTempo //
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTempo.setBounds(565, 503, 60, 14);
		getContentPane().add(lblTempo);
		
		// btnAceptar //
		btnAceptar.addActionListener(this);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAceptar.setBackground(new Color(100, 149, 237));
		btnAceptar.setBounds(440, 470, 120, 27);
		getContentPane().add(btnAceptar);
		
		// panellblTransacciones //
		panellblTransacciones.setBounds(50, 85, 900, 40);
		panellblTransacciones.setBackground(MenuPrincipal.colorPaneles);
		panellblTransacciones.setBorder(new LineBorder(new Color(100, 149, 237)));
		getContentPane().add(panellblTransacciones);
		
		// panellblTransacciones //
		panellblTransacciones.setLayout(null);
		panellblTransacciones.add(lblHistorialDeTransacciones);
		panellblTransacciones.add(lblMostrarNombrePropietario);
		
		// lblHistorialDeTransacciones //
		lblHistorialDeTransacciones.setBounds(10, 10, 230, 20);
		lblHistorialDeTransacciones.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarNombrePropietario.setBounds(235, 10, 465, 20);
		
		// lblMostrarNombrePropietario //
		lblMostrarNombrePropietario.setText(MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal);
		rdbtnMasReciente.setBounds(180, 425, 110, 20);
		getContentPane().add(rdbtnMasReciente);
		
		// rdbtnMasReciente //
		rdbtnMasReciente.setFont(new Font("Dialog", Font.BOLD, 12));
		rdbtnMasReciente.setOpaque(false);
		rdbtnMasReciente.addActionListener(this);
		rdbtnMasReciente.setSelected(true);
		rdbtnMenosReciente.setBounds(300, 425, 130, 20);
		getContentPane().add(rdbtnMenosReciente);
		
		// rdbtnMenosReciente //
		rdbtnMenosReciente.setFont(new Font("Dialog", Font.BOLD, 12));
		rdbtnMenosReciente.setOpaque(false);
		rdbtnMenosReciente.addActionListener(this);
		
		// panelOrdenar //
		panelOrdenar.setLayout(null);
		panelOrdenar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelOrdenar.setBackground(new Color(180, 200, 210, 150));
		panelOrdenar.setBounds(50, 415, 900, 40);
		getContentPane().add(panelOrdenar);
		
		// lblOrdenar //
		lblOrdenar.setFont(new Font("Dialog", Font.BOLD, 14));
		lblOrdenar.setBounds(10, 10, 110, 20);
		panelOrdenar.add(lblOrdenar);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(401, 503, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		lblTituloHistorialDeTransacciones.setOpaque(true);
		lblTituloHistorialDeTransacciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloHistorialDeTransacciones.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloHistorialDeTransacciones.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloHistorialDeTransacciones.setBackground(new Color(180, 200, 210, 150));
		lblTituloHistorialDeTransacciones.setBounds(318, 23, 364, 40);
		
		getContentPane().add(lblTituloHistorialDeTransacciones);
		
		// Esta ventana //
		this.setDefaultCloseOperation(0);
		this.setBounds(100, 100, 1010, 560);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.setTitle("Historial de Transacciones");
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		BD_banco bd = new BD_banco();
		if (e.getSource() == this.btnAceptar) {
			this.setVisible(false);
			this.dispose();
			a.setVisible(true);
		}
		if (e.getSource() == this.rdbtnMasReciente) {
			this.rdbtnMasReciente.setSelected(true);
			this.rdbtnMenosReciente.setSelected(false);
			bd.mostrarHistorialDeTransacciones(MenuPrincipal.id_cuenta_menu_principal, true);
		}
		if (e.getSource() == this.rdbtnMenosReciente) {
			this.rdbtnMasReciente.setSelected(false);
			this.rdbtnMenosReciente.setSelected(true);
			bd.mostrarHistorialDeTransacciones(MenuPrincipal.id_cuenta_menu_principal, false);
		}
	}
	
	class FondoVentanaHistorialTransacciones extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoTransferencia.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}

	}
}
