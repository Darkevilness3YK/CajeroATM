package metodos;
/*
 * [EQUIPO 5 - INTEGRANTES]
 * - NOZ GAMBOA LUZ ANGELICA
 * - ORDOÑEZ POOL ALAN JAIR
 * - PEDRAZA SÁNCHEZ JAVIER AGUSTIN
 * - VAZQUEZ NIETO ADRIAN
 */

import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class RegistroTarjetas {
	public String nombre, numTarjeta,contra;
	public float saldo;
	
	public RegistroTarjetas() {
	}
	public boolean agregarRegistro(String nombre, String numTarjeta, float saldo, String contra) {
		long pos = 0;
		boolean repetido = false;
		
		RandomAccessFile arch = null;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e1) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		
		try {
			repetido = false;
			while (pos != arch.length()) {
				if (saldo < 0) {
					JOptionPane.showMessageDialog(null, "El saldo inicial no puede ser menor a 0.");
					return false;
				}
				arch.seek(pos);
				if (arch.readUTF().compareTo(numTarjeta) == 0) {
					JOptionPane.showMessageDialog(null, "El número de tarjeta ya existe. Introduzca otro.");
					return false;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
				}
			}
			
			if (repetido == false) {
				arch.seek(arch.length());
				arch.writeUTF(numTarjeta);
				arch.writeUTF(nombre);
				arch.writeFloat(saldo);
			}
		}
		catch (IOException e2) {
			System.out.println("EL ARCHIVO ESTÁ ABIERTO O NO SE PUEDE GRABAR EN ÉL");
		}
		try {
			arch.close();
			JOptionPane.showMessageDialog(null, "Tarjeta agregada exitósamente");
		}
		catch (IOException e3) {
			System.out.println("NO SE PUEDE CERRAR EL ARCHIVO");
		}
		return true;
	}
	public void leerRegistro(String numTarjeta) {
		RandomAccessFile arch = null;
		long pos = 0;
		boolean encontrado = false;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e4) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		try {
			arch.seek(pos); // Colocar la posición en 0 (long pos = 0)
			while (pos != arch.length()) { // Mientras la posición no sea la del tamaño del archivo (es decir, mientras no se alcance el final del archivo)
				if (arch.readUTF().compareTo(numTarjeta) == 0) {
					JOptionPane.showMessageDialog(null, "Información de la tarjeta: \n\n - Número de tarjeta: " + numTarjeta + "\n - Propietario: " + arch.readUTF() + "\n - Saldo total: " + arch.readFloat() + " MXN");
					pos = arch.getFilePointer();
					encontrado = true;
					break;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					// System.out.println(pos);
				}
			}
			if (encontrado == false) {
				JOptionPane.showMessageDialog(null, "Tarjeta no encontrada");
			}
		}
		catch (IOException e5) {
			System.out.println("EL ARCHIVO ESTÁ ABIERTO O NO SE PUEDE LEER " + e5);
		}
		try {
			arch.close();
		}
		catch (IOException e7) {
			System.out.println("NO SE PUEDE CERRAR EL ARCHIVO");
		}
	}
	
	public boolean leerContra(String numTarjeta, String contra) {
		RandomAccessFile arch = null;
		long pos = 0;
		boolean encontrado = false;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e4) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		try {
			arch.seek(pos); // Colocar la posición en 0 (long pos = 0)
			while (pos != arch.length()) { // Mientras la posición no sea la del tamaño del archivo (es decir, mientras no se alcance el final del archivo)
				if (arch.readUTF().compareTo(numTarjeta) == 0) {
					if (arch.readUTF().compareTo(contra) == 0) {
						
						pos = arch.getFilePointer();
						encontrado = true;
						return true;
					}
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					// System.out.println(pos);
				}
			}
			if (encontrado == false) {
				JOptionPane.showMessageDialog(null, "Tarjeta o contraseña no encontrada"); return false;
			}
		}
		catch (IOException e5) {
			System.out.println("ERROR DATOS NO ENCONTRADOS " + e5);
		}
		try {
			arch.close();
		}
		catch (IOException e7) {
			System.out.println("NO SE PUEDE CERRAR EL ARCHIVO");
		}
		return false;
	}
	
	
	
	
	public boolean depositar(String numTarjeta, float monto) {
		RandomAccessFile arch = null;
		String nombreTarjeta;
		float montoAnterior = 0, montoActual;
		long pos = 0, posSaldoAnt = 0;
		boolean tarjetaEncontrada = false;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e4) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		try {
			arch.seek(pos);
			// BUSCAR LA TARJETA
			while (pos != arch.length()) {
				if (arch.readUTF().compareTo(numTarjeta) == 0) {
					if (monto <= 0) {
						JOptionPane.showMessageDialog(null, "Para poder depositar, necesita introducir un monto mayor a 0.");
						return false;
					}
					nombreTarjeta = arch.readUTF();
					posSaldoAnt = arch.getFilePointer();
					montoAnterior = arch.readFloat();
					montoActual = montoAnterior + monto;
					arch.seek(posSaldoAnt);
					arch.writeFloat(montoActual);
					tarjetaEncontrada = true;
					VentanaInfoDepositoORetiro d1 = new VentanaInfoDepositoORetiro("", nombreTarjeta, numTarjeta, Float.toString(montoAnterior), Float.toString(montoActual), 1);
					return true;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					System.out.println(pos);
				}
			}
			if (tarjetaEncontrada == false) {
				JOptionPane.showMessageDialog(null, "Tarjeta no encontrada.");
			}
		}
		catch (IOException e1) {
			System.out.println("EL ARCHIVO ESTÁ ABIERTO O NO SE PUEDE LEER");
		}
		return false;
	}
	public boolean retirar(String numTarjeta, float monto) {
		RandomAccessFile arch = null;
		String nombreTarjeta;
		float montoAnterior = 0, montoActual;
		long pos = 0, posSaldoAnt = 0;
		boolean tarjetaEncontrada = false;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e4) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		try {
			arch.seek(pos);
			// BUSCAR LA TARJETA
			while (pos != arch.length()) {
				if (arch.readUTF().compareTo(numTarjeta) == 0) {
					nombreTarjeta = arch.readUTF();
					posSaldoAnt = arch.getFilePointer();
					montoAnterior = arch.readFloat();
					if (montoAnterior == 0) {
						JOptionPane.showMessageDialog(null, "La tarjeta seleccionada no tiene dinero. No puede retirar efectivo de ella.");
						return false;
					}
					if (montoAnterior < monto) {
						JOptionPane.showMessageDialog(null, "No tiene suficiente dinero para retirar la cantidad solicitada.");
						return false;
					}
					if (monto <= 0) {
						JOptionPane.showMessageDialog(null, "Para poder retirar efectivo, necesita introducir un monto mayor a 0.");
						return false;
					}
					montoActual = montoAnterior - monto;
					arch.seek(posSaldoAnt);
					arch.writeFloat(montoActual);
					tarjetaEncontrada = true;
					VentanaInfoDepositoORetiro e1 = new VentanaInfoDepositoORetiro("", nombreTarjeta, numTarjeta, Float.toString(montoAnterior), Float.toString(montoActual), 2);
					return true;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					System.out.println(pos);
				}
			}
			if (tarjetaEncontrada == false) {
				JOptionPane.showMessageDialog(null, "Tarjeta no encontrada.");
			}
		}
		catch (IOException e1) {
			System.out.println("EL ARCHIVO ESTÁ ABIERTO O NO SE PUEDE LEER");
		}
		return false;
	}
	public boolean hacerTransferencia(String tarjetaOrigen, String tarjetaDestino, float monto) {
		RandomAccessFile arch = null;
		String nombreOrigen, nombreDestino;
		float montoOrigen = 0, montoDestino = 0, montoActualOrigen = 0, montoActualDestino = 0;
		long pos = 0, posSaldoOrigen = 0, posSaldoDestino = 0, posTarjetaOrigen = 0, posTarjetaDestino = 0;
		boolean encontradoTarjetaOrigen = false, encontradoTarjetaDestino = false;
		try {
			arch = new RandomAccessFile("src/recursos/registros.bin", "rws");
		}
		catch (IOException e4) {
			System.out.println("NO EXISTE O NO SE ENCUENTRA EL ARCHIVO");
		}
		try {
			arch.seek(pos);
			// BUSCAR LA TARJETA DE ORIGEN
			while (pos != arch.length()) {
				posTarjetaOrigen = arch.getFilePointer();
				if (arch.readUTF().compareTo(tarjetaOrigen) == 0) {
					arch.readUTF();
					posSaldoOrigen = arch.getFilePointer();
					arch.readFloat();
					pos = arch.getFilePointer();
					encontradoTarjetaOrigen = true;
					break;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					System.out.println(pos);
				}
			}
			pos = 0;
			arch.seek(pos);
			// BUSCAR LA TARJETA DE DESTINO
			while (pos != arch.length()) {
				posTarjetaDestino = arch.getFilePointer();
				if (arch.readUTF().compareTo(tarjetaDestino) == 0) {
					arch.readUTF();
					posSaldoDestino = arch.getFilePointer();
					arch.readFloat();
					pos = arch.getFilePointer();
					encontradoTarjetaDestino = true;
					break;
				}
				else {
					arch.readUTF();
					arch.readFloat();
					pos = arch.getFilePointer();
					System.out.println(pos);
				}
			}
			if (encontradoTarjetaOrigen == true && encontradoTarjetaDestino == true) {
				arch.seek(posSaldoOrigen);
				montoOrigen = arch.readFloat();
				if (montoOrigen == 0) {
					JOptionPane.showMessageDialog(null, "La tarjeta de origen no tiene dinero. No puede transferir dinero de ella.");
					return false;
				}
				else {
					arch.seek(posSaldoDestino);
					montoDestino = arch.readFloat();
					if (montoOrigen < monto) {
						JOptionPane.showMessageDialog(null, "La tarjeta de origen no tiene suficiente dinero para transferir la cantidad solicitada.");
						return false;
					}
					if (monto <= 0) {
						JOptionPane.showMessageDialog(null, "Para poder transferir, necesita introducir un monto mayor a 0.");
						return false;
					}
					
					arch.seek(posSaldoOrigen);
					arch.writeFloat(montoOrigen - monto);
					arch.seek(posSaldoDestino);
					arch.writeFloat(montoDestino + monto);
					
					arch.seek(posTarjetaOrigen);
					arch.readUTF();
					nombreOrigen = arch.readUTF();
					montoActualOrigen = arch.readFloat();
					
					arch.seek(posTarjetaDestino);
					arch.readUTF();
					nombreDestino = arch.readUTF();
					montoActualDestino = arch.readFloat();
					VentanaInfoTransaccion e = new VentanaInfoTransaccion(nombreOrigen, tarjetaOrigen, Float.toString(montoOrigen), Float.toString(montoActualOrigen), nombreDestino, tarjetaDestino, Float.toString(montoDestino), Float.toString(montoActualDestino));
					
				}
			}
			else {
				if (encontradoTarjetaOrigen == false) {
					JOptionPane.showMessageDialog(null, "Tarjeta de origen no encontrada.");
				}
				if (encontradoTarjetaDestino == false) {
					JOptionPane.showMessageDialog(null, "Tarjeta de destino no encontrada.");
				}
				return false;
			}
		}
		catch (IOException e5) {
			System.out.println("EL ARCHIVO ESTÁ ABIERTO O NO SE PUEDE LEER " + e5);
		}
		try {
			arch.close();
		}
		catch (IOException e7) {
			System.out.println("NO SE PUEDE CERRAR EL ARCHIVO");
		}
		return true;
	}
}
