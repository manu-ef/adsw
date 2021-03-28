package es.upm.dit.adsw.practica1;

/**
 * @author mmiguel
 *
 * Clase que permite representar los viales que distribuye 
 * el ayuntamiento de Madrid en sus ficheros
 */
public class Via {

	private int codigo;
	private String clase;
	private String par;
	private String nombre;
	private String nombreAcentos;
	private int comienza,termina;
	private Via viaComienzo,viaTermina;
	
	/**
	 * Constructor de una Via. Deja sin inicializar la referencia a
	 * las vias de comienzo y terminacion
	 * 
	 * @param codigo codigo del ayuntamiento de Madrid de la via
	 * @param clase clase de via (calle, plaza, plazuela, ...)
	 * @param par particula de union entre clase y nombre (de, de la, del, ...)
	 * @param nombre nombre de la via sin acentos, en mayusculas, pero con eñes
	 * @param nombreAcentos nombre de la via con acentos y en mayusculas
	 * @param comienza codigo de la via donde comienza la via
	 * @param termina codigo de la via donde termina la via
	 */
	public Via(int codigo,String clase,String par, String nombre, String nombreAcentos, int comienza, int termina) {
		this.codigo=codigo;
		this.clase=clase;
		this.par=par;
		this.nombre=nombre;
		this.nombreAcentos=nombreAcentos;
		this.comienza=comienza;
		this.termina=termina;
	}

	/**
	 * Imprime en salida estandar la via de forma formateada
	 */
	public void formatPrint() {
		System.out.printf("%8d ", codigo);
		System.out.printf("%5.5s ", clase);
		System.out.printf("%5.5s ", par);
		System.out.printf("%5.5s ", nombre);
		System.out.printf("%8d ", comienza);
		System.out.printf("%8d", termina);
	}
	
	/**
	 * Devuelve el codigo de via
	 * @return el codigo de la via
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Devuelve la clase de via
	 * @return la clase de la via
	 */
	public String getClase() {
		return clase;
	}

	/**
	 * Devuelve la particula de la via
	 * @return la particula de la via
	 */
	public String getPar() {
		return par;
	}

	/**
	 * Devuelve el nombre de la via sin acentos
	 * @return el nombre de la via
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el nombre de la via con acentos
	 * @return el nombre con acentos
	 */
	public String getNombreAcentos() {
		return nombreAcentos;
	}

	/**
	 * Devuelve el codigo de la via donde comienza esta via
	 * @return codigo de comienzo
	 */
	public int getComienza() {
		return comienza;
	}

	/**
	 * Actualiza la referencia a la via de comienzo. El codigo de esa
	 * via debe ser el mismo que el codigo de la via de comienzo 
	 * de esta via
	 * @param viaComienzo nueva via de comienzo
	 */
	public void setViaComienzo(Via viaComienzo) {
		if (viaComienzo != null && viaComienzo.getCodigo() != comienza)
			throw new RuntimeException("Via de comienzo inconsistente");
		this.viaComienzo = viaComienzo;
	}

	/**
	 * Actualiza la referencia a la via de terminación. El codigo de esa
	 * via debe ser el mismo que el codigo de la via done termina esta via
	 * @param viaTermina nueva via donde termina
	 */
	public void setViaTermina(Via viaTermina) {
		if (viaTermina != null && viaTermina.getCodigo() != termina)
			throw new RuntimeException("Via de terminacion inconsistente");
		this.viaTermina = viaTermina;
	}

	/**
	 * Devuelve el codigo de via donde termina esta via
	 * @return el codigo de via donde termina esta via
	 */
	public int getTermina() {
		return termina;
	}
	
	/**
	 * Referencia a la via donde comienza esta via
	 * @return referencia a la via
	 */
	public Via getViaComienzo() {
		return viaComienzo;
	}

	/**
	 * Referencia a la via donde termina esta via
	 * @return referencia a la via
	 */
	public Via getViaTermina() {
		return viaTermina;
	}
	
	@Override
	public String toString() {
		return ""+codigo+"("+nombre+")";
	}
	
	@Override
	public boolean equals(Object otra) {
		return otra != null && (otra instanceof Via) && codigo == ((Via) otra).codigo;
	}
}
