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
import metodos.RegistroTarjetas;
public class VentanaTransferirDesactualizado extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtCuentaOrigen = new JTextField();
	private JTextField txtCuentaDestino = new JTextField();;
	private JTextField txtMonto = new JTextField();
	private JPanel panelIDTarjetaOrigen = new JPanel();
	private JLabel lblTarjetaDeOrigen = new JLabel("ID de tarjeta de origen:");
	private JPanel panelIDCuentaDestino = new JPanel();
	private JLabel lblIDTarjetaDestino = new JLabel("ID de tarjeta de destino:");
	private JPanel panelMonto = new JPanel();
	private JLabel lblMontoATransferir = new JLabel("Monto a transferir (MXN):");
	private JPanel panelBotones = new JPanel();
	private JButton btnTransferir = new JButton("Confirmar transferencia");
	private JButton btnCancelar = new JButton("Cancelar");
	private JLabel lblTempo = new JLabel("00:00");
	private JPanel panelIDCuentaOrigen = new JPanel();
	private JLabel lblIDCuentaOrigen = new JLabel("ID de cuenta de origen:");
	private JLabel lblMostrarIDCuentaOrigen = new JLabel("");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTransferirDesactualizado frame = new VentanaTransferirDesactualizado();
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
	public VentanaTransferirDesactualizado() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		
		FondoVentanaTransferir f = new FondoVentanaTransferir();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setTitle("Transferencia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 360);
		
		contentPane.setLayout(new GridLayout(5, 1, 0, 0));
		
		getContentPane().add(panelIDCuentaOrigen);
		panelIDCuentaOrigen.setLayout(null);
		lblIDCuentaOrigen.setBounds(40, 20, 200, 20);
		lblIDCuentaOrigen.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelIDCuentaOrigen.add(lblIDCuentaOrigen);
		
		// lblMostrarcuentaOrigen //
		lblMostrarIDCuentaOrigen.setText(MenuPrincipal.id_cuenta_menu_principal);
		lblMostrarIDCuentaOrigen.setBounds(250, 20, 150, 20);
		panelIDCuentaOrigen.add(lblMostrarIDCuentaOrigen);
		
		contentPane.add(panelIDTarjetaOrigen);
		panelIDTarjetaOrigen.setLayout(null);
		lblTarjetaDeOrigen.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblTarjetaDeOrigen.setBounds(40, 20, 200, 20);
		panelIDTarjetaOrigen.add(lblTarjetaDeOrigen);
		txtCuentaOrigen.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtCuentaOrigen.setBounds(250, 20, 150, 20);
		panelIDTarjetaOrigen.add(txtCuentaOrigen);
		txtCuentaOrigen.setColumns(10);
		
		contentPane.add(panelIDCuentaDestino);
		panelIDCuentaDestino.setLayout(null);
		lblIDTarjetaDestino.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblIDTarjetaDestino.setBounds(40, 20, 200, 20);
		panelIDCuentaDestino.add(lblIDTarjetaDestino);
		txtCuentaDestino.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtCuentaDestino.setBounds(250, 20, 150, 20);
		panelIDCuentaDestino.add(txtCuentaDestino);
		txtCuentaDestino.setColumns(10);
		
		contentPane.add(panelMonto);
		panelMonto.setLayout(null);
		lblMontoATransferir.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblMontoATransferir.setBounds(40, 20, 200, 20);
		panelMonto.add(lblMontoATransferir);
		txtMonto.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtMonto.setBounds(250, 20, 150, 20);
		panelMonto.add(txtMonto);
		txtMonto.setColumns(10);
		
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		btnTransferir.setBounds(41, 5, 225, 27);
		btnTransferir.setForeground(Color.WHITE);
		btnTransferir.setBackground(new Color(100, 149, 237));
		btnTransferir.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelBotones.add(btnTransferir);
		btnCancelar.setBounds(271, 5, 120, 27);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelBotones.add(btnCancelar);
		
		btnTransferir.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		this.panelBotones.setOpaque(false);
		this.panelIDCuentaDestino.setOpaque(false);
		this.panelIDTarjetaOrigen.setOpaque(false);
		this.panelMonto.setOpaque(false);
		this.panelIDCuentaOrigen.setOpaque(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.getRootPane().setDefaultButton(btnTransferir);
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setBounds(178, 43, 77, 14);
		
		panelBotones.add(lblTempo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		RegistroTarjetas r = new RegistroTarjetas();
		
		try {
			if (e.getSource() == this.btnTransferir) {
				if (r.hacerTransferencia(this.txtCuentaOrigen.getText(), this.txtCuentaDestino.getText(), Float.parseFloat(this.txtMonto.getText()))) {
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
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos correctamente antes de proceder.");
		}
	}
	
	class FondoVentanaTransferir extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoTransferencia.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
