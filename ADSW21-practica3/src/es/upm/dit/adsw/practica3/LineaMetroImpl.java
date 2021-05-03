package es.upm.dit.adsw.practica3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LineaMetroImpl implements LineaMetro {

	protected List<Tramo> tramos;
	private Color color;
	private int id;
	private MapaMetro mapa;

	/**
	 * Construye una linea de metro convencional
	 * @param id identificador de la linea
	 * @param color color con el que se dibuja la linea
	 * @param tramos secuencia de tramos que recorre la linea. 
	 * El destino de un tramo debe ser el origen del siguiente tramo
	 * @param mapa mapa donde se dibuja la linea
	 */
	public LineaMetroImpl(int id, Color color, List<Tramo> tramos, MapaMetro mapa) {
		this.id=id;
		this.color=color;
		this.tramos=tramos;
		this.mapa=mapa;
	}

	@Override
	public MapaMetro getMapa() {
		return mapa;
	}

	@Override
	public List<Estacion> getEstaciones() {
		List<Estacion> estaciones=new ArrayList<Estacion>();
		for (Tramo t : tramos) {
			if (!estaciones.contains(t.desde()))
				estaciones.add(t.desde());
			if (!estaciones.contains(t.hasta()))
				estaciones.add(t.hasta());
		}
		return estaciones;
	}
	
	@Override
	public Estacion getEstacion(String nombre) {
		for (Estacion est : getEstaciones())
			if (est.getNombre().equalsIgnoreCase(nombre))
				return est;
		return null;
	}

	@Override
	public List<Tramo> getTramos() {
		return tramos;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "linea "+id;
	}
}
