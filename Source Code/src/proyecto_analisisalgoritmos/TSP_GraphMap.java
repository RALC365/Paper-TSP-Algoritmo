package proyecto_analisisalgoritmos;

/**
 *
 * @author Crysthel && Richardson && César
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;
 
class TSP_GraphMap extends JPanel {

    boolean limitFlag = false;
    boolean actualResult = false;
    Vector Position;
    Vector Horizontal;
    Vector Vertical;
    int CoorX;
    int CoorY;
    int TempY;
    Image Buffer;
    Graphics2D Gra2D;
    int floatPoints = 0;
    Point[] drawPoint;
    String actualWay;
    
    TSP_GraphMap() {
        this.setBackground(Color.black);
        drawPoint = new Point[100];
        Position = new Vector();
        Vertical = new Vector();
        Horizontal = new Vector();
        this.setBorder(BorderFactory.createBevelBorder(1));

        this.addMouseMotionListener(new MouseMotionAdapter() { //agregamos el metodo para hacer que el mouse se mueva y setee la posicion
            public void mouseMoved(MouseEvent e) {
                CoorX = e.getX() / 20;
                //set coordenada
                TempY = e.getY();
                //temporal
                CoorY = ((TempY - getHeight()) * -1);
                //Set coordenada
                repaint(); //pintamos segun la posicion en el mapa
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!limitFlag) {
                    if (!((CoorX == 24) || (CoorY / 20 == 24))) {
                        drawPoint[floatPoints++] = e.getPoint();
                        Position.add("(" + e.getX() / 20 + "," + CoorY / 20 + ")");
                        //Agregamos posiciones del mouse
                        Horizontal.add(e.getX() / 20);
                        Vertical.add(CoorY / 20);
                        repaint();//re pintamos
                        //validamos que los puntos no sean mayores a 10
                        /*if (floatPoints >= 10) { //si es mayor a 9 entonces manda un mensaje de error
                            limitFlag = true;
                            JOptionPane.showMessageDialog(null, "Solo se pueden agregar como mucho 9 vertices", "Validacion: Has llegado al numero maximo de vertices", JOptionPane.INFORMATION_MESSAGE);
                        }//fin del if
                        */
                    }
                }
            }
        });
    }
    
    public void PaintMap(Graphics2D Graphic) {
        //Con esto mejoraremos la calidad de la pintada de los puntos
        Graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphic.setColor(Color.BLACK);
        //Pintamos el fondo negro
        Graphic.fillRect(0, 0, getWidth(), getHeight());
        Graphic.setColor(Color.GREEN);
        //pintamos lineas verdes
        for (int i = 0; i < 100; i += 4) {
            Graphic.drawLine(5 * i, 500, 5 * i, 0);
            Graphic.drawLine(0, 5 * i, 500, 5 * i);
        }

        Graphic.setColor(Color.WHITE);
        Graphic.setFont(new Font("Arial", Font.BOLD, 12));
        Graphic.drawString("(" + CoorX + "," + CoorY / 20 + ")", CoorX * 20 + 2, TempY);

        if (this.actualResult) {
            this.PrintResult(Graphic);
        }

        for (int i = 0; i < floatPoints; i++) {
            Graphic.setColor(Color.WHITE); //Dibujamos el circulo para el grafico
            //asignamos color
            Graphic.fillOval(drawPoint[i].x - 8, drawPoint[i].y - 8, 18, 18);
            Graphic.setColor(Color.RED);
            //Numero correspondiente al numero de vertice
            Graphic.drawString("" + (i + 1), (drawPoint[i].x - 8) + 9 / 2, (drawPoint[i].y - 9) + 15);
            //pintamos
            Graphic.setColor(Color.YELLOW);
            //Colocamos las coordenadas que tendra el vertice
            Graphic.drawString("" + Position.elementAt(i), drawPoint[i].x - 9, drawPoint[i].y - 18);
        }

    }

    public void paintComponent(Graphics g) {
        Buffer = this.createImage(this.getWidth(), this.getWidth());
        Gra2D = (Graphics2D) Buffer.getGraphics();
        PaintMap(Gra2D);
        g.drawImage(Buffer, 0, 0, this.getWidth(), this.getWidth(), this);
    }


    public void PrintResult(Graphics2D graphTemp) {//Dibujamos la arista entre dos vertices y su posicion
        int Temp1, Temp2;
        graphTemp.setColor(Color.RED);
        //Traemos por referencia el grafico y coloreamos las lineas para conectar los vertices y ponemos el numero sobre ella
        for (int i = 0; i < actualWay.length() - 1; i++) {
            Temp1 = Integer.parseInt("" + actualWay.substring(i, i + 1));
            Temp2 = Integer.parseInt("" + actualWay.substring(i + 1, i + 2));
            graphTemp.drawLine(drawPoint[Temp1 - 1].x, drawPoint[Temp1 - 1].y, drawPoint[Temp2 - 1].x, drawPoint[Temp2 - 1].y);
            graphTemp.drawString("" + (i + 1), (int) ((((drawPoint[Temp1 - 1].x + drawPoint[Temp2 - 1].x) / 2) + drawPoint[Temp1 - 1].x) / 2), (int) ((((drawPoint[Temp1 - 1].y + drawPoint[Temp2 - 1].y) / 2) + drawPoint[Temp1 - 1].y) / 2));
        }
    }

    public int getWay() {
        //Devolvemos el camino de recorrido de los puntos flotantes
        return this.floatPoints;
    }

    public int getHorizontal(int Num) {
        //Recibimos la posicion horizontal del vertice
        return Integer.parseInt("" + Horizontal.elementAt(Num));
    }

    public int getVertical(int Num) {
        //Devolvemos la posicion vertical del vertice solicitado
        return Integer.parseInt("" + Vertical.elementAt(Num));
    }

    public void setResult(String way) {
        //Camino que actualmente se evalua, se setea y se recibe
        this.actualResult = true;
        this.actualWay = way;
        repaint();

    }

    public void update(Graphics g) {
        paint(g);
    }
}
