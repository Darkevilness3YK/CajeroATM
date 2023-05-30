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
public class VentanaDepositarDesactualizado extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtMonto = new JTextField();
	private JPanel panelNumTarjeta = new JPanel();
	private JLabel lblIDTarjeta = new JLabel("ID de tarjeta:");
	private JPanel panelMonto = new JPanel();
	private JLabel lblMonto = new JLabel("Monto a depositar (MXN):");
	private JPanel panelBotones = new JPanel();
	private JButton btnConfirmarDeposito = new JButton("Confirmar dep\u00F3sito");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	private JLabel lblMostrarIDTarjeta = new JLabel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaDepositarDesactualizado frame = new VentanaDepositarDesactualizado();
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
	public VentanaDepositarDesactualizado() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		
		FondoVentanaDepositar f = new FondoVentanaDepositar();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setTitle("Depósito");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 215);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		contentPane.add(panelNumTarjeta);
		panelNumTarjeta.setLayout(null);
		lblIDTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblIDTarjeta.setBounds(40, 20, 144, 20);
		panelNumTarjeta.add(lblIDTarjeta);
		
		contentPane.add(panelMonto);
		panelMonto.setLayout(null);
		lblMonto.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblMonto.setBounds(40, 20, 200, 20);
		panelMonto.add(lblMonto);
		txtMonto.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtMonto.setBounds(250, 20, 150, 20);
		txtMonto.setColumns(10);
		panelMonto.add(txtMonto);
		
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		btnConfirmarDeposito.setBounds(60, 5, 195, 27);
		btnConfirmarDeposito.setForeground(Color.WHITE);
		btnConfirmarDeposito.setBackground(new Color(100, 149, 237));
		btnConfirmarDeposito.setFont(new Font("Dialog", Font.BOLD, 14));
		panelBotones.add(btnConfirmarDeposito);
		btnCancelar.setBounds(260, 5, 120, 27);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		panelBotones.add(btnCancelar);
		
		btnConfirmarDeposito.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		this.panelMonto.setOpaque(false);
		this.panelBotones.setOpaque(false);
		this.panelNumTarjeta.setOpaque(false);
		lblMostrarIDTarjeta.setBounds(249, 20, 151, 20);
		
		panelNumTarjeta.add(lblMostrarIDTarjeta);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.getRootPane().setDefaultButton(btnConfirmarDeposito);
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setBounds(199, 43, 46, 14);
		
		panelBotones.add(lblTempo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		RegistroTarjetas r = new RegistroTarjetas();
		//BD_banco bd = new BD_banco();
		try {
			if (e.getSource() == this.btnConfirmarDeposito) {
				if (r.depositar("a", Float.parseFloat(this.txtMonto.getText()))) {
					this.setVisible(false);
					this.dispose();
					a.setVisible(true);
				}
			}
			if (e.getSource() == this.btnCancelar) {
				this.setVisible(false);
				this.dispose();
				a.setVisible(true);
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos antes de proceder.");
		}
	}
	class FondoVentanaDepositar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoDeposito.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
