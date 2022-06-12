package dev.volcent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int port = 3000;
    public static String host = "localhost";

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port: " + port);
            while (true) {
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("Connection accepted!");
                    ConnectionHandler connectionHandler = new ConnectionHandler(client);
                    new Thread(connectionHandler).start();
                } catch (IOException ex) {
                    System.err.println("Inner server: " + ex.getMessage());
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Outer server: " + ex.getMessage());
        }
    }

    public static void main(String[] argv) {
        Server server = new Server();
        server.startServer();
    }
}
