package metodos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.*;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import ventanas.MenuPrincipal;
import ventanas.VentanaConsultar;
import ventanas.VentanaHistorialTransacciones;
import ventanas.VentanaRenovar;

public class BD_banco {
	public static Connection conexion;
	public static String string_comando, string_comando2;
	public static Statement comando;
	public static ResultSet tabla, tabla_aux;
	public static boolean primer_acceso = true;
	public static final Object[] tarjeta = new Object[4];
	public static final Object[] transaccion = new Object[9];
	
	public BD_banco(){
	}
	
	public void conectar_a_BD() throws ClassNotFoundException, CommunicationsException, SQLException {
		if (primer_acceso) {
			//////////PASO 1: TENER ACCESO AL DRIVER //////////
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			////////// PASO 2: CONECTAR CON LA BD //////////
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajero_atm", "root", ""); // Esto es la cadena de conexión.
			comando = conexion.createStatement();
			System.out.println("CONECTADO CORRECTAMENTE\n");
			
			primer_acceso = false;
		}
	}
	
	public String generarIDCuenta() {
		int num_cuenta = -1;
		int cifras_num_cuenta = 1;
		String id_cuenta = "";
		boolean lista_de_cuentas_vacia = true;
		
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			
			while (tabla.next()) {
				lista_de_cuentas_vacia = false;
				num_cuenta++;
				System.out.println(num_cuenta);
				if (num_cuenta >= 10000) {
					JOptionPane.showMessageDialog(null, "¡¡¡SE HA EXCEDIDO EL LÍMITE DE CUENTAS!!!");
					return "ERROR";
				}
				if (num_cuenta >= 10) {
					cifras_num_cuenta = 2;
					if (num_cuenta >= 100) {
						cifras_num_cuenta = 3;
						if (num_cuenta >= 1000) {
							cifras_num_cuenta = 4;
						}
					}
				}
				System.out.println("C000" + num_cuenta + " / " + tabla.getString("id_cuenta"));
				switch (cifras_num_cuenta) {
					case 1:
						if (!("C000" + num_cuenta).equals(tabla.getString("id_cuenta"))) {
							//System.out.println("CUBRIR HUECO CON C000");
							id_cuenta = "C000" + num_cuenta;
							return id_cuenta;
						}
						break;
					case 2:
						if (!("C00" + num_cuenta).equals(tabla.getString("id_cuenta"))) {
							//System.out.println("CUBRIR HUECO CON C00");
							id_cuenta = "C00" + num_cuenta;
							return id_cuenta;
						}
						break;
					case 3:
						if (!("C0" + num_cuenta).equals(tabla.getString("id_cuenta"))) {
							//System.out.println("CUBRIR HUECO CON C0");
							id_cuenta = "C0" + num_cuenta;
							return id_cuenta;
						}
						break;
					case 4:
						if (!("C" + num_cuenta).equals(tabla.getString("id_cuenta"))) {
							//System.out.println("CUBRIR HUECO CON C");
							id_cuenta = "C" + num_cuenta;
							return id_cuenta;
						}
						break;
					default:
						JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (switch cifras_num_cuenta1)...");
						break;
				}
			}
			if (lista_de_cuentas_vacia) {
				//System.out.println("PRIMERA CUENTA");
				id_cuenta = "C0000";
			}
			else {
				//System.out.println("NO PRIMERA CUENTA");
				num_cuenta++;
				if (num_cuenta >= 10) {
					cifras_num_cuenta = 2;
					if (num_cuenta >= 100) {
						cifras_num_cuenta = 3;
						if (num_cuenta >= 1000) {
							cifras_num_cuenta = 4;
						}
					}
				}
				switch (cifras_num_cuenta) {
					case 1:
						id_cuenta = "C000" + num_cuenta;
						break;
					case 2:
						id_cuenta = "C00" + num_cuenta;
						break;
					case 3:
						id_cuenta = "C0" + num_cuenta;
						break;
					case 4:
						id_cuenta = "C" + num_cuenta;
						break;
					default:
						break;
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return id_cuenta;
	}
	
	public boolean crear_cuenta(String id_cuenta, String nom_propietario, String ap_paterno_propietario, String ap_materno_propietario, String contrasena, String confirmar_contra) {
		if (id_cuenta.length() == 0 || nom_propietario.length() == 0 || ap_paterno_propietario.length() == 0 || ap_materno_propietario.length() == 0 || contrasena.length() == 0) {
			JOptionPane.showMessageDialog(null, "Asegúrese de llenar todos los campos correctamente antes de registrarse.");
			return false;
		}
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			while (tabla.next()) {
				if (id_cuenta.compareToIgnoreCase(tabla.getString("id_cuenta")) == 0) {
					JOptionPane.showMessageDialog(null, "El ID de cuenta ya existe. Recargue esta ventana para generar uno nuevo.");
					return false;
				}
			}
			
			if (contrasena.compareTo(confirmar_contra) != 0) {
				JOptionPane.showMessageDialog(null, "La contraseña confirmada no coincide con la que introdujo. Intente de nuevo.");
				return false;
			}
			else {
				if (contrasena.length() <= 7) {
					int opcion = JOptionPane.showConfirmDialog(null, "<html>ADVERTENCIA: se recomienda que la contraseña tenga una<br>longitud mayor a 7 caracteres para mayor seguridad.<br>¿Está seguro/a que desea continuar con la contraseña que introdujo?</html>", "Advertencia de contraseña", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcion != JOptionPane.YES_OPTION) {
						return false;
					}
				}
				if (contrasena.length() > 30) {
					JOptionPane.showMessageDialog(null, "La contraseña no puede tener más de 30 caracteres.");
					return false;
				}
			}
			
			string_comando = "INSERT INTO info_cuenta (id_cuenta, sesion_activa, nom_propietario, ap_paterno_propietario, ap_materno_propietario, contrasena) VALUES ('" + id_cuenta + "', 0, '" + nom_propietario + "', '" + ap_paterno_propietario + "', '" + ap_materno_propietario + "', '" + contrasena + "')";
			comando.executeUpdate(string_comando);
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Cuenta registrada exitósamente");
		return true;
	}
	
	public boolean autenticacion_exitosa(String id_cuenta, String contra_introducida) {
		boolean existe_cuenta = false;
		String contra_real = "";
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			while (tabla.next()) {
				if (id_cuenta.compareTo(tabla.getString("id_cuenta")) == 0) {
					existe_cuenta = true;
					contra_real = tabla.getString("contrasena");
					break;
				}
			}
			if (existe_cuenta) {
				if (contra_introducida.compareTo(contra_real) == 0) {
					if (!tabla.getBoolean("sesion_activa")) {
						Icon palomita = new ImageIcon(getClass().getResource("/recursos/icono_palomita_verde.png"));
						JOptionPane.showMessageDialog(null, "¡Inicio de sesión correcto!", "Autenticación Exitosa", JOptionPane.PLAIN_MESSAGE, palomita);
						comando.executeUpdate("UPDATE info_cuenta SET sesion_activa = 1 WHERE id_cuenta = '" + id_cuenta + "'");
					}
					else {
						JOptionPane.showMessageDialog(null, "La sesión de esta cuenta está activa. Ciérrela e intente loguearse de nuevo.");
						return false;
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Contraseña errónea. Intente de nuevo.", "Contraseña Errónea", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "El ID de cuenta especificado no existe. Intente de nuevo.");
				return false;
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			return false;
		}
		return true;
	}
	
	public void cerrarSesion(String id_cuenta) {
		try {
			comando.executeUpdate("UPDATE info_cuenta SET sesion_activa = 0 WHERE id_cuenta = '" + id_cuenta + "'");
		}
		catch (SQLException e1) {
			System.out.println("NO SE PUDO CERRAR LA SESIÓN DE " + id_cuenta);
		}
	}
	
	public boolean verifContrasena(String id_cuenta, String contra) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta WHERE id_cuenta = '" + id_cuenta + "'");
			tabla.next();
			if (contra.equals(tabla.getString("contrasena"))) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "La contraseña introducida no coincide con la actual. Intente de nuevo.");
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return false;
	}
	
	public boolean cambiarContrasena(String id_cuenta, String contraNueva, String confirmarContraNueva) {
		if (contraNueva.length() == 0) {
			JOptionPane.showMessageDialog(null, "Debe introducir una contraseña nueva.");
			return false;
		}
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta WHERE id_cuenta = '" + id_cuenta + "'");
			tabla.next();
			if (contraNueva.equals(confirmarContraNueva)) {
				if (contraNueva.length() <= 7) {
					int opcion = JOptionPane.showConfirmDialog(null, "<html>ADVERTENCIA: se recomienda que la contraseña tenga una<br>longitud mayor a 7 caracteres para mayor seguridad.<br>¿Está seguro/a que desea continuar con la contraseña que introdujo?</html>", "Advertencia de contraseña", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcion != JOptionPane.YES_OPTION) {
						return false;
					}
				}
				if (contraNueva.length() > 30) {
					JOptionPane.showMessageDialog(null, "La contraseña no puede tener más de 30 caracteres.");
					return false;
				}
				comando.executeUpdate("UPDATE info_cuenta SET contrasena = '" + contraNueva + "' WHERE id_cuenta = '" + id_cuenta + "'");
				JOptionPane.showMessageDialog(null, "Contraseña cambiada con éxito");
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "La contraseña confirmada no coincide con la que introdujo. Intente de nuevo.");
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return false;
	}
	
	public String generarIDTarjeta() {
		int num_tarjeta = -1;
		int cifras_num_tarjeta = 1;
		String id_tarjeta = "";
		boolean lista_de_tarjetas_vacia = true;
		
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta");
			
			while (tabla.next()) {
				lista_de_tarjetas_vacia = false;
				num_tarjeta++;
				System.out.println(num_tarjeta);
				if (num_tarjeta >= 10000) {
					JOptionPane.showMessageDialog(null, "¡¡¡SE HA EXCEDIDO EL LÍMITE DE TARJETAS!!!");
					return "ERROR";
				}
				if (num_tarjeta >= 10) {
					cifras_num_tarjeta = 2;
					if (num_tarjeta >= 100) {
						cifras_num_tarjeta = 3;
						if (num_tarjeta >= 1000) {
							cifras_num_tarjeta = 4;
						}
					}
				}
				System.out.println("T000" + num_tarjeta + " / " + tabla.getString("id_tarjeta"));
				switch (cifras_num_tarjeta) {
					case 1:
						if (!("T000" + num_tarjeta).equals(tabla.getString("id_tarjeta"))) {
							//System.out.println("CUBRIR HUECO CON T000");
							id_tarjeta = "T000" + num_tarjeta;
							return id_tarjeta;
						}
						break;
					case 2:
						if (!("T00" + num_tarjeta).equals(tabla.getString("id_tarjeta"))) {
							//System.out.println("CUBRIR HUECO CON T00");
							id_tarjeta = "T00" + num_tarjeta;
							return id_tarjeta;
						}
						break;
					case 3:
						if (!("T0" + num_tarjeta).equals(tabla.getString("id_tarjeta"))) {
							//System.out.println("CUBRIR HUECO CON T0");
							id_tarjeta = "T0" + num_tarjeta;
							return id_tarjeta;
						}
						break;
					case 4:
						if (!("T" + num_tarjeta).equals(tabla.getString("id_tarjeta"))) {
							//System.out.println("CUBRIR HUECO CON C");
							id_tarjeta = "T" + num_tarjeta;
							return id_tarjeta;
						}
						break;
					default:
						JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (switch cifras_num_cuenta1)...");
						break;
				}
			}
			if (lista_de_tarjetas_vacia) {
				//System.out.println("PRIMERA TARJETA");
				id_tarjeta = "T0000";
			}
			else {
				//System.out.println("NO PRIMERA TARJETA");
				num_tarjeta++;
				if (num_tarjeta >= 10) {
					cifras_num_tarjeta = 2;
					if (num_tarjeta >= 100) {
						cifras_num_tarjeta = 3;
						if (num_tarjeta >= 1000) {
							cifras_num_tarjeta = 4;
						}
					}
				}
				switch (cifras_num_tarjeta) {
					case 1:
						id_tarjeta = "T000" + num_tarjeta;
						break;
					case 2:
						id_tarjeta = "T00" + num_tarjeta;
						break;
					case 3:
						id_tarjeta = "T0" + num_tarjeta;
						break;
					case 4:
						id_tarjeta = "T" + num_tarjeta;
						break;
					default:
						break;
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return id_tarjeta;
	}
	
	public String getNomPropietarioDeIDCuenta(String id_cuenta) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			while (tabla.next()) {
				if (id_cuenta.compareTo(tabla.getString("id_cuenta")) == 0) {
					//JOptionPane.showMessageDialog(null, "Cuenta obtenida: " + tabla.getString("id_cuenta"));
					//JOptionPane.showMessageDialog(null, "nom_propietario de la cuenta obtenida: " + tabla.getString("nom_propietario"));
					return tabla.getString("nom_propietario");
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return "ERROR";
	}
	
	public String getApPaternoPropietarioDeIDCuenta(String id_cuenta) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			while (tabla.next()) {
				if (id_cuenta.compareTo(tabla.getString("id_cuenta")) == 0) {
					//JOptionPane.showMessageDialog(null, "Cuenta obtenida: " + tabla.getString("id_cuenta"));
					//JOptionPane.showMessageDialog(null, "nom_propietario de la cuenta obtenida: " + tabla.getString("nom_propietario"));
					return tabla.getString("ap_paterno_propietario");
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return "ERROR";
	}
	
	public String getApMaternoPropietarioDeIDCuenta(String id_cuenta) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_cuenta");
			while (tabla.next()) {
				if (id_cuenta.compareTo(tabla.getString("id_cuenta")) == 0) {
					//JOptionPane.showMessageDialog(null, "Cuenta obtenida: " + tabla.getString("id_cuenta"));
					//JOptionPane.showMessageDialog(null, "nom_propietario de la cuenta obtenida: " + tabla.getString("nom_propietario"));
					return tabla.getString("ap_materno_propietario");
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return "ERROR";
	}
	
	public boolean agregarTarjeta(String id_cuenta, int CVV_CVC, String fechaVencimiento, String nom_propietario, String ap_paterno_propietario, String ap_materno_propietario, String numTarjeta, float saldo) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta");
			string_comando = "SELECT * FROM info_tarjeta WHERE id_tarjeta = '" + numTarjeta + "')";
			while (tabla.next()) {
				if (numTarjeta.compareToIgnoreCase(tabla.getString("id_tarjeta")) == 0) {
					JOptionPane.showMessageDialog(null, "El ID de tarjeta ya existe. Recargue esta ventana para generar uno nuevo.");
					return false;
				}
			}
			string_comando = "INSERT INTO info_tarjeta (id_tarjeta, cvv_cvc, fecha_vencimiento, id_cuenta, nom_propietario, ap_paterno_propietario, ap_materno_propietario, saldo_actual) VALUES ('" + numTarjeta + "', " + CVV_CVC + ", '" + fechaVencimiento + "', '" + id_cuenta + "', '" + nom_propietario + "', '" + ap_paterno_propietario + "', '" + ap_materno_propietario + "', " + saldo + ")";
			comando.executeUpdate(string_comando);
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			JOptionPane.showMessageDialog(null, "Hubo un error al agregar la tarjeta");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Tarjeta agregada exitósamente");
		return true;
	}
	
	public void consultarTarjetas(String id_cuenta, JTable tablaDeVentana) {
		//VentanaConsultar.tablaTarjetas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaDeVentana.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel modeloTablaTarjetas = new DefaultTableModel();
		Object[] nombreColumnas = {"ID Tarjeta", "CVV/CVC", "Fecha Vencimiento", "Saldo Actual"};
		modeloTablaTarjetas.setColumnIdentifiers(nombreColumnas);
		//VentanaConsultar.tablaTarjetas.setModel(modeloTablaTarjetas);
		tablaDeVentana.setModel(modeloTablaTarjetas);
		//TableColumnModel modeloColumnas = VentanaConsultar.tablaTarjetas.getColumnModel();
		TableColumnModel modeloColumnas = tablaDeVentana.getColumnModel();
		TableColumn colIDTarjeta = modeloColumnas.getColumn(0);
		TableColumn colCVV_CVC = modeloColumnas.getColumn(1);
		TableColumn colFechaVencimiento = modeloColumnas.getColumn(2);
		TableColumn colSaldoActual = modeloColumnas.getColumn(3);
		colIDTarjeta.setPreferredWidth(100);
		colCVV_CVC.setPreferredWidth(100);
		colFechaVencimiento.setPreferredWidth(140);
		colSaldoActual.setPreferredWidth(140);
		try {
			//System.out.println("Tarjetas a nombre de " + nombre + ": ");
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				//System.out.println("ID tarjeta: " + tabla.getString("id_tarjeta"));
				tarjeta[0] = tabla.getString("id_tarjeta");
				tarjeta[1] = tabla.getString("cvv_cvc");
				tarjeta[2] = tabla.getString("fecha_vencimiento");
				tarjeta[3] = tabla.getString("saldo_actual") + " MXN";
				modeloTablaTarjetas.addRow(tarjeta);
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
	}
	
	public void buscarTarjetaPorID(String id_cuenta, String id_tarjeta) {
		boolean tarjeta_encontrada = false;
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				if (id_tarjeta.equals(tabla.getString("id_tarjeta"))) {
					
					VentanaConsultar.tablaTarjetas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					DefaultTableModel modeloTablaTarjetas = new DefaultTableModel();
					Object[] nombreColumnas = {"ID Tarjeta", "CVV/CVC", "Fecha Vencimiento", "Saldo Actual"};
					modeloTablaTarjetas.setColumnIdentifiers(nombreColumnas);
					VentanaConsultar.tablaTarjetas.setModel(modeloTablaTarjetas);
					TableColumnModel modeloColumnas = VentanaConsultar.tablaTarjetas.getColumnModel();
					TableColumn colIDTarjeta = modeloColumnas.getColumn(0);
					TableColumn colCVV_CVC = modeloColumnas.getColumn(1);
					TableColumn colFechaVencimiento = modeloColumnas.getColumn(2);
					TableColumn colSaldoActual = modeloColumnas.getColumn(3);
					colIDTarjeta.setPreferredWidth(100);
					colCVV_CVC.setPreferredWidth(100);
					colFechaVencimiento.setPreferredWidth(140);
					colSaldoActual.setPreferredWidth(140);
					
					tarjeta_encontrada = true;
					tarjeta[0] = tabla.getString("id_tarjeta");
					tarjeta[1] = tabla.getString("cvv_cvc");
					tarjeta[2] = tabla.getString("fecha_vencimiento");
					tarjeta[3] = tabla.getString("saldo_actual") + " MXN";
					modeloTablaTarjetas.addRow(tarjeta);
				}
			}
			if (!tarjeta_encontrada) {
				JOptionPane.showMessageDialog(null, "El ID de tarjeta especificado no existe o no es de su propiedad. Intente de nuevo.");
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
	}
	
	public String generarIDTransaccion() {
		int num_transaccion = 0;
		int cifras_num_registro = 1;
		String id_transaccion = "";
		
		try {
			tabla = comando.executeQuery("SELECT * FROM info_transacciones");
			
			while (tabla.next()) {
				num_transaccion++;
				if (num_transaccion >= 100000) {
					JOptionPane.showMessageDialog(null, "¡¡¡SE HA EXCEDIDO EL NÚMERO DE TRANSACCIONES!!!");
					return "ERROR";
				}
			}
			if (num_transaccion >= 10) {
				cifras_num_registro = 2;
				if (num_transaccion >= 100) {
					cifras_num_registro = 3;
					if (num_transaccion >= 1000) {
						cifras_num_registro = 4;
						if (num_transaccion >= 10000) {
							cifras_num_registro = 5;
						}
					}
				}
			}
			switch (cifras_num_registro) {
				case 1:
					id_transaccion = "TR" + "-0000" + num_transaccion; break;
				case 2:
					id_transaccion = "TR" + "-000" + num_transaccion; break;
				case 3:
					id_transaccion = "TR" + "-00" + num_transaccion; break;
				case 4:
					id_transaccion = "TR" + "-0" + num_transaccion; break;
				case 5:
					id_transaccion = "TR" + "-" + num_transaccion; break;
				default:
					JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (switch cifras_num_transaccion)...");
					break;
			}
			System.out.println("ID_TRANSACCION = " + id_transaccion);
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return id_transaccion;
	}
	
	public String getFechaYHoraActual() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Obtener la fecha actual con el formato seleccionado (primero imprime año, luego imprime mes, luego imprime día; posteriormente imprime hora, luego minutos y luego segundos del día)
		String fecha = sdf.format(new Date()); // El formato establecido se respeta a la hora de obtener la fecha actual con la instrucción "new Date()";
		return fecha;
	}
	
	public boolean verifSiHayTarjetasCreadas(String id_cuenta) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			if (!tabla.next()) {
				JOptionPane.showMessageDialog(null, "Usted no tiene tarjetas creadas en esta cuenta. No puede utilizar esta operación.");
				return false;
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			return false;
		}
		return true;
	}
	
	public String[] getInfoCuentaMedianteTarjeta(String id_tarjeta, String tipo_de_tarjeta) {
		String[] info_cuenta = {"", "", "", "", ""};
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_tarjeta = '" + id_tarjeta + "'");
			if (!tabla.next()) {
				JOptionPane.showMessageDialog(null, "El ID de tarjeta de " + tipo_de_tarjeta + " no existe");
				info_cuenta[0] = "ERROR";
				return info_cuenta;
			}
			else {
				info_cuenta[1] = tabla.getString("id_cuenta");
				info_cuenta[2] = tabla.getString("nom_propietario");
				info_cuenta[3] = tabla.getString("ap_paterno_propietario");
				info_cuenta[4] = tabla.getString("ap_materno_propietario");
			}
			System.out.println("INFORMACIÓN DE LA CUENTA DE " + tipo_de_tarjeta + ":");
			System.out.println("Tarjeta proporcionada para la búsqueda: " + id_tarjeta);
			System.out.println("ID de la cuenta: " + info_cuenta[1]);
			System.out.println("Nombre del propietario: " + info_cuenta[2]);
			System.out.println("Apellido paterno del propietario: " + info_cuenta[3]);
			System.out.println("Apellido materno del propietario: " + info_cuenta[4]);
			System.out.println("Si hubo error, debe aparecer \"ERROR\" a continuación: " + info_cuenta[0]);
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			info_cuenta[0] = "ERROR";
			return info_cuenta;
		}
		return info_cuenta;
	}
	
	
	public boolean realizarTransaccion(String id_cuenta_origen, String id_cuenta_destino, String id_transaccion, int tipo_transaccion, String fecha_y_hora_transaccion, float monto, String id_tarjeta_origen, String nom_propietario_origen, String ap_paterno_propietario_origen, String ap_materno_propietario_origen, String id_tarjeta_destino, String nom_propietario_destino, String ap_paterno_propietario_destino, String ap_materno_propietario_destino) {
		boolean tarjeta_encontrada = false;
		String string_tipo_transaccion = "";
		
		float saldo_actual_tarjeta_origen = 0.0f;
		float saldo_actual_tarjeta_destino = 0.0f;
		
		//String st_cambiar = "UPDATE tabladatos SET nombre = 'Escritorio', precio = 500 WHERE clave = 1 ";
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta_origen + "'");
			while (tabla.next()) {
				if (id_tarjeta_origen.compareTo(tabla.getString("id_tarjeta")) == 0) {
					tarjeta_encontrada = true;
						//System.out.println("ID DE TARJETA ENCONTRADA = " + tabla.getString("id_tarjeta"));
					saldo_actual_tarjeta_origen = tabla.getFloat("saldo_actual");
						//System.out.println("SALDO DE LA TARJETA " + tabla.getString("id_tarjeta") + " = " + tabla.getFloat("saldo_actual"));
						switch (tipo_transaccion) {
						case 1:
							if (monto <= 0) {
								JOptionPane.showMessageDialog(null, "Para poder depositar, necesita introducir un monto mayor a 0.");
								return false;
							}
							break;
						case 2:
							if (tabla.getFloat("saldo_actual") <= 0) {
								JOptionPane.showMessageDialog(null, "La tarjeta seleccionada no tiene dinero. No puede retirar efectivo de ella.");
								return false;
							}
							if (tabla.getFloat("saldo_actual") < monto) {
								//System.out.println(tabla.getString("id_tarjeta"));
								//System.out.println(tabla.getFloat("saldo_actual"));
								JOptionPane.showMessageDialog(null, "No tiene suficiente dinero para retirar la cantidad solicitada.");
								return false;
							}
							if (monto <= 0) {
								JOptionPane.showMessageDialog(null, "Para poder retirar efectivo, necesita introducir un monto mayor a 0.");
								return false;
							}
							break;
						case 3:
							if (tabla.getFloat("saldo_actual") <= 0) {
								JOptionPane.showMessageDialog(null, "La tarjeta de origen no tiene dinero. No puede usarla para transferir dinero.");
								return false;
							}
							if (tabla.getFloat("saldo_actual") < monto) {
								//System.out.println(tabla.getString("id_tarjeta"));
								//System.out.println(tabla.getFloat("saldo_actual"));
								JOptionPane.showMessageDialog(null, "La tarjeta de origen no tiene suficiente dinero para transferir la cantidad solicitada.");
								return false;
							}
							if (monto <= 0) {
								JOptionPane.showMessageDialog(null, "Para poder transferir dinero, necesita introducir un monto mayor a 0 en la tarjeta de origen.");
								return false;
							}
							if (id_tarjeta_origen.equals(id_tarjeta_destino)) {
								JOptionPane.showMessageDialog(null, "La tarjeta de origen debe ser diferente a la tarjeta de destino para poder realizar una transferencia.");
								return false;
							}
							break;
						default:
							JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (switch tipo_transaccion1)...");
							break;
					}
					break;
				}
				else {
					System.out.println("ESTA NO ES LA TARJETA. ID: " + tabla.getString("id_tarjeta"));
				}
			}
			if (!tarjeta_encontrada) {
				JOptionPane.showMessageDialog(null, "El ID de tarjeta especificado no existe o no es de su propiedad. Intente de nuevo.");
				return false;
			}
			else {
				switch (tipo_transaccion) {
				case 1:
					string_tipo_transaccion = "deposito";
					
					string_comando = "UPDATE info_tarjeta SET saldo_actual = " + (saldo_actual_tarjeta_origen + monto) + " WHERE id_tarjeta = '" + id_tarjeta_origen + "'";
					comando.executeUpdate(string_comando);
					
					string_comando = "INSERT INTO info_transacciones (id_transaccion, tipo_transaccion, fecha_y_hora_transaccion, id_cuenta_origen, id_tarjeta_origen, monto_origen, nom_propietario_origen, ap_paterno_propietario_origen, ap_materno_propietario_origen) VALUES ('" + id_transaccion + "', '" + string_tipo_transaccion + "', '" + fecha_y_hora_transaccion + "', '" + id_cuenta_origen + "', '" + id_tarjeta_origen + "', " + monto + ", '" + nom_propietario_origen + "', '" + ap_paterno_propietario_origen + "', '" + ap_materno_propietario_origen + "')";
					//System.out.println(string_comando);
					comando.executeUpdate(string_comando);
					
					VentanaInfoDepositoORetiro a = new VentanaInfoDepositoORetiro(id_transaccion, (nom_propietario_origen + " " + ap_paterno_propietario_origen + " " + ap_materno_propietario_origen), id_tarjeta_origen, Float.toString(saldo_actual_tarjeta_origen), Float.toString(saldo_actual_tarjeta_origen + monto), 1);
					a.setVisible(true);
					
					break;
					
				case 2:
					string_tipo_transaccion = "retiro";
					
					string_comando = "UPDATE info_tarjeta SET saldo_actual = " + (saldo_actual_tarjeta_origen - monto) + " WHERE id_tarjeta = '" + id_tarjeta_origen + "'";
					comando.executeUpdate(string_comando);
					
					string_comando = "INSERT INTO info_transacciones (id_transaccion, tipo_transaccion, fecha_y_hora_transaccion, id_cuenta_origen, id_tarjeta_origen, monto_origen, nom_propietario_origen, ap_paterno_propietario_origen, ap_materno_propietario_origen) VALUES ('" + id_transaccion + "', '" + string_tipo_transaccion + "', '" + fecha_y_hora_transaccion + "', '" + id_cuenta_origen + "', '" + id_tarjeta_origen + "', " + (-1 * monto) + ", '" + nom_propietario_origen + "', '" + ap_paterno_propietario_origen + "', '" + ap_materno_propietario_origen + "')";
					//System.out.println(string_comando);
					comando.executeUpdate(string_comando);
					
					VentanaInfoDepositoORetiro b = new VentanaInfoDepositoORetiro(id_transaccion, (nom_propietario_origen + " " + ap_paterno_propietario_origen + " " + ap_materno_propietario_origen), id_tarjeta_origen, Float.toString(saldo_actual_tarjeta_origen), Float.toString(saldo_actual_tarjeta_origen - monto), 2);
					b.setVisible(true);
					
					break;
					
				case 3:
					string_tipo_transaccion = "transferencia";
					
					string_comando = "UPDATE info_tarjeta SET saldo_actual = " + (saldo_actual_tarjeta_origen - monto) + " WHERE id_tarjeta = '" + id_tarjeta_origen + "'";
					comando.executeUpdate(string_comando);
					
					tabla_aux = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_tarjeta = '" + id_tarjeta_destino + "'");
						//System.out.println("Tarjeta destino a buscar = " + id_tarjeta_destino);
					tabla_aux.next();
					/*
					System.out.println("Tarjeta destino hallada = " + tabla_aux.getString("id_tarjeta"));
					System.out.println("AAAAA");
					System.out.println(tabla_aux.getFloat("saldo_actual"));
					System.out.println("AAAAA");
					*/
					saldo_actual_tarjeta_destino = tabla_aux.getFloat("saldo_actual");
							
					System.out.println("Saldo actual de la tarjeta de destino: " + saldo_actual_tarjeta_destino);
					string_comando = "UPDATE info_tarjeta SET saldo_actual = " + (saldo_actual_tarjeta_destino + monto) + " WHERE id_tarjeta = '" + id_tarjeta_destino + "'";
					comando.executeUpdate(string_comando);
					
					string_comando = "INSERT INTO info_transacciones (id_transaccion, tipo_transaccion, fecha_y_hora_transaccion, id_cuenta_origen, id_tarjeta_origen, monto_origen, nom_propietario_origen, ap_paterno_propietario_origen, ap_materno_propietario_origen, id_cuenta_destino, id_tarjeta_destino, monto_destino, nom_propietario_destino, ap_paterno_propietario_destino, ap_materno_propietario_destino) VALUES ('" + id_transaccion + "', '" + string_tipo_transaccion + "', '" + fecha_y_hora_transaccion + "', '" + id_cuenta_origen + "', '" + id_tarjeta_origen + "', " + (-1 * monto) + ", '" + nom_propietario_origen + "', '" + ap_paterno_propietario_origen + "', '" + ap_materno_propietario_origen + "', '" + id_cuenta_destino + "', '" + id_tarjeta_destino + "', " + monto + ", '" + nom_propietario_destino + "', '" + ap_paterno_propietario_destino + "', '" + ap_materno_propietario_destino + "')";
					System.out.println(string_comando);
					comando.executeUpdate(string_comando);
					
					VentanaInfoTransferencia c = new VentanaInfoTransferencia(id_transaccion, (nom_propietario_origen + " " + ap_paterno_propietario_origen + " " + ap_materno_propietario_origen), id_tarjeta_origen, Float.toString(saldo_actual_tarjeta_origen), Float.toString(saldo_actual_tarjeta_origen - monto), (nom_propietario_destino + " " + ap_paterno_propietario_destino + " " + ap_materno_propietario_destino), id_tarjeta_destino, Float.toString(saldo_actual_tarjeta_destino), Float.toString(saldo_actual_tarjeta_destino + monto));
					c.setVisible(true);
					break;
					
				default:
					JOptionPane.showMessageDialog(null, "ESTO NO DEBE MOSTRARSE (switch tipo_transaccion2)...");
					break;
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTOargh");
			return false;
		}
		return true;
	}
	public void mostrarHistorialDeTransacciones(String id_cuenta, boolean ordenar_desde_mas_reciente) {
		VentanaHistorialTransacciones.tableHistorial.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel modeloTablaHistorial = new DefaultTableModel();
		Object[] nombreColumnas = {"ID Transacción", "Tipo", "Fecha Y Hora", "ID Tarjeta Origen", "Monto Origen", "Nombre Propietario Origen", "ID Tarjeta Destino", "Monto Destino", "Nombre Propietario Destino"};
		modeloTablaHistorial.setColumnIdentifiers(nombreColumnas);
		VentanaHistorialTransacciones.tableHistorial.setModel(modeloTablaHistorial);
		TableColumnModel modeloColumnas = VentanaHistorialTransacciones.tableHistorial.getColumnModel();
		TableColumn colIDTransaccion = modeloColumnas.getColumn(0);
		TableColumn colTipoTransaccion = modeloColumnas.getColumn(1);
		TableColumn colFechYHora = modeloColumnas.getColumn(2);
		TableColumn colIDTarjetaOrigen = modeloColumnas.getColumn(3);
		TableColumn colMontoOrigen = modeloColumnas.getColumn(4);
		TableColumn colNombrePropietarioOrigen = modeloColumnas.getColumn(5);
		TableColumn colIDTarjetaDestino = modeloColumnas.getColumn(6);
		TableColumn colMontoDestino = modeloColumnas.getColumn(7);
		TableColumn colNombrePropietarioDestino = modeloColumnas.getColumn(8);
		
		colIDTransaccion.setPreferredWidth(110);
		colTipoTransaccion.setPreferredWidth(90);
		colFechYHora.setPreferredWidth(130);
		colIDTarjetaOrigen.setPreferredWidth(130);
		colMontoOrigen.setPreferredWidth(100);
		colNombrePropietarioOrigen.setPreferredWidth(230);
		colIDTarjetaDestino.setPreferredWidth(140);
		colMontoDestino.setPreferredWidth(110);
		colNombrePropietarioDestino.setPreferredWidth(230);
		
		try {
			//System.out.println("Tarjetas a nombre de " + nombre + ": ");
			if (ordenar_desde_mas_reciente) {
				tabla = comando.executeQuery("SELECT * FROM info_transacciones WHERE id_cuenta_origen = '" + id_cuenta + "' || id_cuenta_destino = '" + id_cuenta + "' ORDER BY info_transacciones.fecha_y_hora_transaccion DESC;");
			}
			else {
				tabla = comando.executeQuery("SELECT * FROM info_transacciones WHERE id_cuenta_origen = '" + id_cuenta + "' || id_cuenta_destino = '" + id_cuenta + "' ORDER BY info_transacciones.fecha_y_hora_transaccion ASC;");
			}
			while (tabla.next()) {
				transaccion[0] = tabla.getString("id_transaccion");
				transaccion[1] = tabla.getString("tipo_transaccion");
				transaccion[2] = tabla.getString("fecha_y_hora_transaccion");
				transaccion[3] = tabla.getString("id_tarjeta_origen");
				
				// transaccion[4] //
				if ((tabla.getString("monto_origen")).equals("0.0")) {
					transaccion[4] = "";
				}
				else {
					transaccion[4] = tabla.getString("monto_origen") + " MXN";
				}
				
				transaccion[5] = tabla.getString("nom_propietario_origen") + " " + tabla.getString("ap_paterno_propietario_origen") + " " + tabla.getString("ap_materno_propietario_origen");
				
				// transaccion[6] //
				if ((tabla.getString("id_tarjeta_destino") + " ").equals("null ")) {
					transaccion[6] = "N/A";
				}
				else {
					transaccion[6] = tabla.getString("id_tarjeta_destino");
				}
				
				// transaccion[7] //
				if ((tabla.getString("monto_destino") + " ").equals("null ")) {
					transaccion[7] = "N/A";
				}
				else {
					transaccion[7] = tabla.getString("monto_destino") + " MXN";
				}
				if ((tabla.getString("monto_destino") + " ").equals("0.0 ")) {
					transaccion[7] = "";
				}
				
				// transaccion[8] //
				if ((tabla.getString("nom_propietario_destino") + " " + tabla.getString("ap_paterno_propietario_destino") + " " + tabla.getString("ap_materno_propietario_destino")).equals("null null null")) {
					transaccion[8] = "N/A";
				}
				else {
					transaccion[8] = tabla.getString("nom_propietario_destino") + " " + tabla.getString("ap_paterno_propietario_destino") + " " + tabla.getString("ap_materno_propietario_destino");
				}
				
				modeloTablaHistorial.addRow(transaccion);
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
	}
	
	public ArrayList<Object> getListaTarjetasDeBD() {
		ArrayList<Object> listaTarjetas = new ArrayList<Object>();
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta");
			while (tabla.next()) {
				listaTarjetas.add(tabla.getString("id_tarjeta"));
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return listaTarjetas;
	}
	
	public ArrayList<Object> getListaTarjetasDeUsuario(String id_cuenta) {
		ArrayList<Object> listaTarjetas = new ArrayList<Object>();
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				listaTarjetas.add(tabla.getString("id_tarjeta"));
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return listaTarjetas;
	}
	
	public ArrayList<Object> getListaTarjetasVencidasDeUsuario(String id_cuenta) {
		ArrayList<Object> listaTarjetasVencidas = new ArrayList<Object>();
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fecha_actual = sdf.format(new Date());
				//System.out.println(fecha_actual);
				//System.out.println(tabla.getString("fecha_vencimiento"));
				Date f1 = sdf.parse(fecha_actual);
				//System.out.println("AAA");
				Date f2 = sdf.parse(tabla.getString("fecha_vencimiento"));
				//System.out.println("BBB");
				//System.out.println(f2.getTime() - f1.getTime());
				if ((f2.getTime() - f1.getTime()) < 0) {
					listaTarjetasVencidas.add(tabla.getString("id_tarjeta"));
				}
			}
		}
		catch (ParseException e1) {
			System.out.println("ERROR AL PARSEAR FECHAS (getListaTarjetasVencidasDeUsuario)");
		}
		catch (SQLException e2) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return listaTarjetasVencidas;
	}
	
	public boolean verifSiYaVencioLaTarjeta(String id_tarjeta, String tipo_tarjeta) {
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_tarjeta = '" + id_tarjeta + "'");
			tabla.next();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fecha_actual = sdf.format(new Date());
			try {
				System.out.println(fecha_actual);
				System.out.println(tabla.getString("fecha_vencimiento"));
				Date f1 = sdf.parse(fecha_actual);
				System.out.println("AAA");
				Date f2 = sdf.parse(tabla.getString("fecha_vencimiento"));
				System.out.println("BBB");
				System.out.println(f2.getTime() - f1.getTime());
				if ((f2.getTime() - f1.getTime()) < 0) {
					JOptionPane.showMessageDialog(null, "La tarjeta de " + tipo_tarjeta + " ha vencido. No puede usarla para ejecutar esta transacción.");
					return true;
				}
			}
			catch (ParseException e1) {
				System.out.println("ERROR AL PARSEAR FECHAS");
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return false;
	}
	
	public boolean mostrarTarjetasVencidas (String id_cuenta) {
		boolean hay_alguna_tarjeta_vencida = false;
		VentanaRenovar.tablaTarjetasVencidas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel modeloTablaTarjetas = new DefaultTableModel();
		Object[] nombreColumnas = {"ID Tarjeta", "CVV/CVC", "Fecha Vencimiento", "Saldo Actual"};
		modeloTablaTarjetas.setColumnIdentifiers(nombreColumnas);
		VentanaRenovar.tablaTarjetasVencidas.setModel(modeloTablaTarjetas);
		TableColumnModel modeloColumnas = VentanaRenovar.tablaTarjetasVencidas.getColumnModel();
		TableColumn colIDTarjeta = modeloColumnas.getColumn(0);
		TableColumn colCVV_CVC = modeloColumnas.getColumn(1);
		TableColumn colFechaVencimiento = modeloColumnas.getColumn(2);
		TableColumn colSaldoActual = modeloColumnas.getColumn(3);
		colIDTarjeta.setPreferredWidth(100);
		colCVV_CVC.setPreferredWidth(100);
		colFechaVencimiento.setPreferredWidth(140);
		colSaldoActual.setPreferredWidth(140);
		
		try {
			//System.out.println("Tarjetas a nombre de " + nombre + ": ");
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fecha_actual = sdf.format(new Date());
				//System.out.println(fecha_actual);
				//System.out.println(tabla.getString("fecha_vencimiento"));
				Date f1 = sdf.parse(fecha_actual);
				//System.out.println("AAA");
				Date f2 = sdf.parse(tabla.getString("fecha_vencimiento"));
				//System.out.println("BBB");
				//System.out.println(f2.getTime() - f1.getTime());
				if ((f2.getTime() - f1.getTime()) < 0) {
					hay_alguna_tarjeta_vencida = true;
					System.out.println("ID tarjeta venicda: " + tabla.getString("id_tarjeta"));
					tarjeta[0] = tabla.getString("id_tarjeta");
					tarjeta[1] = tabla.getString("cvv_cvc");
					tarjeta[2] = tabla.getString("fecha_vencimiento");
					tarjeta[3] = tabla.getString("saldo_actual") + " MXN";
					modeloTablaTarjetas.addRow(tarjeta);
				}
			}
			if (hay_alguna_tarjeta_vencida) {
				return true;
			}
		}
		catch (ParseException e1) {
			System.out.println("ERROR AL PARSEAR FECHAS");
		}
		catch (SQLException e2) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return false;
	}
	
	public void renovarTarjetaVencida(String id_cuenta, String id_tarjeta) {
		boolean tarjeta_encontrada = false;
		boolean tarjeta_vencida = false;
		
		int anio_sumado = 0;
		SimpleDateFormat anio_obtenido = new SimpleDateFormat("yyyy");
		String anio = anio_obtenido.format(new Date());
		anio_sumado = Integer.parseInt(anio);
		anio_sumado += 4;
		anio = Integer.toString(anio_sumado);
		
		SimpleDateFormat fecha_sin_anio = new SimpleDateFormat("MM/dd");
		String nueva_fecha_vencimiento = anio + "/" + fecha_sin_anio.format(new Date());
		
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				if (id_tarjeta.equals(tabla.getString("id_tarjeta"))) {
					tarjeta_encontrada = true;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String fecha_actual = sdf.format(new Date());
					//System.out.println(fecha_actual);
					//System.out.println(tabla.getString("fecha_vencimiento"));
					Date f1 = sdf.parse(fecha_actual);
					//System.out.println("AAA");
					Date f2 = sdf.parse(tabla.getString("fecha_vencimiento"));
					//System.out.println("BBB");
					//System.out.println(f2.getTime() - f1.getTime());
					if ((f2.getTime() - f1.getTime()) < 0) {
						tarjeta_vencida = true;
					}
				}
			}
			if (!tarjeta_encontrada) {
				JOptionPane.showMessageDialog(null, "El ID de tarjeta especificado no existe o no es de su propiedad. Intente de nuevo.");
			}
			else if (tarjeta_vencida) {
				comando.executeUpdate("UPDATE info_tarjeta SET fecha_vencimiento = '" + nueva_fecha_vencimiento + "' WHERE id_tarjeta = '" + id_tarjeta + "'");
				JOptionPane.showMessageDialog(null, "Tarjeta " + id_tarjeta + " renovada exitósamente.");
			}
			else {
				JOptionPane.showMessageDialog(null, "La tarjeta proporcionada no está vencida.");
			}
		}
		catch (ParseException e1) {
			System.out.println("ERROR AL PARSEAR FECHAS (renovarTarjetaVencida)");
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
	}
	
	public void renovarTodasLasTarjetasVencidas (String id_cuenta) {
		ArrayList<String> ids_tarjetas_vencidas = new ArrayList<String>();
		
		int anio_sumado = 0;
		SimpleDateFormat anio_obtenido = new SimpleDateFormat("yyyy");
		String anio = anio_obtenido.format(new Date());
		anio_sumado = Integer.parseInt(anio);
		anio_sumado += 4;
		anio = Integer.toString(anio_sumado);
		
		SimpleDateFormat fecha_sin_anio = new SimpleDateFormat("MM/dd");
		String nueva_fecha_vencimiento = anio + "/" + fecha_sin_anio.format(new Date());
		
		try {
			//System.out.println("Tarjetas a nombre de " + nombre + ": ");
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fecha_actual = sdf.format(new Date());
				//System.out.println(fecha_actual);
				//System.out.println(tabla.getString("fecha_vencimiento"));
				Date f1 = sdf.parse(fecha_actual);
				//System.out.println("AAA");
				Date f2 = sdf.parse(tabla.getString("fecha_vencimiento"));
				//System.out.println("BBB");
				//System.out.println(f2.getTime() - f1.getTime());
				if ((f2.getTime() - f1.getTime()) < 0) {
					ids_tarjetas_vencidas.add(tabla.getString("id_tarjeta"));
				}
			}
			for (int i = 0; i < ids_tarjetas_vencidas.size(); i++) {
				comando.executeUpdate("UPDATE info_tarjeta SET fecha_vencimiento = '" + nueva_fecha_vencimiento + "' WHERE id_tarjeta = '" + ids_tarjetas_vencidas.get(i) + "'");
			}
		}
		catch (ParseException e1) {
			System.out.println("ERROR AL PARSEAR FECHAS (renovarTodasLasTarjetasVencidas");
		}
		catch (SQLException e2) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
			//e2.printStackTrace();
		}
	}
	
	public int eliminarTarjetaDeUsuario(String id_cuenta, String id_tarjeta) {
		boolean tarjeta_encontrada = false;
		
		try {
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			while (tabla.next()) {
				if (id_tarjeta.equals(tabla.getString("id_tarjeta"))) {
					tarjeta_encontrada = true;
				}
			}
			if (!tarjeta_encontrada) {
				JOptionPane.showMessageDialog(null, "El ID de tarjeta especificado no existe o no es de su propiedad. Intente de nuevo.");
				return 2;
			}
			else {
				int opcion = JOptionPane.showConfirmDialog(null, "<html>¿Está seguro/a de eliminar la tarjeta " + id_tarjeta + "?<br><br>Se eliminará del sistema lo siguiente:<ul><li>La tarjeta indicada (también desaparecerá de las transferencias realizadas con otros usuarios)</li><li>Todas sus transacciones con esa tarjeta</li></ul>Una vez presione \"SÍ\", no habrá forma de revertir el proceso.</html>", "Eliminar Tarjeta", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (opcion == JOptionPane.YES_OPTION) {
					comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_origen = '', id_tarjeta_origen = '', monto_origen = 0, nom_propietario_origen = '', ap_paterno_propietario_origen = '', ap_materno_propietario_origen = '' WHERE id_tarjeta_origen = '" + id_tarjeta + "'");
					//System.out.println("Tarjeta eliminada de ID_TARJETA_ORIGEN");
					comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_destino = '', id_tarjeta_destino = '', monto_destino = 0, nom_propietario_destino = '', ap_paterno_propietario_destino = '', ap_materno_propietario_destino = '' WHERE id_tarjeta_destino = '" + id_tarjeta + "'");
					//System.out.println("Tarjeta eliminada de ID_TARJETA_DESTINO");
					comando.executeUpdate("DELETE FROM info_tarjeta WHERE id_tarjeta = '"  + id_tarjeta + "'");
					//System.out.println("Tarjeta eliminada de INFO_TARJETA");
					return 0;
				}
				else {
					return 2;
				}
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return 1;
	}
	
	public int eliminarTodasLasTarjetasDeUsuario (String id_cuenta) {
		try {
			int opcion = JOptionPane.showConfirmDialog(null, "<html>¿Está REALMENTE SEGURO/A que desea eliminar todas las tarjetas de<br>" + MenuPrincipal.nom_propietario_menu_principal + " " + MenuPrincipal.ap_paterno_propietario_menu_principal + " " + MenuPrincipal.ap_materno_propietario_menu_principal + " (ID: " + MenuPrincipal.id_cuenta_menu_principal + ")?<br><br>Una vez presione \"SÍ\", no habrá forma de revertir el proceso.</html>", "ELIMINAR TODAS LAS TARJETAS", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {
				comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_origen = '', id_tarjeta_origen = '', monto_origen = 0, nom_propietario_origen = '', ap_paterno_propietario_origen = '', ap_materno_propietario_origen = '' WHERE id_cuenta_origen = '" + id_cuenta + "'");
				//System.out.println("Tarjeta eliminada de ID_TARJETA_ORIGEN");
				comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_destino = '', id_tarjeta_destino = '', monto_destino = 0, nom_propietario_destino = '', ap_paterno_propietario_destino = '', ap_materno_propietario_destino = '' WHERE id_cuenta_destino = '" + id_cuenta + "'");
				//System.out.println("Tarjeta eliminada de ID_TARJETA_DESTINO");
				comando.executeUpdate("DELETE FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
				//System.out.println("Tarjeta eliminada de INFO_TARJETA");
				return 0;
			}
			else {
				return 2;
			}
		}
		catch (SQLException e1) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
		return 1;
	}
	
	public boolean eliminarCuentaUsuario (String id_cuenta) {
		try {
			// PARTE DE ELIMINACION DE TODAS LAS TARJETAS DEL USUARIO, INCLUYENDO SU ELIMINACIÓN EN TRANSACCIONES //
			comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_origen = '', id_tarjeta_origen = '', monto_origen = 0, nom_propietario_origen = '', ap_paterno_propietario_origen = '', ap_materno_propietario_origen = '' WHERE id_cuenta_origen = '" + id_cuenta + "'");
			comando.executeUpdate("UPDATE info_transacciones SET id_cuenta_destino = '', id_tarjeta_destino = '', monto_destino = 0, nom_propietario_destino = '', ap_paterno_propietario_destino = '', ap_materno_propietario_destino = '' WHERE id_cuenta_destino = '" + id_cuenta + "'");
			comando.executeUpdate("DELETE FROM info_tarjeta WHERE id_cuenta = '" + id_cuenta + "'");
			
			// PARTE DE LA ELIMINACIÓN DE LA CUENTA DEL USUARIO //
			comando.executeUpdate("DELETE FROM info_cuenta WHERE id_cuenta = '" + id_cuenta + "'");
		}
		catch (SQLException e1) {
			
		}
		return false;
	}
	
	/*
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
		//////////PASO 1: TENER ACCESO AL DRIVER //////////
		Class.forName("com.mysql.cj.jdbc.Driver");
		////////// PASO 2: CONECTAR CON LA BD //////////
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajero_atm", "root", ""); // Esto es la cadena de conexión.
			System.out.println("CONECTADO CORRECTAMENTE\n");
			comando = conexion.createStatement();
			tabla = comando.executeQuery("SELECT * FROM info_tarjeta");
			while (tabla.next()) {
				System.out.println(tabla.getInt("id_tarjeta"));
				System.out.println(tabla.getInt("cvv_cvc"));
				System.out.println(tabla.getDate("fecha_vencimiento"));
				System.out.println(tabla.getString("nom_propietario"));
				System.out.println(tabla.getFloat("saldo_actual"));
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Obtener la fecha actual con el formato seleccionado (primero imprime año, luego imprime mes, luego imprime día)
			String fecha = sdf.format(new Date()); // El formato establecido se respeta a la hora de obtener la fecha actual con la instrucción "new Date()";
			System.out.println("Fecha actual sin hora: " + fecha);
			String cambiar = "UPDATE info_tarjeta SET fecha_vencimiento = '" + fecha + "' WHERE id_tarjeta = 1";
			System.out.println(cambiar);
			
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fecha = sdf.format(new Date());
			System.out.println("Fecha actual con hora: " + fecha);
			cambiar = "UPDATE info_tarjeta SET fecha_vencimiento = '" + fecha + "' WHERE id_tarjeta = 1";
			System.out.println(cambiar);
			System.out.println();
			
			//comando.executeUpdate(cambiar);
			
			//tabla = comando.executeQuery("SELECT * FROM info_tarjeta");
			tabla = comando.executeQuery("SELECT * FROM info_transacciones");
			while (tabla.next()) {
				System.out.println(tabla.getInt("id_transaccion"));
				System.out.println(tabla.getInt("id_tarjeta_origen"));
				System.out.println(tabla.getInt("id_tarjeta_destino"));
				System.out.println(tabla.getString("tipo_transaccion"));
				System.out.println(tabla.getTimestamp("fecha_y_hora_transaccion"));
			}
			
			cambiar = "UPDATE info_transacciones SET fecha_y_hora_transaccion = '" + fecha + "' WHERE id_tarjeta_origen = 1011";
			comando.executeUpdate(cambiar);
			
			tabla = comando.executeQuery("SELECT * FROM info_transacciones");
			while (tabla.next()) {
				System.out.println(tabla.getInt("id_transaccion"));
				System.out.println(tabla.getInt("id_tarjeta_origen"));
				System.out.println(tabla.getInt("id_tarjeta_destino"));
				System.out.println(tabla.getString("tipo_transaccion"));
				System.out.println(tabla.getTimestamp("fecha_y_hora_transaccion"));
			}
		}
		catch (CommunicationsException e1) {
			System.out.println("CONEXIÓN FALLIDA");
		}
		catch (SQLException e2) {
			System.out.println("EXCEPCIÓN EN SQL, PROBABLEMENTE UN COMANDO INCORRECTO");
		}
	}
	*/
}
