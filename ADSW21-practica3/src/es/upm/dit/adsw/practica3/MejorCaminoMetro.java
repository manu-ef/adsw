package es.upm.dit.adsw.practica3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;


public class MejorCaminoMetro {

	private MapaMetro mapa;
	private double[] distTo;
	private Tramo[] edgeTo;
	private IndexMinPQ<Double> pq;
	
	/**
	 * Construye un buscado de caminos de metro a partir de un mapa de metro
	 * @param mapa mapa con el que funcionara el buscador de caminos
	 */
	public MejorCaminoMetro(MapaMetro mapa) {
		if (mapa == null)
			throw new IllegalArgumentException();
		this.mapa=mapa;
	}
	
	/**
	 * Devuelve el mapa con el que trabaja el buscador de mejor camino
	 * @return el mapa utilizado
	 */
	public MapaMetro getMapa() {
		return mapa;
	}
	
    /**
     * Calcula el camino que nos llevara menos tiempo entre dos estaciones del mapa de metro. Solo tiene en cuenta
     * el tiempo que se tarda en hacer los tramos.
     * @param origen estacion de partida
     * @param destino estacion de llegada
     * @return secuencia de tramos a seguir
     * @throws java.lang.IllegalArgumentException cuando el origen o el destino son null o no son posiciones del mapa
     */
    public List<Tramo> caminoMasRapido(Estacion origen, Estacion destino){
    	// Comprobamos que los parametros son correctos
    	if (origen == null || destino == null)
    		throw new IllegalArgumentException("El origen o el destino es null");
    	
    	if (!mapa.getEstaciones().contains(origen) || !mapa.getEstaciones().contains(destino)) {
    		throw new IllegalArgumentException("El origen o el destino estan fuera del mapa");
    	}
    	
    	// HashMap para establecer una relacion id-estacion
    	HashMap<Integer, Estacion> estacionesDijkstra = new HashMap<Integer, Estacion>();
    	for (Estacion e : mapa.getEstaciones()) {
			estacionesDijkstra.put(e.getId(), e);
		}
    	
    	// Algoritmo de DIJKSTRA adaptado de la Universidad de Princeton
    	distTo = new double[estacionesDijkstra.size()+1];
    	edgeTo = new Tramo[estacionesDijkstra.size()+1];
    	pq = new IndexMinPQ<Double>(estacionesDijkstra.size()+1);
    	
    	for (int v = 0; v < estacionesDijkstra.size(); v++)
    		distTo[v] = Double.POSITIVE_INFINITY;
        distTo[origen.getId()] = 0.0;
    	
        pq.insert(origen.getId(), distTo[origen.getId()]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Tramo t : mapa.getSalidasEstacion(estacionesDijkstra.get(v))) {
            	relax(t);
            }
        }
        
        // Recuperamos los tramos guardados por Dijkstra
        List<Tramo> recorridoInverso = new ArrayList<Tramo>();
        List<Tramo> recorrido = new ArrayList<Tramo>();
        
        if(distTo[destino.getId()] != Double.POSITIVE_INFINITY) {
        	Tramo t = edgeTo[destino.getId()]; 
        	while (t != null) {
        		recorridoInverso.add(t);
        		t = edgeTo[t.desde().getId()];
        	}
        	// Recorremos la lista desde el final para invertir el orden de los tramos
        	while(recorridoInverso.size() > 0) {
        		recorrido.add(recorridoInverso.remove(recorridoInverso.size()-1));
        	}
        }
        return recorrido;
    }
    
    /**
     * Actualiza las distancias minimas y la cola de prioridad segun el algoritmo de Dijkstra
     * @param t tramo de estudio
     */
    private void relax(Tramo t) {
        int v = t.desde().getId(), w = t.hasta().getId();
        if (distTo[w] > distTo[v] + t.tiempo()) {
            distTo[w] = distTo[v] + t.tiempo();
            edgeTo[w] = t;
            if (pq.contains(w))
            	pq.decreaseKey(w, distTo[w]);
            else
            	pq.insert(w, distTo[w]);
        }        
    }
    
    /**
     *  The {@code IndexMinPQ} class represents an indexed priority queue of generic keys.
     *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
     *  operations, along with <em>delete</em> and <em>change-the-key</em> 
     *  methods. In order to let the client refer to keys on the priority queue,
     *  an integer between {@code 0} and {@code maxN - 1}
     *  is associated with each key???the client uses this integer to specify
     *  which key to delete or change.
     *  It also supports methods for peeking at the minimum key,
     *  testing if the priority queue is empty, and iterating through
     *  the keys.
     *  <p>
     *  This implementation uses a binary heap along with an array to associate
     *  keys with integers in the given range.
     *  The <em>insert</em>, <em>delete-the-minimum</em>, <em>delete</em>,
     *  <em>change-key</em>, <em>decrease-key</em>, and <em>increase-key</em>
     *  operations take &Theta;(log <em>n</em>) time in the worst case,
     *  where <em>n</em> is the number of elements in the priority queue.
     *  Construction takes time proportional to the specified capacity.
     *  <p>
     *  For additional documentation, see
     *  <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
     *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
     *
     *  @author Robert Sedgewick
     *  @author Kevin Wayne
     *
     *  @param <Key> the generic type of key on this priority queue
     */
    private static class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
        private int maxN;        // maximum number of elements on PQ
        private int n;           // number of elements on PQ
        private int[] pq;        // binary heap using 1-based indexing
        private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
        private Key[] keys;      // keys[i] = priority of i

        /**
         * Initializes an empty indexed priority queue with indices between {@code 0}
         * and {@code maxN - 1}.
         * @param  maxN the keys on this priority queue are index from {@code 0}
         *         {@code maxN - 1}
         * @throws IllegalArgumentException if {@code maxN < 0}
         */
        public IndexMinPQ(int maxN) {
            if (maxN < 0) throw new IllegalArgumentException();
            this.maxN = maxN;
            n = 0;
            keys = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
            pq   = new int[maxN + 1];
            qp   = new int[maxN + 1];                   // make this of length maxN??
            for (int i = 0; i <= maxN; i++)
                qp[i] = -1;
        }

        /**
         * Returns true if this priority queue is empty.
         *
         * @return {@code true} if this priority queue is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return n == 0;
        }

        /**
         * Is {@code i} an index on this priority queue?
         *
         * @param  i an index
         * @return {@code true} if {@code i} is an index on this priority queue;
         *         {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         */
        public boolean contains(int i) {
            validateIndex(i);
            return qp[i] != -1;
        }

        /**
         * Returns the number of keys on this priority queue.
         *
         * @return the number of keys on this priority queue
         */
        public int size() {
            return n;
        }

        /**
         * Associates key with index {@code i}.
         *
         * @param  i an index
         * @param  key the key to associate with index {@code i}
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws IllegalArgumentException if there already is an item associated
         *         with index {@code i}
         */
        public void insert(int i, Key key) {
            validateIndex(i);
            if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
            n++;
            qp[i] = n;
            pq[n] = i;
            keys[i] = key;
            swim(n);
        }

        /**
         * Returns an index associated with a minimum key.
         *
         * @return an index associated with a minimum key
         * @throws NoSuchElementException if this priority queue is empty
         */
        public int minIndex() {
            if (n == 0) throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }

        /**
         * Returns a minimum key.
         *
         * @return a minimum key
         * @throws NoSuchElementException if this priority queue is empty
         */
        public Key minKey() {
            if (n == 0) throw new NoSuchElementException("Priority queue underflow");
            return keys[pq[1]];
        }

        /**
         * Removes a minimum key and returns its associated index.
         * @return an index associated with a minimum key
         * @throws NoSuchElementException if this priority queue is empty
         */
        public int delMin() {
            if (n == 0) throw new NoSuchElementException("Priority queue underflow");
            int min = pq[1];
            exch(1, n--);
            sink(1);
            assert min == pq[n+1];
            qp[min] = -1;        // delete
            keys[min] = null;    // to help with garbage collection
            pq[n+1] = -1;        // not needed
            return min;
        }

        /**
         * Returns the key associated with index {@code i}.
         *
         * @param  i the index of the key to return
         * @return the key associated with index {@code i}
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws NoSuchElementException no key is associated with index {@code i}
         */
        public Key keyOf(int i) {
            validateIndex(i);
            if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
            else return keys[i];
        }

        /**
         * Change the key associated with index {@code i} to the specified value.
         *
         * @param  i the index of the key to change
         * @param  key change the key associated with index {@code i} to this key
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws NoSuchElementException no key is associated with index {@code i}
         */
        public void changeKey(int i, Key key) {
            validateIndex(i);
            if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
            keys[i] = key;
            swim(qp[i]);
            sink(qp[i]);
        }

        /**
         * Change the key associated with index {@code i} to the specified value.
         *
         * @param  i the index of the key to change
         * @param  key change the key associated with index {@code i} to this key
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @deprecated Replaced by {@code changeKey(int, Key)}.
         */
        @Deprecated
        public void change(int i, Key key) {
            changeKey(i, key);
        }

        /**
         * Decrease the key associated with index {@code i} to the specified value.
         *
         * @param  i the index of the key to decrease
         * @param  key decrease the key associated with index {@code i} to this key
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws IllegalArgumentException if {@code key >= keyOf(i)}
         * @throws NoSuchElementException no key is associated with index {@code i}
         */
        public void decreaseKey(int i, Key key) {
            validateIndex(i);
            if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) == 0)
                throw new IllegalArgumentException("Calling decreaseKey() with a key equal to the key in the priority queue");
            if (keys[i].compareTo(key) < 0)
                throw new IllegalArgumentException("Calling decreaseKey() with a key strictly greater than the key in the priority queue");
            keys[i] = key;
            swim(qp[i]);
        }

        /**
         * Increase the key associated with index {@code i} to the specified value.
         *
         * @param  i the index of the key to increase
         * @param  key increase the key associated with index {@code i} to this key
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws IllegalArgumentException if {@code key <= keyOf(i)}
         * @throws NoSuchElementException no key is associated with index {@code i}
         */
        public void increaseKey(int i, Key key) {
            validateIndex(i);
            if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) == 0)
                throw new IllegalArgumentException("Calling increaseKey() with a key equal to the key in the priority queue");
            if (keys[i].compareTo(key) > 0)
                throw new IllegalArgumentException("Calling increaseKey() with a key strictly less than the key in the priority queue");
            keys[i] = key;
            sink(qp[i]);
        }

        /**
         * Remove the key associated with index {@code i}.
         *
         * @param  i the index of the key to remove
         * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
         * @throws NoSuchElementException no key is associated with index {@code i}
         */
        public void delete(int i) {
            validateIndex(i);
            if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
            int index = qp[i];
            exch(index, n--);
            swim(index);
            sink(index);
            keys[i] = null;
            qp[i] = -1;
        }

        // throw an IllegalArgumentException if i is an invalid index
        private void validateIndex(int i) {
            if (i < 0) throw new IllegalArgumentException("index is negative: " + i);
            if (i >= maxN) throw new IllegalArgumentException("index >= capacity: " + i);
        }

       /***************************************************************************
        * General helper functions.
        ***************************************************************************/
        private boolean greater(int i, int j) {
            return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
        }

        private void exch(int i, int j) {
            int swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
            qp[pq[i]] = i;
            qp[pq[j]] = j;
        }


       /***************************************************************************
        * Heap helper functions.
        ***************************************************************************/
        private void swim(int k) {
            while (k > 1 && greater(k/2, k)) {
                exch(k, k/2);
                k = k/2;
            }
        }

        private void sink(int k) {
            while (2*k <= n) {
                int j = 2*k;
                if (j < n && greater(j, j+1)) j++;
                if (!greater(k, j)) break;
                exch(k, j);
                k = j;
            }
        }


       /***************************************************************************
        * Iterators.
        ***************************************************************************/

        /**
         * Returns an iterator that iterates over the keys on the
         * priority queue in ascending order.
         * The iterator doesn't implement {@code remove()} since it's optional.
         *
         * @return an iterator that iterates over the keys in ascending order
         */
        public Iterator<Integer> iterator() { return new HeapIterator(); }

        private class HeapIterator implements Iterator<Integer> {
            // create a new pq
            private IndexMinPQ<Key> copy;

            // add all elements to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                copy = new IndexMinPQ<Key>(pq.length - 1);
                for (int i = 1; i <= n; i++)
                    copy.insert(pq[i], keys[pq[i]]);
            }

            public boolean hasNext()  { return !copy.isEmpty();                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                return copy.delMin();
            }
        }
    }
}
