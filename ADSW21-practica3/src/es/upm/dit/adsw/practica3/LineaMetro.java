package es.upm.dit.adsw.practica3;

import java.awt.Color;
import java.util.List;

public interface LineaMetro {
	
	/**
	 * Indica el mapa en el que se representa esta linea de metro
	 * @return el mapa representado con un grafo de estaciones y tramos
	 */
	MapaMetro getMapa();
	
	/**
	 * Devuelve la secuencia de estaciones que incluye la linea
	 * @return secuencia de estaciones
	 */
	List<Estacion> getEstaciones();
	
	/**
	 * Devuelve la estacion cuyo nombre coincide con el parametro. Se ignora la diferencia de mayusculas-minuscula
	 * @param nombre no,bre de a estacion buscada
	 * @return la estacion con ese nombre o null si la linea no tiene ninguna estacion con ese nombre
	 */
	Estacion getEstacion(String nombre);
	
	/**
	 * Devuelve la secuencia de tramos que recorremos a lo largo de la linea
	 * @return secuencia de tramos
	 */
	List<Tramo> getTramos();
	
	/**
	 * Devuelve el color con el que se dibuja la linea
	 * @return color de la linea
	 */
	Color getColor();
	
	/**
	 * Devuelve el numero de la linea
	 * @return numero de linea
	 */
	int getId();
}

