package es.upm.dit.adsw.practica2;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	private boolean viasYaOrdenadasPorNombre;

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
		inicializaReferencias();
		viasYaOrdenadasPorNombre = false;
	}
	
	/**
	 * Metodo que inicializa la referecias a las vias de comienzo y terminacion
	 * de todas las vias contenidas en vias.
	 * Debe ser utilizado unicamente para hacer pruebas o en el constructor
	 */
	public void inicializaReferencias() {
		ordenaVias();
		
		Map<Integer,Via> viasAux = viasAux(vias);
		for (Via via : vias) {
			via.setViaComienzo(viasAux.get(via.getComienza()));
			via.setViaTermina(viasAux.get(via.getTermina()));
		}
	}
	
	private Map<Integer,Via> viasAux (Via[] vias) {
		Map<Integer, Via> viasAux = new HashMap<Integer, Via>();
		for (Via via : vias) {
			viasAux.put(via.getCodigo(), via);
		}
		return viasAux;
	}
	
	/**
	 * Metodo que ordena las vias en funcion del codigo de via.
	 * Debe ser utilizado unicamente para hacer pruebas
	 */
	public void ordenaVias() {
		// TODO
		SolucionP1.ordenaVias(vias);
		viasYaOrdenadasPorNombre = false;
	}
	
	/**
	 * Devuelve la referencia a la via cuyo codigo es el parametro del metodo
	 * @param codigo codigo de la via buscada
	 * @return via cuyo codigo es codigo, o null si no existe
	 */
	public Via buscaViaCodigo(int codigo) {
		ordenaVias();
		return buscaViaCodigo(codigo, 0, vias.length-1); 

	}
	
	private Via buscaViaCodigo(int codigo, int a, int z) {
		if (a >= z)
			return vias[a];
		int m = (a + z) / 2;
		if (codigo == vias[m].getCodigo())
			return vias[m];
		if (codigo < vias[m].getCodigo())
			return buscaViaCodigo(codigo, 0, m);
		else
			return buscaViaCodigo(codigo, m + 1, z);  
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
	 * @param vias array de vias a ordenar por nombre
	 * @return conjunto de vias ordenadas por nombre 
	 */
	public void ordenaViasPorNombre(Via[] vias) {
		SolucionP1.ordenaViasPorNombre(vias);
		viasYaOrdenadasPorNombre = true;
	}
	
	/**
	 * Devuelve el conjunto de vias del callejero cuyo nombre comienza
	 * por viaBuscada
	 * 
	 * @param viaBuscada secuencia de caracteres en mayusculas con las 
	 * que comienza el nombre de las vias que se buscan
	 * @return conjunto de vias que comienza por viaBuscada
	 */
	public Set<Via> buscaVia(String viaBuscada) {
		if (viasYaOrdenadasPorNombre == false)
			ordenaViasPorNombre(vias);
		return buscaVia(viaBuscada, 0, vias.length-1);
	}
	
	private Set<Via> buscaVia(String viaBuscada, int a, int z) {
		Set<Via> encontradas = new HashSet<>();
		while (a < z) {
			int m = (a+z)/2;
			boolean contains = vias[m].getNombre().contains(viaBuscada);
			int cmp = vias[m].getNombre().compareTo(viaBuscada);
			if (contains){
				encontradas.add(vias[m]);
			}
			if (cmp < 0) {
				a = m+1;
			} else {
				z = m;
			}
		}
		return encontradas;
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
			c.buscaVia("CORONA");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
