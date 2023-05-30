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
import metodos.RegistroTarjetas;
import metodos.RegistroTarjetas;
public class VentanaRetirarDesactualizado extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtNumTarjeta = new JTextField();
	private JTextField txtMonto = new JTextField();;
	private JPanel panelNumTarjeta = new JPanel();
	private JLabel lblNumTarjeta = new JLabel("N\u00FAmero de tarjeta:");
	private JPanel panelMonto = new JPanel();
	private JLabel lblMonto = new JLabel("Monto a retirar (MXN):");
	private JPanel panelBotones = new JPanel();
	private JButton btnConfirmarRetiro = new JButton("Confirmar retiro");
	private JButton btnCancelar = new JButton("Cancelar");
	private final JLabel lblTempo = new JLabel("00:00");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRetirarDesactualizado frame = new VentanaRetirarDesactualizado();
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
	public VentanaRetirarDesactualizado() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		
		FondoVentanaRetirar f = new FondoVentanaRetirar();
		this.setContentPane(f);
		contentPane = getContentPane();
		
		setResizable(false);
		setTitle("Retiro de Efectivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 215);
		
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		contentPane.add(panelNumTarjeta);
		panelNumTarjeta.setLayout(null);
		lblNumTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblNumTarjeta.setBounds(40, 20, 144, 17);
		panelNumTarjeta.add(lblNumTarjeta);
		txtNumTarjeta.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtNumTarjeta.setColumns(10);
		txtNumTarjeta.setBounds(250, 17, 150, 20);
		panelNumTarjeta.add(txtNumTarjeta);
		
		contentPane.add(panelMonto);
		panelMonto.setLayout(null);
		lblMonto.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblMonto.setBounds(40, 20, 200, 17);
		panelMonto.add(lblMonto);
		txtMonto.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtMonto.setBounds(250, 17, 150, 20);
		txtMonto.setColumns(10);
		panelMonto.add(txtMonto);
		
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		btnConfirmarRetiro.setBounds(74, 5, 170, 27);
		btnConfirmarRetiro.setForeground(Color.WHITE);
		btnConfirmarRetiro.setBackground(new Color(100, 149, 237));
		btnConfirmarRetiro.setFont(new Font("Dialog", Font.BOLD, 14));
		panelBotones.add(btnConfirmarRetiro);
		btnCancelar.setBounds(249, 5, 120, 27);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		panelBotones.add(btnCancelar);
		
		btnConfirmarRetiro.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		this.panelBotones.setOpaque(false);
		this.panelMonto.setOpaque(false);
		this.panelNumTarjeta.setOpaque(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.getRootPane().setDefaultButton(btnConfirmarRetiro);
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setBounds(199, 37, 46, 14);
		
		panelBotones.add(lblTempo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		RegistroTarjetas r = new RegistroTarjetas();
		
		try {
			if (e.getSource() == this.btnConfirmarRetiro) {
				if (r.retirar(this.txtNumTarjeta.getText(), Float.parseFloat(this.txtMonto.getText()))) {
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
