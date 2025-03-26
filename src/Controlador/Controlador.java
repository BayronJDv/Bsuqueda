
package Controlador;

import Modelo.Laberinto_1;
import Modelo.nodo;
import Vista.Gui;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

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
            
            /*
            nodo ultimo = lab.aplicarbfs();
            if (ultimo != null) {
                lab.recuprarruta(ultimo);
                List<String> coordenadas = lab.coordenadasRuta();
                System.out.println("\nCoordenadas en orden:");
                for (String coordenada : coordenadas) {
                    System.out.println(coordenada);
                }
                lab.imprimirMatriz();
            } else {
                System.out.println("No se encontró ruta, está mal.");
            */
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }
    
    public static List<String> aplicarbfs(JTextArea area){
        area.setText("");
        nodo ultimo = miLab.aplicarbfs();
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
