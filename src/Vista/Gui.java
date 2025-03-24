
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
import Controlador.Controlador;

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
    String[] tipo = {"Informada", "No Informada"};
    String[] algoritmosNI = {"B x Amplitud", "B x Profundidad", "B x Csoto"};
    String[] algoritmosI = {"Avara", "A*"};
    
    JPanel mapa;JPanel panelControl;JPanel panelnorte;JPanel panelsur;
    JButton botonBuscar;JButton botonrecorrido;
    
    JComboBox<String> comboTipo;
    JComboBox<String> comboAlgoritmo;
    
    public Gui(){
        
        setTitle("Busqueda");
        setSize(850,440);
        setLayout(new BorderLayout());
        crearGui();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    public void crearGui(){
        
        mapa = new JPanel(new GridLayout(10, 10));
        mapa.setPreferredSize(new Dimension(500, 500));
        
        //paneles de adorno 
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
        
        JLabel labelTipo = new JLabel("seleccione el tipo de busqueda");
        JLabel labelAlgoritmo = new JLabel("Selecciona un tipo algoritmo:");
        
  
        
        comboTipo = new JComboBox<>(tipo);
        comboTipo.setMaximumSize(new Dimension(200, 100)); 
        comboAlgoritmo = new JComboBox<>(algoritmosI);
        comboAlgoritmo.setMaximumSize(new Dimension(200, 100)); 
        botonBuscar = new JButton("aplicar algoritmo");
        JLabel areaLabel = new JLabel("estado de la busqueda");
        JTextArea areaTexto = new JTextArea(8, 4);
        areaTexto.setText("un vez ejecute un algoritmo el estado de la busqueda aparecera aqui :D");
        botonrecorrido = new JButton("Mostrar recorrido");botonrecorrido.setEnabled(false);
        areaTexto.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        
        //eventos 
        Eventos e = new Eventos();
        botonBuscar.addActionListener(e);
        comboAlgoritmo.addActionListener(e);
        comboTipo.addActionListener(e);
       
        
        panelControl.add(labelTipo);
        panelControl.add(comboTipo);
        panelControl.add(labelAlgoritmo);
        panelControl.add(comboAlgoritmo);
        panelControl.add(botonBuscar);
        panelControl.add(areaLabel);
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
     class Eventos implements ActionListener{
    
         @Override
         public void actionPerformed(ActionEvent e) {
             if (e.getSource() == comboTipo) {
                 comboAlgoritmo.removeAllItems();  // Asegurar que se limpian las opciones previas

                 String tipoAlgo = (String) comboTipo.getSelectedItem();
                 String[] opciones = tipoAlgo.equals("No Informada") ? algoritmosNI : algoritmosI;

                 for (String elemento : opciones) {
                     comboAlgoritmo.addItem(elemento);
                 }

                 comboAlgoritmo.setSelectedIndex(0);  // Seleccionar el primer elemento por defecto
             }
             if (e.getSource() == botonBuscar) {
                 String eleccion = (String) comboAlgoritmo.getSelectedItem();
                 switch (eleccion) {
                     case "B x Profundidad" ->
                         Controlador.aplicarbfs();
                     default ->
                         throw new AssertionError();
                 }
             }
         }
    
}
    
}


