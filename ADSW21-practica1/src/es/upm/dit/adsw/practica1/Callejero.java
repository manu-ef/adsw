package es.upm.dit.adsw.practica1;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase para representar contenidos de los ficheros de viales que distribuye
 * el ayuntamiento de Madrid
 * 
 * @author mmiguel
 * @author anamtnez3
 * @author manu-ef
 *
 */
public class Callejero {

	private static final int COD_VIA=0;
	private static final int VIA_CLASE=1;
	private static final int VIA_PAR=2;
	private static final int VIA_NOMBRE=3;
	private static final int VIA_NOMBRE_ACENTOS=4;
	private static final int COD_VIA_COMIENZA=5;
	private static final int CLASE_COMIENZA=6;
	private static final int PARTICULA_COMIENZA=7;
	private static final int NOMBRE_COMIENZA=8;
	private static final int NOMBRE_ACENTOS_COMIENZA=9;
	private static final int COD_VIA_TERMINA=10;
	private static final int CLASE_TERMINA=11;
	private static final int PARTICULA_TERMINA=12;
	private static final int NOMBRE_TERMINA=13;
	private static final int NOMBRE_ACENTOS_TERMINA=14;

	protected static final String fichero="VialesVigentes_20201220.csv";
	protected Via[] vias;
	private Via[] viasPorNombre;
	private boolean viasOrdenadas;

	/**
	 * Constructor de callejero a partir de algun tipo de stream que 
	 * incluye las vias del callejero. Ese stream esta soportado con un Scanner. 
	 * El scanner incluye el contenido del callejero y el constructor lee el stream 
	 * que debe estar en formato csv
	 * 
	 * @param viales scanner del que extraemos el contenido del callejero
	 * @param numViales numero de viales que incluye el scanner
	 */
	public Callejero(Scanner viales,int numViales) {
		vias=new Via[numViales];
		String[] vias_csv;
		for (int i=0; i < numViales; i++) {
			String linea=viales.nextLine();
			vias_csv=linea.split(";");
			vias[i]=new Via(Integer.parseInt(vias_csv[COD_VIA]),vias_csv[VIA_CLASE],vias_csv[VIA_PAR],
					vias_csv[VIA_NOMBRE],vias_csv[VIA_NOMBRE_ACENTOS],
					Integer.parseInt(vias_csv[COD_VIA_COMIENZA]),Integer.parseInt(vias_csv[COD_VIA_TERMINA]));
			if ((i == numViales-1 && viales.hasNext()) || (i < numViales-1 && !viales.hasNext()))
				throw new RuntimeException("Formato fichero errorneo");
		}
		viales.close();
		ordenaVias();
		viasOrdenadas = false;
	}
	
	/**
	 * Metodo que ordena las vias en función del código de via.
	 * Debe ser utilizado unicamente para hacer pruebas
	 */
	public void ordenaVias() {
		// llamamos al metodo fachada
		ordenaViasPorSeleccion();
	}
	
	// ordenamos el array, algoritmo de seleccion
	private void ordenaViasPorSeleccion() {
		for (int i = 0; i < vias.length; i++) {
			int m = i;
			for (int j = i+1; j < vias.length; j++) {
				if(vias[j].getCodigo() < vias[m].getCodigo())
					m = j;
			}
			Via aux = vias[i];
			vias[i] = vias[m];
			vias[m] = aux;
		}
	}
	
	/**
	 * Imprime en salida estandar todos los viales del callejero
	 */
	public void printViales() {
		for (Via via : vias) {
			via.formatPrint();
			System.out.println();
		}
	}
	
	/**
	 * Devuelve las vias del callejero
	 * @return vias del callejero
	 */
	public Via[] getVias() {
		return vias;
	}
	
	/**
	 * Fija las vias del callejero. Debe estar completamente inicializada
	 * @param vias nuevas vias del callejero
	 */
	public void setVias(Via[] vias) {
		this.vias = vias;
	}
	
	/**
	 * Devuelve el cojunto de vias del callejero ordenadas por nombre
	 * 
	 * @return conjunto de vias ordenadas por nombre 
	 */
	public Via[] ordenaViasPorNombre() {
		
		// comprobamos si el array esta ordenado
		if (viasOrdenadas)
			return viasPorNombre;
		
		// copiamos vias a viasPorNombre
		viasPorNombre = new Via[this.vias.length];
		for(int i = 0; i < viasPorNombre.length; i++)
			viasPorNombre[i] = this.vias[i];
		
		// llamamos al metodo fachada
		ordenaViasPorNombrePorSeleccion();
		
		// actualizamos cambios en viasOrdenadas
		viasOrdenadas = true;
		
		// devolvemos el array ordenado
		return viasPorNombre;
	}
	
	// ordenamos el nuevo array, algoritmo de seleccion
	private void ordenaViasPorNombrePorSeleccion() {
		for (int i = 0; i < viasPorNombre.length; i++) {
			int m = i;
			for (int j = i+1; j < viasPorNombre.length; j++) {
				if(viasPorNombre[j].getNombre().compareTo(viasPorNombre[m].getNombre()) < 0)
					m = j;
			}
			Via aux = viasPorNombre[i];
			viasPorNombre[i] = viasPorNombre[m];
			viasPorNombre[m] = aux;
		}
	}
	
	public static void main(String[] args) {
		try {
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
			c.printViales();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
