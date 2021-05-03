package es.upm.dit.adsw.practica3;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * Esta clase implementa un mapa que representa plas estaciones del mapa y los tramos de lineas que las conectan
 * @author mmiguel
 *
 */
public class MapaMetro {

    private List<LineaMetro> lineas=new ArrayList<LineaMetro>();
    private Map<Estacion,Set<Tramo>> salidaEstacion=new HashMap<Estacion,Set<Tramo>>();
    private boolean dibujando=false;

	/**
	 * Construye un mapa inicialmente vacio en el que habra que anadir tramos
	 */
	public MapaMetro() {
	}
	
	/**
	 * Construye un mapa leyedo de un fichero
	 * @param f camino del fichero que contiene el mapa
	 */
	public MapaMetro(String f) {
		this();
        if (f == null) throw new IllegalArgumentException("Scanner de entrada null");
		Locale def = Locale.getDefault();
		Locale.setDefault(new Locale("en", "US"));
		Scanner in=null;
        try {
        	in=new Scanner(new FileInputStream(f));
        	leeFichero(in);
        } catch (FileNotFoundException e) {
        	throw new IllegalArgumentException("nombre de fichero erroneo", e);
		} finally {
        	Locale.setDefault(def);
        	if (in != null) in.close();
        }
	}
	
	/**
	 * Construye el mapa a partir de un Scanner. El lenguaje del empleado en la lectura debe ser US
	 * @param in scanner de entrada que describe el mapa
	 */
	public MapaMetro(Scanner in) {
		this();
        if (in == null) throw new IllegalArgumentException("Scanner de entrada null");
        in.useLocale(new Locale("en", "US"));
        leeFichero(in);
	}
	
	private void leeFichero(Scanner in) {
        try {
            while (in.hasNext()) {
            	String nodeId=in.next();
            	if (nodeId.equals("."))
            		break;
            	int id=Integer.parseInt(nodeId);
            	if (!in.next().equals("(")) throw new IllegalArgumentException("formato de entrada erroneo");
                double x = in.nextDouble();
            	double y = in.nextDouble();
            	Vector p=new Vector(x,y);
            	if (!in.next().equals(")")) throw new IllegalArgumentException("formato de entrada erroneo");
            	double tiempo = in.nextDouble();
            	in.skip(" ");
            	String nombre=in.nextLine();
            	Estacion e=new Estacion(id,p,nombre);
            	salidaEstacion.put(e,new HashSet<Tramo>());
            }
        	while (in.hasNext()) {
        		int lineaId = in.nextInt();
        		int r=in.nextInt();
        		int g=in.nextInt();
        		int b=in.nextInt();
        		List<Tramo> tramos=new ArrayList<Tramo>();
        		Estacion primera=null;
        		Estacion ultima=null;
        		while (in.hasNext()) {
                	String nodeId=in.next();
                	if (nodeId.equals("."))
                		break;
                    int ids = Integer.parseInt(nodeId);
                	int idd = in.nextInt();
                	double tiempo = in.nextDouble();
                	Estacion origen = getEstacion(ids);
                	Estacion destino = getEstacion(idd);
                	if (primera == null)
                		primera=origen;
                	ultima=destino;
                	if (origen == null || destino == null)
                		throw new IllegalArgumentException("formato de entrada erroneo");
                    Tramo t = new Tramo(origen,destino,tiempo,null);
                    tramos.add(t);
                    salidaEstacion.get(origen).add(t);
        		}
        		LineaMetro linea;
        		if (primera == ultima)
        			linea=new LineaMetroCircular(lineaId,new Color(r,g,b),tramos,this);
        		else
        			linea=new LineaMetroImpl(lineaId,new Color(r,g,b),tramos,this);
        		for (Tramo t : tramos) {
        			t.setLineaMetro(linea);
        			salidaEstacion.get(t.hasta()).add(new Tramo(t.hasta(),t.desde(),t.tiempo(),linea));
        		}
        		lineas.add(linea);
            }
        }   
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("formato de entrada erroneo", e);
        }
	}

	private Estacion getEstacion(int id) {
		for (Estacion e : getEstaciones())
			if (e.getId() == id)
				return e;
		return null;
	}
    
	/**
	 * Devuelve las estaciones que incluye el mapa
	 * @return conjunto de estaciones del mapa
	 */
	public Set<Estacion> getEstaciones() {
		return salidaEstacion.keySet();
	}
	
	/**
	 * Devuelve el numero de tramos que tiene la red de metro
	 * @return numero de tramos
	 */
	public int numTramos() {
		int n=0;
		for (Set<Tramo> v : salidaEstacion.values())
			n+=v.size();
		return n;
	}
	
	/**
	 * Devielve todos los tramos en el mapa de metro
	 * @return los tramos del mapa
	 */
	public Set<Tramo> getTramos() {
		Set<Tramo> collection = new HashSet<>();

		for (Set<Tramo> e: salidaEstacion.values())
			collection.addAll(e);

		return collection;
	}
	
	/**
	 * Devuelve el conjunto de tramos de linea que salen de una estacion
	 * @param estacion para la que buscamos sus salidas. Esta estacion no puede ser null y debe estar entre las estaciones del mapa
	 * @return salidas de la estacion
	 */
	public Set<Tramo> getSalidasEstacion(Estacion estacion) {
		if (estacion == null || !getEstaciones().contains(estacion))
			throw new RuntimeException("La estacion para la que se buscan sus salidas no esta en el mapa");
		return salidaEstacion.get(estacion);
	}
	
    /**
     * Devuelve una linea de metro a partir de su identificador
     * @return linea de metro buscada, o null si no se encuentra
     */
    public LineaMetro getLineaMetro(int id) {
    	for (LineaMetro lm : lineas)
    		if (lm.getId() == id)
    			return lm;
    		
    	return null;
    }
    
    private double scl=1.0;
    private double radio=scl/1.8;
    private static final int PASOS = 10;
    
    /**
     * Este método gestiona la representación gráfica y animación del movimiento
     * de un tren en el mapa
     */
    public synchronized void dibuja() {
    	double max=Double.NEGATIVE_INFINITY;
    	double min=Double.POSITIVE_INFINITY;
    	for (Estacion v : getEstaciones()) {
    		if (max < v.getPosicion().getX())
    			max=v.getPosicion().getX();
    		if (max < v.getPosicion().getY())
    			max=v.getPosicion().getY();
    		if (min > v.getPosicion().getX())
    			min=v.getPosicion().getX();
    		if (min > v.getPosicion().getY())
    			min=v.getPosicion().getY();
    	}
    	if (max - min < 0.0001) {
	    	max=max+5;
	    	min=min-5;
    	}

    	StdDraw.setCanvasSize(780,780);
    	StdDraw.setScale(min, max);
    	scl=(max-min)*0.03;
    	radio=scl/1.8;
		StdDraw.clear();
		StdDraw.show(0);
		
	    StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setPenRadius(StdDraw.getPenRadius()*5);
	    for (LineaMetro lm : lineas) {
	    	for (Tramo c : lm.getTramos())
	    		tramo(c.desde().getPosicion().getX(),c.desde().getPosicion().getY(),c.hasta().getPosicion().getX(),c.hasta().getPosicion().getY(),lm.getColor());
	    }
		for (Estacion p : getEstaciones()) {
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.circle(p.getPosicion().getX(),p.getPosicion().getY(),radio);
	        String linea="";
	        for (LineaMetro lm : lineas)
	        	if (lm.getEstaciones().get(0) == p || lm.getEstaciones().get(lm.getEstaciones().size()-1) == p)
	        		linea="("+lm.getId()+")";
	    	StdDraw.setFont(new Font("Arial", Font.PLAIN, 14));
	    	StdDraw.text(p.getPosicion().getX()-radio*2,p.getPosicion().getY()-radio*2,p.getNombre()+linea);
	    }
	    StdDraw.show(0);
	    dibujando=true;
	}
    
    private void tramo(double x1, double y1, double x2, double y2,Color color) {
    	StdDraw.setPenColor(color);
        StdDraw.line(x1, y1, x2, y2);
        StdDraw.setPenColor(StdDraw.BLACK);
    }
}
