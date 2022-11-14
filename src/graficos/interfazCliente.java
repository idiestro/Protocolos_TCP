package graficos;

import javax.swing.*;
import java.awt.*;

public class interfazCliente {

    public static void main(String args[]){
        //Instanciamos la clase
        Ventana ventanaCliente = new Ventana();
        ventanaCliente.setVisible(true);

        ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Ventana extends JFrame{

    //Creamos constructor de la clase
    public Ventana(){
        //Ajustamos la resolución de la ventana al monitor del usuario
        Toolkit monitorHuesped = Toolkit.getDefaultToolkit();
        Dimension dimensionMonitor = monitorHuesped.getScreenSize();
        int alto = dimensionMonitor.height;
        int ancho = dimensionMonitor.width;

        //Personalizamos ventana
        setSize(ancho/2,alto/2);
        setLocation(ancho/4, alto/4);
        setTitle("Chat");
        Image iconoTitulo = monitorHuesped.getImage("src/graficos/Hogwarts-logo.png");
        setIconImage(iconoTitulo);

    }
}
