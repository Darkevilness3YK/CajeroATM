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
public class VentanaRenovar extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblTarjetasVencidas = new JLabel("Tarjetas vencidas a nombre de: ");
	private JLabel lblMostrarNomPropietario = new JLabel();
	private JButton btnAceptar = new JButton("Aceptar");
	private JLabel lblTempo = new JLabel("00:00");
	public static JTable tablaTarjetasVencidas = new JTable();
	private JScrollPane scrollPaneListaTarjetasVencidas = new JScrollPane();
	private JPanel panellblTarjeta = new JPanel();
	private JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private JPanel panelBuscar = new JPanel();
	private JLabel lblRenovarTarjeta = new JLabel("Renovar tarjeta:");
	private JTextField txtBuscarTarjetaVencida = new JTextField();
	private JButton btnRenovar = new JButton("Renovar");
	private JButton btnRenovarTodas = new JButton("Renovar todas");
	private final JLabel lblTituloRenovarTarjeta = new JLabel("Renovar Tarjeta");
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRenovar frame = new VentanaRenovar();
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
	public VentanaRenovar() {
		BD_banco bd1 = new BD_banco();
		TextAutoCompleter ac = new TextAutoCompleter(this.txtBuscarTarjetaVencida);
		ac.addItems(bd1.getListaTarjetasVencidasDeUsuario(MenuPrincipal.id_cuenta_menu_principal));
		txtBuscarTarjetaVencida.setBounds(135, 10, 100, 20);
		txtBuscarTarjetaVencida.setColumns(10);
		
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
		setTitle("Renovar Tarjeta");
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
		btnRenovar.addActionListener(this);
		btnRenovarTodas.addActionListener(this);
		
		this.getRootPane().setDefaultButton(btnRenovar);
		lblTempo.setBounds(343, 358, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		panellblTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		
		// panellblTarjeta //
		panellblTarjeta.setBounds(30, 75, 480, 40);
		panellblTarjeta.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panellblTarjeta);
		
		panellblTarjeta.setLayout(null);
		lblTarjetasVencidas.setBounds(10, 10, 230, 20);
		panellblTarjeta.add(lblTarjetasVencidas);
		lblTarjetasVencidas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarNomPropietario.setBounds(245, 10, 220, 20);
		panellblTarjeta.add(lblMostrarNomPropietario);
		
		lblMostrarNomPropietario.setText(MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal);
		scrollPaneListaTarjetasVencidas.setBounds(30, 130, 480, 125);
		scrollPaneListaTarjetasVencidas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneListaTarjetasVencidas.setViewportView(tablaTarjetasVencidas);
		scrollPaneListaTarjetasVencidas.setBorder(new LineBorder(new Color(100, 149, 237)));
		getContentPane().add(scrollPaneListaTarjetasVencidas);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoRestante.setBounds(170, 358, 160, 14);
		
		getContentPane().add(lblTiempoRestante);
		panelBuscar.setLayout(null);
		panelBuscar.setBorder(new LineBorder(new Color(100, 149, 237)));
		panelBuscar.setBackground(new Color(180, 200, 210, 150));
		panelBuscar.setBounds(30, 270, 480, 40);
		
		getContentPane().add(panelBuscar);
		lblRenovarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblRenovarTarjeta.setBounds(10, 10, 125, 20);
		
		panelBuscar.add(lblRenovarTarjeta);
		
		panelBuscar.add(txtBuscarTarjetaVencida);
		btnRenovar.setForeground(Color.WHITE);
		btnRenovar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRenovar.setBackground(new Color(100, 149, 237));
		btnRenovar.setBounds(245, 10, 90, 20);
		
		panelBuscar.add(btnRenovar);
		btnRenovarTodas.setForeground(Color.WHITE);
		btnRenovarTodas.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRenovarTodas.setBackground(new Color(100, 149, 237));
		btnRenovarTodas.setBounds(345, 10, 125, 20);
		
		panelBuscar.add(btnRenovarTodas);
		lblTituloRenovarTarjeta.setOpaque(true);
		lblTituloRenovarTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloRenovarTarjeta.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloRenovarTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloRenovarTarjeta.setBackground(new Color(180, 200, 210, 150));
		lblTituloRenovarTarjeta.setBounds(145, 17, 250, 40);
		
		getContentPane().add(lblTituloRenovarTarjeta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BD_banco bd = new BD_banco();
		VentanaAjustesDeCuenta a = new VentanaAjustesDeCuenta();
		if (e.getSource() == this.btnRenovar) {
			if (this.txtBuscarTarjetaVencida.getText().length() != 0) {
				int opcion = JOptionPane.showConfirmDialog(null, "¿Renovar la tarjeta " + this.txtBuscarTarjetaVencida.getText() + "?", "Renovar", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					bd.renovarTarjetaVencida(MenuPrincipal.id_cuenta_menu_principal, this.txtBuscarTarjetaVencida.getText());
				}
				if (!bd.mostrarTarjetasVencidas(MenuPrincipal.id_cuenta_menu_principal)) {
					System.out.println("Deteniendo Actualizador...");
					MenuPrincipal.actua=false;
					
					JOptionPane.showMessageDialog(null, "Todas las tarjetas vencidas han sido renovadas exitósamente.");
					this.setVisible(false);
					this.dispose();
					a.setVisible(true);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Debe introducir una ID de tarjeta a renovar.");
			}
		}
		if (e.getSource() == this.btnRenovarTodas) {
			int opcion = JOptionPane.showConfirmDialog(null, "<html>¿Renovar todas las tarjetas vencidas de<br>" + MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal + " (ID: " + MenuPrincipal.id_cuenta_menu_principal + ")?</html>", "Renovar Todas", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {
				bd.renovarTodasLasTarjetasVencidas(MenuPrincipal.id_cuenta_menu_principal);
				if (bd.mostrarTarjetasVencidas(MenuPrincipal.id_cuenta_menu_principal)) {
					JOptionPane.showMessageDialog(null, "Ha habido un error en la renovación.");
				}
				else {
					JOptionPane.showMessageDialog(null, "Todas las tarjetas vencidas han sido renovadas exitósamente.");
					this.setVisible(false);
					this.dispose();
					a.setVisible(true);
				}
			}
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
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoConsultar.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
