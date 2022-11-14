package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static ServerSocket servidorTCP = null;
    public static Socket socketTCP = null;
    public static int puerto = 5000;

    public static void conectarServidor() throws IOException{
        servidorTCP = new ServerSocket(puerto);
        System.out.println("----sockets.Servidor conectado----");
    }
    public static void recibirConexion() throws IOException{
        socketTCP = servidorTCP.accept();
    }
    public static void recibirMensaje() throws IOException{
        DataInputStream streamInput = new DataInputStream(socketTCP.getInputStream());
        String mensaje = streamInput.readUTF();
        System.out.println(mensaje);
    }
    public static void enviarMensaje() throws IOException{
        DataOutputStream streamOutput = new DataOutputStream(socketTCP.getOutputStream());
        streamOutput.writeUTF("sockets.Servidor: conexión con cliente");
    }
    public static void desconectarServidor() throws IOException{
        socketTCP.close();
        servidorTCP.close();
        System.out.println("----sockets.Servidor desconectado----");
    }

    public static void main(String[] args){
        try {
            conectarServidor();
            while (true){
                //Conexion de cliente
                recibirConexion();
                //Streams para envio y recepcion
                recibirMensaje();
                enviarMensaje();
                //Desconexion cliente-servidor
                desconectarServidor();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
