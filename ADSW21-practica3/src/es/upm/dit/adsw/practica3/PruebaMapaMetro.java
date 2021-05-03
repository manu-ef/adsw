package es.upm.dit.adsw.practica3;

import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class PruebaMapaMetro {

    public static void main(String[] args) {
    	String f="mapa_metro2.txt";
    	if (args.length > 0)
    		f=args[0];
		try {

			MapaMetro g = new MapaMetro(f);
			g.dibuja();
    		LineaMetro linea6 = g.getLineaMetro(6);
    		Estacion aa=linea6.getEstacion("Avenida America");
     		Estacion arg=linea6.getEstacion("Arguelles");
    		List<Tramo> camino = new MejorCaminoMetro(g).caminoMasRapido(aa, arg);
			System.out.println("Camino desde Avenida America hasta Arguelles");
			for (Tramo t : camino)
				System.out.println("desde "+t.desde().getNombre()+" hasta "+t.hasta().getNombre());

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
