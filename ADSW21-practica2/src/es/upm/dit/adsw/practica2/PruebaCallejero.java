package es.upm.dit.adsw.practica2;

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
	 * 
	 */
	@Test
	public void pruebaGetViaComienzo() {
		// TODO
	}
	
	/**
	 * 
	 */
	@Test
	public void tiempoInicializaReferencias() {
		// TODO
	}
	
	/**
	 * 
	 */
	@Test
	public void tiempoBuscaViaCodigo() {
		// TODO
	}
	
	/**
	 * 
	 */
	@Test
	public void pruebaBuscaVia() {
		System.out.println(c.buscaVia("CORONA"));
	}

}
