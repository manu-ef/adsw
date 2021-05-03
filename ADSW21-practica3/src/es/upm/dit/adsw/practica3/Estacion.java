package es.upm.dit.adsw.practica3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estacion {

	private int id;
	private Vector posicion;
	private String nombre;

	/**
	 * Crea una nueva estacion en el mapa
	 * @param id identificador de la estacion
	 * @param posicion coordenadas de la estacion en el mapa
	 * @param tiempo tiempo que tardan los trenes en desembarcar/embarcar pasajeros
	 * @param nombre Nombre de la estacion
	 */
	public Estacion(int id, Vector posicion, String nombre) {
		this.id=id;
		this.posicion=posicion;
		this.nombre=nombre;
	}
	
	/**
	 * Devuelve el identificador de la estacion
	 * @return el identificador
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve las coordenadas de la estacion en el mapa
	 * @return posicion de la estacion
	 */
	public Vector getPosicion() {
		return posicion;
	}

	/**
	 * Devueve el nombre de la estacion
	 * @return nombre de la estacion
	 */
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object otra) {
		if (!(otra instanceof Estacion))
			return false;
		return id == ((Estacion) otra).id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
