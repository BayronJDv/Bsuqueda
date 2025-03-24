
package Controlador;

import Modelo.Laberinto_1;
import Modelo.nodo;
import Vista.Gui;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controlador {
    static Gui gui;
    static Laberinto_1 lab;
    
    public static void iniciar(){
        
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
            Laberinto_1 lab = new Laberinto_1();
            lab.leertxt(selectedFile);
            lab.imprimirMatriz();
            Gui interfaz = new Gui();
            interfaz.pintarlab(lab);
            
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
    
    public static void aplicarbfs(){
        System.out.println("hello ");
    }
}
