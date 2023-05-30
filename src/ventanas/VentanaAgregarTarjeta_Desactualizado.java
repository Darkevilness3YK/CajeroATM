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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import javax.swing.SwingConstants;

import metodos.BD_banco;
import metodos.RegistroTarjetas;

public class VentanaAgregarTarjeta_Desactualizado extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtNomPropietario = new JTextField();
	private JTextField txtContra = new JTextField();
	private JPanel panelNomPropietario = new JPanel();
	private JLabel lblNomPropietario = new JLabel("Nombre del propietario/a:");
	public JLabel lblID_tarjeta = new JLabel();
	private JPanel panelNumTarjeta = new JPanel();
	private JLabel lblNumTarjeta = new JLabel("N\u00FAmero de tarjeta:");
	private JPanel panelSaldoInicial = new JPanel();
	private JLabel lblSaldoInicial = new JLabel("Saldo inicial (MXN):");
	private JPanel panelBotones = new JPanel();
	private JButton btnAgregarTarjeta = new JButton("Agregar tarjeta");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	private final JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
	private JTextField textSaldo;
	static boolean origen = false;// detecta de dónde proviene y a donde regresa.
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAgregarTarjeta_Desactualizado frame = new VentanaAgregarTarjeta_Desactualizado();
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
	public VentanaAgregarTarjeta_Desactualizado() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		FondoVentanaAgregar f = new FondoVentanaAgregar();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setTitle("Agregar Tarjeta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 354);
		
		contentPane.setLayout(new GridLayout(4, 0, 0, 0));
		
		contentPane.add(panelNumTarjeta);
		panelNumTarjeta.setLayout(null);
		lblNumTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblNumTarjeta.setBounds(40, 20, 144, 17);
		panelNumTarjeta.add(lblNumTarjeta);
		lblID_tarjeta.setBounds(254, 23, 151, 14);
		panelNumTarjeta.add(lblID_tarjeta);
		
		contentPane.add(panelNomPropietario);
		panelNomPropietario.setLayout(null);
		
		contentPane.add(panelSaldoInicial);
		panelSaldoInicial.setLayout(null);
		lblSaldoInicial.setBounds(40, 34, 144, 14);
		lblSaldoInicial.setFont(new Font("Dialog", Font.BOLD, 14));
		panelSaldoInicial.add(lblSaldoInicial);
		
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		btnAgregarTarjeta.setBounds(62, 5, 178, 27);
		btnAgregarTarjeta.setForeground(Color.WHITE);
		btnAgregarTarjeta.setBackground(new Color(100, 149, 237));
		btnAgregarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelBotones.add(btnAgregarTarjeta);
		btnCancelar.setBounds(245, 5, 120, 27);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelBotones.add(btnCancelar);
		
		btnAgregarTarjeta.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		this.panelBotones.setOpaque(false);
		this.panelNomPropietario.setOpaque(false);
		lblContrasea.setFont(new Font("Dialog", Font.BOLD, 14));
		lblContrasea.setBounds(40, 53, 203, 17);
		
		panelNomPropietario.add(lblContrasea);
		txtContra.setBounds(254, 52, 151, 20);
		panelNomPropietario.add(txtContra);
		txtContra.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtContra.setColumns(10);
		txtNomPropietario.setBounds(254, -1, 150, 20);
		panelNomPropietario.add(txtNomPropietario);
		txtNomPropietario.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtNomPropietario.setColumns(10);
		lblNomPropietario.setBounds(40, 0, 203, 17);
		panelNomPropietario.add(lblNomPropietario);
		lblNomPropietario.setFont(new Font("Dialog", Font.BOLD, 14));
		this.panelNumTarjeta.setOpaque(false);
		
		this.panelSaldoInicial.setOpaque(false);
		
		textSaldo = new JTextField();
		textSaldo.setBounds(254, 32, 150, 20);
		panelSaldoInicial.add(textSaldo);
		textSaldo.setFont(new Font("Roboto", Font.PLAIN, 12));
		textSaldo.setColumns(10);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.getRootPane().setDefaultButton(btnAgregarTarjeta);
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTempo.setBounds(156, 43, 132, 14);
		
		panelBotones.add(lblTempo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		LoginScreen b = new LoginScreen();
		try {
			BD_banco bd = new BD_banco();
			if(e.getSource() == this.btnAgregarTarjeta) {
				//if (bd.agregarRegistro(this.txtNomPropietario.getText(), this.txtNumTarjeta.getText(), Float.parseFloat(this.textSaldo.getText()), this.txtContra.getText())) {
				/*
				if (bd.agregarTarjeta("notfound", this.txtNomPropietario.getText(), this.lblID_tarjeta.getText(), Float.parseFloat(this.textSaldo.getText()))) {
					this.setVisible(false);
					a.setVisible(true);
				}
				*/
			}
			if(e.getSource() == this.btnCancelar) {
				if (origen==true){
					this.setVisible(false);
					b.setVisible(true);
					origen=false;
				}else {
				MenuPrincipal.actua=false;
				this.setVisible(false);
				a.setVisible(true);
				}
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos antes de proceder.");
		}
	}
	
	class FondoVentanaAgregar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoAgregarTarjeta.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
