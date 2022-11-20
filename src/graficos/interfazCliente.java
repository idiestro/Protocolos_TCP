package graficos;

import sockets.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class interfazCliente {

    public static void main(String args[]){
            //Instanciamos la clase Ventana
            Ventana ventanaCliente = new Ventana();
            ventanaCliente.setVisible(true);
            ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Ventana extends JFrame {
    private static int alto;
    private static int ancho;
    private static Toolkit monitorUsuario;

    private void dimensionesMonitor(){
        //Obtenemos resolucion de pantalla usuario
        monitorUsuario = Toolkit.getDefaultToolkit();
        Dimension dimensionMonitor = monitorUsuario.getScreenSize();

        alto = dimensionMonitor.height;
        ancho = dimensionMonitor.width;
    }
    private void dimensionesVentana(){
        //Asignamos tamaño
        setSize(ancho/4,alto/2);
        setLocation(ancho/4, alto/4);
    }

    //Constructor de la clase Ventana
    public Ventana(){
        dimensionesMonitor();
        dimensionesVentana();

        //Asignamos título a la ventana
        setTitle("Chat");
        //Añadimos imagen como icono de la ventana
        Image iconoTitulo = monitorUsuario.getImage("src/graficos/Hogwarts-logo.png");
        setIconImage(iconoTitulo);

        //Instanciamos la clase Lámina
        Lamina miLamina = new Lamina();
        add(miLamina);
    }
}
class Lamina extends JPanel implements Runnable{

    private JTextField campoEscritura, nick;
    private JTextArea campoChat;
    private JButton BtnEnviar;
    private JLabel titulo;

    public Lamina() {
        titulo = new JLabel("Nick");
        add(titulo);

        nick = new JTextField(5);
        add(nick);

        campoChat = new JTextArea(15, 35);
        add(campoChat);

        campoEscritura = new JTextField(35);
        add(campoEscritura);

        BtnEnviar = new JButton("Enviar");

        EnviarMensaje enviar = new EnviarMensaje();
        BtnEnviar.addActionListener(enviar);
        add(BtnEnviar);

        Thread recepcion = new Thread(this);
            recepcion.start();

    }
    @Override
    public void run() {
        try {
            Cliente.mantenerConexionCliente();
                while(true){
                    campoChat.append("\n" + Cliente.recibirMensaje());
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Creamos eventos para interactuar con botones y campos
    class EnviarMensaje implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                try {
                    //Iniciamos conexion
                    Cliente.conectarCliente();
                    //Creamos el paquete que enviamos
                    PaqueteDatos mensaje = new PaqueteDatos();
                        mensaje.setNick(nick.getText());
                        mensaje.setMensaje(campoEscritura.getText());
                    Cliente.enviarMensaje(mensaje);
                    campoEscritura.setText("");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
}

