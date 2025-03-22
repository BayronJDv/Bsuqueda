

package Modelo;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class nodo {
    
    int x, y;
    nodo padre;
    int paquetescolectados;
    int[][] laberinto; 
    Set<String> visitados;

    public nodo(int x, int y, nodo padre,int recolec, int[][] lab ,Set<String> visitados) {
        
        this.x = x;
        this.y = y;
        this.padre = padre;
        this.paquetescolectados = recolec;
        this.laberinto = lab.clone();
        this.visitados = visitados;
    }
    
    public void getInfo(){
        System.out.println("informacion del nodo ");
        System.out.println("coordenadas de encuentro");
        System.out.println(this.x+","+this.y);
        
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
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        
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
