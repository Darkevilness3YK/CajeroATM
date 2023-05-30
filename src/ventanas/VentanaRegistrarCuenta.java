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
import metodos.RegistroTarjetas;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

public class VentanaRegistrarCuenta extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblIDcuenta = new JLabel("ID de cuenta (RECUERDALO):");
	public JLabel lblmostrarIDcuenta = new JLabel();
	private JPanel panelIDcuenta = new JPanel();
	private JLabel lblPropietario = new JLabel("Nombre(s):");
	private JPanel panelPropietario = new JPanel();
	private JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
	private JPasswordField pswdContrasena = new JPasswordField();
	private JPanel panelContrasena = new JPanel();
	private JLabel lblConfirmarContrasea = new JLabel("Confirmar contrase\u00F1a:");
	private JPasswordField pswdConfirmarContrasena = new JPasswordField();
	private JPanel panelConfirmarContra = new JPanel();
	private JButton btnRegistrarCuenta = new JButton("Registrar Cuenta");
	private JButton btnCancelar = new JButton("Cancelar");
	private JTextField txtPropietario = new JTextField();
	private JPanel panelApePaterno = new JPanel();
	private JPanel panelApeMaterno = new JPanel();
	private JLabel lblApePaterno = new JLabel("Apellido paterno:");
	private JTextField txtApePaterno = new JTextField();
	private JLabel lblApeMaterno = new JLabel("Apellido materno:");
	private JTextField txtApeMaterno = new JTextField();
	private final JLabel lblTituloCrearCuenta = new JLabel("Crear Cuenta");
	
	//static boolean origen = false;// detecta de dónde proviene y a donde regresa.
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistrarCuenta frame = new VentanaRegistrarCuenta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the frame.
	 */
	public VentanaRegistrarCuenta() {
		FondoVentanaAgregar f = new FondoVentanaAgregar();
		this.setContentPane(f);
		contentPane = getContentPane();
		getContentPane().setLayout(null);
		panelIDcuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelIDcuenta.setBounds(30, 70, 414, 40);
		
		contentPane.add(panelIDcuenta);
		lblIDcuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblIDcuenta.setBounds(25, 10, 220, 20);
		lblmostrarIDcuenta.setBounds(260, 10, 130, 20);
		panelPropietario.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelPropietario.setBounds(30, 120, 414, 40);
		
		contentPane.add(panelPropietario);
		panelPropietario.setLayout(null);
		lblPropietario.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPropietario.setBounds(25, 10, 100, 20);
		
		panelPropietario.add(lblPropietario);
		panelApePaterno.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelApePaterno.setBounds(30, 170, 414, 40);
		
		getContentPane().add(panelApePaterno);
		panelApePaterno.setLayout(null);
		lblApePaterno.setBounds(25, 10, 150, 20);
		lblApePaterno.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelApePaterno.add(lblApePaterno);
		txtApePaterno.setBounds(246, 10, 144, 20);
		txtApePaterno.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtApePaterno.setColumns(10);
		
		panelApePaterno.add(txtApePaterno);
		panelApeMaterno.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelApeMaterno.setBounds(30, 220, 414, 40);
		
		getContentPane().add(panelApeMaterno);
		panelApeMaterno.setLayout(null);
		lblApeMaterno.setBounds(25, 10, 150, 20);
		lblApeMaterno.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelApeMaterno.add(lblApeMaterno);
		txtApeMaterno.setBounds(246, 10, 144, 20);
		txtApeMaterno.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtApeMaterno.setColumns(10);
		
		panelApeMaterno.add(txtApeMaterno);
		panelContrasena.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelContrasena.setBounds(30, 270, 414, 40);
		
		contentPane.add(panelContrasena);
		panelContrasena.setLayout(null);
		
		lblContrasena.setBounds(25, 10, 150, 20);
		lblContrasena.setFont(new Font("Dialog", Font.BOLD, 14));
		panelContrasena.add(lblContrasena);
		
		pswdContrasena.setBounds(246, 10, 144, 20);
		panelContrasena.add(pswdContrasena);
		panelConfirmarContra.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelConfirmarContra.setBounds(30, 320, 414, 40);
		
		getContentPane().add(panelConfirmarContra);
		panelConfirmarContra.setLayout(null);
		lblConfirmarContrasea.setBounds(25, 10, 180, 20);
		lblConfirmarContrasea.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panelConfirmarContra.add(lblConfirmarContrasea);
		pswdConfirmarContrasena.setBounds(245, 10, 144, 20);
		
		panelConfirmarContra.add(pswdConfirmarContrasena);
		
		panelIDcuenta.setLayout(null);
		panelIDcuenta.add(lblIDcuenta);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		
		/*
		this.panelIDcuenta.setOpaque(false);
		this.panelPropietario.setOpaque(false);
		this.panelApeMaterno.setOpaque(false);
		this.panelApePaterno.setOpaque(false);
		this.panelContrasena.setOpaque(false);
		this.panelConfirmarContra.setOpaque(false);
		*/
		
		this.panelIDcuenta.setBackground(new Color(180, 200, 210, 150));
		this.panelPropietario.setBackground(new Color(180, 200, 210, 150));
		this.panelApeMaterno.setBackground(new Color(180, 200, 210, 150));
		this.panelApePaterno.setBackground(new Color(180, 200, 210, 150));
		this.panelContrasena.setBackground(new Color(180, 200, 210, 150));
		this.panelConfirmarContra.setBackground(new Color(180, 200, 210, 150));
		
		panelIDcuenta.add(lblmostrarIDcuenta);
		
		txtPropietario.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtPropietario.setColumns(10);
		txtPropietario.setBounds(155, 10, 235, 20);
		panelPropietario.add(txtPropietario);
		
		setResizable(false);
		setTitle("Crear Cuenta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 488, 450);
		this.setLocationRelativeTo(null);
		btnRegistrarCuenta.setBounds(85, 370, 178, 27);
		getContentPane().add(btnRegistrarCuenta);
		
		btnRegistrarCuenta.setForeground(Color.WHITE);
		btnRegistrarCuenta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRegistrarCuenta.setBackground(new Color(100, 149, 237));
		
		this.btnRegistrarCuenta.addActionListener(this);
		this.getRootPane().setDefaultButton(btnRegistrarCuenta);
		btnCancelar.setBounds(270, 370, 120, 27);
		getContentPane().add(btnCancelar);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(100, 149, 237));
		lblTituloCrearCuenta.setOpaque(true);
		lblTituloCrearCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloCrearCuenta.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloCrearCuenta.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloCrearCuenta.setBackground(new Color(180, 200, 210, 150));
		lblTituloCrearCuenta.setBounds(130, 15, 215, 40);
		
		getContentPane().add(lblTituloCrearCuenta);
		this.btnCancelar.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LoginScreen b = new LoginScreen();
		try {
			BD_banco bd = new BD_banco();
			if(e.getSource() == this.btnRegistrarCuenta) {
				if (this.lblmostrarIDcuenta.getText().compareToIgnoreCase("ERROR") != 0) {
					if (bd.crear_cuenta(this.lblmostrarIDcuenta.getText(), this.txtPropietario.getText(), this.txtApePaterno.getText(), this.txtApeMaterno.getText(), this.pswdContrasena.getText(), this.pswdConfirmarContrasena.getText())) {
						this.setVisible(false);
						this.dispose();
						b.setVisible(true);
					}
				}
			}
			if(e.getSource() == this.btnCancelar) {
					this.setVisible(false);
					this.dispose();
					b.setVisible(true);
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
