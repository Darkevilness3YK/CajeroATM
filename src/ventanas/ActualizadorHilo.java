package ventanas;
import javax.swing.JLabel;


public class ActualizadorHilo extends Thread{
	JLabel Temp;
	public ActualizadorHilo(JLabel temporizador){
		Temp=temporizador;
	}
	public void run(){
		try {
				if (MenuPrincipal.cont==1){
					boolean terminar=false;
					if(terminar==false){
						do{
							int x=0;
							System.out.println("Ya hay una instancia corriendo.");
							actualizar(x);
							x++;
							if(MenuPrincipal.actua==false){
								terminar=true;
							}
							Thread.sleep(1000);

						}while(terminar==false);
					} else if (terminar == true){
						System.out.println("Hilo terminado...");
					}
				} else{
					String reloj="";
					Temp.setText(reloj);
				}
		}
		catch(Exception e){
			System.out.println("Excepcion en el Hilo..."+e.getMessage());
		}
	}
	
	private void actualizar(int x) {
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

