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
		bottomUpSort(vias, new Via[vias.length]);
	}

	/**
	 * Metodo de ordenacion mediante MergeSort
	 * @param data array de Via para ordenar
	 * @param aux array de Via del tamaño de vias
	 */
	private void bottomUpSort(Via[] data, Via[] aux) {
        int n = data.length;
        for (int window = 1; window < n; window *= 2) {
            for (int i = 0; i < n; i += 2 * window) {
                int iLeft = i;
                int iRight = Math.min(i + window, n);
                int iEnd = Math.min(i + 2 * window, n);
                bottomUpMerge(data, iLeft, iRight, iEnd, aux);
            }
            System.arraycopy(aux, 0, data, 0, n);
        }
    }

    private void bottomUpMerge(Via[] data, int iLeft, int iRight, int iEnd, Via[] aux) {

        assert sorted(data, iLeft, iRight);
        assert sorted(data, iRight, iEnd);
        int i0 = iLeft;
        int i1 = iRight;

        int dst= iLeft;
        while (i0 < iRight && i1 < iEnd) {
            if (data[i0].getCodigo() < data[i1].getCodigo())
                aux[dst++] = data[i0++];
            else
                aux[dst++] = data[i1++];
        }
        while (i0 < iRight)
            aux[dst++] = data[i0++];
        while (i1 < iEnd)
            aux[dst++] = data[i1++];
        assert sorted(aux, iLeft, iEnd);
    }
	
    /**
     * Comprueba si una parte de un array esta ordenada
     *
     * @param datos array para comprobar
     * @param a indice del primer elemento
     * @param z indice del ultimo elemento + 1
     * @return true si datos [a..z) esta ordenado
     */
    private boolean sorted(Via[] datos, int a, int z) {
        for (int i = a; i + 1 < z; i++)
            if (datos[i].getCodigo() > datos[i + 1].getCodigo())
                return false;
        return true;
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
