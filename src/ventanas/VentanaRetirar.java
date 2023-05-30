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
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import javax.swing.SwingConstants;

import metodos.BD_banco;
import metodos.RegistroTarjetas;
import javax.swing.border.LineBorder;

import com.mxrck.autocompleter.TextAutoCompleter;

public class VentanaRetirar extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtIDTarjeta = new JTextField();
	private JTextField txtMonto = new JTextField();
	private JPanel panelNumTarjeta = new JPanel();
	private JLabel lblIDTarjeta = new JLabel("ID de tarjeta:");
	private JPanel panelMonto = new JPanel();
	private JLabel lblMonto = new JLabel("Monto a retirar (MXN):");
	private JButton btnConfirmarRetiro = new JButton("Confirmar retiro");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	private JPanel panelIDCuenta = new JPanel();
	private JLabel lblIDCuenta = new JLabel("ID de cuenta:");
	private JLabel lblMostrarIDCuenta = new JLabel("");
	
	public String id_transaccion = "";
	private final JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private final JLabel lblRetiroDeEfectivo = new JLabel("Retiro de Efectivo");
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRetirar frame = new VentanaRetirar();
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
	public VentanaRetirar() {
		
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
		
		FondoVentanaRetirar f = new FondoVentanaRetirar();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setTitle("Retiro de Efectivo");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 488, 330);
		getContentPane().setLayout(null);
		panelIDCuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDCuenta.setBounds(30, 75, 414, 40);
		
		getContentPane().add(panelIDCuenta);
		panelIDCuenta.setLayout(null);
		lblIDCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblIDCuenta.setBounds(25, 10, 144, 20);
		
		panelIDCuenta.add(lblIDCuenta);
		lblMostrarIDCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarIDCuenta.setText(MenuPrincipal.id_cuenta_menu_principal);
		lblMostrarIDCuenta.setBounds(235, 10, 150, 20);
		
		panelIDCuenta.add(lblMostrarIDCuenta);
		panelNumTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelNumTarjeta.setBounds(30, 130, 414, 40);
		
		contentPane.add(panelNumTarjeta);
		panelNumTarjeta.setLayout(null);
		lblIDTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblIDTarjeta.setBounds(25, 10, 144, 20);
		panelNumTarjeta.add(lblIDTarjeta);
		txtIDTarjeta.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		BD_banco bd1 = new BD_banco();
		TextAutoCompleter ac = new TextAutoCompleter(this.txtIDTarjeta);
		ac.addItems(bd1.getListaTarjetasDeUsuario(MenuPrincipal.id_cuenta_menu_principal));
		
		txtIDTarjeta.setColumns(10);
		txtIDTarjeta.setBounds(235, 10, 150, 20);
		panelNumTarjeta.add(txtIDTarjeta);
		panelMonto.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelMonto.setBounds(30, 185, 414, 40);
		
		contentPane.add(panelMonto);
		panelMonto.setLayout(null);
		lblMonto.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblMonto.setBounds(25, 10, 200, 20);
		panelMonto.add(lblMonto);
		txtMonto.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtMonto.setBounds(235, 10, 150, 20);
		txtMonto.setColumns(10);
		panelMonto.add(txtMonto);
		
		this.setLocationRelativeTo(null);
		this.panelMonto.setBackground(MenuPrincipal.colorPaneles);
		this.panelNumTarjeta.setBackground(MenuPrincipal.colorPaneles);
		this.panelIDCuenta.setBackground(MenuPrincipal.colorPaneles);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		btnConfirmarRetiro.setBounds(88, 240, 170, 27);
		getContentPane().add(btnConfirmarRetiro);
		btnConfirmarRetiro.setForeground(Color.WHITE);
		btnConfirmarRetiro.setBackground(new Color(100, 149, 237));
		btnConfirmarRetiro.setFont(new Font("Dialog", Font.BOLD, 14));
		
		btnConfirmarRetiro.addActionListener(this);
		this.getRootPane().setDefaultButton(btnConfirmarRetiro);
		btnCancelar.setBounds(265, 240, 120, 27);
		getContentPane().add(btnCancelar);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTempo.setBounds(307, 273, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(135, 273, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		lblRetiroDeEfectivo.setOpaque(true);
		lblRetiroDeEfectivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRetiroDeEfectivo.setFont(new Font("Dialog", Font.BOLD, 24));
		lblRetiroDeEfectivo.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblRetiroDeEfectivo.setBackground(new Color(180, 200, 210, 150));
		lblRetiroDeEfectivo.setBounds(104, 17, 266, 40);
		
		getContentPane().add(lblRetiroDeEfectivo);
		btnCancelar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		BD_banco bd = new BD_banco();
		
		try {
			if (e.getSource() == this.btnConfirmarRetiro) {
				if (!bd.verifSiYaVencioLaTarjeta(this.txtIDTarjeta.getText(), "origen")) {
					if(bd.realizarTransaccion(MenuPrincipal.id_cuenta_menu_principal, "", this.id_transaccion, 2, bd.getFechaYHoraActual(), Float.parseFloat(this.txtMonto.getText()), this.txtIDTarjeta.getText(), MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal, "", "", "", "")) {
						this.setVisible(false);
						this.dispose();
						a.setVisible(true);
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
	class FondoVentanaRetirar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoRetiro.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
