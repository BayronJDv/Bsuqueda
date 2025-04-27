package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Modelo.Laberinto_1;
import java.awt.GridLayout;
import java.awt.Image;
import Controlador.Controlador;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal de la interfaz gráfica que implementa la visualización del laberinto.
 * Extiende de JFrame para crear la ventana principal de la aplicación.
 * 
 * Funcionalidades principales:
 * - Visualización del laberinto con sus diferentes elementos (muros, dron, paquetes, etc.)
 * - Panel de control para selección de algoritmos de búsqueda
 * - Animación del recorrido del dron
 * - Visualización de resultados y coordenadas
 * 
 * La clase actúa como vista en el patrón MVC, mostrando el estado del laberinto
 * y permitiendo al usuario interactuar con el sistema a través de la interfaz gráfica.
 * Se comunica con el Controlador para ejecutar las acciones seleccionadas por el usuario.
 */

public class Gui extends JFrame{
    String[] imagenes = {
    "src/Vista/images/libre.png",
    "src/Vista/images/muro.png", 
    "src/Vista/images/dron.jpg",
    "src/Vista/images/campo.png",
    "src/Vista/images/paquete.png",
    "src/Vista/images/amarillo.png",
};
    String[] tipo = {"Informada", "No Informada"};
    String[] algoritmosNI = {"B x Amplitud", "B x Profundidad", "B x Costo"};
    String[] algoritmosI = {"Avara", "A*"};
    
    List<String> ultimaRuta = new ArrayList<>();
    
    JPanel mapa;JPanel panelControl;JPanel panelnorte;JPanel panelsur;
    JButton botonBuscar;JButton botonrecorrido;
    
    JComboBox<String> comboTipo;
    JComboBox<String> comboAlgoritmo;
    
    JLabel[][] labels;
    
    JTextArea areaTexto;
    public Gui(){
        
        setTitle("Busqueda");
        setSize(850,440);
        setLayout(new BorderLayout());
        crearGui();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    public  JTextArea getArea (){
        return  areaTexto; 
   }
    
    public void crearGui(){
        
        mapa = new JPanel(new GridLayout(10, 10));
        mapa.setPreferredSize(new Dimension(500, 500));
        
        //labels del laberinto
        labels = new JLabel[10][10];
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
        panelControl.setPreferredSize(new Dimension(250, 500));
        
        JLabel labelTipo = new JLabel("seleccione el tipo de busqueda");
        JLabel labelAlgoritmo = new JLabel("Selecciona un tipo algoritmo:");
        
        
        comboTipo = new JComboBox<>(tipo);
        comboTipo.setMaximumSize(new Dimension(200, 100)); 
        comboAlgoritmo = new JComboBox<>(algoritmosI);
        comboAlgoritmo.setMaximumSize(new Dimension(200, 100)); 
        botonBuscar = new JButton("aplicar algoritmo");
        JLabel areaLabel = new JLabel("estado de la busqueda");
        areaTexto = new JTextArea(8, 4);
        areaTexto.setText("un vez ejecute un algoritmo el estado de la busqueda aparecera aqui :D");
        botonrecorrido = new JButton("Mostrar recorrido");botonrecorrido.setEnabled(false);
        areaTexto.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        
        //eventos 
        Eventos e = new Eventos();
        botonBuscar.addActionListener(e);
        botonrecorrido.addActionListener(e);
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

// Método para cargar el laberinto en la interfaz
    public void pintarlab(Laberinto_1 lab) {
        // Primero eliminar componentes anteriores
        mapa.removeAll();

        // Recorremos el laberinto y lo mostramos en el grid
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int valor = lab.getLab()[i][j];

                // Verificar que el valor está dentro del rango del array de imágenes
                if (valor >= 0 && valor < imagenes.length) {
                    ImageIcon iconoOriginal = new ImageIcon(imagenes[valor]);
                    Image img = iconoOriginal.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    labels[i][j] = new JLabel(new ImageIcon(img));
                } else {
                    labels[i][j] = new JLabel("Error"); // En caso de valor fuera de rango
                }

                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mapa.add(labels[i][j]); // Agregar la celda al panel
            }
        }

        // Refrescar la interfaz
        mapa.revalidate();
        mapa.repaint();
        pack();
    }
    
    
    public void mostrarruta() {
        new Thread(() -> {
            for (int i = 0; i < ultimaRuta.size() - 1; i++) {
                // Elemento actual (n)
                String[] actual = ultimaRuta.get(i).split(",");
                int x1 = Integer.parseInt(actual[0]);
                int y1 = Integer.parseInt(actual[1]);

                // Elemento siguiente (n+1)
                String[] siguiente = ultimaRuta.get(i + 1).split(",");
                int x2 = Integer.parseInt(siguiente[0]);
                int y2 = Integer.parseInt(siguiente[1]);

                // Pintar la casilla anterior de amarillo
                ImageIcon iconoAmarillo = new ImageIcon(imagenes[5]); // índice 5 para amarillo
                Image imgAmarillo = iconoAmarillo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                labels[x1][y1].setIcon(new ImageIcon(imgAmarillo));

                // Poner el dron en la nueva casilla
                ImageIcon iconodron = new ImageIcon(imagenes[2]);
                Image imgdron = iconodron.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                labels[x2][y2].setIcon(new ImageIcon(imgdron));

                // Refrescar la interfaz
                mapa.revalidate();
                mapa.repaint();

                // Pausa para aparentar una animacion 
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

                 comboAlgoritmo.setSelectedIndex(1);  // Seleccionar el primer elemento por defecto
             }
             if (e.getSource() == botonBuscar) {
                 String eleccion = (String) comboAlgoritmo.getSelectedItem();
                 if (ultimaRuta != null ) {ultimaRuta.clear();}
                 switch (eleccion) {
                     case "B x Amplitud" ->{
                         ultimaRuta = Controlador.aplicarBFS(areaTexto);
                         botonrecorrido.setEnabled(true);}
                     case "B x Profundidad" -> {
                         ultimaRuta = Controlador.aplicarDFS(areaTexto);
                         botonrecorrido.setEnabled(true);
                     }
                     case "B x Costo" -> {
                         ultimaRuta = Controlador.aplicarUCS(areaTexto);
                         botonrecorrido.setEnabled(true);
                     }
                     case "Avara" -> {
                         ultimaRuta = Controlador.aplicarAvara(areaTexto);
                         botonrecorrido.setEnabled(true);
                     }
                     case "A*" -> {
                         ultimaRuta = Controlador.aplicarAstar(areaTexto);
                         botonrecorrido.setEnabled(true);
                     }
                     default ->
                         throw new AssertionError();
                 }
             }
             if (e.getSource() == botonrecorrido) {
                Laberinto_1 copiaInicial = new Laberinto_1(Controlador.laberintoInicial);
                pintarlab(copiaInicial);
                mostrarruta();
            }
             
             
             
             
         }
    
}
    
}