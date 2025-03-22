
package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.Border;
import Modelo.Laberinto_1;
import java.awt.GridLayout;
import java.awt.Image;

/**
 *
 * @author HUAWEI
 */
public class Gui extends JFrame{
    
    String[] imagenes = {
    "src/Vista/images/libre.png",
    "src/Vista/images/muro.png", 
    "src/Vista/images/dron.jpg",
    "src/Vista/images/campo.png",
    "src/Vista/images/paquete.png"
};

    JPanel mapa;JPanel panelControl;JPanel panelnorte;JPanel panelsur;
    
    public Gui(){
        
        setTitle("Busqueda");
        setSize(850,440);
        setLayout(new BorderLayout());
        crearGui();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.DARK_GRAY);
        
    }
    
    public void crearGui(){
        
        mapa = new JPanel(new GridLayout(10, 10));
        mapa.setPreferredSize(new Dimension(500, 500));
        
        
        panelnorte = new JPanel();
        panelnorte.setBackground(Color.DARK_GRAY);
        panelnorte.setPreferredSize(new Dimension(500,50));
        
        panelsur = new JPanel();
        panelsur.setBackground(Color.DARK_GRAY);
        panelsur.setPreferredSize(new Dimension(500,50));
        // panel de control 
        panelControl = new JPanel();
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));
        panelControl.setPreferredSize(new Dimension(200, 400));
        
        JLabel labelAlgoritmo = new JLabel("Selecciona algoritmo:");
        String[] algoritmos = {"B x Amplitud", "B x Profundidad", "A*"};
        JComboBox<String> comboAlgoritmo = new JComboBox<>(algoritmos);
        comboAlgoritmo.setMaximumSize(new Dimension(200, 100)); 
        JButton botonBuscar = new JButton("aplicar algoritmo");
        JTextArea areaTexto = new JTextArea(8, 4);
        JButton botonrecorrido = new JButton("Mostrar recorrido");
        areaTexto.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        
        panelControl.add(labelAlgoritmo);
        panelControl.add(comboAlgoritmo);
        panelControl.add(botonBuscar);
        panelControl.add(scrollTexto);
        panelControl.add(botonrecorrido);
        
        add(panelnorte,BorderLayout.NORTH);
        add(mapa, BorderLayout.CENTER);
        add(panelControl, BorderLayout.EAST);
        add(panelsur, BorderLayout.SOUTH);
    }
    public void pintarlab(Laberinto_1 lab){
        // Primero eliminar componentes anteriores
        mapa.removeAll();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int valor = lab.getLab()[i][j];
                // Verificar que el valor está dentro del rango del array de imágenes
                if (valor >= 0 && valor < imagenes.length) {
                    ImageIcon iconoOriginal = new ImageIcon(imagenes[valor]);
                    Image img = iconoOriginal.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    JLabel label = new JLabel(new ImageIcon(img));
                    
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mapa.add(label);
                } else {
                    // En caso de valor fuera de rango, mostrar un label vacío o con texto
                    JLabel label = new JLabel("Error");
                    mapa.add(label);
                }
            }
        }

        mapa.revalidate();
        mapa.repaint();
        pack(); 
    }
}
