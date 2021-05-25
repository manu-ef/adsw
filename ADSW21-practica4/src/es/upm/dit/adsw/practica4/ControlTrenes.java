package es.upm.dit.adsw.practica4;

/**
 * Es una clase cuyo objetivo es sincronizar a las hebras (trenes).
 * Su objetivo es indicar si los trenes tienen que seguir moviendo o parar. 
 * 
 * @author mmiguel
 *
 */
public class ControlTrenes {

	private boolean finEjecucion=false;
	
	/**
	 * Este metodo permite fijar el valor del atributo con el que se le indica 
	 * a los trenes que deben empezar a parar. Deben ir a extremo de su linea 
	 * hacia donde se dirigen y alli parar
	 * @param fin valor con el que actualizamos el atributo finEjecucion
	 */
	public synchronized void setFinEjecucion(boolean fin) {
		finEjecucion=fin;
	}
	
	/**
	 * Los trenes cada vez que llegan a un extremo de su linea consultan con este 
	 * metodo si deben seguir moviendose
	 * @param tren tren que esta preguntando si debe continuar moviendose
	 * @return devuelve true cuando el tren debe seguir moviendose y false cuando debe parar
	 */
	public synchronized boolean continuoDandoVueltas(Tren tren) {
		return !finEjecucion;
	}
}
