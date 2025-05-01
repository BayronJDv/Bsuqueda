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

public class Gui extends JFrame {
    ImageIcon[] imagenes = {
        new ImageIcon(getClass().getResource("/Vista/images/libre.png")),
        new ImageIcon(getClass().getResource("/Vista/images/muro.png")),
        new ImageIcon(getClass().getResource("/Vista/images/dron.jpg")),
        new ImageIcon(getClass().getResource("/Vista/images/campo.png")),
        new ImageIcon(getClass().getResource("/Vista/images/paquete.png")),
        new ImageIcon(getClass().getResource("/Vista/images/amarillo.png"))
    };

    String[] tipo = {"Informada", "No Informada"};
    String[] algoritmosNI = {"B x Amplitud", "B x Profundidad", "B x Costo"};
    String[] algoritmosI = {"Avara", "A*"};

    List<String> ultimaRuta = new ArrayList<>();

    JPanel mapa, panelControl, panelnorte, panelsur;
    JButton botonBuscar, botonrecorrido;

    JComboBox<String> comboTipo;
    JComboBox<String> comboAlgoritmo;

    JLabel[][] labels;

    JTextArea areaTexto;

    public Gui() {
        setTitle("Busqueda");
        setSize(850, 440);
        setLayout(new BorderLayout());
        crearGui();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JTextArea getArea() {
        return areaTexto;
    }

    public void crearGui() {
        mapa = new JPanel(new GridLayout(10, 10));
        mapa.setPreferredSize(new Dimension(500, 500));

        labels = new JLabel[10][10];

        panelnorte = new JPanel();
        panelnorte.setBackground(Color.DARK_GRAY);
        panelnorte.setPreferredSize(new Dimension(500, 50));

        panelsur = new JPanel();
        panelsur.setBackground(Color.DARK_GRAY);
        panelsur.setPreferredSize(new Dimension(500, 50));

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
        areaTexto.setText("una vez ejecute un algoritmo el estado de la búsqueda aparecerá aquí :D");
        botonrecorrido = new JButton("Mostrar recorrido");
        botonrecorrido.setEnabled(false);
        areaTexto.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(areaTexto);

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

        add(panelnorte, BorderLayout.NORTH);
        add(mapa, BorderLayout.CENTER);
        add(panelControl, BorderLayout.EAST);
        add(panelsur, BorderLayout.SOUTH);
    }

    public void pintarlab(Laberinto_1 lab) {
        mapa.removeAll();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int valor = lab.getLab()[i][j];
                if (valor >= 0 && valor < imagenes.length) {
                    Image img = imagenes[valor].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    labels[i][j] = new JLabel(new ImageIcon(img));
                } else {
                    labels[i][j] = new JLabel("Error");
                }
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mapa.add(labels[i][j]);
            }
        }
        mapa.revalidate();
        mapa.repaint();
        pack();
    }

    public void mostrarruta() {
        new Thread(() -> {
            for (int i = 0; i < ultimaRuta.size() - 1; i++) {
                String[] actual = ultimaRuta.get(i).split(",");
                int x1 = Integer.parseInt(actual[0]);
                int y1 = Integer.parseInt(actual[1]);

                String[] siguiente = ultimaRuta.get(i + 1).split(",");
                int x2 = Integer.parseInt(siguiente[0]);
                int y2 = Integer.parseInt(siguiente[1]);

                Image imgAmarillo = imagenes[5].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                labels[x1][y1].setIcon(new ImageIcon(imgAmarillo));

                Image imgdron = imagenes[2].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                labels[x2][y2].setIcon(new ImageIcon(imgdron));

                mapa.revalidate();
                mapa.repaint();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class Eventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == comboTipo) {
                comboAlgoritmo.removeAllItems();
                String tipoAlgo = (String) comboTipo.getSelectedItem();
                String[] opciones = tipoAlgo.equals("No Informada") ? algoritmosNI : algoritmosI;
                for (String elemento : opciones) {
                    comboAlgoritmo.addItem(elemento);
                }
                comboAlgoritmo.setSelectedIndex(1);
            }
            if (e.getSource() == botonBuscar) {
                String eleccion = (String) comboAlgoritmo.getSelectedItem();
                if (ultimaRuta != null) ultimaRuta.clear();
                switch (eleccion) {
                    case "B x Amplitud" -> {
                        ultimaRuta = Controlador.aplicarBFS(areaTexto);
                        botonrecorrido.setEnabled(true);
                    }
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
                    default -> throw new AssertionError();
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
