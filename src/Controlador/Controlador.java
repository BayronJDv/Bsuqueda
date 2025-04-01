package Controlador;

import Modelo.Laberinto_1;
import Modelo.nodo;
import Vista.Gui;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase Controlador que implementa el patrón MVC (Modelo-Vista-Controlador).
 * Se encarga de coordinar la interacción entre el modelo (Laberinto_1) y la vista (GUI).
 * 
 * Funcionalidades principales:
 * - Inicialización del programa y carga del laberinto desde archivo
 * - Ejecución de algoritmos de búsqueda (BFS, UCS, etc.)
 * - Comunicación de resultados a la interfaz gráfica
 * - Gestión de la visualización de rutas encontradas
 * 
 * Esta clase actúa como intermediario, procesando las acciones del usuario 
 * desde la GUI y actualizando el modelo y la vista según corresponda.
 */

public class Controlador {
    static Gui gui;
    static Laberinto_1 miLab;
    
    public static void iniciar(){
        
        miLab = new Laberinto_1();

        JOptionPane.showMessageDialog(
                null,
                "Por favor, seleccione el archivo del laberinto (formato .txt).",
                "Selección de archivo",
                JOptionPane.INFORMATION_MESSAGE
        );
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            miLab.leertxt(selectedFile);
            miLab.imprimirMatriz();
            Gui interfaz = new Gui();
            interfaz.pintarlab(miLab);
            
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }
    
    public static List<String> aplicarBFS(JTextArea area){ // busqueda por amplitud
        area.setText("");
        nodo ultimo = miLab.aplicarBFS();
        if (ultimo != null){
           miLab.recuprarruta(ultimo);
           List<String> cordenadas = miLab.coordenadasRuta();
           System.out.println("\nCoordenadas en orden:");
           area.append("Coordenadas obtenidas:");
           for (String coordenada : cordenadas) {
                System.out.println(coordenada);
                area.append("\n"+coordenada);
                }
           area.append("""
                       
                        el algoritmo se ejecuto de maneara correcta 
                        puede visualizar la ruta """);
           return cordenadas;
           }else{
           System.out.println("No se encontró ruta, está mal.");
           area.append("No se encontro la ruta; debe haber algun error");
           return null;
        }
    }
    public static List<String> aplicarUCS(JTextArea area){ // busqueda por costo 
        area.setText("");
        nodo ultimo = miLab.aplicarUCS();
        if (ultimo != null){
           miLab.recuprarruta(ultimo);
           List<String> cordenadas = miLab.coordenadasRuta();
           System.out.println("\nCoordenadas en orden:");
           area.append("Coordenadas obtenidas:");
           for (String coordenada : cordenadas) {
                System.out.println(coordenada);
                area.append("\n"+coordenada);
                }
           area.append("""
                       
                        el algoritmo se ejecuto de maneara correcta 
                        puede visualizar la ruta """);
           return cordenadas;
           }else{
           System.out.println("No se encontró ruta, está mal.");
           area.append("No se encontro la ruta; debe haber algun error");
           return null;
        }
    }

    public static List<String> aplicarDFS(JTextArea area){ // busqueda por profundidad
        area.setText("");
        nodo ultimo = miLab.aplicarDFS();
        if (ultimo != null){
           miLab.recuprarruta(ultimo);
           List<String> cordenadas = miLab.coordenadasRuta();
           System.out.println("\nCoordenadas en orden:");
           area.append("Coordenadas obtenidas:");
           for (String coordenada : cordenadas) {
                System.out.println(coordenada);
                area.append("\n"+coordenada);
                }
           area.append("""
                       
                        el algoritmo se ejecuto de maneara correcta 
                        puede visualizar la ruta """);
           return cordenadas;
           }else{
           System.out.println("No se encontró ruta, está mal.");
           area.append("No se encontro la ruta; debe haber algun error");
           return null;
        }
    }
}
