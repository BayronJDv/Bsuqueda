package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.AbstractMap;

/**
 * Clase que representa un laberinto para un problema de búsqueda de paquetes.
 * 
 * Esta clase implementa la estructura y lógica del laberinto donde:
 * - El laberinto es una matriz 10x10 donde cada celda puede contener:
 *   0: Casilla libre
 *   1: Muro
 *   2: Posición inicial del dron
 *   3: Campo electromagnético
 *   4: Paquete a recolectar
 * 
 * Funcionalidades principales:
 * - Lectura del laberinto desde archivo de texto
 * - Manejo del estado del laberinto y posiciones
 * - Implementación de algoritmos de búsqueda (BFS, DFS, etc.)
 * - Seguimiento de paquetes recolectados
 * - Recuperación de rutas encontradas
 * 
 * La clase trabaja en conjunto con la clase Nodo para mantener
 * los estados de búsqueda y generar las soluciones al problema
 * de recolección de paquetes.
 */

public class Laberinto_1 {

    int filas = 10;
    int columnas = 10;
    int[][] casillas = new int[10][10];
    public nodo inicial = null;
    int totalpaq = 0;
    public List<String> coordenadas = new ArrayList<>();
    Set<String> visitados =new HashSet<>();
    
    // Constructor de la clase Laberinto_1
    public Laberinto_1() {
        this.filas = 10;
        this.columnas = 10;
        this.casillas = new int[filas][columnas];
        this.totalpaq = 0;
        this.coordenadas = new ArrayList<>();
        this.visitados = new HashSet<>();
        this.inicial = null;
    }

    // Constructor de copia
    public Laberinto_1(Laberinto_1 original) {
        this.filas = original.filas;
        this.columnas = original.columnas;
        this.casillas = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(original.casillas[i], 0, this.casillas[i], 0, columnas);
        }
        this.totalpaq = original.totalpaq;
        this.coordenadas = new ArrayList<>(original.coordenadas);
        this.visitados = new HashSet<>(original.visitados);
        // Si necesitas copiar el nodo inicial, hazlo aquí (opcional)
        this.inicial = original.inicial; // O haz una copia profunda si es necesario
    }
    
    // metodo que devuelbe el laberinto 
    public int[][] getLab() {
        return this.casillas;
    }

    public List<String> coordenadasRuta() {
        Collections.reverse(coordenadas); // Invertir la lista
        return coordenadas;
    }

    // Método para leer el archivo txt
    public void leertxt(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            for (int i = 0; i < filas; i++) {
                String[] valores = br.readLine().split(" ");
                for (int j = 0; j < columnas; j++) {
                    this.casillas[i][j] = Integer.parseInt(valores[j]);
                    if (casillas[i][j] == 2) {
                        this.inicial = new nodo(i, j, null, 0, this.casillas, this.visitados);
                    }
                    if (casillas[i][j] == 4) {
                        this.totalpaq += 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Fallo al leer el txt: " + e);
        }
    }

    // Método para imprimir la matriz en pantalla y su informacion 
    public void imprimirMatriz() {
        for (int[] casilla : casillas) {
            for (int j = 0; j < casilla.length; j++) {
                System.out.print(casilla[j] + " ");
            }
            System.out.println(); // Nueva línea después de cada fila
        }
        System.out.println("la posicion inicial del dron es :  x = " + inicial.x + " y = " + inicial.y);
        System.out.println("el numero total de paquetes es : " + totalpaq);
    }

    public AbstractMap.SimpleEntry<nodo, Integer> aplicarBFS() {
        
        Queue<nodo> cola = new LinkedList<>();
        visitados.add(inicial.x + ":" + inicial.y);
        for (String visit : visitados){
            System.out.println(visit);
        }
        nodo raiz = new nodo(inicial.x, inicial.y, null,0, casillas, visitados); // Pasamos el laberinto inicial
        raiz.getInfo();
        cola.add(raiz);
        int expand = 0;
        while (!cola.isEmpty()) {
            nodo actual = cola.poll();

            // Si este nodo ha recogido todos los paquetes, retornar
            if (actual.laberinto[actual.x][actual.y] == 4) {

                actual.paquetescolectados++;// el nodo marca que recolecto un paquete 
                actual.laberinto[actual.x][actual.y] = 0; // el nodo cambia su estado 
                actual.visitados.remove(actual.padre.x + ":" + actual.padre.y);// si recojo un paquete elimino el nodo del que vengo para poder deolverme 

            }
            // antes de expandirse verifica si es la meta 
            if (actual.paquetescolectados == this.totalpaq) {
                return new AbstractMap.SimpleEntry<>(actual, expand);
            }
            expand +=1;
            actual.expandirse(cola);
            
        }
               
        return null;
    }

    public AbstractMap.SimpleEntry<nodo, Integer> aplicarUCS() {

        PriorityQueue<nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo));
        visitados.add(inicial.x + ":" + inicial.y);
        for (String visit : visitados){
            System.out.println(visit);
        }
        nodo raiz = new nodo(inicial.x, inicial.y, null,0, casillas, visitados); // Pasamos el laberinto inicial
        raiz.getInfo();
        cola.add(raiz);
        int expand = 0;
        while (!cola.isEmpty()) {
            nodo actual = cola.poll();

            // Si este nodo ha recogido todos los paquetes, retornar
            if (actual.laberinto[actual.x][actual.y] == 4) {
                actual.paquetescolectados++; // el nodo marca que recolecto un paquete 
                actual.laberinto[actual.x][actual.y] = 0; // el nodo cambia su estado 
                actual.visitados.remove(actual.padre.x + ":" + actual.padre.y); // si recojo un paquete elimino el nodo del que vengo para poder deolverme 
            }
            
            // antes de expandirse verifica si es la meta 
            if (actual.paquetescolectados == totalpaq) {
                return new AbstractMap.SimpleEntry<>(actual, expand);
            }
            expand +=1;
            actual.expandirse(cola);
        }
               
        return null;
    }
    public AbstractMap.SimpleEntry<nodo, Integer> aplicarDFS() {
        Stack<nodo> pila = new Stack<>();
        visitados.add(inicial.x + ":" + inicial.y);
        for (String visit : visitados){
            System.out.println(visit);
        }
        nodo raiz = new nodo(inicial.x, inicial.y, null, 0, casillas, visitados);
        raiz.getInfo();
        pila.push(raiz);
        int expand = 0;
        while (!pila.isEmpty()) {
            nodo actual = pila.pop();

            if (actual.laberinto[actual.x][actual.y] == 4) {
                actual.paquetescolectados++; // el nodo marca que recolecto un paquete 
                actual.laberinto[actual.x][actual.y] = 0; // el nodo cambia su estado 
                actual.visitados.remove(actual.padre.x + ":" + actual.padre.y); // si recojo un paquete elimino el nodo del que vengo para poder devolverme 
            }
            
            // antes de expandirse verifica si es la meta 
            if (actual.paquetescolectados == totalpaq) {
                return new AbstractMap.SimpleEntry<>(actual, expand);
            }
            expand +=1;
            actual.expandirseDFS(pila);
        }
               
        return null;
    }

    // Método para aplicar el algoritmo de búsqueda avar (GBFS)
    public AbstractMap.SimpleEntry<nodo, Integer> aplicarGBFS() {
        PriorityQueue<nodo> cola = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.calcularHeuristica(totalpaq))
        );
    
        visitados.add(inicial.x + ":" + inicial.y);
        nodo raiz = new nodo(inicial.x, inicial.y, null, 0, casillas, new HashSet<>(visitados));
        raiz.getInfo();
        cola.add(raiz);
        int expand = 0;
        while (!cola.isEmpty()) {
            nodo actual = cola.poll();
    
            // Si este nodo ha recogido todos los paquetes, retornar 0
            if (actual.laberinto[actual.x][actual.y] == 4) {
                actual.paquetescolectados++;
                actual.laberinto[actual.x][actual.y] = 0;
    
                if (actual.padre != null) {
                    actual.visitados.remove(actual.padre.x + ":" + actual.padre.y);
                }
            }
    
            if (actual.paquetescolectados == totalpaq) {
                return new AbstractMap.SimpleEntry<>(actual, expand);
            }
            expand +=1;
            actual.expandirse(cola);
        }
    
        return null;
    }
    
    public AbstractMap.SimpleEntry<nodo, Integer> aplicarASTAR() {
        PriorityQueue<nodo> cola = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.calcularHeuristica(totalpaq) + n.costo)
        );
    
        visitados.add(inicial.x + ":" + inicial.y);
        nodo raiz = new nodo(inicial.x, inicial.y, null, 0, casillas, new HashSet<>(visitados));
        raiz.getInfo();
        cola.add(raiz);
        int expand = 0;
        while (!cola.isEmpty()) {
            nodo actual = cola.poll();
    
            // Si este nodo ha recogido todos los paquetes, retornar 0
            if (actual.laberinto[actual.x][actual.y] == 4) {
                actual.paquetescolectados++;
                actual.laberinto[actual.x][actual.y] = 0;
    
                if (actual.padre != null) {
                    actual.visitados.remove(actual.padre.x + ":" + actual.padre.y);
                }
            }
    
            if (actual.paquetescolectados == totalpaq) {
                return new AbstractMap.SimpleEntry<>(actual, expand);
            }
            //Cantidad nodos expandidos
            expand +=1;
            actual.expandirse(cola);
        }
    
        return null;
    }
    
    public void recuprarruta(nodo ultimo) {
        if (ultimo.padre == null) {
            coordenadas.add(ultimo.x + "," + ultimo.y );
        } else {
            coordenadas.add(ultimo.x + "," + ultimo.y );
            recuprarruta(ultimo.padre); 
        }
    }


}