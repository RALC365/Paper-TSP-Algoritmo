/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Crysthel && Richardson && César
 */
public class TSP_GraphAgent extends JFrame implements Runnable {

    JButton btnStart; //Botones
    JButton btnOptimum; //Botones
    JButton btnGreedy; //Boton generar camino por algoritmo codicioso
    Container Content; //Panel de contenido
    TSP_GraphMap Map; //Mapa del los grafos
    TSP_AdjacencyMatrixBoard Board;
    Thread agentThread;
    TSP_GraphAgent Graph_Actual_Agent;
    JList Ways; //Lista de caminos posibles
    Vector Way; //Camino actual evaluado
    Vector WayGreedy;//Caminos evaluaos por el Greedy algorith
    JLabel Label1, Label2, Label3, Label4; //Viñetas para rotular
    JList List; //Lista de vertices
    JScrollPane Panel; //Panel para el contenido general
    JComboBox ComboVertex; //Lista de vertices para inicializar el recorrido
    int InitialVertex;
    int selectWay = -1;
    float[][] AdjacencyMatriz;
    boolean thread_way;


    TSP_GraphAgent() {
        
        Map = new TSP_GraphMap();
        Map.setBounds(10, 10, 200, 200);
        Ways = new JList();
        Way = new Vector();
        WayGreedy = new Vector();
        Content = this.getContentPane();
        Content.setLayout(null);
        Graph_Actual_Agent = this;
        Board = new TSP_AdjacencyMatrixBoard();
        Content.add(Map);
        Label1 = new JLabel("Vertice Inicial");
        Label2 = new JLabel("Matriz de Adyacencia");
        btnStart = new JButton("Generar Matriz de Adyacencia");
        btnOptimum = new JButton("Camino Optimizado");
        btnGreedy = new JButton("Camino Algoritmo Codiciso");
        thread_way = true;
        this.setTitle("Problema TSP_ Grafos");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String String = "";
                Way.clear();
                int Tam = getWay();
                String Vector[] = new String[Tam];
                for (int i = 0; i < Tam; i++) {
                    Vector[i] = "" + (i + 1);
                    ComboVertex.addItem(Vector[i]);
                }
                List.updateUI();
                List.setListData(Way);
                Panel.updateUI();
                btnStart.setVisible(false);
                btnOptimum.setVisible(true);
                btnGreedy.setVisible(true);
                Matrix(Tam);
                JOptionPane.showMessageDialog(null, "Se ha creado una matriz de adyacencia con distancias estandar y se medira dicha distancia");
                Board.setMatrix(AdjacencyMatriz);
                JOptionPane.showMessageDialog(null, "Selecciona el nodo inicial");
            }
        });
        List = new JList();
        Panel = new JScrollPane(List);
        //Modificacion de tamaños y posiciones de los elementos
        ComboVertex = new JComboBox();
        Label1.setBounds(211, 10, 100, 20);
        ComboVertex.setBounds(211, 30, 100, 20);
        btnStart.setBounds(211, 210, 200, 20);
        btnOptimum.setBounds(211, 210, 200, 20);
        btnOptimum.setVisible(false);
        btnGreedy.setBounds(211, 420, 200, 20);
        btnGreedy.setVisible(false);
        Label2.setBounds(11, 210, 200, 20);
        Panel.setBounds(211, 55, 100, 150);
        Board.setBounds(11, 240, 800 - 511, 160);
        //Agregamos los elementos al panel principal del contenido
        Content.add(Board);
        Content.add(ComboVertex);
        Content.add(Label1);
        Content.add(Label2);
        Content.add(btnStart);
        Content.add(btnOptimum);
        Content.add(btnGreedy);
        Content.add(Panel);
        //this.setSize(Content.getWidth() + 300, Content.getHeight());
        //Hacemos el clickAction activo para cualquier momento para evaluar la matriz de adyacencia
        btnOptimum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //double temp_milis = System.currentTimeMillis();
                int tempTam = getWay();
                InitialVertex = ComboVertex.getSelectedIndex() + 1;
                Way.clear();
                String Cad_Temp = "";
                
                double temp_milis = System.currentTimeMillis();
                //Comenzamos a analizar cada uno de los posibles caminos entre los vertices y calculamos el menor
                Next_Matrix_Way(ComboVertex.getSelectedIndex(), tempTam, 0, Cad_Temp);
                double Time_Milis_Res = System.currentTimeMillis() - temp_milis;
                
                List.updateUI();
                System.out.println("Tiempo de respuesta en milisegundos óptimo: " + Time_Milis_Res);
                JOptionPane.showMessageDialog(null, "Tiempo de respuesta en milisegundos " + Time_Milis_Res + ", A continuacion de mostrara como se calcularon los caminos");
                //Simulación de caminos
                thread_way = true; //sirve como bandera para simulacion
                agentThread = new Thread(Graph_Actual_Agent);
                agentThread.start();        }
        });
        
        //Hacemos el clickAction activo para cualquier momento para evaluar el Algoritmo Ocioso
        btnGreedy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //double temp_milis = System.currentTimeMillis();
                int tempTam = getWay();
                InitialVertex = ComboVertex.getSelectedIndex() + 1;
                Way.clear();
                String Cad_Temp = "";
                
                double temp_milis = System.currentTimeMillis();
                //Comenzamos a analizar cada uno de los posibles caminos entre los vertices y calculamos el menor
                greedyAlgorith();
                //Next_Matrix_Way(ComboVertex.getSelectedIndex(), tempTam, 0, Cad_Temp);
                //Operaciones de tiempo y actualicación del GUI
                double Time_Milis_Res = System.currentTimeMillis() - temp_milis;
                
                List.updateUI();
                System.out.println("Tiempo de respuesta en milisegundos de Ocioso: " + Time_Milis_Res);
                JOptionPane.showMessageDialog(null, "Tiempo de respuesta en milisegundos " + Time_Milis_Res + ", A continuacion de mostrara como se calcularon los caminos");
                thread_way = false; //sirve como bandera para simulacion
                agentThread = new Thread(Graph_Actual_Agent);
                agentThread.start();
            }
        });
        
    }
    
     public int getWay() {
        // Obtenemos el camino del Mapa
        return Map.getWay();
    }
     
     public float[][] getMatrixAdjacency(){
         //Obtenemos la matriz de adyacencia
         return this.AdjacencyMatriz;
     }

    public void ShowActualWay(String way) {
        //Actualizamos en el mapa el camino que actualmente evaluamos
        Map.setResult(way);
    }

    public void run() {
        if(thread_way){
        int i = 0;
        float shorterDistance = getValidationC("" + Way.elementAt(0)), menor_temp = 0;
        while (i < Way.size()) {
            try {
                String Cad_Temp = "";
                Cad_Temp = "" + Way.elementAt(i);
                menor_temp = getValidationC(Cad_Temp);
                ShowActualWay(Cad_Temp);
                Thread.sleep(100); //Se hace una espera de 1 milisegundo para mostrar el proceso del camino evaluado
                if (menor_temp <= shorterDistance) {
                    //Se compara los caminos actuales y el temporar y se evalua cual es menor, y el menor es seleccionado para convertirse en el nuevo temporal
                    selectWay = i;
                    shorterDistance = menor_temp;
                }
                i++;
            } catch (Exception ex) {
            }
        }
        ShowActualWay("" + Way.elementAt(selectWay));
        System.out.println("Distancia menor: " + shorterDistance + "\n" + "Ruta Optima a seguir por vertice: " + Way.elementAt(selectWay));
        JOptionPane.showMessageDialog(Board, "Distancia menor: " + shorterDistance + "\n" + "Ruta Optima a seguir por vertice: " + Way.elementAt(selectWay), "Ruta Optimizada TSP", 1);
        }else{
            for (Object caminos : WayGreedy) {
                try {
                    System.out.println(caminos);
                    ShowActualWay((String) caminos);
                    Thread.sleep(1000); //Se hace una espera de 1 milisegundo para mostrar el proceso del camino evaluado
                } catch (InterruptedException ex) {
                    System.out.println("¡CAGADAL!");
                    Logger.getLogger(TSP_GraphAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        

    }

    
    public void Matrix(int Num) {
        AdjacencyMatriz = new float[Num][Num];
        for (int k = 0; k < Num; k++) {
            for (int z = 0; z < Num; z++) {
                AdjacencyMatriz[k][z] = getDistance("" + k, "" + z);
                System.out.print(AdjacencyMatriz[k][z]+",");
            }
            System.out.print("\n");
        }
    }
    
     public float getDistance(String i, String j) {
        //Metodo para evaluar la distancia entre dos vertices seleccionados, recibimos
        //Como parametro el numero de vertice que comparamos
        int CoorA1, CoorB1, CoorA2, CoorB2;
        float ResultDistanceHo, ResultDistanceVe;
        //Consultamos en el mapa las coordenadas
        CoorA1 = Map.getHorizontal(Integer.parseInt(i));
        CoorB1 = Map.getHorizontal(Integer.parseInt(j));
        CoorA2 = Map.getVertical(Integer.parseInt(i));
        CoorB2 = Map.getVertical(Integer.parseInt(j));
        //Casteamos el resultado para hacerlo flotante y pormediamos un numero
        ResultDistanceHo = (float) Math.pow(CoorB1 - CoorA1, 2);
        //tanto en horizontal como vertical
        ResultDistanceVe = (float) Math.pow(CoorB2 - CoorA2, 2);
        float retorno = (float) Math.sqrt((ResultDistanceHo + ResultDistanceVe));
        return retorno;
    }

    public float getTotal() {
        String temporalCad;
        float currentMinorDistance = getValidationC("" + Way.elementAt(0));
        float stringTemporalDistance = 0;
        for (int i = 0; i < Way.size(); i++) {
            temporalCad = "" + Way.elementAt(i);
            stringTemporalDistance = getValidationC(temporalCad);
            if (temporalCad.startsWith("" + InitialVertex)) {
                if (stringTemporalDistance <= currentMinorDistance) {
                    selectWay = i;
                    currentMinorDistance = stringTemporalDistance;
                }
            } else {
                return currentMinorDistance;
            }
        }
        return currentMinorDistance;
    }

    public float getValidationC(String cad_temp) {
        //Validamos la cadena, retorna en numero de la cadena validado
        float return_num = 0;
        int numA = 0, numB = 0;
        for (int i = 0; i < cad_temp.length() - 1; i++) {
            numA = Integer.parseInt(cad_temp.substring(i, i + 1));
            numB = Integer.parseInt(cad_temp.substring(i + 1, i + 2));
            return_num = return_num + AdjacencyMatriz[numA - 1][numB - 1];
        }
        //return
        return return_num;
    }

    public void Next_Matrix_Way(int i, int n, int p, String Acum) {
        //metodo recursivo para evaluar los caminos en distancia
        //Se hacer recursivo para asegurarse de pasar por todos los vertices y agregar todos los caminos a la lista de caminos posibles
        if (p < n && i < n)// si se acabo un para o un contador
        {
            boolean no = false;
            for (int k = 0; k < Acum.length() && !no; k++)//si ya esta el numero del vertice
            {
                if (Acum.substring(k, k + 1).equals("" + (i + 1))) {
                    no = true;
                }
            }
            //Evalua los caminos posibles y analiza si son iguales, menores o mayores
            if (!no) {
                Next_Matrix_Way(0, n, p + 1, Acum + "" + (i + 1));
            }
            Next_Matrix_Way(i + 1, n, p, Acum);
            no = false;
            for (int k = 0; k < Acum.length() && !no; k++) {
                if (Acum.substring(k, k + 1).equals("" + (i + 1))) {
                    no = true;
                }
            }
            if (!no && Acum.length() == n - 1) {
                Acum = Acum + "" + (i + 1) + InitialVertex;
                if (Acum.startsWith("" + InitialVertex)) {
                    Way.add(Acum);
                } else {
                    return;
                }
            }
        }
    }
    
    public void greedyAlgorith(){
        //Strings que se mandaran para actualizar ver cómo funciona
        //el algoritmo mientras se ejecuta
        WayGreedy = new Vector();
        String greedyWay = "" + InitialVertex;
        float DistanciaTotal = 0;
        int id = 0;
        float less;
        int node = InitialVertex - 1;
        for (int i = 0; i < this.AdjacencyMatriz.length - 1; i++) {
            less = (float) 200;
            for (int j = 0; j < this.AdjacencyMatriz[node].length; j++) {
                WayGreedy.add(greedyWay + ""+ ((int)(j + 1)));
                if(!greedyWay.contains("" + (int)(j + 1))){
                    if(this.AdjacencyMatriz[node][j] < less){
                        less = this.AdjacencyMatriz[node][j];
                        id = j + 1;
                    }
                }
            }
            DistanciaTotal += this.AdjacencyMatriz[node][id - 1];
            node = id - 1;
            greedyWay += "" + (int)(id);
            WayGreedy.add(greedyWay);
        }
        greedyWay += "" + InitialVertex;//Hacemos que vuelva al primero en duro
        DistanciaTotal += this.AdjacencyMatriz[node][InitialVertex - 1];
        ShowActualWay(greedyWay);
        WayGreedy.add(greedyWay);
        System.out.println("Camino Algoritmo Codicioso: " + greedyWay);
        System.out.println("Distancia Algoritmo Codicioso " + DistanciaTotal);
    }
}
