package es.upm.dit.adsw.practica3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class PruebaMejorCaminoMetro {
	
	private static final String metro1="mapa_metro.txt";
	private static final String metro2="mapa_metro2.txt";
	private static final String metroMadrid="MetroMadrid_2019_12.txt";
	
	private static MapaMetro m1;
	private static MapaMetro m2;
	private static MapaMetro m3;
	
	/**
     * Definimos los ficheros del metro
     */
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		m1 = new MapaMetro(metro1);
		m2 = new MapaMetro(metro2);
		m3 = new MapaMetro(metroMadrid);
	}
	
	/**
     * Calcula el caminoMasRapido que nos llevara desde Nuevos Ministerios hasta Pacifico (mapa_metro)
     */
	@Test
	public void test1() {
		System.out.println("-----test1-----");
		LineaMetro linea6 = m1.getLineaMetro(6);
		Estacion nuevos = linea6.getEstacion("Nuevos Ministerios");
		Estacion pacifico = linea6.getEstacion("Pacifico");
		
		List<Tramo> recorrido = new MejorCaminoMetro(m1).caminoMasRapido(nuevos, pacifico);
		
		String[] paradas = new String[recorrido.size()+1];
		paradas[0] = recorrido.get(0).desde().getNombre();
		int i = 1;
		for (Tramo t : recorrido)
			paradas[i++] = t.hasta().getNombre();
		
		for (int j = 0; j < paradas.length; j++)
			System.out.println(paradas[j]);
		
		assertEquals(4, paradas.length);
	}
	
	/**
     * Calcula el caminoMasRapido que nos llevara desde Prosperidad hasta Pitis (mapa_metro2)
     */
	@Test
	public void test2() {
		System.out.println("-----test2-----");
		LineaMetro linea4 = m2.getLineaMetro(4);
		LineaMetro linea7 = m2.getLineaMetro(7);
		Estacion prosperidad = linea4.getEstacion("Prosperidad");
		Estacion pitis = linea7.getEstacion("Pitis");
		
		List<Tramo> recorrido = new MejorCaminoMetro(m2).caminoMasRapido(prosperidad, pitis);
		
		String[] paradas = new String[recorrido.size()+1];
		paradas[0] = recorrido.get(0).desde().getNombre();
		int i = 1;
		for (Tramo t : recorrido)
			paradas[i++] = t.hasta().getNombre();
		
		
		for(int j = 0; j < paradas.length; j++)
			System.out.println(paradas[j]);
		
		assertEquals(5, paradas.length);
	}
	
	/**
     * Calcula el caminoMasRapido que nos llevara desde Colombia hasta Carabanchel (mapa_metro2)
     */
	@Test
	public void test3() {
		System.out.println("-----test3-----");
		LineaMetro linea9 = m2.getLineaMetro(9);
		LineaMetro linea5 = m2.getLineaMetro(5);
		Estacion colombia = linea9.getEstacion("Colombia");
		Estacion carabanchel = linea5.getEstacion("Carabanchel");
		
		List<Tramo> recorrido = new MejorCaminoMetro(m2).caminoMasRapido(colombia, carabanchel);
		
		String[] paradas = new String[recorrido.size()+1];
		paradas[0] = recorrido.get(0).desde().getNombre();
		int i = 1;
		for (Tramo t : recorrido)
			paradas[i++] = t.hasta().getNombre();
		
		
		for(int j = 0; j < paradas.length; j++)
			System.out.println(paradas[j]);
		
		assertEquals(6, paradas.length);
		
	}
	
	/**
     * Calcula el caminoMasRapido que nos llevara desde AVENIDA DE AMERICA hasta ARGUELLES (MetroMadrid_2019_12)
     */
	@Test
	public void test4() {
		System.out.println("-----test4-----");
		LineaMetro linea4 = m3.getLineaMetro(4);
		Estacion avenida = linea4.getEstacion("AVENIDA DE AMERICA");
		Estacion arguelles = linea4.getEstacion("ARGÜELLES");
		
		List<Tramo> recorrido = new MejorCaminoMetro(m3).caminoMasRapido(avenida, arguelles);
		
		String[] solucion = {"AVENIDA DE AMERICA", "NUÑEZ DE BALBOA", "RUBEN DARIO", "ALONSO MARTINEZ", "BILBAO", "SAN BERNARDO", "ARGÜELLES"};
		
		String[] paradas = new String[recorrido.size()+1];
		paradas[0] = recorrido.get(0).desde().getNombre();
		int i = 1;
		for (Tramo t : recorrido)
			paradas[i++] = t.hasta().getNombre();
		
		
		for(int j = 0; j < paradas.length; j++)
			System.out.println(paradas[j]);
		
		assertEquals(7, paradas.length);
		assertArrayEquals(solucion, paradas);
	}
}
