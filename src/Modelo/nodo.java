package Modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Clase que representa un nodo en el espacio de búsqueda del laberinto.
 * Cada nodo contiene información sobre:
 * - Su posición (x,y) en el laberinto
 * - Su nodo padre en el árbol de búsqueda
 * - Cantidad de paquetes recolectados hasta el momento
 * - Estado actual del laberinto 
 * - Conjunto de posiciones visitadas
 * - Costo acumulado del camino
 *
 * Esta clase es fundamental para los algoritmos de búsqueda (BFS, UCS, etc.)
 * ya que permite:
 * - Mantener el estado de la búsqueda
 * - Expandir nodos generando sucesores válidos
 * - Rastrear el camino desde el nodo inicial hasta la meta
 * - Calcular costos de movimiento (1 para casillas normales, 8 para campos electromagneticos)
 */

public class nodo {
    
    int x, y;
    nodo padre;
    int paquetescolectados;
    int[][] laberinto; 
    Set<String> visitados;
    int costo;
    int profundidad;
    
        public nodo(int x, int y, nodo padre,int recolec, int[][] lab ,Set<String> visitados) {
            
            this.x = x;
            this.y = y;
            this.padre = padre;
            this.paquetescolectados = recolec;
            this.laberinto = lab.clone();
            this.visitados = visitados;
            if(padre == null){
                this.costo = 0;
            }else{
                this.costo = (lab[x][y] == 3) ? padre.costo + 8 : padre.costo + 1;
            }
            this.profundidad = (padre == null) ? 0 : padre.profundidad + 1;
    }
    
    public void getInfo(){
        System.out.println("informacion del nodo ");
        System.out.println("coordenadas de encuentro");
        System.out.println(this.x+","+this.y);
        System.out.println("costo del acumulado: "+this.costo);
        System.out.println("profundidad: "+this.profundidad);
        System.out.println("paq recolec:"+this.paquetescolectados);
        imprimirMatriz();
        System.out.println("visitados");
        for (String vi : visitados ){
            System.out.println(vi);
        }
        System.out.println("==================================");
    }

    private boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10 && this.laberinto[x][y]!=1;
    }
    
    public void expandirse(Queue<nodo> cola){
       // derecha, izquierda, abajo, arriba 
       int[] dx = {0, 0, 1, -1};
       int[] dy = {1, -1, 0, 0};
        
        for (int d = 0; d < 4; d++){
            int nx = this.x+dx[d];
            int ny = this.y + dy[d];

            if (!this.visitados.contains(nx + ":" + ny) && esPosicionValida(nx, ny)) {

                Set<String> copiavistados = new HashSet<>(this.visitados);
                int[][] copiaLab = new int[10][10];
                for (int i = 0; i < 10; i++) {
                    System.arraycopy(this.laberinto[i], 0, copiaLab[i], 0, 10);
                }
                int copiarecolec = this.paquetescolectados;
                nodo nodoavisitar = new nodo(nx, ny, this,copiarecolec,copiaLab,copiavistados);
                nodoavisitar.visitados.add(nx+":"+ny);
                cola.add(nodoavisitar);
                System.out.println("se añadio el siguiente nodo ");
                nodoavisitar.getInfo();
            }
        }
    }

    public void expandirseDFS(Stack<nodo> pila){
       // derecha, izquierda, abajo, arriba 
       int[] dx = {0, 0, 1, -1};
       int[] dy = {1, -1, 0, 0};
        
        for (int d = 0; d < 4; d++){
            int nx = this.x+dx[d];
            int ny = this.y + dy[d];

            if (!this.visitados.contains(nx + ":" + ny) && esPosicionValida(nx, ny)) {

                Set<String> copiavistados = new HashSet<>(this.visitados);
                int[][] copiaLab = new int[10][10];
                for (int i = 0; i < 10; i++) {
                    System.arraycopy(this.laberinto[i], 0, copiaLab[i], 0, 10);
                }
                int copiarecolec = this.paquetescolectados;
                nodo nodoavisitar = new nodo(nx, ny, this,copiarecolec,copiaLab,copiavistados);
                nodoavisitar.visitados.add(nx+":"+ny);
                pila.push(nodoavisitar);
                System.out.println("se añadio el siguiente nodo ");
                nodoavisitar.getInfo();
            }
        }
    }
    
    public int calcularHeuristica(int totalPaquetes) {
        // Si ya se recolectaron todos los paquetes, retorna 0
        if (this.paquetescolectados == totalPaquetes) return 0;
        
        // Lista para almacenar coordenadas de los paquetes
        List<int[]> paquetes = new ArrayList<>();
        
        // Recorre todo el laberinto buscando paquetes
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[0].length; j++) {
                if (laberinto[i][j] == 4) {
                    paquetes.add(new int[]{i, j});
                }
            }
        }
        
        if (paquetes.isEmpty()) return 0;
        
        // Agregar la posición actual del agente como punto de inicio
        List<int[]> nodosTotal = new ArrayList<>();
        nodosTotal.add(new int[]{this.x, this.y});
        nodosTotal.addAll(paquetes);
        
        int costoTotal = 0;
        boolean[] visitado = new boolean[nodosTotal.size()];
        
        visitado[0] = true;
        int nodoActual = 0;
        
        // Mientras no hayamos visitado todos los nodos
        for (int count = 1; count < nodosTotal.size(); count++) {
            int distanciaMin = Integer.MAX_VALUE;
            int siguienteNodo = -1;
            
            // Buscar el nodo no visitado más cercano al nodo actual
            for (int i = 0; i < nodosTotal.size(); i++) {
                if (!visitado[i]) {
                    int distancia = Math.abs(nodosTotal.get(nodoActual)[0] - nodosTotal.get(i)[0]) + 
                                   Math.abs(nodosTotal.get(nodoActual)[1] - nodosTotal.get(i)[1]);
                    
                    if (distancia < distanciaMin) {
                        distanciaMin = distancia;
                        siguienteNodo = i;
                    }
                }
            }
            
            // Sumar la distancia mínima al costo total
            costoTotal += distanciaMin;
            
            visitado[siguienteNodo] = true;
            nodoActual = siguienteNodo;
        }
        
        return costoTotal;
    }

    public void imprimirMatriz() {
        System.out.println("matriz del nodo :");
        for (int[] laberinto1 : laberinto) {
            for (int j = 0; j < laberinto1.length; j++) {
                System.out.print(laberinto1[j] + " ");
            }
            System.out.println(); // Nueva línea después de cada fila
        }

    }
    
}
