package sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    private static Socket socketTCP;
    private static ServerSocket socketServidor;
    private static String ip = "127.0.0.1";
    private static int puertoServidor = 5000;
    private static int puertoRecepcion = 9090;


    public static void conectarCliente() throws IOException {
        socketTCP = new Socket(ip, puertoServidor);
    }

    public static void mantenerConexionCliente() throws IOException {
        socketServidor = new ServerSocket(puertoRecepcion);
    }

    public static String recibirMensaje() throws IOException {
        socketTCP = socketServidor.accept();
        DataInputStream streamInput = new DataInputStream(socketTCP.getInputStream());
        String mensajeRecibido = streamInput.readUTF();
        return mensajeRecibido;
    }

    public static void enviarMensaje(Object mensaje) throws IOException {
        ObjectOutputStream streamOut = new ObjectOutputStream(socketTCP.getOutputStream());
        streamOut.writeObject(mensaje);
        streamOut.close();
    }

    public static void desconectarCliente() throws IOException {
        socketTCP.close();
    }
}
