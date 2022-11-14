package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    private static Socket socketTCP = null;
    private static String ip = "127.0.0.1";
    private static int puerto = 5000;

    public static void conectarCliente() throws IOException{
        Socket socketTCP = new Socket(ip,puerto);
    }
    public static void recibirMensaje() throws IOException{
        DataInputStream streamInput = new DataInputStream(socketTCP.getInputStream());
        String mensajeRecibido = streamInput.readUTF();
    }
    public static void enviarMensaje() throws IOException{
        DataOutputStream streamOut = new DataOutputStream(socketTCP.getOutputStream());
        streamOut.writeUTF("sockets.Cliente: conectado con servidor");
    }
    public static void desconectarCliente() throws IOException{
        socketTCP.close();
    }

    public static void main (String[] args){;

        try {
            //Inicio cliente
            conectarCliente();
            //Mensaje enviado a servidor
            enviarMensaje();
            //Mensaje recibido del servidor
            recibirMensaje();
            //Desconexion del servidor
            desconectarCliente();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
