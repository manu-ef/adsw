package es.upm.dit.adsw.practica3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


public class PruebaMejorCaminoMetro {
	
	@Test
	public void test1() {
		
	}
	
	@Test
	public void test2() {
		String f = "mapa_metro2.txt";
		MapaMetro g = new MapaMetro(f);
		
		LineaMetro linea4 = g.getLineaMetro(4);
		LineaMetro linea7 = g.getLineaMetro(7);
		Estacion prosp = linea4.getEstacion("Prosperidad");
		Estacion pit = linea7.getEstacion("Pitis");
		
		List<Tramo> camino = new MejorCaminoMetro(g).caminoMasRapido(prosp, null);
		
		
		
		String[] solucionCorrecta = {"Prosperidad", "Avenida America", "Nuevos Ministerios", "Guzman Bueno", "Pitis"};
		
		String[] estaciones = new String[camino.size()+1];
		estaciones[0] = camino.get(0).desde().getNombre();
		int i = 1;
		for (Tramo t : camino)
			estaciones[i++] = t.hasta().getNombre();
		
		
		for(int j = 0; j<estaciones.length; j++)
			System.out.println(estaciones[j]);
	}
	
	@Test
	public void test3() {
		
	}
	@Test
	public void test4() {
		
	}
}
