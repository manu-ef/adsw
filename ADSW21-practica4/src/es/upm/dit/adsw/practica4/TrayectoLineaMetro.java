package es.upm.dit.adsw.practica4;

public interface TrayectoLineaMetro {

	/**
	 * Comprueba si se ha recorrido la ruta. Ya se ha llegado al destino.
	 * @return true si no hay mas movimientos que recorrer	 * @throws NoSuchElementException si no se ha fijado todavia la ruta
	 */
	boolean finMovimiento();
	
	/**
	 * Devuelve el siguiente tramo de la ruta
	 * @return siguiente tramo
	 */
	Tramo siguienteMovimiento();

}
