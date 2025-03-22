package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Laberinto_1 {

    int filas = 10;
    int columnas = 10;
    int[][] casillas = new int[10][10];
    public nodo inicial = null;
    int totalpaq = 0;
    public List<String> coordenadas = new ArrayList<>();
    Set<String> visitados =new HashSet<>();
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

    public nodo aplicarbfs() {
        
        Queue<nodo> cola = new LinkedList<>();
        visitados.add(inicial.x + "," + inicial.y);
        for (String visit : visitados){
            System.out.println(visit);
        }
        nodo raiz = new nodo(inicial.x, inicial.y, null,0, casillas, visitados); // Pasamos el laberinto inicial
        raiz.getInfo();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            nodo actual = cola.poll();

            // Si este nodo ha recogido todos los paquetes, retornar
            if (actual.laberinto[actual.x][actual.y] == 4) {

                actual.paquetescolectados++;// el nodo marca que recolecto un paquete 
                actual.laberinto[actual.x][actual.y] = 0; // el nodo cambia su estado 
                actual.visitados.remove(actual.padre.x + "," + actual.padre.y);// si recojo un paquete elimino el nodo del que vengo para poder deolverme 

            }
            // antes de expandirse verifica si es la meta 
            if (actual.paquetescolectados == this.totalpaq) {
                return actual;
            }
            
            actual.expandirse(cola);
            
        }
               
        return null;
    }

    public void recuprarruta(nodo ultimo) {
        if (ultimo.padre == null) {
            // Caso base: llegamos al inicio de la ruta
            coordenadas.add("Inicio: " + ultimo.x + "," + ultimo.y + " (Paquetes: " + ultimo.paquetescolectados + ")");
        } else {
            // Caso recursivo: imprimir y guardar el paso actual
            coordenadas.add("Paso: (" + ultimo.x + "," + ultimo.y + ") (Paquetes: " + ultimo.paquetescolectados + ")");
            recuprarruta(ultimo.padre); // Llamada recursiva
        }
    }


}