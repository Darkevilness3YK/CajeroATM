package ventanas;
import javax.swing.JOptionPane;

import metodos.BD_banco;

import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class TemporizadorHilo extends Thread{
	JLabel Temp;
	static boolean active=false;
	public TemporizadorHilo(JLabel temporizador){
		Temp=temporizador;
	}
	public void run(){
		try {
				if (MenuPrincipal.cont!=1){
					System.out.println("Error. Checar instancia principal inicializada.");
				} else {
					int x=0;
					while(active==true){
					Thread.sleep(1000);
					ejecutarHilo(x);
					x++;
					}
				}
		}
		catch(Exception e){
			System.out.println("Excepcion en el Hilo..."+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void ejecutarHilo(int x) {
		System.out.println(x+"-"+Thread.currentThread().getName());
		MenuPrincipal.seg--;
		if(MenuPrincipal.seg<0){
			MenuPrincipal.seg=59;
			MenuPrincipal.min--;
		}
		if(MenuPrincipal.min==0 && MenuPrincipal.seg==0){ // Cuando el temporizador llegue a "00:00", esto pasará...
			BD_banco bd = new BD_banco();
			bd.cerrarSesion(MenuPrincipal.id_cuenta_menu_principal); // Se cerrará la sesión (info_cuenta sesion_activa = 0 en la BD);
			active=false; // La bandera de que el temporizador está activo se pone en falso
			JOptionPane.showMessageDialog(null, "Su sesión ha finalizado automáticamente."); // Se lanza el mensaje de que la sesión ha finalizado
			Window[] ventana = JFrame.getWindows(); // Se obtiene un vector con todas las ventanas generadas durante la ejecución de la aplicación
			for (int i = 0; i < ventana.length; i++) { // Se inicia un ciclo para acceder a cada ventana
				if (!ventana[i].getName().equals("primeraVentana")) { // Si la ventana analizada no es la primera ventana...
					ventana[i].removeAll(); // Entonces a la ventana se eliminan sus componentes
					ventana[i].dispose(); // Y se le libera la memoria reservada
				}
			}
			//Arrays.asList(JFrame.getFrames()).forEach(e -> e.dispose());
			
			MenuPrincipal.id_cuenta_menu_principal = "";
			MenuPrincipal.nom_propietario_menu_principal = "";
			MenuPrincipal.ap_paterno_propietario_menu_principal = "";
			MenuPrincipal.ap_materno_propietario_menu_principal = "";
			MenuPrincipal.seg = 0;
			MenuPrincipal.min = 5;
			MenuPrincipal.cont = 0;
			MenuPrincipal.actua = false;
			MenuPrincipal.lblTempo.setText("05:00");
			TemporizadorHilo.active = false;
			
			LoginScreen a = new LoginScreen();
			a.setVisible(true);
		}
		String TextSeg="";
		String TextMin="";
		if(MenuPrincipal.seg<10){
			TextSeg="0"+MenuPrincipal.seg;
		}
		else{
			TextSeg=""+MenuPrincipal.seg;
		}
		if(MenuPrincipal.min<10){
			TextMin="0"+MenuPrincipal.min;
		}
		else{
			TextMin=""+MenuPrincipal.min;
		}
		String reloj=TextMin+":"+TextSeg;
		Temp.setText(reloj);
	}
}

