package ventanas;


import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.mxrck.autocompleter.TextAutoCompleter;

import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import metodos.BD_banco;
import metodos.RegistroTarjetas;
import javax.swing.border.LineBorder;
public class VentanaConsultar extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblNumTarjeta = new JLabel("Tarjetas a nombre de: ");
	private JLabel lblMostrarNomPropietario = new JLabel();
	private JButton btnAceptar = new JButton("Aceptar");
	private JLabel lblTempo = new JLabel("00:00");
	public static JTable tablaTarjetas = new JTable();
	private JScrollPane scrollPaneListaTarjetas = new JScrollPane();
	private JPanel panellblTarjeta = new JPanel();
	private JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private JPanel panelBuscar = new JPanel();
	private JLabel lblBuscar = new JLabel("Buscar ID de tarjeta:");
	private JTextField txtBuscarIDTarjeta = new JTextField();
	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnLimpiar = new JButton("Limpiar");
	private final JLabel lblTituloVentanaConsultar = new JLabel("Consultar Tarjetas");
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaConsultar frame = new VentanaConsultar();
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
	public VentanaConsultar() {
		BD_banco bd1 = new BD_banco();
		TextAutoCompleter ac = new TextAutoCompleter(this.txtBuscarIDTarjeta);
		ac.addItems(bd1.getListaTarjetasDeUsuario(MenuPrincipal.id_cuenta_menu_principal));
		txtBuscarIDTarjeta.setBounds(170, 10, 110, 20);
		txtBuscarIDTarjeta.setColumns(10);
		
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
		
		FondoVentanaConsultar f = new FondoVentanaConsultar();
		this.setContentPane(f);
		contentPane = getContentPane();
		setResizable(false);
		setTitle("Consultar Tarjetas");
		setDefaultCloseOperation(0);
		setBounds(100, 100, 558, 420);
		getContentPane().setLayout(null);
		
		this.setLocationRelativeTo(null);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		btnAceptar.setBounds(210, 325, 120, 27);
		getContentPane().add(btnAceptar);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setBackground(new Color(100, 149, 237));
		btnAceptar.setFont(new Font("Dialog", Font.BOLD, 14));
		
		btnAceptar.addActionListener(this);
		btnBuscar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		this.getRootPane().setDefaultButton(btnBuscar);
		lblTempo.setBounds(343, 358, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		panellblTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		
		// panellblTarjeta //
		panellblTarjeta.setBounds(30, 75, 480, 40);
		panellblTarjeta.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panellblTarjeta);
		
		panellblTarjeta.setLayout(null);
		lblNumTarjeta.setBounds(10, 10, 177, 20);
		panellblTarjeta.add(lblNumTarjeta);
		lblNumTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarNomPropietario.setBounds(200, 10, 265, 20);
		panellblTarjeta.add(lblMostrarNomPropietario);
		
		lblMostrarNomPropietario.setText(MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal);
		scrollPaneListaTarjetas.setBounds(30, 130, 480, 125);
		scrollPaneListaTarjetas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneListaTarjetas.setViewportView(tablaTarjetas);
		scrollPaneListaTarjetas.setBorder(new LineBorder(new Color(100, 149, 237)));
		getContentPane().add(scrollPaneListaTarjetas);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(170, 358, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		panelBuscar.setLayout(null);
		panelBuscar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelBuscar.setBackground(new Color(180, 200, 210, 150));
		panelBuscar.setBounds(30, 270, 480, 40);
		
		getContentPane().add(panelBuscar);
		lblBuscar.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBuscar.setBounds(10, 10, 160, 20);
		
		panelBuscar.add(lblBuscar);
		
		panelBuscar.add(txtBuscarIDTarjeta);
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnBuscar.setBackground(new Color(100, 149, 237));
		btnBuscar.setBounds(295, 10, 80, 20);
		
		panelBuscar.add(btnBuscar);
		btnLimpiar.setForeground(Color.WHITE);
		btnLimpiar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLimpiar.setBackground(new Color(100, 149, 237));
		btnLimpiar.setBounds(390, 10, 80, 20);
		
		panelBuscar.add(btnLimpiar);
		lblTituloVentanaConsultar.setOpaque(true);
		lblTituloVentanaConsultar.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloVentanaConsultar.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloVentanaConsultar.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloVentanaConsultar.setBackground(new Color(180, 200, 210, 150));
		lblTituloVentanaConsultar.setBounds(138, 17, 264, 40);
		
		getContentPane().add(lblTituloVentanaConsultar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		BD_banco bd = new BD_banco();
		if (e.getSource() == this.btnBuscar) {
			if (this.txtBuscarIDTarjeta.getText().equals("")) {
				bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaConsultar.tablaTarjetas);
			}
			else {
				bd.buscarTarjetaPorID(MenuPrincipal.id_cuenta_menu_principal, this.txtBuscarIDTarjeta.getText());
			}
		}
		if (e.getSource() == this.btnLimpiar) {
			this.txtBuscarIDTarjeta.setText("");
			bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaConsultar.tablaTarjetas);
		}
		if (e.getSource() == this.btnAceptar) {
			this.setVisible(false);
			this.dispose();
			a.setVisible(true);
		}
	}
	
	class FondoVentanaConsultar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo9.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
