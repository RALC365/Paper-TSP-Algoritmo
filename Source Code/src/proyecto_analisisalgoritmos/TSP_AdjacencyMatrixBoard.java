/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_analisisalgoritmos;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Crysthel && Richardson && César
 */
class TSP_AdjacencyMatrixBoard extends JPanel {
    //Variable global para Matriz de adyacencia

    JTextField[][] Matrix_Main;
    TSP_AdjacencyMatrixBoard() {

    }
    //Actualizamos la matriz de adyacencia para completar el panel visual y hacer los calculos correspondientes para las rutas

    public void setMatrix(float Matrix_Complete[][]) {
        this.removeAll();
        //Dibujamos la matriz en el panel principal
        Matrix_Main = new JTextField[Matrix_Complete.length][Matrix_Complete[0].length];
        this.setLayout(new GridLayout(Matrix_Complete.length, Matrix_Complete[0].length));
        for (int x = 0; x < Matrix_Complete.length; x++) {
            for (int y = 0; y < Matrix_Complete[0].length; y++) {
                //seteamos los valores
                Matrix_Main[x][y] = new JTextField();
                Matrix_Main[x][y].setEditable(false);
                Matrix_Main[x][y].setAutoscrolls(false);
                Matrix_Main[x][y].setFont((new Font("Arial", Font.BOLD, 10)));
                Matrix_Main[x][y].setText("" + Matrix_Complete[x][y]);
                Matrix_Main[x][y].setCaretPosition(0);
                add(Matrix_Main[x][y]);
                
            }
        }
        //Actualizamos el panel principal para que se muestre la matriz
        this.updateUI();

    }

}
