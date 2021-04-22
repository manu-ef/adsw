package es.upm.dit.adsw.practica2;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

//import es.upm.dit.adsw.practica1.Via;

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
		Via Trafalgar = c.buscaViaCodigo(750700);
		Via Luchana = Trafalgar.getViaComienzo();
		assertEquals(Luchana.getNombre(), "LUCHANA");
		
		Via Bilbao = Luchana.getViaComienzo();
		assertEquals(Bilbao.getNombre(), "BILBAO");
		
		Via Alcala = c.buscaViaCodigo(18900);
		Via Sol = Alcala.getViaComienzo();
		assertEquals(Sol.getNombre(), "PUERTA DEL SOL");
		
		Via Alcala2 = Sol.getViaComienzo();
		assertEquals(Alcala2.getNombre(), "ALCALA");
	}
	
	/**
	 * 
	 */
	@Test
	public void tiempoInicializaReferencias() {
		long tiempo_inicio= System.nanoTime();
		
		for (Via via : c.vias)
			for (Via via2 : c.vias) {
				if (via.getComienza() == via2.getCodigo())
					via.setViaComienzo(via2);
				if (via.getTermina() == via2.getCodigo())
					via.setViaTermina(via2);
			}
		
		long tiempo_final = System.nanoTime();
		System.out.println("Tiempo inicializando referencias O(N^2): " + (tiempo_final-tiempo_inicio));
		
		long tiempo_inicio_inicializaReferencias= System.nanoTime();
		c.inicializaReferencias();
		long tiempo_final_inicializaReferencias = System.nanoTime();
		System.out.println("Tiempo inicializaReferencias: " + (tiempo_final_inicializaReferencias-tiempo_inicio_inicializaReferencias));

		assertTrue("El tiempo de ejecucion del metodo incializaReferencias es mayor de lo esperado.", 
				(tiempo_final_inicializaReferencias-tiempo_inicio_inicializaReferencias) < 0.5*(tiempo_final-tiempo_inicio));
	}
	
	/**
	 * 
	 */
	@Test
	public void tiempoBuscaViaCodigo() {
		long tiempo_inicio= System.nanoTime();
		
		for (Via via : c.vias)
			if (via.getCodigo() == 99000001)
				continue;
		
		long tiempo_final = System.nanoTime();
		System.out.println("Tiempo buscando via por codigo O(N): " + (tiempo_final-tiempo_inicio));
		
		long tiempo_inicio_buscaViaCodigo = System.nanoTime();
		c.buscaViaCodigo(99000001);
		long tiempo_final_buscaViaCodigo = System.nanoTime();
		System.out.println("Tiempo buscaViaCodigo: " + (tiempo_final_buscaViaCodigo - tiempo_inicio_buscaViaCodigo));
		
		assertTrue("El tiempo de ejecucion de buscaViaCodigo es mayor de lo esperado.", 
				(tiempo_final_buscaViaCodigo-tiempo_inicio_buscaViaCodigo) < 0.5*(tiempo_final-tiempo_inicio));
	}
	
	/**
	 * 
	 */
	@Test
	public void pruebaBuscaVia() {
		System.out.println(c.buscaVia("FILOMENA"));
		assertTrue(c.buscaVia("FILOMENA").isEmpty());
		
		System.out.println(c.buscaVia("CORONA"));
		//assertEquals(c.buscaVia("CORONA"), );
	}

}
