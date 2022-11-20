package sockets;

import graficos.PaqueteDatos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Servidor extends Thread {

    public static ServerSocket servidorTCP;
    public static Socket socketTCP;
    public static int puerto = 5000;
    public static int puertoEnvio = 9090;
    public static String mensajeEnviado;


    public static void conectarServidor() throws IOException{
        servidorTCP = new ServerSocket(puerto);
        System.out.println("----Servidor conectado----");
    }
    public static void recibirConexion() throws IOException{
        socketTCP = servidorTCP.accept();
    }
    public static void recibirMensaje() throws IOException, ClassNotFoundException {
        String nick, mensaje;
        ObjectInputStream streamInput = new ObjectInputStream(socketTCP.getInputStream());
        PaqueteDatos mensajeRecibido = (PaqueteDatos) streamInput.readObject();

        //Obtenemos componentes del paquete
        nick = mensajeRecibido.getNick();
        mensaje = mensajeRecibido.getMensaje();
        mensajeEnviado = (nick + ": " + mensaje);
    }

    public static void enviarMensaje() throws IOException{
        socketTCP = new Socket("localhost", 9090);
        DataOutputStream streamOutput = new DataOutputStream(socketTCP.getOutputStream());
        streamOutput.writeUTF(mensajeEnviado);
        streamOutput.close();
    }
    public static void desconectarServidor() throws IOException{
        socketTCP.close();
        servidorTCP.close();
        System.out.println("----Servidor desconectado----");
    }
    public void run(){
        try {
            conectarServidor();

            while (true){
                //Conexion de cliente
                recibirConexion();
                //Streams para envio y recepcion
                recibirMensaje();
                enviarMensaje();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        Servidor servidor = new Servidor();
        servidor.start();
    }
}
