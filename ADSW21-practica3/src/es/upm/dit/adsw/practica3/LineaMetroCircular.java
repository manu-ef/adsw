package es.upm.dit.adsw.practica3;

import java.awt.Color;
import java.util.List;
import java.util.NoSuchElementException;

public class LineaMetroCircular extends LineaMetroImpl {

	/**
	 * Construye una linea de metro circular
	 * @param id identificador de la linea
	 * @param color color con el que se representa
	 * @param tramos secuencia de tramos de la linea. 
	 * El destino de un tramo debe ser el origen del siguiente tramo.
	 * En estas lineas la estacion de origen del primer tramo y el destino del ultimo tramo deben 
	 * ser la misma estacion
	 * @param mapa mapa donde se dibuja la linea
	 */
	public LineaMetroCircular(int id, Color color, List<Tramo> tramos, MapaMetro mapa) {
		super(id,color,tramos,mapa);
		assert tramos.get(0).desde().equals(tramos.get(tramos.size()-1).hasta());
	}
	
}
