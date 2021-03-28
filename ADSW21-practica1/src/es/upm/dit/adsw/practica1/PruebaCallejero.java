package es.upm.dit.adsw.practica1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class PruebaCallejero {
	
	protected static final String fichero="VialesVigentes_20201220.csv";

	@Test
	public void testOrdenPorCodigo() throws FileNotFoundException {
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
		Callejero c=new Callejero(viales,lineas-1);
		
		// bucle for que recorra el callejero y que compruebe con assertTrue que cada calle tiene un
		// codigo mayor que el anterior
	}
	
	@Test
	public void testComienzoTermina() throws FileNotFoundException {
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
		Callejero c=new Callejero(viales,lineas-1);
	}

	@Test
	public void testTiempoOrdenaVias() {
	}
	
	@Test
	public void testTiempoOrdenaViasPorNombre() {
	}

}
