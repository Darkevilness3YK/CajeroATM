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
public class VentanaEliminarTarjeta extends JFrame implements ActionListener {

	private Container contentPane;
	private JLabel lblTarjetas = new JLabel("Tarjetas a nombre de: ");
	private JLabel lblMostrarNomPropietario = new JLabel();
	private JButton btnAceptar = new JButton("Aceptar");
	private JLabel lblTempo = new JLabel("00:00");
	public static JTable tablaTarjetas = new JTable();
	private JScrollPane scrollPaneListaTarjetasVencidas = new JScrollPane();
	private JPanel panellblTarjeta = new JPanel();
	private JLabel lblTiempoRestante = new JLabel("Tiempo restante de sesi\u00F3n:");
	private JPanel panelBuscar = new JPanel();
	private JLabel lblEliminarTarjeta = new JLabel("Eliminar tarjeta:");
	private JTextField txtBuscarTarjeta = new JTextField();
	private JButton btnEliminar = new JButton("Eliminar");
	private JButton btnEliminarTodas = new JButton("ELIMINAR TODAS");
	private final JLabel lblTituloEliminarTarjeta = new JLabel("Eliminar Tarjeta");
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEliminarTarjeta frame = new VentanaEliminarTarjeta();
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
	public VentanaEliminarTarjeta() {
		BD_banco bd1 = new BD_banco();
		TextAutoCompleter ac = new TextAutoCompleter(this.txtBuscarTarjeta);
		ac.addItems(bd1.getListaTarjetasDeUsuario(MenuPrincipal.id_cuenta_menu_principal));
		txtBuscarTarjeta.setBounds(135, 10, 100, 20);
		txtBuscarTarjeta.setColumns(10);
		
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
				int opcion = JOptionPane.showConfirmDialog(null, "�Salir de Cajero ATM? Se cerrar� tu sesi�n.", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
		setTitle("Eliminar Tarjeta");
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
		btnEliminar.addActionListener(this);
		btnEliminarTodas.addActionListener(this);
		
		this.getRootPane().setDefaultButton(btnEliminar);
		lblTempo.setBounds(343, 358, 60, 14);
		getContentPane().add(lblTempo);
		lblTempo.setHorizontalAlignment(SwingConstants.LEFT);
		panellblTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		
		// panellblTarjeta //
		panellblTarjeta.setBounds(30, 75, 480, 40);
		panellblTarjeta.setBackground(MenuPrincipal.colorPaneles);
		getContentPane().add(panellblTarjeta);
		
		panellblTarjeta.setLayout(null);
		lblTarjetas.setBounds(10, 10, 177, 20);
		panellblTarjeta.add(lblTarjetas);
		lblTarjetas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMostrarNomPropietario.setBounds(200, 10, 265, 20);
		panellblTarjeta.add(lblMostrarNomPropietario);
		
		lblMostrarNomPropietario.setText(MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal);
		scrollPaneListaTarjetasVencidas.setBounds(30, 130, 480, 125);
		scrollPaneListaTarjetasVencidas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneListaTarjetasVencidas.setViewportView(tablaTarjetas);
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
		lblEliminarTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEliminarTarjeta.setBounds(10, 10, 125, 20);
		
		panelBuscar.add(lblEliminarTarjeta);
		
		panelBuscar.add(txtBuscarTarjeta);
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnEliminar.setBackground(new Color(205, 92, 92));
		btnEliminar.setBorder(new LineBorder(new Color(208, 48, 48)));
		btnEliminar.setBounds(245, 10, 90, 20);
		
		panelBuscar.add(btnEliminar);
		btnEliminarTodas.setForeground(Color.WHITE);
		btnEliminarTodas.setFont(new Font("Dialog", Font.BOLD, 12));
		btnEliminarTodas.setBackground(new Color(205, 92, 92));
		btnEliminarTodas.setBorder(new LineBorder(new Color(208, 48, 48)));
		btnEliminarTodas.setBounds(345, 10, 125, 20);
		
		panelBuscar.add(btnEliminarTodas);
		lblTituloEliminarTarjeta.setOpaque(true);
		lblTituloEliminarTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloEliminarTarjeta.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTituloEliminarTarjeta.setBorder(new LineBorder(new Color(100, 149, 237)));
		lblTituloEliminarTarjeta.setBackground(new Color(180, 200, 210, 150));
		lblTituloEliminarTarjeta.setBounds(145, 17, 250, 40);
		
		getContentPane().add(lblTituloEliminarTarjeta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BD_banco bd = new BD_banco();
		VentanaAjustesDeCuenta a = new VentanaAjustesDeCuenta();
		if (e.getSource() == this.btnEliminar) {
			if (this.txtBuscarTarjeta.getText().length() != 0) {
				switch (bd.eliminarTarjetaDeUsuario(MenuPrincipal.id_cuenta_menu_principal, this.txtBuscarTarjeta.getText())) {
					case 0:
						JOptionPane.showMessageDialog(null, "La tarjeta " + this.txtBuscarTarjeta.getText() + " ha sido eliminada exit�samente.");
						bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaEliminarTarjeta.tablaTarjetas);
						break;
					case 1:
						JOptionPane.showMessageDialog(null, "Hubo un error con la eliminaci�n");
						break;
					case 2:
						break;
					default:
						JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (eliminarTarjeta)");
						break;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Debe introducir una ID de tarjeta a renovar.");
			}
		}
		if (e.getSource() == this.btnEliminarTodas) {
			switch (bd.eliminarTodasLasTarjetasDeUsuario(MenuPrincipal.id_cuenta_menu_principal)) {
				case 0:
					JOptionPane.showMessageDialog(null, "Todas las tarjetas han sido eliminadas exit�samente.");
					bd.consultarTarjetas(MenuPrincipal.id_cuenta_menu_principal, VentanaEliminarTarjeta.tablaTarjetas);
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "Hubo un error con la eliminaci�n");
					break;
				case 2:
					break;
				default:
					JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (eliminarTodas)");
					break;
			}
		}
		if (e.getSource() == this.btnAceptar) {
			System.out.println("Deteniendo Actualizador...");
			MenuPrincipal.actua=false;
			
			this.setVisible(false);
			this.dispose();
			a.setVisible(true);
		}
	}
	
	class FondoVentanaConsultar extends JPanel {
		private Image fondo;
		public void paint (Graphics g) {
			fondo = new ImageIcon(getClass().getResource("/recursos/FondoNuevo8.png")).getImage();
			g.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
