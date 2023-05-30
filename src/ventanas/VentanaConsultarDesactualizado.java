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
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
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
import metodos.RegistroTarjetas;
public class VentanaConsultarDesactualizado extends JFrame implements ActionListener {

	private Container contentPane;
	private JTextField txtNumTarjeta = new JTextField();
	private JPanel panelNumTarjeta = new JPanel();
	private JLabel lblNumTarjeta = new JLabel("N\u00FAmero de tarjeta:");
	private JPanel panelBotones = new JPanel();
	private JButton btnComprobarSaldo = new JButton("Consultar saldo");
	private JButton btnCancelar = new JButton("Cancelar");
	private final JLabel lblTempo = new JLabel("00:00");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaConsultarDesactualizado frame = new VentanaConsultarDesactualizado();
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
	public VentanaConsultarDesactualizado() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				ActualizadorHilo actu=new ActualizadorHilo(lblTempo);
				System.out.println("Inicia Actualizador...");
				MenuPrincipal.actua=true;
				actu.start();
			}
		});
		FondoVentanaConsultar f = new FondoVentanaConsultar();
		this.setContentPane(f);
		contentPane = getContentPane();
		setResizable(false);
		setTitle("Consultar Saldo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 160);
		
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		contentPane.add(panelNumTarjeta);
		lblNumTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		
		lblNumTarjeta.setBounds(40, 20, 144, 17);
		txtNumTarjeta.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		txtNumTarjeta.setBounds(250, 17, 150, 20);
		txtNumTarjeta.setColumns(10);
		panelNumTarjeta.setLayout(null);
		panelNumTarjeta.add(lblNumTarjeta);
		panelNumTarjeta.add(txtNumTarjeta);
		
		contentPane.add(panelBotones);
		btnComprobarSaldo.setBounds(73, 5, 170, 27);
		btnComprobarSaldo.setForeground(Color.WHITE);
		btnComprobarSaldo.setBackground(new Color(100, 149, 237));
		btnComprobarSaldo.setFont(new Font("Dialog", Font.BOLD, 14));
		
		btnComprobarSaldo.addActionListener(this);
		btnCancelar.setBounds(248, 5, 120, 27);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(100, 149, 237));
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		panelBotones.setLayout(null);
		panelBotones.add(btnComprobarSaldo);
		panelBotones.add(btnCancelar);
		btnCancelar.addActionListener(this);
		
		this.setLocationRelativeTo(null);
		//System.out.println("Tamano: " + this.getSize());
		this.panelBotones.setOpaque(false);
		this.panelNumTarjeta.setOpaque(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono_moneda.png")).getImage());
		this.getRootPane().setDefaultButton(btnComprobarSaldo);
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setBounds(199, 40, 46, 14);
		
		panelBotones.add(lblTempo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuPrincipal a = new MenuPrincipal(MenuPrincipal.id_cuenta_menu_principal, MenuPrincipal.nom_propietario_menu_principal, MenuPrincipal.ap_paterno_propietario_menu_principal, MenuPrincipal.ap_materno_propietario_menu_principal);
		RegistroTarjetas r = new RegistroTarjetas();
		
		try {
			if (e.getSource() == this.btnComprobarSaldo) {
				r.leerRegistro(this.txtNumTarjeta.getText());
			}
			if (e.getSource() == this.btnCancelar) {
				this.setVisible(false);
				a.setVisible(true);
			}
		}
		catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos antes de proceder.");
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
