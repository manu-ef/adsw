package es.upm.dit.adsw.practica1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class PruebaCallejero {
	
	protected static final String fichero="VialesVigentes_20201220.csv";
	private static Callejero c;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException {
		FileInputStream fi=new FileInputStream(fichero);
		Scanner viales=new Scanner(fi);
		int lineas=0;
		while (viales.hasNext()) {
			lineas++;
			viales.nextLine();
		}
		viales.close();
		fi=new FileInputStream(fichero);
		viales=new Scanner(fi);
		viales.nextLine(); // nos saltamos las cabeceras del fichero
		c=new Callejero(viales,lineas-1);
	}
	
	@Test
	public void testOrdenPorCodigo() throws FileNotFoundException {
		
		// bucle for que recorra el callejero y que compruebe con assertTrue que cada calle tiene un
		// codigo mayor que el anterior
	}
	
	@Test
	public void testComienzoTermina() throws FileNotFoundException {
		
	}

	@Test
	public void testTiempoOrdenaVias() {
		long tiempo_inicio= System.nanoTime();
		for (int i = 0; i < c.vias.length; i++) {
			int m = i;
			for (int j = i+1; j < c.vias.length; j++) {
				if(c.vias[j].getCodigo() < c.vias[m].getCodigo())
					m = j;
			}
			Via aux = c.vias[i];
			c.vias[i] = c.vias[m];
			c.vias[m] = aux;
		}
		long tiempo_final = System.nanoTime();
		System.out.println("Tiempo ordenando vias por algoritmo de seleccion: " + (tiempo_final-tiempo_inicio));
		
		
		long tiempo_inicio_Merge= System.nanoTime();
		c.ordenaVias();
		long tiempo_final_Merge = System.nanoTime();
		System.out.println("Tiempo ordenando vias por algoritmo Mergesort: " + (tiempo_final_Merge-tiempo_inicio_Merge));

		
		assertTrue("El tiempo de ejecucion del algorimo Mergesort es bastante menor que el de seleccion (menos de la mitad).",(tiempo_final_Merge-tiempo_inicio_Merge) < 0.5*(tiempo_final-tiempo_inicio));
		
	}
	
	@Test
	public void testTiempoOrdenaViasPorNombre() {
		long tiempo_inicio= System.nanoTime();
		for (int i = 0; i < c.vias.length; i++) {
			int m = i;
			for (int j = i+1; j < c.vias.length; j++) {
				if(c.vias[j].getNombre().compareTo(c.vias[m].getNombre()) < 0)
					m = j;
			}
			Via aux = c.vias[i];
			c.vias[i] = c.vias[m];
			c.vias[m] = aux;
		}
		long tiempo_final = System.nanoTime();
		System.out.println("Tiempo ordenando vias por algoritmo de seleccion: " + (tiempo_final-tiempo_inicio));
		
		
		long tiempo_inicio_Merge= System.nanoTime();
		c.ordenaVias();
		long tiempo_final_Merge = System.nanoTime();
		System.out.println("Tiempo ordenando vias por algoritmo Mergesort: " + (tiempo_final_Merge-tiempo_inicio_Merge));

		
		assertTrue("El tiempo de ejecucion del algorimo Mergesort es bastante menor que el de seleccion (menos de la mitad).",(tiempo_final_Merge-tiempo_inicio_Merge) < 0.5*(tiempo_final-tiempo_inicio));
		
	}

	}
