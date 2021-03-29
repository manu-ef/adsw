package es.upm.dit.adsw.practica1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase JUnit para comprobar los resultados de Callejero
 * @author anamtnez3
 * @author manu-ef
 */

public class PruebaCallejero {
	
	protected static final String fichero="VialesVigentes_20201220.csv";
	private static Callejero c;
	
	/**
	 * Creamos primero un objeto callejero a partir del fichero de viales
	 * @throws FileNotFoundException si no se encuentra el fichero de viales
	 */
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
	
	/**
	 * Test que recorre las vias del callejero y comprueba que estan ordenadas por codigo
	 */
	@Test
	public void testOrdenPorCodigo() {
		for (int i = 1; i < c.vias.length; i++) {
			assertTrue("Las vias estan ordenadas correctamente", c.vias[i].getCodigo() >= c.vias[i-1].getCodigo());
		}
	}
	
	/**
	 * Test que recorre las vias del callejero y comprueba que el comienzo y el final de las vias es null
	 */
	@Test
	public void testComienzoTermina() {
		for (int i = 0; i < c.vias.length; i++) {
			assertSame(null, c.vias[i].getViaComienzo());
			assertSame(null, c.vias[i].getViaTermina());
		}
	}
	
	/**
	 * Test que comprueba si el tiempo de ordenacion de las vias es menor que el especificado (menos de la mitad que el algoritmo de seleccion)
	 */
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
	
	/**
	 * Test que comprueba si el tiempo de ordenacion de las vias por nombre es menor que el especificado (menos de la mitad que el algoritmo de seleccion)
	 */
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
